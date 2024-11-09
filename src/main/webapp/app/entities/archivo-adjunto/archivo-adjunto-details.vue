<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="archivoAdjunto">
        <h2 class="jh-entity-heading" data-cy="archivoAdjuntoDetailsHeading">
          <span v-text="t$('ventanillaUnicaApp.archivoAdjunto.detail.title')"></span> {{ archivoAdjunto.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('ventanillaUnicaApp.archivoAdjunto.nombre')"></span>
          </dt>
          <dd>
            <span>{{ archivoAdjunto.nombre }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.archivoAdjunto.tipo')"></span>
          </dt>
          <dd>
            <span>{{ archivoAdjunto.tipo }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.archivoAdjunto.urlArchivo')"></span>
          </dt>
          <dd>
            <span>{{ archivoAdjunto.urlArchivo }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.archivoAdjunto.fechaSubida')"></span>
          </dt>
          <dd>
            <span v-if="archivoAdjunto.fechaSubida">{{ formatDateLong(archivoAdjunto.fechaSubida) }}</span>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.archivoAdjunto.pqrs')"></span>
          </dt>
          <dd>
            <div v-if="archivoAdjunto.pqrs">
              <router-link :to="{ name: 'PqrsView', params: { pqrsId: archivoAdjunto.pqrs.id } }">{{ archivoAdjunto.pqrs.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('ventanillaUnicaApp.archivoAdjunto.respuesta')"></span>
          </dt>
          <dd>
            <div v-if="archivoAdjunto.respuesta">
              <router-link :to="{ name: 'RespuestaView', params: { respuestaId: archivoAdjunto.respuesta.id } }">{{
                archivoAdjunto.respuesta.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <button type="button" @click.prevent="downloadFile()" class="btn btn-info mx-2" data-cy="downloadFileButton">
          <font-awesome-icon icon="download"></font-awesome-icon>&nbsp;<span v-text="t$('Descargar')"></span>
        </button>
        <router-link
          v-if="archivoAdjunto.id"
          :to="{ name: 'ArchivoAdjuntoEdit', params: { archivoAdjuntoId: archivoAdjunto.id } }"
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

<script lang="ts" src="./archivo-adjunto-details.component.ts"></script>
