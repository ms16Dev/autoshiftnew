<template>
  <div
      class="flex items-center justify-between gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
      @click="goToProfile"
  >

      <!-- Avatar -->
      <span class="text-pink-500 font-lg font-extrabold">
        <i class="fas fa-user text-3xl text-gray-400"></i>
      </span>

      <!-- User Name -->
      <span class="text-pink-500 font-lg font-extrabold">{{ user.username }}</span>

      <!-- Email -->
      <span class="text-gray-500 font-lg ">{{ user.email }}</span>

      <!-- Status -->
      <span class="text-gray-500 font-lg ">{{ user.active }}</span>

      <!-- Status -->
      <span class="text-gray-500 font-lg ">{{ user.roles }}</span>

  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  user: {
    type: Object,
    required: true,
    default: () => ({
      email: 'mhmn@gmail.com',
      username: 'User Name',
      date: "2 days ago",
      avatar: '/vite.svg',
      id: null,
      active:'Active',
      roles: 'Admin'
    })
  }
});


const goToProfile = () => {
  if (props.user.id) {
    router.push(`/dealers/${props.user.id}`);
  }
};
</script>
