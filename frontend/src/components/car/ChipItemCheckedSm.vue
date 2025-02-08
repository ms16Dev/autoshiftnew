<script setup lang="ts">
defineProps({
  option: {
    type: String,
    required: true,
    default: "1.png"
  },
});


</script>

<template>
    <button class=" text-gray-400 bg-pink-50 rounded-full m-1 px-3">
      <i :class="['fas', 'fa-check', 'text-xl text-pink-300 text-center w-8 h-8  me-2']" />
      <span class=" font-medium">{{ option }}</span>
    </button>
</template>

<style scoped>

</style>