<template>
  <nav id="header" class="fixed w-full z-30 top-0 text-white">
    <div class="w-full container mx-auto flex flex-wrap items-center justify-between mt-0 py-2">
      <div class="pl-4 flex items-center ">


        <img src="../../assets/asl.png" alt="Logo" width="100" height="100">


        <a class="toggleColour text-white no-underline hover:no-underline font-bold text-2xl lg:text-4xl" href="#">
          AUTOSHIFT
        </a>
      </div>
      <div class="block lg:hidden pr-4">
        <button
          id="nav-toggle"
          class="flex items-center p-1 text-orange-700 hover:text-gray-900 focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
          @click="mobileSidebar = !mobileSidebar"
        >
          <svg class="fill-current h-6 w-6" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
            <title>Menu</title>
            <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z" />
          </svg>
        </button>
      </div>
      <div
        id="nav-content"
        :class="mobileSidebar ? '' : 'hidden'"
        class="w-full flex-grow lg:flex lg:items-center lg:w-auto mt-2 lg:mt-0 bg-white lg:bg-transparent text-white p-4 lg:p-0 z-20"
      >
        <ul class="list-reset lg:flex justify-end flex-1 items-center">
          <li class="mr-3">
            <a class="inline-block uppercase py-2 px-4 font-medium no-underline" href="#">Welcome</a>
          </li>
          <li class="mr-3">
            <a
              class="inline-block uppercase no-underline font-medium hover:text-gray-800 hover:text-underline py-2 px-4"
              href="#about"
              >Cars</a
            >
          </li>
          <li class="mr-3">
            <a
              class="inline-block uppercase no-underline font-medium hover:text-gray-800 hover:text-underline py-2 px-4"
              href="#customers"
              >Dealers</a
            >
          </li>
          <li class="mr-3">
            <a
              class="inline-block uppercase no-underline font-medium hover:text-gray-800 hover:text-underline py-2 px-4"
              href="#pricing"
              >More</a
            >
          </li>
          <li class="mr-3">
            <a
              class="inline-block uppercase no-underline font-medium hover:text-gray-800 hover:text-underline py-2 px-4"
              href="#contact"
              >Contact</a
            >
          </li>
        </ul>
        <router-link to="/auth/sign-in">
          <button
            id="navAction"
            class="mx-auto lg:mx-0 hover:underline bg-white text-gray-800 font-bold rounded-full mt-4 lg:mt-0 py-4 px-8 shadow opacity-75 focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
          >
            Support
          </button>
        </router-link>
      </div>
    </div>
    <hr class="border-b border-gray-100 opacity-25 my-0 py-0" />
  </nav>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'LayoutNav',
  data() {
    return {
      scrollY: 0,
      mobileSidebar: false,
    }
  },

  watch: {
    scrollY(newValue: number) {
      const navaction = document.getElementById('navAction')
      const header = document.getElementById('header')
      const toToggle = document.querySelector('.toggleColour')
      if (newValue > 10) {
        navaction?.classList.remove('bg-white')
        navaction?.classList.add('gradient')
        navaction?.classList.remove('text-[#170F4F]')
        navaction?.classList.add('text-white')
        header?.classList.add('bg-white')
        header?.classList.add('navbar-active')
        toToggle?.classList.add('text-purple-800')
        toToggle?.classList.remove('text-white')
      } else {
        navaction?.classList.add('bg-white')
        navaction?.classList.remove('gradient')
        navaction?.classList.add('text-[#170F4F]')
        navaction?.classList.remove('text-white')
        header?.classList.remove('bg-white')
        header?.classList.remove('navbar-active')
        toToggle?.classList.remove('text-purple-800')
        toToggle?.classList.add('text-white')
      }
    },
  },
  mounted() {
    this.scrollY = window.scrollY
    document.getElementById('main-css')?.remove()

    // Listen to the scroll event to update the scrollY property dynamically
    window.addEventListener('scroll', this.handleScroll)
  },

  methods: {
    handleScroll() {
      this.scrollY = window.scrollY
    },
  },
})
</script>

<style scoped>
a {
  font-weight: 600;
  font-style: normal !important;
  color: #7e1972;
}

.navbar-active ul li a {
  color: #170f4f;
}
</style>
