<template>
  <div>
    <h2 id="page-heading" data-cy="NotificacionHeading">
      <span v-text="t$('ventanillaUnicaApp.notificacion.home.title')" id="notificacion-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('ventanillaUnicaApp.notificacion.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'NotificacionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-notificacion"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('ventanillaUnicaApp.notificacion.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && notificacions && notificacions.length === 0">
      <span v-text="t$('ventanillaUnicaApp.notificacion.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="notificacions && notificacions.length > 0">
      <table class="table table-striped" aria-describedby="notificacions">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('tipo')">
              <span v-text="t$('ventanillaUnicaApp.notificacion.tipo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipo'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('fecha')">
              <span v-text="t$('ventanillaUnicaApp.notificacion.fecha')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('mensaje')">
              <span v-text="t$('ventanillaUnicaApp.notificacion.mensaje')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mensaje'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('leido')">
              <span v-text="t$('ventanillaUnicaApp.notificacion.leido')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'leido'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="notificacion in notificacions" :key="notificacion.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NotificacionView', params: { notificacionId: notificacion.id } }">{{
                notificacion.id
              }}</router-link>
            </td>
            <td>{{ notificacion.tipo }}</td>
            <td>{{ formatDateShort(notificacion.fecha) || '' }}</td>
            <td>{{ notificacion.mensaje }}</td>
            <td>{{ notificacion.leido }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'NotificacionView', params: { notificacionId: notificacion.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'NotificacionEdit', params: { notificacionId: notificacion.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(notificacion)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="ventanillaUnicaApp.notificacion.delete.question"
          data-cy="notificacionDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-notificacion-heading" v-text="t$('ventanillaUnicaApp.notificacion.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-notificacion"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeNotificacion()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="notificacions && notificacions.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./notificacion.component.ts"></script>
