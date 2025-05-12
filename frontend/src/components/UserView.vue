<template>
  <div
      class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
      @click="goToProfile"
  >
    <!-- User Avatar -->
    <img :src="user.avatar || defaultAvatar" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

    <div class="flex flex-col items-start">
      <!-- User Name -->
      <span class="text-pink-500 font-lg font-extrabold">{{ user.name }}</span>
      <!-- Date -->
      <span class="text-gray-500 text-sm ">{{ user.date }}</span>
    </div>

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
      name: 'User Name',
      date: "2days ago",
      avatar:  '../assets/user.jpeg',
      id: null
    })
  }
});

const defaultAvatar = '../assets/user.jpeg'; // Placeholder image

const goToProfile = () => {
  if (props.user.id) {
    router.push(`/dealers/${props.user.id}`);
  }
};
</script>
