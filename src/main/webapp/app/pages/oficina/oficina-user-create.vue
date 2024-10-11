<!--
<template>
    <div class="create-oficina">
      <h2>Crear Oficina</h2>
      <form @submit.prevent="saveOficina" novalidate>
        <div class="form-group">
          <label for="nombre">Nombre de la Oficina:</label>
          <input
            type="text"
            id="nombre"
            v-model="oficina.nombre"
            required
            class="form-control"
            :class="{ 'is-invalid': submitted && !oficina.nombre }"
          />
          <div v-if="submitted && !oficina.nombre" class="invalid-feedback">
            El nombre es requerido.
          </div>
        </div>
  
        <div class="form-group">
          <label for="responsable">Usuario Responsable:</label>
          <select
            id="responsable"
            v-model="oficina.responsableId"
            required
            class="form-control"
            :class="{ 'is-invalid': submitted && !oficina.responsableId }"
          >
            <option value="" disabled>Selecciona un usuario</option>
            <option v-for="user in users" :key="user.id" :value="user.id">
              {{ user.name }}
            </option>
          </select>
          <div v-if="submitted && !oficina.responsableId" class="invalid-feedback">
            Debes seleccionar un usuario responsable.
          </div>
        </div>
  
        <button type="submit" class="btn btn-primary">Guardar</button>
        <button type="button" class="btn btn-secondary" @click="cancel">Cancelar</button>
      </form>
  
      <div v-if="error" class="alert alert-danger mt-3">
        {{ error }}
      </div>
    </div>
  </template>
  
  <script>
  import OficinaService from '@/entities/oficina/oficina.service';
  import UserService from '@/entities/user/user.service';
  
  export default {
    data() {
      return {
        oficina: {
          nombre: '',
          responsableId: null,
        },
        users: [],
        submitted: false,
        error: null,
      };
    },
    created() {
      this.loadUsers();
    },
    methods: {
      async loadUsers() {
        try {
          const response = await UserService.retrieveAll(); // Método que debes definir para obtener usuarios
          this.users = response.data; // Asume que la respuesta es un array de usuarios
        } catch (err) {
          this.error = 'Error al cargar los usuarios.';
        }
      },
      async saveOficina() {
        this.submitted = true;
        this.error = null;
  
        if (this.oficina.nombre && this.oficina.responsableId) {
          try {
            await OficinaService.createWithUser(this.oficina.this.oficina.responsableId);
            this.$router.push({ name: 'oficina-list' }); // Redirige a la lista de oficinas
          } catch (err) {
            this.error = 'Error al crear la oficina: ' + err.response.data.message;
          }
        }
      },
      cancel() {
        this.$router.push({ name: 'oficina-list' }); // Redirige a la lista de oficinas
      },
    },
  };
  </script>
  
  <style scoped>
  .create-oficina {
    max-width: 600px;
    margin: auto;
  }
  .form-group {
    margin-bottom: 1.5rem;
  }
  </style>
-->
<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="ventanillaUnicaApp.oficina.home.createOrEditLabel"
          data-cy="OficinaCreateUpdateHeading"
          v-text="t$('ventanillaUnicaApp.oficina.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="oficina.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="oficina.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.oficina.nombre')" for="oficina-nombre"></label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="oficina-nombre"
              data-cy="nombre"
              :class="{ valid: !v$.nombre.$invalid, invalid: v$.nombre.$invalid }"
              v-model="v$.nombre.$model"
              required
            />
            <div v-if="v$.nombre.$anyDirty && v$.nombre.$invalid">
              <small class="form-text text-danger" v-for="error of v$.nombre.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>

          <!------------------------------------------------->
          <div class="form-group">
            <label for="responsableDTO">Usuario Responsable:</label>
            <select
              id="responsableDTO"
              v-model="oficina.responsableDTO"
              required
              class="form-control"
              :class="{ 'is-invalid': submitted && !oficina.responsableDTO.id }"
            >
              <option value="" disabled>Selecciona un usuario</option>
              <option v-for="user in users" :key="user.id" :value="user">
                {{ user.login }}
              </option>
            </select>
            <div v-if="submitted && !oficina.responsableDTO.id" class="invalid-feedback">Debes seleccionar un usuario responsable.</div>
          </div>

          <!------------------------------------------------->

          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.oficina.descripcion')" for="oficina-descripcion"></label>
            <input
              type="text"
              class="form-control"
              name="descripcion"
              id="oficina-descripcion"
              data-cy="descripcion"
              :class="{ valid: !v$.descripcion.$invalid, invalid: v$.descripcion.$invalid }"
              v-model="v$.descripcion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ventanillaUnicaApp.oficina.nivel')" for="oficina-nivel"></label>
            <input
              type="text"
              class="form-control"
              name="nivel"
              id="oficina-nivel"
              data-cy="nivel"
              :class="{ valid: !v$.nivel.$invalid, invalid: v$.nivel.$invalid }"
              v-model="v$.nivel.$model"
              required
            />
            <div v-if="v$.nivel.$anyDirty && v$.nivel.$invalid">
              <small class="form-text text-danger" v-for="error of v$.nivel.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('ventanillaUnicaApp.oficina.oficinaSuperior')"
              for="oficina-oficinaSuperior"
            ></label>
            <input
              type="text"
              class="form-control"
              name="oficinaSuperior"
              id="oficina-oficinaSuperior"
              data-cy="oficinaSuperior"
              :class="{ valid: !v$.oficinaSuperior.$invalid, invalid: v$.oficinaSuperior.$invalid }"
              v-model="v$.oficinaSuperior.$model"
            />
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
<script lang="ts" src="./oficina-user-update.component.ts"></script>
