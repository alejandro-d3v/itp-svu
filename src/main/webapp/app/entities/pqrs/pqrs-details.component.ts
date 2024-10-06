import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PqrsService from './pqrs.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat } from '@/shared/composables';
import { type IPqrs } from '@/shared/model/pqrs.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PqrsDetails',
  setup() {
    const dateFormat = useDateFormat();
    const pqrsService = inject('pqrsService', () => new PqrsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const pqrs: Ref<IPqrs> = ref({});

    const retrievePqrs = async pqrsId => {
      try {
        const res = await pqrsService().find(pqrsId);
        pqrs.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.pqrsId) {
      retrievePqrs(route.params.pqrsId);
    }

    return {
      ...dateFormat,
      alertService,
      pqrs,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
