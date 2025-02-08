<template>
  <section class="relative w-full h-auto mx-auto flex flex-col gap-3 md:flex-row bg-gradient-to-br from-purple-900 via-purple-600 to-black  mb-24 p-6">

    <!-- Dealer Card -->
    <div class="w-full  flex flex-col items-center justify-center text-white p-6 ">
      <h3 class="text-2xl font-bold">Join Us Now</h3>
      <p class="text-md text-white mt-2 text-center">
        Get free space to showcase your cars and reach more clients easily.
      </p>
      <router-link to="auth/sign-up">
        <button class="bg-white px-3 hover:bg-gray-300 text-gray-900 font-bold py-2 rounded-lg transition shadow-md mt-4">
          Register Now
        </button>
      </router-link>

    </div>



  </section>
</template>

<style scoped>

</style>
<script setup lang="ts">
</script>