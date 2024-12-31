<script setup lang="ts">
import router from "../../router";

defineOptions({
  name: 'Sign-Up'
})

import { ref } from 'vue';
import ImageLoader from '../../components/ImageLoader.vue';

const userType = ref('');

function submitForm(){

  router.push("/auth/verify-email")
};
</script>

<template>
    <!-- Shadow and container styling -->



        <div class="relative group px-6 max-w-xl mx-auto">
          <form @submit.prevent="submitForm" class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg bg-white shadow-lg">

            <h1 class="text-extrabold text-center text-3xl text-pink-900">Sign Up</h1>

            <!-- Name Input -->
            <div class="flex flex-col">
              <label for="name" class="font-bold text-pink-700">Name</label>
              <input type="text" id="name" required
                     class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
                     placeholder="Your Name" />
            </div>

            <!-- Email Input -->
            <div class="flex flex-col">
              <label for="email" class="font-bold text-pink-700">Email</label>
              <input type="email" id="email" required
                     class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
                     placeholder="Your Email" />
            </div>


            <!-- Phone Number Input -->
            <div class="flex flex-col">
              <label for="phone" class="font-bold text-pink-700">Phone Number</label>
              <input type="tel" id="phone" required pattern="[0-9]{10}"
                     class="form-input w-full px-3 py-2 rounded-lg border-2 border-pink-300 focus:border-pink-700 invalid:border-red-500"
                     placeholder="Your Phone Number" />
            </div>


            <!-- Password Input -->
            <div class="flex flex-col">
              <label for="password" class="font-bold text-pink-700">Password</label>
              <input type="password" id="password" required class="w-full px-3 py-2 rounded-lg border-2 border-pink-700 focus:border-pink-700 invalid:border-red-500" placeholder="Your Password" />
            </div>

            <!-- Submit Button -->
            <button type="submit" class="w-full py-3 font-bold text-white bg-pink-700 rounded-lg hover:bg-pink-600">
              Register
            </button>
          </form>




      <!-- Sign-In Section -->
      <div class="text-center my-3">
        <p class="text-white">Already have an account?</p>
        <router-link to="/auth/sign-in" class="mt-2 text-pink-700 font-bold hover:underline">Sign In</router-link>
      </div>
  </div>
</template>

<style scoped>
/* Add any additional scoped styles here if needed */
</style>
