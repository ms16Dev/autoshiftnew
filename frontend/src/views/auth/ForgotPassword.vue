<script setup lang="ts">
import { ref } from 'vue';

const email = ref('');
</script>

<template>
  <div class="relative group max-w-md mx-auto">
    <div class="absolute inset-px shadow-lg rounded-lg bg-white dark:bg-gray-900 group-hover:bg-pink-50"></div>

    <div class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg">
      <h2 class="text-xl font-bold text-center text-pink-700">Forgot Password</h2>
      <p class="text-center text-gray-600">Enter your email address to receive a password reset link.</p>

      <!-- Email Input -->
      <div class="flex flex-col">
        <label for="email" class="font-bold text-pink-700">Email</label>
        <input type="email" id="email" v-model="email" class="w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700" placeholder="Your Email" />
      </div>

      <!-- Send Reset Link Button -->
      <button class="w-full py-3 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600">
        Send Reset Link
      </button>
    </div>
  </div>
</template>
