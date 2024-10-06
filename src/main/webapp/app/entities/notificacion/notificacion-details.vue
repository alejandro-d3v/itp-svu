<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="notificacion">
        <h2 class="jh-entity-heading" data-cy="notificacionDetailsHeading">
          <span v-text="t$('ventanillaUnicaApp.notificacion.detail.title')"></span> {{ notificacion.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('ventanillaUnicaApp.notificacion.tipo')"></span>
          </dt>
          <dd>
            <span>{{ notificacion.tipo }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.notificacion.fecha')"></span>
          </dt>
          <dd>
            <span v-if="notificacion.fecha">{{ formatDateLong(notificacion.fecha) }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.notificacion.mensaje')"></span>
          </dt>
          <dd>
            <span>{{ notificacion.mensaje }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.notificacion.leido')"></span>
          </dt>
          <dd>
            <span>{{ notificacion.leido }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.notificacion.destinatarios')"></span>
          </dt>
          <dd>
            <span v-for="(destinatarios, i) in notificacion.destinatarios" :key="destinatarios.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'OficinaView', params: { oficinaId: destinatarios.id } }">{{ destinatarios.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="notificacion.id"
          :to="{ name: 'NotificacionEdit', params: { notificacionId: notificacion.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./notificacion-details.component.ts"></script>
