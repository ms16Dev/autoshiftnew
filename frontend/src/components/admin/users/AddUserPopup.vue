<script setup lang="ts">
import { ref } from 'vue';

const emit = defineEmits(['close', 'user-added']);

const username = ref('');
const email = ref('');
const password = ref('');

const handleSubmit = async () => {
  try {
    // Here you would typically call your API service to add the user
    // For example:
    // await apiService.post("users", {
    //   username: username.value,
    //   email: email.value,
    //   password: password.value
    // });

    // Emit event that user was added
    emit('user-added');

    // Reset form
    username.value = '';
    email.value = '';
    password.value = '';

  } catch (error) {
    console.error("Error adding user:", error);
    // You might want to add error handling here
  }
};

const handleClose = () => {
  emit('close');
};
</script>

<template>
  <div class="absolute bg-pink-700 shadow-lg p-6 h-full w-full z-50">
    <!-- Back button at top left -->
    <button
        @click="handleClose"
        class="absolute top-0 left-0 w-12 h-12 text-white hover:bg-white hover:text-pink-700 transition duration-200"
        aria-label="Close"
    >
      <i class="fas fa-arrow-left text-xl"></i>
    </button>

    <h2 class="text-xl font-bold text-white mb-4 text-center">Add User</h2>
    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label for="username" class="block text-white">Username</label>
        <input
            type="text"
            id="username"
            v-model="username"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>
      <div class="mb-4">
        <label for="email" class="block text-white">Email</label>
        <input
            type="email"
            id="email"
            v-model="email"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>
      <div class="mb-4">
        <label for="password" class="block text-white">Password</label>
        <input
            type="password"
            id="password"
            v-model="password"
            required
            class="border border-gray-300 rounded px-3 py-2 w-full"
        />
      </div>
      <div class="flex justify-end">
        <button
            type="submit"
            class="bg-pink-500 text-white font-bold py-2 px-4 rounded hover:bg-pink-900 transition duration-200"
        >
          Add User
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
/* You can add any custom styles here if needed */
</style>