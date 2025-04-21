<script setup lang="ts">


import IconButton from "../components/IconButton.vue";
</script>

<template>
  <div class="flex md:flex-row flex-wrap w-full bg-gray-200">
    <!-- Sidebar - Dealer Card -->
    <div class="w-full lg:w-1/5 p-4 text-center text-gray-400">
      <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
        <div class="group flex-col inset-px shadow-md">

          <!-- Logo or Title -->
          <div class="text-center bg-gradient-to-br from-gray-900 via-gray-800 to-black text-gray-300">
            <!-- Center the logo inside the router-link -->
            <router-link to="" class="flex justify-center">
              <img src="../assets/asl.png" alt="Logo" width="120" height="120">
            </router-link>
            <h3 class="text-3xl font-bold text-white">AUTOSHIFT</h3>

            <p class="mt-2 text-gray-500">Connecting buyers and sellers in the car market</p>
          </div>
          <!--Devider-->
          <div class="w-full border-b-2 border-b-pink-700 "></div>
          <div class="flex justify-center text-pink-500 font-bold text-2xl ">Admin</div>


          <div class="flex px-2  text-pink-500 font-bold text-md ">Users & Roles</div>

          <div class="grid grid-cols-4 w-full justify-between px-3">

            <router-link to="/admin/users">
              <IconButton icon="fas fa-user" label="Users" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/roles">
              <IconButton icon="fas fa-shield" label="Roles" class="hover:bg-pink-100"/>
            </router-link>

          </div>
          <div class="flex px-2  text-pink-500 font-bold text-md ">Car Options</div>

          <div class="grid grid-cols-4 w-full justify-between px-3">


            <router-link to="/admin/makes">
              <IconButton icon="fas fa-tag" label="Make" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/classes">
              <IconButton icon="fas fa-list" label="Class" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/fuels">
              <IconButton icon="fas fa-gas-pump" label="Fuels" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/engines">
              <IconButton icon="fas fa-bolt" label="Engine" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/gears">
              <IconButton icon="fas fa-gear" label="Gear" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/colors">
              <IconButton icon="fas fa-brush" label="Color" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/shapes">
              <IconButton icon="fas fa-car" label="Shape" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/luxury">
              <IconButton icon="fas fa-leaf" label="Luxury" class="hover:bg-pink-100"/>
            </router-link>
            <router-link to="/admin/safety">
              <IconButton icon="fas fa-life-ring" label="Safety" class="hover:bg-pink-100"/>
            </router-link>



          </div>

          <div class="flex px-2  text-pink-500 font-bold text-md ">Other</div>

          <div class="grid grid-cols-4 w-full justify-between px-3">

          <router-link to="/admin/location">
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