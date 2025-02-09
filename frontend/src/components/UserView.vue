<template>
  <div
      class="flex items-center gap-3 p-2 cursor-pointer hover:bg-gray-100 rounded-lg transition"
      @click="goToProfile"
  >
    <!-- User Avatar -->
    <img :src="user.avatar || defaultAvatar" alt="User Avatar" class="w-16 h-16 rounded-full object-cover" />

    <!-- User Name -->
    <span class="text-pink-500 font-lg font-extrabold">{{ user.name }}</span>
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
      avatar: '',
      id: null
    })
  }
});

const defaultAvatar = 'https://via.placeholder.com/150?text=User'; // Placeholder image

const goToProfile = () => {
  if (props.user.id) {
    router.push(`/dealers/${props.user.id}`);
  }
};
</script>
