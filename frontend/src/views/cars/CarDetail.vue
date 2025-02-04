<script setup lang="ts">
import AdItem from "../../components/AdItem.vue";
import Comments from "../../components/Comments.vue";


defineOptions({
  name: 'car-details'
});
</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full md:w-1/5 p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full md:w-3/5  p-4 text-center text-gray-200">

      <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-2 -px-4 gap-3">

<!--        Headline-->

        <div class="flex flex-wrap text-3xl text-pink-500 font-extrabold col-span-2 text-center">
          <span>Toyota -</span>
          <span>Yaris -</span>
          <span>2024 -</span>
          <span>Korean</span>
        </div>
        <div class="flex flex-wrap text-xl text-gray-500  col-span-2 text-center">
            <div class="w-12 h-12 rounded-full border-2 border-pink-900 overflow-hidden">
              <img src="/vite.svg">
            </div>
          <span>Al Najm Cars</span>
          <span> - Sana'a -</span>
          <span> 2 days ago </span>
        </div>

<!--        Image Viewer-->

        <div class="w-full col-span-2 h-[460px]">
          <img src="/car-phldr.png" class="w-full h-full object-cover">
        </div>
        <div class="flex flex-wrap col-span-2 gap-3">
          <img src="/car-phldr.png" width="80" height="80"/>
          <img src="/car-phldr.png" width="80" height="80"/>
          <img src="/car-phldr.png" width="80" height="80"/>
          <img src="/car-phldr.png" width="80" height="80"/>
          <img src="/car-phldr.png" width="80" height="80"/>
          <img src="/car-phldr.png" width="80" height="80"/>

        </div>

        <div class="flex flex-wrap col-span-2 gap-3">
          <button class="flex flex-col items-center justify-center" >
            <i class="w-16 h-16 rounded-full fas fa-tachometer bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
           gfdgfd</span>
          </button>

          <button class="flex flex-col items-center justify-center"  >
            <i class="w-16 h-16 rounded-full fas fa-gears bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
           hfghfg</span>
          </button>

          <button class="flex flex-col items-center justify-center" hfghfghfgh>
            <i class="w-16 h-16 rounded-full fas fa-gas-pump bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">ghfhgf</p>
          </button>

          <button class="flex flex-col items-center justify-center" >
            <i class="w-16 h-16 rounded-full fas fa-bolt bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <p class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">sfdsfsd</p>
          </button>

          <button class="flex flex-col items-center justify-center" >
            <i class="w-16 h-16 rounded-full fas fa-car bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">reddddd
            </span>
          </button>

          <button class="flex flex-col items-center justify-center" >
            <i class="w-16 h-16 rounded-full fas fa-brush bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <span class="text-pink-500 text-sm font-bold text-center px-2 h-[30px] flex items-center justify-center">
           dfsdsf</span>
          </button>


        </div>


        <div class="flex flex-col items-start col-span-2">
          <button class="flex flex-row items-center justify-center p-4" >
            <i class="w-16 h-16 rounded-full fas fa-leaf bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <p class="text-pink-500 text-xl font-bold text-center px-2 h-[30px] flex items-center justify-center">Luxury</p>
          </button>
          <div class="flex flex-wrap col-span-2">
            <button class="text-gray-400 text-lg bg-pink-100 rounded-full m-1 p-1">
              <i :class="['fas', 'fa-check', 'text-2xl text-center w-12 h-8  md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center me-2']" />
              <span class=" font-medium">dsaads</span>
            </button>
            <button class="text-gray-400 text-lg bg-pink-100 rounded-full m-1 p-1">
              <i :class="['fas', 'fa-check', 'text-2xl text-center w-12 h-8  md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center me-2']" />
              <span class=" font-medium">dsaads</span>
            </button>
            <button class="text-gray-400 text-lg bg-pink-100 rounded-full m-1 p-1">
              <i :class="['fas', 'fa-check', 'text-2xl text-center w-12 h-8  md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center me-2']" />
              <span class=" font-medium">dsaads</span>
            </button>
          </div>


          <button class="flex flex-row items-center justify-center p-4" >
            <i class="w-16 h-16 rounded-full fas fa-life-ring bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <p class="text-pink-500 text-xl font-bold text-center px-2 h-[30px] flex items-center justify-center">Safety</p>
          </button>
          <div class="flex flex-wrap col-span-2">
            <button class="text-gray-400 text-lg bg-pink-100 rounded-full m-1 p-1">
              <i :class="['fas', 'fa-check', 'text-2xl text-center w-12 h-8  md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center me-2']" />
              <span class=" font-medium">dsaads</span>
            </button>
            <button class="text-gray-400 text-lg bg-pink-100 rounded-full m-1 p-1">
              <i :class="['fas', 'fa-check', 'text-2xl text-center w-12 h-8  md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center me-2']" />
              <span class=" font-medium">dsaads</span>
            </button>
            <button class="text-gray-400 text-lg bg-pink-100 rounded-full m-1 p-1">
              <i :class="['fas', 'fa-check', 'text-2xl text-center w-12 h-8  md:h-10 xl:w-5 xl:h-8 xl:text-lg xl:text-center me-2']" />
              <span class=" font-medium">dsaads</span>
            </button>
          </div>


          <button class="flex flex-row items-center justify-center p-4" >
            <i class="w-16 h-16 rounded-full fas fa-life-ring bg-pink-300 text-white text-xl p-2  flex items-center justify-center"></i>
            <p class="text-pink-500 text-xl font-bold text-center px-2 h-[30px] flex items-center justify-center">Seller notes</p>
          </button>

          <p class="text-gray-400 text-lg">
            Lorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg eryw

          </p>


          <Comments></Comments>




        </div>













      </div>




    </div>

    <div class="w-full md:w-1/5  p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>

    </div>
  </div></template>


<style scoped>

</style>