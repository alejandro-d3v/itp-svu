import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import NotificacionService from './notificacion.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat } from '@/shared/composables';
import { type INotificacion } from '@/shared/model/notificacion.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'NotificacionDetails',
  setup() {
    const dateFormat = useDateFormat();
    const notificacionService = inject('notificacionService', () => new NotificacionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const notificacion: Ref<INotificacion> = ref({});

    const retrieveNotificacion = async notificacionId => {
      try {
        const res = await notificacionService().find(notificacionId);
        notificacion.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.notificacionId) {
      retrieveNotificacion(route.params.notificacionId);
    }

    return {
      ...dateFormat,
      alertService,
      notificacion,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
