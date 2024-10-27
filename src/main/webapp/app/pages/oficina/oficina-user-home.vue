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
          <div><strong>ID:</strong> {{ pqr.id }}</div>
          <div><strong>Tipo:</strong> {{ pqr.tipo }}</div>
          <div><strong>Descripción:</strong> {{ pqr.descripcion }}</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { type Ref, computed, defineComponent, inject, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

import { type IOficina, Oficina } from '@/shared/model/oficina.model';

import OficinaService from '@/entities/oficina/oficina.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OficinaUserHome',

  setup() {
    const route = useRoute();

    const oficinaService = new OficinaService();

    const alertService = inject('alertService', () => useAlertService(), true);

    const oficina: Ref<IOficina> = ref(new Oficina());
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    onMounted(() => {
      loadOficina();
    });

    const retrieveOficina = async (userId: string) => {
      console.log('retrieveOficina - userId:', userId);

      try {
        const response = await oficinaService.findOficinaUser(userId);
        console.log('Oficina encontrada:', response);
        oficina.value = response;
      } catch (error) {
        console.error('retrieveOficina err:', error);
        alertService.showHttpError(error.response);
      }
    };

    const loadOficina = () => {
      const userId = route.params.userId;

      if (userId) {
        retrieveOficina(userId);
      }
    };

    return {
      alertService,
      oficina,
      currentLanguage,
    };
  },
});
</script>
