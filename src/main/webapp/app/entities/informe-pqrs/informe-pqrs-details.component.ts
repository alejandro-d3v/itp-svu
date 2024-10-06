import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import InformePqrsService from './informe-pqrs.service';
import { useDateFormat } from '@/shared/composables';
import { type IInformePqrs } from '@/shared/model/informe-pqrs.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InformePqrsDetails',
  setup() {
    const dateFormat = useDateFormat();
    const informePqrsService = inject('informePqrsService', () => new InformePqrsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const informePqrs: Ref<IInformePqrs> = ref({});

    const retrieveInformePqrs = async informePqrsId => {
      try {
        const res = await informePqrsService().find(informePqrsId);
        informePqrs.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.informePqrsId) {
      retrieveInformePqrs(route.params.informePqrsId);
    }

    return {
      ...dateFormat,
      alertService,
      informePqrs,

      previousState,
      t$: useI18n().t,
    };
  },
});
