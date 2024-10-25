<template>
  <div class="oficina-home">
    <div class="header">
      <h1>Secretaría del Instituto Tecnológico del Putumayo</h1>
    </div>
    <div class="oficina-info">
      <h2>Datos de la Secretaría</h2>
      <p><strong>Nombre:</strong> {{ oficina.nombre }}</p>
      <p><strong>Dirección:</strong> {{ oficina.direccion }}</p>
      <p><strong>Teléfono:</strong> {{ oficina.telefono }}</p>
    </div>

    <div class="pqr-list">
      <h2>Lista de PQRS</h2>
      <ul>
        <li v-for="pqr in oficina.pqrsList" :key="pqr.id" class="pqr-item">
          <strong>ID:</strong> {{ pqr.id }} - <strong>Tipo:</strong> {{ pqr.tipo }} - <strong>Descripción:</strong> {{ pqr.descripcion }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OficinaService from '@/entities/oficina/oficina.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';
//import UserService from '@/entities/user/user.service';

import { type IOficina, Oficina } from '@/shared/model/oficina.model';
import account from '@/router/account';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OficinaUserHome',
  setup() {
    const oficinaService = inject('oficinaService', () => new OficinaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const oficina: Ref<IOficina> = ref(new Oficina());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOficina = async userId => {
      try {
        console.log(res);
        const res = await oficinaService().findOficinaUser(userId);
        //const res = await oficinaService().findOficina(oficinaByUserId);
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
  methods: {},
});
</script>
