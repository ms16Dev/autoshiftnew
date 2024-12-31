<script setup lang="ts">
const props = defineProps({
  url: {
    type: String,
    required: true,
    default: "1.png"
  },
});


</script>

<template>
  <button class="flex-col justify-items-center">
    <img :src="props.url" class="h-24 w-24 object-center object-contain"/>

  </button>
</template>

<style scoped>

</style>