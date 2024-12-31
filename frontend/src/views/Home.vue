<script setup lang="ts">


import CarItem from "../components/CarItem.vue";
import ButtonWithPopupMenu from "../components/FAB.vue";

defineOptions({
  name: 'Home'
})
</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full">
    <div class="w-full md:w-1/5 bg-gray-600 p-4 text-center text-gray-400">
      <CarItem/>

    </div>
    <div class="w-full md:w-3/5 bg-gray-500 p-4 text-center text-gray-200">

      <div class="flex-col w-full  gap-3">

        <h1>Featured carrousel</h1>
        <br>
        <h1>Advertisement</h1>
        <br>
        <h1>Resent </h1>
        <br>
        <h1>Advertisement</h1>
        <br>
        <h1>Self branding</h1>

      </div>


    </div>
    <div class="w-full md:w-1/5 bg-gray-400 p-4 text-center text-gray-700 fixed sticky">
      <CarItem class="w-1\4"/>

    </div>
  </div>
  <div class="fixed bottom-14 end-6   rounded-full">
    <ButtonWithPopupMenu
        text="Quick Add"
        icon="fas fa-plus"
        menuPosition="top"
    />
  </div>

</template>

<style scoped>

</style>