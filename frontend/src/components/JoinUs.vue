<template>
  <section class="relative w-full h-auto mx-auto flex flex-col gap-3 md:flex-row   mb-24 p-6">

    <!-- Dealer Card -->
    <div class="w-full md:w-1/2 flex flex-col items-center justify-center text-gray-900 bg-gray-50 pattern-bg p-6 rounded-lg shadow-md">
      <h3 class="text-2xl font-bold">Join as a Dealer</h3>
      <p class="text-md text-gray-700 mt-2 text-center">
        Get free space to showcase your cars and reach more clients easily.
      </p>
      <button class="w-full bg-white hover:bg-gray-200 text-gray-900 font-bold py-2 rounded-lg transition shadow-md mt-4">
        Register as Dealer 🚗
      </button>
    </div>

    <!-- Buyer Card -->
    <div class="w-full md:w-1/2 p-6 bg-pink-100 backdrop-blur-md flex flex-col justify-center items-center rounded-lg shadow-md">
      <h3 class="text-2xl font-bold text-gray-900">Join as a Buyer</h3>
      <p class="text-md text-gray-700 mt-2 text-center">
        Find the best deals and chat with sellers instantly – all for free!
      </p>
      <button class="w-full bg-white hover:bg-pink-300 text-pink-700 font-bold py-2 rounded-lg transition shadow-md mt-4">
        Register as Buyer 🏎️
      </button>
    </div>

  </section>
</template>

<style scoped>
.pattern-bg {
  background-image: url('/pattern1.svg'); /* If stored in assets */
  background-size: 50px 50px;
  background-repeat: repeat;
  background-position: center;
}
</style>
