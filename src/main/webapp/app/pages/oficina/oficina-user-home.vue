<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <h2>{{ t$('Oficina') }}: {{ oficina.nombre ?? '---' }}</h2>

      <TableWithPag :service="oficinaService" :nameMetod="'findPqrsByUserIdWithPag'" :params="{ userId: route.params.userId }">
        <template v-slot="{ data }">
          <div class="table-responsive">
            <table class="table table-striped" aria-describedby="oficinas">
              <thead>
                <tr>
                  <th scope="row">
                    <span>{{ t$('ventanillaUnicaApp.pqrs.id') }}</span>
                  </th>
                  <th scope="row">
                    <span>{{ t$('ventanillaUnicaApp.pqrs.titulo') }}</span>
                  </th>
                  <th scope="row">
                    <span>{{ t$('ventanillaUnicaApp.pqrs.descripcion') }}</span>
                  </th>
                  <th scope="row"></th>
                </tr>
              </thead>

              <tbody>
                <template v-for="(pqr, index) in data" :key="pqr.id">
                  <tr class="pqr-item">
                    <td>
                      <router-link
                        :to="{
                          name: 'PqrsView',
                          params: { pqrsId: pqr.id },
                        }"
                        >{{ pqr.id }}</router-link
                      >
                    </td>
                    <td>{{ pqr.titulo }}</td>
                    <td>{{ pqr.descripcion }}</td>

                    <td class="text-right">
                      <div class="btn-group">
                        <router-link
                          :to="{
                            name: 'PqrsView',
                            params: { pqrsId: pqr.id },
                          }"
                          custom
                          v-slot="{ navigate }"
                        >
                          <button class="btn btn-info btn-sm details" data-cy="entityDetailsButton" @click="navigate">
                            <font-awesome-icon icon="eye" />
                            <span class="d-none d-md-inline" v-text="t$('entity.action.view')" />
                          </button>
                        </router-link>

                        <router-link
                          :to="{
                            name: 'PqrsEdit',
                            params: { pqrsId: pqr.id },
                          }"
                          custom
                          v-slot="{ navigate }"
                        >
                          <button class="btn btn-primary btn-sm edit" data-cy="entityEditButton" @click="navigate">
                            <font-awesome-icon icon="pencil-alt" />
                            <span class="d-none d-md-inline" v-text="t$('entity.action.edit')" />
                          </button>
                        </router-link>
                      </div>
                    </td>
                  </tr>
                </template>
              </tbody>
            </table>
          </div>
        </template>
      </TableWithPag>
    </div>
  </div>
</template>

<script lang="ts">
import { type Ref, defineComponent, inject, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useI18n } from 'vue-i18n';

import TableWithPag from '@/shared/components/tableWithPag/tableWithPag.vue';

import { type IOficina, Oficina } from '@/shared/model/oficina.model';

import OficinaService from '@/entities/oficina/oficina.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OficinaUserHome',
  components: {
    TableWithPag,
  },

  setup() {
    const { t: t$ } = useI18n();
    const route = useRoute();

    const oficinaService = new OficinaService();

    const alertService = inject('alertService', () => useAlertService(), true);

    const oficina: Ref<IOficina> = ref(new Oficina());

    onMounted(() => {
      loadOficina();
    });

    const retrieveOficina = async (userId: string) => {
      console.log('retrieveOficina - userId:', userId);

      try {
        const response = await oficinaService.findOficinaUser(userId);
        oficina.value = response;
      } catch (error: any) {
        console.error('retrieveOficina err:', error);
        alertService.showHttpError(error.response);
      }
    };

    const loadOficina = () => {
      const userId = `${route.params.userId}`;

      if (userId) {
        retrieveOficina(userId);
      }
    };

    return {
      t$,
      route,

      oficina,

      oficinaService,
    };
  },
});
</script>
