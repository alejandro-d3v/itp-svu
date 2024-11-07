<template>
  <!-- selector of number of records per page -->
  <template v-if="service && !loading">
    <section class="d-flex justify-content-start">
      <div class="col-4 p-0 mb-2">
        <span>Mostrar </span>

        <select v-model="perPage">
          <option v-for="perPageOption in perPageOptions" :key="perPageOption">
            {{ perPageOption }}
          </option>
        </select>

        <span> registros por página</span>
      </div>
    </section>
  </template>

  <ProgressBar v-if="loading" />

  <!-- data -->
  <template v-else>
    <template v-if="data.length">
      <div>
        <slot :data="data"></slot>
      </div>
    </template>

    <template v-else>
      <p class="text-center m-0">Sin registros</p>
    </template>
  </template>

  <!-- pagination -->
  <div v-show="!loading && data && data.length > 0">
    <div class="row justify-content-center">
      <jhi-item-count :page="currentPage" :total="total" :itemsPerPage="perPage" />
    </div>

    <div class="row justify-content-center">
      <b-pagination v-model="currentPage" size="md" :total-rows="total" :per-page="perPage" />
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, onUnmounted, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import ProgressBar from '@/shared/components/progressBar.vue';

import type { IResponsePag, IParamsPag } from '@/shared/interfaces/pagination.interface';

export default defineComponent({
  name: 'TableWithPag',

  components: {
    ProgressBar,
  },

  props: {
    service: {
      type: Object,
      default: null,
    },
    nameMetod: {
      type: String,
      default: 'run',
    },
    params: {
      type: Object,
      default: () => {},
    },
    defaultPerPage: {
      type: Number,
      default: 10,
    },
  },

  setup(props) {
    const route = useRoute();
    const router = useRouter();

    const currentService = props.service ?? null;

    const data = ref<any[]>([]);
    const timeoutId = ref<any>(null);

    const total = ref(0);
    const currentPage = ref(1);
    const perPage = ref(props.defaultPerPage);
    const perPageOptions = ref([3, 5, 10, 20, 50, 100]);

    const loading = ref(false);

    const params: any = computed(() => {
      let params: IParamsPag = {
        perPage: perPage.value ?? props.defaultPerPage,
        page: currentPage.value,
      };

      if (props.params) params = { ...params, ...props.params };

      setParamsOnRoutes(params);

      return params;
    });

    const getData = async () => {
      if (!currentService) return;
      if (loading.value) return;

      loading.value = true;

      try {
        const response: IResponsePag<any> = await currentService[props.nameMetod](params.value);

        total.value = response.total;
        data.value = response.data;
        currentPage.value = response.page;
      } catch (e) {
        console.log(e);
      }

      loading.value = false;
    };

    onMounted(async () => {
      await getData();

      document.removeEventListener('updateDatatable', refreshData, true);
      document.addEventListener('updateDatatable', refreshData, true);
    });

    const fetchData = async () => {
      if (timeoutId.value) {
        clearTimeout(timeoutId.value);
      }

      timeoutId.value = setTimeout(async () => {
        await getData();
      }, 400);
    };

    if (currentService) {
      watch(currentPage, async () => {
        await fetchData();
      });
      watch(perPage, async () => {
        if (!perPage.value) perPage.value = props.defaultPerPage;

        await fetchData();
      });
    }

    const setParamsOnRoutes = (paramsDatatable: any) => {
      setTimeout(() => {
        if (!paramsDatatable) return;

        const query: any = { ...route.query, page: paramsDatatable.page };

        if ('perPage' in query && !parseInt(query.perPage)) query.perPage = props.defaultPerPage;

        router.push({ query });
      }, 200);
    };

    const refreshData = async () => {
      await getData();
    };

    onUnmounted(() => {
      document.removeEventListener('updateDatatable', refreshData, true);
    });

    return {
      data,
      loading,

      perPageOptions,
      currentPage,
      perPage,
      total,

      refreshData,
    };
  },
});
</script>

<style scoped>
.search-section-container {
  width: 100%;
  flex: none;
}

.filter-section-container {
  display: flex;
  justify-content: right;
}

:deep(div .o-ctrl-sel select) {
  padding: 0.1rem;
  text-align: center;
}

:deep(div .o-ctrl-sel .o-sel-arrow) {
  border-radius: 4px;
  background-image: none;
}

:deep(.o-sel-arrow) {
  height: auto;
}

:deep(.o-sel-arrow) {
  padding-right: 0.4rem !important;
}

.filter-section-container > :nth-child(2) {
  display: flex;
  justify-content: space-between;
  min-width: 30rem;
  max-width: 30rem;
  width: 30rem;
}

.filter-section-container section {
  width: 40%;
}

:deep(.pagination) {
  border: 0;
}

.o-form-control,
.o-form-control-sm {
  display: block;
  width: 100%;
  padding: var(--padding-1);
  border-radius: calc(var(--border-radius-1) + 1rem);
  font-size: var(--font-size-2);
  font-weight: var(--font-weight-normal);
  line-height: var(--line-height-3);
  color: rgba(var(--color-gray-900-rgb), var(--opacity-color-80));
  background-color: var(--color-light);
  border-color: rgba(var(--color-gray-600-rgb), var(--opacity-color-60));
  border-width: var(--border-1);
}

.o-form-control:focus,
.o-form-control-sm:focus {
  border-color: rgba(var(--color-gray-600-rgb), var(--opacity-color-100));
  outline: 0;
}

.o-form-control-sm {
  display: inline;
  width: 4rem;
  border-radius: 0.5rem;
  padding: 0.3rem var(--padding-1);
}

.input-number-records {
  background-color: var(--color-primary);
  color: #fff;
  border-radius: 4px;
  text-align: center;
  outline: none;
  width: 2rem;
}

.input-number-records-container input[type='number']::-webkit-inner-spin-button,
.input-number-records-container input[type='number']::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.grid-cards {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(auto-fill, minmax(min(100%, 18rem), 1fr));
}

/* Small devices (tablets) */
@media screen and (min-width: 576px) and (max-width: 767.98px) {
}

/* Medium devices (desktops) */
@media screen and (min-width: 768px) and (max-width: 991.98px) {
  .footer-pagination {
    display: flex;
    align-items: center;
    margin-top: 1rem;
  }
}

/* Large devices (large desktops) */
@media screen and (min-width: 992px) and (max-width: 1199.98px) {
  .footer-pagination {
    display: flex;
    align-items: center;
    margin-top: 1rem;
  }
}

/* Extra large devices (larger desktops) */
@media screen and (min-width: 1200px) {
  .footer-pagination {
    display: flex;
    align-items: center;
    margin-top: 1rem;
  }
}
</style>
