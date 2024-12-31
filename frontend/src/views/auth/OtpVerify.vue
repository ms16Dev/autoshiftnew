<script setup lang="ts">
import { ref } from 'vue';

const otpCode = ref('');
</script>

<template>
  <div class="relative group max-w-md mx-auto">
    <div class="absolute inset-px shadow-lg rounded-lg bg-white dark:bg-gray-900 group-hover:bg-pink-50"></div>

    <div class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg">
      <h2 class="text-xl font-bold text-center text-pink-700">Enter OTP Code</h2>
      <p class="text-center text-gray-600">Please enter the 6-digit code sent to your phone number.</p>

      <!-- OTP Input -->
      <div class="flex flex-col">
        <label for="otpCode" class="font-bold text-pink-700">OTP Code</label>
        <input type="text" id="otpCode" v-model="otpCode" maxlength="6" class="w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 text-center" placeholder="123456" />
      </div>

      <!-- Verify OTP Button -->
      <button class="w-full py-3 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600">
        Verify OTP
      </button>
    </div>
  </div>
</template>
