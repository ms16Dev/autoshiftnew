<script setup lang="ts">


import Navigation from "../components/Navigation.vue";
import Footer from "../components/Footer.vue";
</script>

<template>
  <Navigation/>
  <div class="flex justify-center items-center xl:pt-32 sm:ps-24 xl:ps-0 bg-gray-200">
  <router-view/>
  </div>


  <Footer/>




</template>

<style scoped>

</style>