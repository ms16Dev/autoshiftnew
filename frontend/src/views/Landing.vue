<template>
  <div class="w-full">
    <!-- Hero Section -->
    <section class="relative h-screen flex items-center justify-center text-white text-center ">
      <div class="absolute inset-0 bg-fixed bg-cover bg-center parallax-bg-1 "></div>
      <div class="flex items-center  w-full h-screen   z-10 bg-gradient-to-br from-gray-900 via-transparent via-20%  to-black backdrop-blur-sm">
        <div class="relative w-full">
        <h1 class="text-5xl font-extrabold drop-shadow-lg">Welcome to AutoShift</h1>
        <p class="mt-4 text-lg">Find the best deals or grow your dealership for free!</p>
        <router-link to="/join-us" class="mt-6 px-6 py-3 bg-pink-500 hover:bg-pink-700 text-white rounded-lg shadow-lg inline-block transition">
          Get Started 🚗
        </router-link>
        </div>

      </div>
    </section>

    <!-- Features Section -->
    <section class="relative bg-gray-900 text-white">
      <div class="absolute inset-0 bg-fixed bg-cover bg-center parallax-bg-2"></div>
      <div class="flex items-center  w-full h-screen   z-10 bg-gradient-to-bl from-gray-900  via-transparent via-10%  to-black backdrop-blur-sm">
        <div class="relative w-full ">
        <h2 class="text-4xl font-bold">Why AutoShift?</h2>
        <p class="mt-4 text-lg">A free and powerful platform to connect buyers and sellers effortlessly.</p>
        <div class="mt-6 flex flex-col md:flex-row gap-6 justify-center">
          <div class="p-6 bg-gray-800 bg-opacity-80 rounded-lg shadow-lg w-full md:w-1/3">
            <h3 class="text-xl font-semibold">🚀 Sell Faster</h3>
            <p class="mt-2 text-gray-300">Post listings and reach thousands of buyers instantly.</p>
          </div>
          <div class="p-6 bg-gray-800 bg-opacity-80 rounded-lg shadow-lg w-full md:w-1/3">
            <h3 class="text-xl font-semibold">💰 Best Deals</h3>
            <p class="mt-2 text-gray-300">Find competitive prices and connect directly with sellers.</p>
          </div>
          <div class="p-6 bg-gray-800 bg-opacity-80 rounded-lg shadow-lg w-full md:w-1/3">
            <h3 class="text-xl font-semibold">📢 Free Advertising</h3>
            <p class="mt-2 text-gray-300">Dealers get free exposure to their potential customers.</p>
          </div>
        </div>
        </div>
      </div>
    </section>

    <!-- Call to Action -->
    <section class="relative flex items-center justify-center text-white">
      <div class="absolute inset-0 bg-fixed bg-cover bg-center parallax-bg-3"></div>
      <div class="flex items-center  w-full h-screen   z-10 bg-gradient-to-tr from-gray-900  via-transparent via-10%  to-black backdrop-blur-sm">
        <h2 class="text-3xl font-bold">Ready to Join?</h2>
        <router-link to="/register" class="mt-4 px-6 py-3 bg-purple-500 hover:bg-purple-700 text-white rounded-lg shadow-lg inline-block transition">
          Sign Up Now 🚘
        </router-link>

      </div>
    </section>
  </div>
</template>

<style scoped>
/* Parallax Backgrounds */
.parallax-bg-1 {
  background-image: url('https://car-images.bauersecure.com/pagefiles/74108/1752x1168/toyota_hybrids_01.jpg?mode=max&quality=90&scale=down');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}
.parallax-bg-2 {
  background-image: url('https://th.bing.com/th/id/OIP.ZAyXhHsIY4fpVbSm3DZKWQHaEK?w=875&h=492&rs=1&pid=ImgDetMain');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}
.parallax-bg-3 {
  background-image: url('https://th.bing.com/th/id/R.80dbc6f688f302d511616b8d063a1b9c?rik=anvTJW1eD7YTqA&pid=ImgRaw&r=0');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}

/* Mobile Fix for Parallax */
@media (max-width: 768px) {
  .parallax-bg-1, .parallax-bg-2, .parallax-bg-3 {
    background-attachment: scroll;
  }
}
</style>
<script setup lang="ts">
</script>