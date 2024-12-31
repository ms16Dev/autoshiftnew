<script setup lang="ts">
import { ref } from 'vue';

const newPassword = ref('');
const confirmPassword = ref('');
</script>

<template>
  <div class="relative group max-w-md mx-auto">
    <div class="absolute inset-px shadow-lg rounded-lg bg-white dark:bg-gray-900 group-hover:bg-pink-50"></div>

    <div class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg">
      <h2 class="text-xl font-bold text-center text-pink-700">Reset Password</h2>
      <p class="text-center text-gray-600">Enter a new password for your account.</p>

      <!-- New Password Input -->
      <div class="flex flex-col">
        <label for="newPassword" class="font-bold text-pink-700">New Password</label>
        <input type="password" id="newPassword" v-model="newPassword" class="w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700" placeholder="New Password" />
      </div>

      <!-- Confirm Password Input -->
      <div class="flex flex-col">
        <label for="confirmPassword" class="font-bold text-pink-700">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" class="w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700" placeholder="Confirm Password" />
      </div>

      <!-- Reset Password Button -->
      <button class="w-full py-3 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600">
        Reset Password
      </button>
    </div>
  </div>
</template>
