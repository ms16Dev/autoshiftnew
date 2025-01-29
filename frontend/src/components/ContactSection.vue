<template>
  <div class="relative w-full  h-[300px] mx-auto flex bg-gradient-to-br from-gray-900 via-gray-800 to-black  overflow-hidden shadow-lg">

    <!-- Left Side (Contact Info / Illustration) -->
    <div class="w-1/2 flex flex-col items-center justify-center text-white p-6 bg-gradient-to-tl from-white to-pink-600">
      <h2 class="text-2xl font-bold">Let's Connect</h2>
      <p class="text-sm text-gray-200 mt-2 text-center">Feel free to reach out anytime.</p>
      <div class="mt-4 flex space-x-3">
        <a href="#" class="text-xl hover:text-gray-300 transition">📧</a>
        <a href="#" class="text-xl hover:text-gray-300 transition">📞</a>
        <a href="#" class="text-xl hover:text-gray-300 transition">🌐</a>
      </div>
    </div>

    <!-- Right Side (Form) -->
    <div class="w-1/2 p-6 bg-white/10 backdrop-blur-md flex flex-col justify-center">
      <form @submit.prevent="submitForm" class="space-y-2">
        <input type="text" v-model="form.name" placeholder="Your Name" class="w-full p-2 bg-transparent border-b border-gray-400 text-white focus:ring-0 outline-none" required>
        <input type="email" v-model="form.email" placeholder="Your Email" class="w-full p-2 bg-transparent border-b border-gray-400 text-white focus:ring-0 outline-none" required>
        <input type="text" v-model="form.message" placeholder="Your Massage" class="w-full p-2 bg-transparent border-b border-gray-400 text-white focus:ring-0 outline-none" required>
        <button type="submit" class="w-full bg-pink-500 hover:bg-pink-600 text-white font-bold py-2 rounded-lg transition shadow-md mt-3">
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
