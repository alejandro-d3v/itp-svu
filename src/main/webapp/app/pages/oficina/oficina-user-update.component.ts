import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OficinaService from '@/entities/oficina/oficina.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';
import UserService from '@/admin/user-management/user-management.service';

import { type IOficina, Oficina } from '@/shared/model/oficina.model';
import { type IUser, User } from '@/shared/model/user.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OficinaUserUpdate',
  setup() {
    const oficinaService = inject('oficinaService', () => new OficinaService());
    const userService = inject('userService', () => new UserService());

    const alertService = inject('alertService', () => useAlertService(), true);

    const oficina: Ref<IOficina> = ref(new Oficina());
    const users: Ref<IUser[]> = ref([]); // Aquí definimos el array de usuarios
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOficina = async oficinaId => {
      try {
        const res = await oficinaService().find(oficinaId);
        oficina.value = res;
        console.log('Oficina:', oficina.value);
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    // Llamada al API de usuarios
    const retrieveUsers = async () => {
      try {
        const res = await userService().retrieveAll(); // Llama al servicio de usuarios
        users.value = res.data; // Asigna los datos a la lista de usuarios
        console.log('Usuarios cargando...');
        //console.log('Users loaded:', users.value);  // Aquí imprimimos el array de usuarios en la consola
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.oficinaId) {
      retrieveOficina(route.params.oficinaId);
      // Llamar a retrieveUsers al cargar la página
    }
    retrieveUsers();

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
      users, // Retornamos la lista de usuarios para usar en el template
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
        //this.oficina.responsableId = this.responsableId;
        this.oficinaService()
          //.create(this.oficina)
          .createWithUser(this.oficina)

          //.createWithUser(this.oficina,this.oficina.responsableId)

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
