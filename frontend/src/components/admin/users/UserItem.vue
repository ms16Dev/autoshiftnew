<template>
  <div
      class="flex items-center justify-between gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
  >

      <!-- Avatar -->
      <span class="text-pink-500 font-lg font-extrabold">
        <i class="fas fa-user text-3xl text-gray-400"></i>
      </span>

      <!-- User Name -->
      <span class="text-pink-500 font-lg font-extrabold">{{ props.user.username }}</span>

      <!-- Email -->
      <span class="text-gray-500 font-lg ">{{ props.user.email }}</span>

      <!-- Status -->
      <span class="text-gray-500 font-lg ">{{ props.user.active }}</span>

      <!-- Status -->
      <span class="text-gray-500 font-lg ">{{ props.user.roles }}</span>

  </div>
</template>

<script setup lang="ts">

import { UserListDto } from '../../../core/models/UserListDto.ts'

const props = defineProps<{
  user: UserListDto
}>()


</script>
