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
      <i :class="['text-sm text-center w-12 h-8  md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center me-2']" />
      <span class=" font-medium">{{ option }}</span>
    </button>
</template>

<style scoped>

</style>