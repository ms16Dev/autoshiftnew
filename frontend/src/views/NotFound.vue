<!-- src/components/NotFound.vue -->
<template>
  <div class="flex flex-col items-center justify-center h-screen">
    <IconButton label="" icon="fa-info" class="scale-150"></IconButton>
    <h1 class="text-4xl font-bold text-pink-300">404 - Page Not Found</h1>
    <p class="mt-4 text-lg text-gray-600">Sorry, the page you're looking for doesn't exist.</p>
    <router-link to="/home" class="mt-6 text-pink-500 hover:text-pink-700">Go back to Home</router-link>
  </div>
</template>

<script setup>
// No script necessary for this basic 404 component
import IconButton from "../components/IconButton.vue";
</script>

<style scoped>
/* Add any additional styling if necessary */
</style>
