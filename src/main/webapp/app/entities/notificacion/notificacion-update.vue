<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="ventanillaUnicaApp.notificacion.home.createOrEditLabel"
          data-cy="NotificacionCreateUpdateHeading"
          v-text="t$('ventanillaUnicaApp.notificacion.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="notificacion.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="notificacion.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.notificacion.tipo')" for="notificacion-tipo"></label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="notificacion-tipo"
              data-cy="tipo"
              :class="{ valid: !v$.tipo.$invalid, invalid: v$.tipo.$invalid }"
              v-model="v$.tipo.$model"
              required
            />
            <div v-if="v$.tipo.$anyDirty && v$.tipo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.tipo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.notificacion.fecha')" for="notificacion-fecha"></label>
            <div class="d-flex">
              <input
                id="notificacion-fecha"
                data-cy="fecha"
                type="datetime-local"
                class="form-control"
                name="fecha"
                :class="{ valid: !v$.fecha.$invalid, invalid: v$.fecha.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.fecha.$model)"
                @change="updateInstantField('fecha', $event)"
              />
            </div>
            <div v-if="v$.fecha.$anyDirty && v$.fecha.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fecha.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.notificacion.mensaje')" for="notificacion-mensaje"></label>
            <textarea
              class="form-control"
              name="mensaje"
              id="notificacion-mensaje"
              data-cy="mensaje"
              :class="{ valid: !v$.mensaje.$invalid, invalid: v$.mensaje.$invalid }"
              v-model="v$.mensaje.$model"
              required
            ></textarea>
            <div v-if="v$.mensaje.$anyDirty && v$.mensaje.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mensaje.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.notificacion.leido')" for="notificacion-leido"></label>
            <input
              type="checkbox"
              class="form-check"
              name="leido"
              id="notificacion-leido"
              data-cy="leido"
              :class="{ valid: !v$.leido.$invalid, invalid: v$.leido.$invalid }"
              v-model="v$.leido.$model"
            />
          </div>
          <div class="form-group">
            <label v-text="t$('ventanillaUnicaApp.notificacion.destinatarios')" for="notificacion-destinatarios"></label>
            <select
              class="form-control"
              id="notificacion-destinatarios"
              data-cy="destinatarios"
              multiple
              name="destinatarios"
              v-if="notificacion.destinatarios !== undefined"
              v-model="notificacion.destinatarios"
            >
              <option
                :value="getSelected(notificacion.destinatarios, oficinaOption, 'id')"
                v-for="oficinaOption in oficinas"
                :key="oficinaOption.id"
              >
                {{ oficinaOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./notificacion-update.component.ts"></script>
