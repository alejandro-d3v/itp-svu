import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import NotificacionService from './notificacion.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OficinaService from '@/entities/oficina/oficina.service';
import { type IOficina } from '@/shared/model/oficina.model';
import { type INotificacion, Notificacion } from '@/shared/model/notificacion.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'NotificacionUpdate',
  setup() {
    const notificacionService = inject('notificacionService', () => new NotificacionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const notificacion: Ref<INotificacion> = ref(new Notificacion());

    const oficinaService = inject('oficinaService', () => new OficinaService());

    const oficinas: Ref<IOficina[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveNotificacion = async notificacionId => {
      try {
        const res = await notificacionService().find(notificacionId);
        res.fecha = new Date(res.fecha);
        notificacion.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.notificacionId) {
      retrieveNotificacion(route.params.notificacionId);
    }

    const initRelationships = () => {
      oficinaService()
        .retrieve()
        .then(res => {
          oficinas.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      tipo: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      fecha: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      mensaje: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      leido: {},
      destinatarios: {},
    };
    const v$ = useVuelidate(validationRules, notificacion as any);
    v$.value.$validate();

    return {
      notificacionService,
      alertService,
      notificacion,
      previousState,
      isSaving,
      currentLanguage,
      oficinas,
      ...dataUtils,
      v$,
      ...useDateFormat({ entityRef: notificacion }),
      t$,
    };
  },
  created(): void {
    this.notificacion.destinatarios = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.notificacion.id) {
        this.notificacionService()
          .update(this.notificacion)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ventanillaUnicaApp.notificacion.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.notificacionService()
          .create(this.notificacion)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ventanillaUnicaApp.notificacion.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option, pkField = 'id'): any {
      if (selectedVals) {
        return selectedVals.find(value => option[pkField] === value[pkField]) ?? option;
      }
      return option;
    },
  },
});
