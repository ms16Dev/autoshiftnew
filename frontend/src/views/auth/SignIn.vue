<script setup lang="ts">
import { ref } from 'vue';
import ApiService from "../../services/ApiService.ts";

// Define reactive form fields
const username = ref('');
const password = ref('');

// Login function
function login() {
  // Pass email and password as an object to the API service
  const values = {
    username: username.value,
    password: password.value,
  };

  // Send the login request
  return ApiService.post("/login", values)
      .then(({ data }) => {
        // Handle success response
        alert('Logged in successfully!');
        console.log(data);  // or handle authentication as needed
      })
      .catch(({ response }) => {
        // Handle error response
        alert('Login failed');
        console.error(response.data.errors);  // You can handle error messages here
      });
}
</script>

<template>
  <div class="relative group px-6 max-w-xl mx-auto">
    <form @submit.prevent="login" class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg bg-white shadow-lg">

      <!-- Email Input -->
      <div class="flex flex-col">
        <label for="username" class="font-bold text-pink-700">Username</label>
        <input type="text" id="username" required v-model="username"
               class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
               placeholder="Your username" />
      </div>

      <!-- Password Input -->
      <div class="flex flex-col">
        <label for="password" class="font-bold text-pink-700">Password</label>
        <input type="password" id="password" v-model="password" class="w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700" placeholder="Your Password" />
      </div>

      <!-- Sign-In Button -->
      <button class="w-full py-3 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600">
        Sign In
      </button>

      <!-- Forgot Password Link -->
      <div class="text-center mt-4">
        <router-link class="text-pink-700 font-bold hover:underline" to="/auth/forgot-password">Forgot Password?</router-link>
      </div>

    </form>

    <!-- Sign-Up Section -->
    <div class="text-center my-3">
      <p class="text-white">Not a user?</p>
      <router-link to="/auth/sign-up" class="mt-2 text-pink-700 font-bold hover:underline">Sign Up</router-link>
    </div>
  </div>
</template>
