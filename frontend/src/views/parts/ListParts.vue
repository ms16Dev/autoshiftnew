<script setup lang="ts">
import CarItem from "../../components/CarItem.vue";

defineOptions({
  name: 'Parts'
})
</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full">
    <div class="w-full md:w-1/5 bg-gray-600 p-4 text-center text-gray-400">
      <CarItem/>

    </div>
    <div class="w-full md:w-3/5 bg-gray-500 p-4 text-center text-gray-200">

      <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 -px-4 gap-3">

        <CarItem/>
        <CarItem/>
        <CarItem/>
        <CarItem/>
        <CarItem/>
        <CarItem/>
        <CarItem/>
      </div>


    </div>
    <div class="w-full md:w-1/5 bg-gray-400 p-4 text-center text-gray-700 fixed sticky">
      <CarItem class="w-1\4"/>

    </div>
  </div></template>

<style scoped>

</style>