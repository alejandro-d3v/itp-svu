import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OficinaService from './oficina.service';
import { type IOficina } from '@/shared/model/oficina.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OficinaDetails',
  setup() {
    const oficinaService = inject('oficinaService', () => new OficinaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const oficina: Ref<IOficina> = ref({});

    const retrieveOficina = async oficinaId => {
      try {
        const res = await oficinaService().find(oficinaId);
        oficina.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.oficinaId) {
      retrieveOficina(route.params.oficinaId);
    }

    return {
      alertService,
      oficina,

      previousState,
      t$: useI18n().t,
    };
  },
});
