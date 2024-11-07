<template>
  <div class="o-progress-bar">
    <div class="o-progress-bar-value"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ProgressBar',
});
</script>

<style scoped>
.o-progress-bar {
  height: 4px;
  background-color: rgba(0, 0, 0, 0.2);
  width: 100%;
  overflow: hidden;
}

.o-progress-bar-value {
  width: 100%;
  height: 100%;
  background-color: rgba(73, 143, 226, 1);
  animation: indeterminateAnimation 1s infinite linear;
  transform-origin: 0% 50%;
}

@keyframes indeterminateAnimation {
  0% {
    transform: translateX(0) scaleX(0);
  }

  40% {
    transform: translateX(0) scaleX(0.4);
  }

  100% {
    transform: translateX(100%) scaleX(0.5);
  }
}
</style>
