<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="ventanillaUnicaApp.archivoAdjunto.home.createOrEditLabel" data-cy="ArchivoAdjuntoCreateUpdateHeading">
          {{ t$('ventanillaUnicaApp.archivoAdjunto.home.createOrEditLabel') }}
        </h2>

        <div>
          <div class="form-group" v-if="archivoAdjunto.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="archivoAdjunto.id" readonly />
          </div>

          <div class="form-group">
            <label class="form-control-label" v-text="t$('Adjuntar archivo')" for="archivo-adjunto-file"></label>
            <input
              type="file"
              class="form-control"
              id="archivo-adjunto-file"
              data-cy="file"
              :class="{ valid: !v$.file.$invalid, invalid: v$.file.$invalid }"
              @change="onFileChange"
            />
            <div v-if="v$.file.$anyDirty && v$.file.$invalid">
              <small class="form-text text-danger" v-for="error of v$.file.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>

          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.archivoAdjunto.nombre')" for="archivo-adjunto-nombre"></label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="archivo-adjunto-nombre"
              data-cy="nombre"
              :class="{ valid: !v$.nombre.$invalid, invalid: v$.nombre.$invalid }"
              v-model="v$.nombre.$model"
              required
            />
            <div v-if="v$.nombre.$anyDirty && v$.nombre.$invalid">
              <small class="form-text text-danger" v-for="error of v$.nombre.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>

          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.archivoAdjunto.tipo')" for="archivo-adjunto-tipo"></label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="archivo-adjunto-tipo"
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
            <label
              class="form-control-label"
              v-text="t$('ventanillaUnicaApp.archivoAdjunto.urlArchivo')"
              for="archivo-adjunto-urlArchivo"
            ></label>
            <input
              type="text"
              class="form-control"
              name="urlArchivo"
              id="archivo-adjunto-urlArchivo"
              data-cy="urlArchivo"
              :class="{ valid: !v$.urlArchivo.$invalid, invalid: v$.urlArchivo.$invalid }"
              v-model="v$.urlArchivo.$model"
            />
          </div>

          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.archivoAdjunto.pqrs')" for="archivo-adjunto-pqrs"></label>
            <select class="form-control" id="archivo-adjunto-pqrs" data-cy="pqrs" name="pqrs" v-model="archivoAdjunto.pqrs">
              <option :value="null"></option>
              <option
                :value="archivoAdjunto.pqrs && pqrsOption.id === archivoAdjunto.pqrs.id ? archivoAdjunto.pqrs : pqrsOption"
                v-for="pqrsOption in pqrs"
                :key="pqrsOption.id"
              >
                {{ pqrsOption.titulo }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('ventanillaUnicaApp.archivoAdjunto.respuesta')"
              for="archivo-adjunto-respuesta"
            ></label>
            <select
              class="form-control"
              id="archivo-adjunto-respuesta"
              data-cy="respuesta"
              name="respuesta"
              v-model="archivoAdjunto.respuesta"
            >
              <option :value="null"></option>
              <option
                :value="
                  archivoAdjunto.respuesta && respuestaOption.id === archivoAdjunto.respuesta.id
                    ? archivoAdjunto.respuesta
                    : respuestaOption
                "
                v-for="respuestaOption in respuestas"
                :key="respuestaOption.id"
              >
                {{ respuestaOption.contenido }}
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

<script lang="ts" src="./archivo-adjunto-update.component.ts"></script>
