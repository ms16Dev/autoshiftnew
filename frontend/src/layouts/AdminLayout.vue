<script setup lang="ts">


import IconButton from "../components/IconButton.vue";
</script>

<template>
  <div class="flex md:flex-row flex-wrap w-full bg-gray-200">
    <!-- Sidebar - Dealer Card -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-400">
      <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
        <div class="group flex-col inset-px shadow-md">

          <!-- Cover image-->
          <div class="overflow-hidden">
            <img class="w-full transition-transform duration-700  group-hover:scale-110" src="/car_plchldr1.jpg"/>

          </div>
          <!--Devider-->
          <div class="w-full border-b-2 border-b-pink-700 "></div>
          <div class="flex flex-row w-full justify-center">
            <div class="z-10"></div>
            <div class="flex rounded-full ring-2 ring-pink-700 h-24 w-24 bg-gray-100 -translate-y-1/2  overflow-hidden">
              <img src="https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg">
            </div>
          </div>
          <div class="flex justify-center -translate-y-12 text-pink-500 font-bold text-2xl ">Admin</div>

          <div class="grid grid-cols-4 w-full justify-between px-3">

            <router-link to="/admin/users">
              <IconButton icon="fas fa-tachometer" label="Users" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-tachometer" label="Roles" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-gears" label="Make" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-gas-pump" label="Class" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-bolt" label="Engine" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-car" label="Gear" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-brush" label="Color" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-leaf" label="Shape" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-life-ring" label="Luxury" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-map-marker" label="Safety" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/users">
              <IconButton icon="fas fa-map-marker" label="Location" class="hover:bg-pink-100"/>
            </router-link>


          </div>


        </div>


      </div>
    </div>

    <!-- Main Content Area -->
    <div class="w-full lg:w-3/5 p-4 text-gray-400">

      <router-view/>

    </div>

    <!-- Fixed Sidebar - Ad Item -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-700 fixed">
    </div>
  </div>


</template>

<style scoped>

</style>