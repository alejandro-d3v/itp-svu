import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OficinaService from './oficina.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IOficina, Oficina } from '@/shared/model/oficina.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OficinaUpdate',
  setup() {
    const oficinaService = inject('oficinaService', () => new OficinaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const oficina: Ref<IOficina> = ref(new Oficina());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nombre: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      descripcion: {},
      nivel: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      oficinaSuperior: {},
    };
    const v$ = useVuelidate(validationRules, oficina as any);
    v$.value.$validate();

    return {
      oficinaService,
      alertService,
      oficina,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.oficina.id) {
        this.oficinaService()
          .update(this.oficina)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ventanillaUnicaApp.oficina.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.oficinaService()
          .create(this.oficina)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ventanillaUnicaApp.oficina.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
