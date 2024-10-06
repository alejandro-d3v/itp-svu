<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="ventanillaUnicaApp.informePqrs.home.createOrEditLabel"
          data-cy="InformePqrsCreateUpdateHeading"
          v-text="t$('ventanillaUnicaApp.informePqrs.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="informePqrs.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="informePqrs.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('ventanillaUnicaApp.informePqrs.fechaInicio')"
              for="informe-pqrs-fechaInicio"
            ></label>
            <div class="d-flex">
              <input
                id="informe-pqrs-fechaInicio"
                data-cy="fechaInicio"
                type="datetime-local"
                class="form-control"
                name="fechaInicio"
                :class="{ valid: !v$.fechaInicio.$invalid, invalid: v$.fechaInicio.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.fechaInicio.$model)"
                @change="updateInstantField('fechaInicio', $event)"
              />
            </div>
            <div v-if="v$.fechaInicio.$anyDirty && v$.fechaInicio.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fechaInicio.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.informePqrs.fechaFin')" for="informe-pqrs-fechaFin"></label>
            <div class="d-flex">
              <input
                id="informe-pqrs-fechaFin"
                data-cy="fechaFin"
                type="datetime-local"
                class="form-control"
                name="fechaFin"
                :class="{ valid: !v$.fechaFin.$invalid, invalid: v$.fechaFin.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.fechaFin.$model)"
                @change="updateInstantField('fechaFin', $event)"
              />
            </div>
            <div v-if="v$.fechaFin.$anyDirty && v$.fechaFin.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fechaFin.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.informePqrs.totalPqrs')" for="informe-pqrs-totalPqrs"></label>
            <input
              type="number"
              class="form-control"
              name="totalPqrs"
              id="informe-pqrs-totalPqrs"
              data-cy="totalPqrs"
              :class="{ valid: !v$.totalPqrs.$invalid, invalid: v$.totalPqrs.$invalid }"
              v-model.number="v$.totalPqrs.$model"
              required
            />
            <div v-if="v$.totalPqrs.$anyDirty && v$.totalPqrs.$invalid">
              <small class="form-text text-danger" v-for="error of v$.totalPqrs.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('ventanillaUnicaApp.informePqrs.totalResueltas')"
              for="informe-pqrs-totalResueltas"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalResueltas"
              id="informe-pqrs-totalResueltas"
              data-cy="totalResueltas"
              :class="{ valid: !v$.totalResueltas.$invalid, invalid: v$.totalResueltas.$invalid }"
              v-model.number="v$.totalResueltas.$model"
              required
            />
            <div v-if="v$.totalResueltas.$anyDirty && v$.totalResueltas.$invalid">
              <small class="form-text text-danger" v-for="error of v$.totalResueltas.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('ventanillaUnicaApp.informePqrs.totalPendientes')"
              for="informe-pqrs-totalPendientes"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalPendientes"
              id="informe-pqrs-totalPendientes"
              data-cy="totalPendientes"
              :class="{ valid: !v$.totalPendientes.$invalid, invalid: v$.totalPendientes.$invalid }"
              v-model.number="v$.totalPendientes.$model"
              required
            />
            <div v-if="v$.totalPendientes.$anyDirty && v$.totalPendientes.$invalid">
              <small class="form-text text-danger" v-for="error of v$.totalPendientes.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.informePqrs.oficina')" for="informe-pqrs-oficina"></label>
            <select class="form-control" id="informe-pqrs-oficina" data-cy="oficina" name="oficina" v-model="informePqrs.oficina">
              <option :value="null"></option>
              <option
                :value="informePqrs.oficina && oficinaOption.id === informePqrs.oficina.id ? informePqrs.oficina : oficinaOption"
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
<script lang="ts" src="./informe-pqrs-update.component.ts"></script>
