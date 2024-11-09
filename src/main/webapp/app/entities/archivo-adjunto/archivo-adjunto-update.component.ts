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

    const retrieveArchivoAdjunto = async (archivoAdjuntoId: string) => {
      try {
        const res = await archivoAdjuntoService().find(archivoAdjuntoId);
        res.fechaSubida = new Date(res.fechaSubida ?? '');
        archivoAdjunto.value = res;
      } catch (error: any) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.archivoAdjuntoId) {
      retrieveArchivoAdjunto(route.params.archivoAdjuntoId as string);
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
      file: { required: validations.required(t$('entity.validation.required').toString()) },

      nombre: {},
      tipo: {},
      urlArchivo: {},
      fechaSubida: {},

      pqrs: {},
      respuesta: {},
    };
    const v$ = useVuelidate(validationRules, archivoAdjunto as any);
    v$.value.$validate();

    const onFileChange = (event: any) => {
      const file = event.target.files[0];
      if (file) {
        v$.value.file.$model = file;
        archivoAdjunto.value.file = file;
        archivoAdjunto.value.nombre = file.name;
        archivoAdjunto.value.tipo = file.type;
      }
    };

    const save = (): void => {
      isSaving.value = true;

      const formData = new FormData();

      if (archivoAdjunto.value.file) {
        formData.append('file', archivoAdjunto.value.file as Blob);
      }
      if (archivoAdjunto.value.nombre) {
        formData.append('nombre', archivoAdjunto.value.nombre);
      }
      if (archivoAdjunto.value.tipo) {
        formData.append('tipo', archivoAdjunto.value.tipo);
      }
      if (archivoAdjunto.value.urlArchivo) {
        formData.append('urlArchivo', archivoAdjunto.value.urlArchivo);
      }
      if (archivoAdjunto.value.pqrs?.id) {
        formData.append('pqrsId', archivoAdjunto.value.pqrs.id);
      }
      if (archivoAdjunto.value.respuesta?.id) {
        formData.append('respuestaId', archivoAdjunto.value.respuesta.id);
      }

      if (archivoAdjunto.value.id) {
        archivoAdjuntoService()
          .update(formData, archivoAdjunto.value.id)
          .then(param => {
            isSaving.value = false;
            previousState();
            alertService.showInfo(t$('ventanillaUnicaApp.archivoAdjunto.updated', { param: param.id }));
          })
          .catch(error => {
            isSaving.value = false;
            alertService.showHttpError(error.response);
          });
      } else {
        archivoAdjuntoService()
          .create(formData)
          .then(param => {
            isSaving.value = false;
            previousState();
            alertService.showSuccess(t$('ventanillaUnicaApp.archivoAdjunto.created', { param: param.id }).toString());
          })
          .catch(error => {
            isSaving.value = false;
            alertService.showHttpError(error.response);
          });
      }
    };

    return {
      archivoAdjuntoService,
      alertService,
      archivoAdjunto,
      previousState,
      isSaving,
      currentLanguage,
      pqrs,
      respuestas,
      onFileChange,
      save,
      v$,
      ...useDateFormat({ entityRef: archivoAdjunto }),
      t$,
    };
  },
  created(): void {},
});
