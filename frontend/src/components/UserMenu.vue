<script setup lang="ts">

import IconButtonLink from './IconButtonLink.vue';
import {useAuthStore} from "../stores/auth.ts";

const store =  useAuthStore();

defineProps({
  position: {
    type: String,
    default: 'bottom', // Can be 'top', 'bottom', 'left', 'right'
  },
});

const emit = defineEmits(['close']);


import {computed} from "vue";


const userRole = computed(() => {
  if (store?.userInfo?.roles?.includes("ROLE_ADMIN")) {
    return "Admin";
  }
  return "User"; // Default to "User" if "ROLE_ADMIN" is not found
});


const logout = () => {
  store.logout();
  emit('close');
}

</script>

<template>
  <div
      class="absolute rtl:-translate-x-3 ltr:translate-x-3  bg-gradient-to-r from-[#ff80b5] to-[#9089fc]  rounded-lg shadow-lg ring-1 ring-gray-300 z-50 !important bottom-full  end-0 md:block xl:hidden "
  >

    <div class="flex-col p-2">
      <h1 class="text-white text-center">{{store?.userInfo?.name}}</h1>
    </div>

    <ul class="divide-y divide-gray-200">
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Profile"
            icon="fas fa-user"
            :target="`/users/${store.userInfo?.name ?? ''}`"
            class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Admin"
            icon="fas fa-cogs"
            :target="`/admin`"
            class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="logout" target="#" text="Logout" icon="fas fa-sign-out"/>
      </li>
      <li v-if="!store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="store.logout()" target="/auth/sign-in" text="Login" icon="fas fa-sign-in"/>
      </li>
    </ul>

  </div>
  <div
      class="absolute bg-gradient-to-r from-[#ff80b5] to-[#9089fc]  rounded-lg shadow-lg ring-1 ring-gray-300 z-50 top-full md:hidden xl:block"
  >
    <div class="flex-col p-2">
      <h1 class="text-white text-lg">{{store?.userInfo?.name}}</h1>
      <h1 class="text-white rounded-full bg-purple-600 w-fit px-2">{{userRole}}</h1>
    </div>

    <ul class="divide-y divide-gray-200">
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Profile"
            icon="fas fa-user"
            :target="`/users/${store.userInfo?.name ?? ''}`"
            class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated" class="p-2">
        <IconButtonLink
            text="Admin"
            icon="fas fa-cogs"
            :target="`/admin`"
            class="text-pink-500" @click="$emit('close')"/>
      </li>
      <li v-if="store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="logout" target="#" text="Logout" icon="fas fa-sign-out"/>
      </li>
      <li v-if="!store.isAuthenticated">
        <IconButtonLink  class="text-pink-500" @click="store.logout()" target="/auth/sign-in" text="Login" icon="fas fa-sign-in"/>
      </li>
    </ul>

  </div>
</template>

<style scoped>
</style>
