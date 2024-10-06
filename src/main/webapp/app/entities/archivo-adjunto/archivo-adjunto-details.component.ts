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

    return {
      ...dateFormat,
      alertService,
      archivoAdjunto,

      previousState,
      t$: useI18n().t,
    };
  },
});
