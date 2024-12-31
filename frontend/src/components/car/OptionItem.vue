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
    <button class="h-full text-2xl">{{option}}</button>
</template>

<style scoped>

</style>