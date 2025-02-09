<script setup lang="ts">
import { ref } from 'vue';
import {UserLoginDto} from "../../core/models/UserLoginDto.ts";
import {useAuthStore} from "../../stores/auth.ts";
import router from "../../router";
import Swal from 'sweetalert2';


// Define reactive form fields
const username = ref('');
const password = ref('');


const store = useAuthStore();

const onSubmitLogin = async () => {

  const login: UserLoginDto = {
    username: username.value,
    password: password.value
  };

  console.log(login)
  // Clear existing errors
  // await store.logout();


  // Send login request
  await store.login(login);
  const error = Object.values(store.errors);

  if (error.length === 0) {
    Swal.fire({
      text: "Login Successful",
      icon: "success",
      buttonsStyling: false,
      confirmButtonText: "Ok",
      heightAuto: false,
      customClass: {
        confirmButton: "btn fw-semibold btn-light-primary",
      },
    }).then(() => {
      // Go to page after successfully login
      router.push({ name: "home" });
    });
  } else {
    Swal.fire({
      text: error[0] as string,
      icon: "error",
      buttonsStyling: false,
      confirmButtonText: "Try again!",
      heightAuto: false,
      customClass: {
        confirmButton: "btn fw-semibold btn-light-danger",
      },
    }).then(() => {
      store.errors = {};
    });
  }

};






</script>

<template>
  <div class="relative group px-6 max-w-xl mx-auto">
    <form @submit.prevent="onSubmitLogin" class="relative flex flex-col p-6 space-y-6 overflow-hidden rounded-lg bg-white shadow-lg">

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
