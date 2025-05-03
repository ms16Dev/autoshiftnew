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
    <button class="text-sm bg-pink-400 rounded-full m-1">
      <i :class="['fas', 'fa-check', 'text-sm text-center w-4 h-4  md:h-4 xl:w-4 xl:h-4 xl:text-lg xl:text-center me-2']" />
      <span class=" font-medium">{{ option }}</span>
    </button>
</template>

<style scoped>

</style>