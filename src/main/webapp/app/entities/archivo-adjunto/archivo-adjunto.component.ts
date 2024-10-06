import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ArchivoAdjuntoService from './archivo-adjunto.service';
import { type IArchivoAdjunto } from '@/shared/model/archivo-adjunto.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArchivoAdjunto',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const archivoAdjuntoService = inject('archivoAdjuntoService', () => new ArchivoAdjuntoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const archivoAdjuntos: Ref<IArchivoAdjunto[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveArchivoAdjuntos = async () => {
      isFetching.value = true;
      try {
        const res = await archivoAdjuntoService().retrieve();
        archivoAdjuntos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveArchivoAdjuntos();
    };

    onMounted(async () => {
      await retrieveArchivoAdjuntos();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IArchivoAdjunto) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeArchivoAdjunto = async () => {
      try {
        await archivoAdjuntoService().delete(removeId.value);
        const message = t$('ventanillaUnicaApp.archivoAdjunto.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveArchivoAdjuntos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      archivoAdjuntos,
      handleSyncList,
      isFetching,
      retrieveArchivoAdjuntos,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeArchivoAdjunto,
      t$,
    };
  },
});