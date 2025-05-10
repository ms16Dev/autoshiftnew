<script setup lang="ts">
import AdItem from "../components/AdItem.vue";
import JoinUs from "../components/JoinUs.vue";
import { useI18n } from 'vue-i18n'
import UserMenuButton from "../components/UserMenuButton.vue";
import {ref, watch} from "vue";
import i18n from "../plugins/i18n.ts";
import {useStaticDataStore} from "../stores/staticDataStore.ts";
const { t } = useI18n()
defineOptions({
  name: 'More'
})
const staticData = useStaticDataStore();


const currentLang = ref(localStorage.getItem("lang") || "en");

const toggleLanguage = (lang: string) => {
  if (currentLang.value === lang) {
    return; // Do nothing if the selected language is already active
  }

  currentLang.value = lang; // Set the selected language
  i18n.global.locale.value = lang as 'en' | 'ar';

  // Update `html` direction
  document.documentElement.lang = currentLang.value;
  document.documentElement.dir = currentLang.value === "ar" ? "rtl" : "ltr";


  // Save preference
  localStorage.setItem("lang", currentLang.value);

  staticData.toggleLanguage(lang)

};

// Apply saved language on load
watch(currentLang, (newLang) => {
  document.documentElement.lang = newLang;
  document.documentElement.dir = newLang === "ar" ? "rtl" : "ltr";
}, { immediate: true });


</script>


<template>
  <div class="flex xl:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full xl:w-1/5  p-4 text-center text-gray-400">
      <div class="flex justify-between bg-pink-500 rounded-full h-[50px]  md:hidden">
        <user-menu-button icon="" text=""/>

        <img src="../assets/asl.png" alt="logo" >

        <div class="flex flex-row justify-between p-2">
          <button
              v-if="currentLang !== 'en'"
              @click="() => { toggleLanguage('en'); $emit('close'); }"
              class="rounded-full text-sm xl:text-sm bg-purple-400 hover:bg-purple-700 text-white w-full m-1 p-1"
          >
            English
          </button>

          <button
              v-if="currentLang !== 'ar'"
              @click="() => { toggleLanguage('ar'); $emit('close'); }"
              class="rounded-full text-sm bg-purple-400 hover:bg-purple-700 text-white w-full m-1 p-1"
          >
            عربي
          </button>
        </div>


      </div>
      <AdItem/>

    </div>
    <div class="w-full xl:w-3/5  p-4 text-center text-gray-200">

      <JoinUs/>

      <div class="container relative z-10 mx-auto flex flex-wrap pt-4 pb-12">
        <h2 class="w-full my-2 text-2xl font-bold leading-tight text-center text-purple-500">{{t('soon_in_as')}}</h2>

        <div data-aos="fade-up" class="w-full mb-4">
          <div class="h-1 mx-auto gradient w-64 opacity-25 my-0 py-0 rounded-t"></div>
        </div>
        <!--        Begin SDLC cards-->
        <!--        SDLC card-->

        <div data-aos="zoom-in-down" class="w-full md:w-1/4 p-6 flex flex-col flex-grow flex-shrink">
          <div class="flex-1 bg-white rounded-t rounded-b-none overflow-hidden shadow">
            <div class="flex items-center justify-start">
              <button
                  class="mx-4 lg:mx-4 hover:underline gradient text-white font-bold rounded-full my-6 py-4 px-8 shadow-lg focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
              >
                {{ t('parts') }}
              </button>
            </div>
            <img src="/Auto-Parts.webp">
          </div>
        </div>
        <!--        SDLC card-->

        <div data-aos="zoom-in-down" class="w-full md:w-1/4 p-6 flex flex-col flex-grow flex-shrink">
          <div class="flex-1 bg-white rounded-t rounded-b-none overflow-hidden shadow">
            <div class="flex items-center justify-start">
              <button
                  class="mx-4 lg:mx-4 hover:underline gradient text-white font-bold rounded-full my-6 py-4 px-8 shadow-lg focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
              >
                {{ t('services') }}
              </button>
            </div>

            <img src="/auto-service.jpg">

          </div>
        </div>
        <!--        SDLC card-->

        <div data-aos="zoom-in-down" class="w-full md:w-1/4 p-6 flex flex-col flex-grow flex-shrink">
          <div class="flex-1 bg-white rounded-t rounded-b-none overflow-hidden shadow">
            <div class="flex items-center justify-start">
              <button
                  class="mx-4 lg:mx-4 hover:underline gradient text-white font-bold rounded-full my-6 py-4 px-8 shadow-lg focus:outline-none focus:shadow-outline transform transition hover:scale-105 duration-300 ease-in-out"
              >
                {{ t('auctions') }}
              </button>
            </div>
            <img src="/Car-Auction.jpg">
          </div>
        </div>

      </div>




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