<script setup lang="ts">

</script>

<template>
  <div class="w-full h-[200] bg-pink-700 rounded-md inset-2">
    <div class="flex-col grid grid-cols-2">
      <div class="flex-col col">
        <h1 class="text-xl">Welcome to CARS ONLY Zone !</h1>
        <h3>Find best deals in no time!</h3>


      </div>
      <div class="flex-col">
        <img src="/car_plchldr1.jpg"/>
      </div>






    </div>
    <div
        class="flex flex-col md:flex-row md:justify-center items-center w-full mt-4"
    >
      <div class="flex items-center">
        <ul class="flex space-x-2">


          <li
              v-for="(page, i) in pages"
              :key="i"
              class="page-item"
          >
            <a
                class="page-link p-2 border rounded text-white bg-pink-500 hover:bg-pink-300"
                :class="{ 'bg-pink-700 text-white': isPageActive(page.name) }"

                @click="onClickPage(page.name)"
            >
              {{ page.name }}
            </a>
          </li>

        </ul>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>