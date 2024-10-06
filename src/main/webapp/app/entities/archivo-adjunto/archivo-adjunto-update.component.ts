import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ArchivoAdjuntoService from './archivo-adjunto.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PqrsService from '@/entities/pqrs/pqrs.service';
import { type IPqrs } from '@/shared/model/pqrs.model';
import RespuestaService from '@/entities/respuesta/respuesta.service';
import { type IRespuesta } from '@/shared/model/respuesta.model';
import { ArchivoAdjunto, type IArchivoAdjunto } from '@/shared/model/archivo-adjunto.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArchivoAdjuntoUpdate',
  setup() {
    const archivoAdjuntoService = inject('archivoAdjuntoService', () => new ArchivoAdjuntoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const archivoAdjunto: Ref<IArchivoAdjunto> = ref(new ArchivoAdjunto());

    const pqrsService = inject('pqrsService', () => new PqrsService());

    const pqrs: Ref<IPqrs[]> = ref([]);

    const respuestaService = inject('respuestaService', () => new RespuestaService());

    const respuestas: Ref<IRespuesta[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveArchivoAdjunto = async archivoAdjuntoId => {
      try {
        const res = await archivoAdjuntoService().find(archivoAdjuntoId);
        res.fechaSubida = new Date(res.fechaSubida);
        archivoAdjunto.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.archivoAdjuntoId) {
      retrieveArchivoAdjunto(route.params.archivoAdjuntoId);
    }

    const initRelationships = () => {
      pqrsService()
        .retrieve()
        .then(res => {
          pqrs.value = res.data;
        });
      respuestaService()
        .retrieve()
        .then(res => {
          respuestas.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nombre: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      tipo: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      urlArchivo: {},
      fechaSubida: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      pqrs: {},
      respuesta: {},
    };
    const v$ = useVuelidate(validationRules, archivoAdjunto as any);
    v$.value.$validate();

    return {
      archivoAdjuntoService,
      alertService,
      archivoAdjunto,
      previousState,
      isSaving,
      currentLanguage,
      pqrs,
      respuestas,
      v$,
      ...useDateFormat({ entityRef: archivoAdjunto }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.archivoAdjunto.id) {
        this.archivoAdjuntoService()
          .update(this.archivoAdjunto)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ventanillaUnicaApp.archivoAdjunto.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.archivoAdjuntoService()
          .create(this.archivoAdjunto)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ventanillaUnicaApp.archivoAdjunto.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
