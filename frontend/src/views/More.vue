<script setup lang="ts">
import AdItem from "../components/AdItem.vue";
import JoinUs from "../components/JoinUs.vue";
import AdvertiseWithUs from "../components/AdvertiseWithUs.vue";
import Footer from "../components/Footer.vue";

defineOptions({
  name: 'More'
})
</script>


<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full md:w-1/5  p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full md:w-3/5  p-4 text-center text-gray-200">

  <JoinUs/>


      <AdvertiseWithUs/>


    </div>

    <div class="w-full md:w-1/5 p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>

    </div>
  </div>
</template>

<style scoped>

</style>