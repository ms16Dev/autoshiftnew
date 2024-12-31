<script setup lang="ts">

import CarFormNew from "../../components/car/CarFormNew.vue";

defineOptions({
  name: 'Add-Car'
});

</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full">
    <div class="w-full md:w-1/5  p-4 text-center ">


    </div>
    <div class="w-full md:w-3/5  p-4 text-center ">

      <div class="flex-col grid grid-cols-1 -px-4 gap-3 justify-items-center ">

        <CarFormNew />
      </div>


    </div>
    <div class="w-full md:w-1/5  p-4 text-center  ">

    </div>
  </div>


</template>

<style scoped>

</style>