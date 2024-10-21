<template>
  <div class="oficina-home">
    <h1>Información de la Oficina</h1>
    <div class="oficina-info">
      <h2>Datos de la Oficina</h2>
      <p><strong>Nombre:</strong> {{ oficina.nombre }}</p>
      <p><strong>Dirección:</strong> {{ oficina.direccion }}</p>
      <p><strong>Teléfono:</strong> {{ oficina.telefono }}</p>
    </div>

    <div class="pqr-list">
      <h2>Lista de PQRS</h2>
      <ul>
        <li v-for="pqr in pqrs" :key="pqr.id">
          <strong>ID:</strong> {{ pqr.id }} - <strong>Tipo:</strong> {{ pqr.tipo }} - <strong>Descripción:</strong> {{ pqr.descripcion }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      oficina: {
        nombre: 'Oficina Central',
        direccion: '123 Calle Principal, Ciudad',
        telefono: '123-456-7890',
      },
      pqrs: [
        { id: 1, tipo: 'Queja', descripcion: 'Falta de atención al cliente' },
        { id: 2, tipo: 'Sugerencia', descripcion: 'Mejorar el horario de atención' },
        { id: 3, tipo: 'Petición', descripcion: 'Agregar más personal' },
      ],
    };
  },
};
</script>

<style scoped>
.oficina-home {
  padding: 20px;
  background-color: #f9f9f9;
}

.oficina-info,
.pqr-list {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

h1,
h2 {
  color: #333;
}
</style>
