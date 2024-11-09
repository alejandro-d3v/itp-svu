import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ArchivoAdjuntoService from './archivo-adjunto.service';
import { useDateFormat } from '@/shared/composables';
import { type IArchivoAdjunto } from '@/shared/model/archivo-adjunto.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArchivoAdjuntoDetails',
  setup() {
    const dateFormat = useDateFormat();
    const archivoAdjuntoService = inject('archivoAdjuntoService', () => new ArchivoAdjuntoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const archivoAdjunto: Ref<IArchivoAdjunto> = ref({});

    const retrieveArchivoAdjunto = async archivoAdjuntoId => {
      try {
        const res = await archivoAdjuntoService().find(archivoAdjuntoId);
        archivoAdjunto.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.archivoAdjuntoId) {
      retrieveArchivoAdjunto(route.params.archivoAdjuntoId);
    }

    const downloadFile = async () => {
      if (archivoAdjunto.value.id) {
        const blob = await archivoAdjuntoService().downloadFile(archivoAdjunto.value.id);

        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');

        link.href = url;
        link.setAttribute('download', archivoAdjunto.value.nombre || 'archivo');

        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
        URL.revokeObjectURL(url);
      }
    };

    return {
      ...dateFormat,
      alertService,
      archivoAdjunto,

      downloadFile,
      previousState,
      t$: useI18n().t,
    };
  },
});
