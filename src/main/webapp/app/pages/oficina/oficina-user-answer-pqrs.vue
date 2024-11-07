<template>
  <template v-if="!loading">
    <div class="row justify-content-center">
      <div class="col-8">
        <h2>{{ t$('ventanillaUnicaApp.pqrs.detail.title') }}: {{ pqrs.id ?? '---' }}</h2>

        <dl class="row jh-entity-details">
          <dt>{{ t$('ventanillaUnicaApp.pqrs.titulo') }}</dt>
          <dd>{{ pqrs.titulo }}</dd>

          <dt>{{ t$('ventanillaUnicaApp.pqrs.descripcion') }}</dt>
          <dd>{{ pqrs.descripcion }}</dd>

          <dt>{{ t$('ventanillaUnicaApp.pqrs.fechaCreacion') }}</dt>
          <dd>
            <span v-if="pqrs.fechaCreacion">
              {{ formatDateLong(pqrs.fechaCreacion) }}
            </span>
          </dd>

          <dt>{{ t$('ventanillaUnicaApp.pqrs.fechaLimiteRespuesta') }}</dt>
          <dd>
            <span v-if="pqrs.fechaLimiteRespuesta">
              {{ formatDateLong(pqrs.fechaLimiteRespuesta) }}
            </span>
          </dd>

          <dt>{{ t$('ventanillaUnicaApp.pqrs.estado') }}</dt>
          <dd>{{ pqrs.estado }}</dd>

          <dt>{{ t$('ventanillaUnicaApp.pqrs.oficinaResponder') }}</dt>
          <dd>
            <template v-if="pqrs.oficinaResponder">
              <router-link
                :to="{
                  name: 'OficinaView',
                  params: { oficinaId: pqrs.oficinaResponder.id },
                }"
                >{{ pqrs.oficinaResponder.nombre }}</router-link
              >
            </template>
          </dd>

          <dt>{{ t$('Respuesta') }}</dt>
          <dd>
            <b-form-textarea v-model="txtanswer" placeholder="Responde algo..." rows="3" max-rows="6"></b-form-textarea>
          </dd>
        </dl>

        <button class="btn btn-info mr-2" @click.prevent="previousState()">
          <font-awesome-icon icon="arrow-left" />
          <span v-text="t$('entity.action.back')" />
        </button>
        <button class="btn btn-primary" @click.prevent="sendAnswer()">
          <font-awesome-icon icon="save" />
          <span v-text="t$('entity.action.save')" />
        </button>
      </div>
    </div>
  </template>
</template>

<script lang="ts">
import { type Ref, defineComponent, inject, ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';

import { useDateFormat } from '@/shared/composables';

import type { IRespuesta } from '@/shared/model/respuesta.model';
import type { IPqrs } from '@/shared/model/pqrs.model';

import { useAlertService } from '@/shared/alert/alert.service';
import PqrsService from '@/entities/pqrs/pqrs.service';

export default defineComponent({
  name: 'OficinaUserAnswerPqrs',
  components: {},

  setup() {
    const dateFormat = useDateFormat();
    const { t: t$ } = useI18n();
    const router = useRouter();
    const route = useRoute();

    const pqrsService = new PqrsService();

    const alertService = inject('alertService', () => useAlertService(), true);

    const userId: Ref<string> = ref(`${route.params.userId}`);
    const pqrsId: Ref<string> = ref(`${route.params.pqrsId}`);

    const pqrs: Ref<IPqrs> = ref({});
    const answer: Ref<IRespuesta> = ref({});

    const txtanswer: Ref<string> = ref('');

    const loading: Ref<boolean> = ref(true);

    onMounted(async () => {
      await getPqrs();
      await getAnswer();

      loading.value = false;
    });

    const getPqrs = async () => {
      try {
        pqrs.value = await pqrsService.find(pqrsId.value);
      } catch (error: any) {
        alertService.showHttpError(error.response);
      }
    };
    const getAnswer = () => {
      console.log('getAnswer', userId.value);
    };

    const previousState = () => {
      router.go(-1);
    };

    const sendAnswer = () => {
      console.log('sendAnswer', txtanswer.value);
    };

    return {
      t$,
      route,
      loading,
      ...dateFormat,

      pqrs,
      answer,
      txtanswer,

      sendAnswer,
      previousState,
    };
  },
});
</script>
