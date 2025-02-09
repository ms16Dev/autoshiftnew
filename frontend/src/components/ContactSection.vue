<template>
  <div class="pattern-bg relative w-full h-auto mx-auto flex flex-col md:flex-row bg-gradient-to-br from-gray-900 via-gray-800 to-black overflow-hidden shadow-lg mb-24">

    <!-- Left Side (Contact Info / Illustration) -->
    <div class="w-full md:w-1/2 flex flex-col items-center justify-center text-gray-900 bg-gray-50 pattern-bg p-6">
      <h2 class="text-2xl font-bold">Let's Connect</h2>
      <p class="text-md text-gray-700 mt-2 text-center">Feel free to reach out anytime.</p>
      <div class="mt-4 flex space-x-3">
        <a href="#" class="text-xl hover:text-gray-300 transition">📧</a>
        <a href="#" class="text-xl hover:text-gray-300 transition">📞</a>
        <a href="#" class="text-xl hover:text-gray-300 transition">🌐</a>
      </div>
    </div>

    <!-- Right Side (Form) -->
    <div class="w-full md:w-1/2 p-6 bg-pink-100 backdrop-blur-md flex flex-col justify-center">
      <form @submit.prevent="submitForm" class="space-y-2">
        <input type="text" v-model="form.name" placeholder="Your Name" class="w-full p-2 border-b border-gray-400 text-gray-900 focus:ring-0 outline-none" required>
        <input type="email" v-model="form.email" placeholder="Your Email" class="w-full p-2 border-b border-gray-400 text-gray-900 focus:ring-0 outline-none" required>
        <input type="text" v-model="form.message" placeholder="Your Message" class="w-full p-2 border-b border-gray-400 text-gray-900 focus:ring-0 outline-none" required>
        <button type="submit" class="w-full bg-pink-500 hover:bg-pink-700 text-white font-bold py-2 rounded-lg transition shadow-md mt-3">
          Send 🚀
        </button>
      </form>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue";

const form = reactive({
  name: "",
  email: "",
  message: "",
});

const submitForm = () => {
  alert(`Message Sent by ${form.name}!`);
};
</script>

<style scoped>
.pattern-bg {
  background-image: url('/pattern1.svg'); /* If stored in assets */
  background-size: 50px 50px;
  background-repeat: repeat; /* Tiling effect */
  background-position: center;
}
</style>
