<script setup lang="ts">


</script>

<template>

  <div class="relative h-screen bg-cover bg-center " style="background-image: url('https://car-images.bauersecure.com/pagefiles/74108/1752x1168/toyota_hybrids_01.jpg?mode=max&quality=90&scale=down');">
    <!-- Blurry Overlay Div -->
    <div class="absolute overflow-y-scroll inset-0 backdrop-blur-md bg-white/50 flex items-center justify-center">
        <router-view/>

    </div>
  </div>


</template>

<style scoped>

</style>