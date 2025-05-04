<script setup lang="ts">
import AdItem from "../components/AdItem.vue";
import JoinUs from "../components/JoinUs.vue";
import AdvertiseWithUs from "../components/AdvertiseWithUs.vue";

defineOptions({
  name: 'More'
})
</script>


<template>
  <div class="flex xl:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full xl:w-1/5  p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full xl:w-3/5  p-4 text-center text-gray-200">

      <JoinUs/>

      <div class="container relative z-10 mx-auto flex flex-wrap pt-4 pb-12">
        <h2 class="w-full my-2 text-2xl font-bold leading-tight text-center text-purple-500">Soon in AUTOSHIFT</h2>

        <div data-aos="fade-up" class="w-full mb-4">
          <div class="h-1 mx-auto gradient w-64 opacity-25 my-0 py-0 rounded-t"></div>
        </div>
        <!--        Begin SDLC cards-->
        <!--        SDLC card-->

        <div data-aos="zoom-in-down" class="w-full md:w-1/4 p-6 flex flex-col flex-grow flex-shrink">
          <div class="flex-1 bg-purple-100 rounded-t rounded-b-none overflow-hidden shadow">
            <div class="flex items-center justify-start">
              <button
                  class="mx-4 lg:mx-4 hover:underline gradient text-white font-bold rounded-full my-6 py-4 px-8 shadow-lg focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
              >
                Parts
              </button>
            </div>
            <a href="#" class="flex flex-wrap no-underline hover:no-underline">
              <p class="text-gray-800 text-base px-6 mb-5">Understand Requirements</p>
              <p class="text-gray-500 text-base px-6 mb-5">
                Begin by gathering requirements to ensure the project aligns with your goals and needs. Clear
                communication at this stage sets the foundation for a successful development process.
              </p>
            </a>
          </div>
        </div>
        <!--        SDLC card-->

        <div data-aos="zoom-in-down" class="w-full md:w-1/4 p-6 flex flex-col flex-grow flex-shrink">
          <div class="flex-1 bg-purple-100 rounded-t rounded-b-none overflow-hidden shadow">
            <div class="flex items-center justify-start">
              <button
                  class="mx-4 lg:mx-4 hover:underline gradient text-white font-bold rounded-full my-6 py-4 px-8 shadow-lg focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
              >
                Services
              </button>
            </div>
            <a href="#" class="flex flex-wrap no-underline hover:no-underline">
              <p class="text-gray-800 text-base px-6 mb-5">Craft Solutions</p>
              <p class="text-gray-500 text-base px-6 mb-5">
                Next, design the architecture and user interface, focusing on creating a seamless and intuitive
                experience. Every element is planned for efficiency and scalability.
              </p>
            </a>
          </div>
        </div>
        <!--        SDLC card-->

        <div data-aos="zoom-in-down" class="w-full md:w-1/4 p-6 flex flex-col flex-grow flex-shrink">
          <div class="flex-1 bg-purple-100 rounded-t rounded-b-none overflow-hidden shadow">
            <div class="flex items-center justify-start">
              <button
                  class="mx-4 lg:mx-4 hover:underline gradient text-white font-bold rounded-full my-6 py-4 px-8 shadow-lg focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
              >
                Auctions
              </button>
            </div>
            <a href="#" class="flex flex-wrap no-underline hover:no-underline">
              <p class="text-gray-800 text-base px-6 mb-5">Build and Integrate</p>
              <p class="text-gray-500 text-base px-6 mb-5">
                Write clean, maintainable code, integrating various systems and ensuring everything works together
                flawlessly. Security and performance are always top priorities.
              </p>
            </a>
          </div>
        </div>

      </div>


      <AdvertiseWithUs/>


    </div>

    <div class="w-full xl:w-1/5 p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>

    </div>
  </div>
</template>

<style scoped>
.gradient {
  @apply  bg-gradient-to-r from-[#ff80b5] to-[#9089fc]
}
</style>