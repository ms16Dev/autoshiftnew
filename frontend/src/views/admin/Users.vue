<script setup lang="ts">

import IconButton from "../../components/IconButton.vue";
import UserItem from "../../components/admin/users/UserItem.vue";
</script>

<template>
  <div class="flex justify-center items-center">
    <div class="w-full  p-4 text-center text-gray-400">
      <div class="relative flex flex-col overflow-hidden rounded-lg bg-white">
        <div class="group flex-col inset-px shadow-md">


          <!--Devider-->
          <div class="w-full border-b-2 border-b-pink-700 "></div>
          <div class="flex justify-between items-center bg-gray-100">

          <div class="flex px-4  text-pink-500 font-bold text-2xl ">Users</div>
          <div class="flex p-4  text-pink-500 font-bold text-2xl ">
            <IconButton icon="fas fa-plus" label="Add" class="hover:bg-pink-100"/>
            <IconButton icon="fas fa-x" label="Delete" class="hover:bg-pink-100"/>
            <IconButton icon="fas fa-pen" label="Edit" class="hover:bg-pink-100"/>

          </div>
          </div>
          <!--Devider-->

          <div class="w-full border-b-2 border-b-pink-700 "></div>

          <!--User Item -->
          <UserItem/>
          <UserItem/>
          <UserItem/>
          <UserItem/>



        </div>


      </div>
    </div>

  </div>
</template>

<style scoped>

</style>