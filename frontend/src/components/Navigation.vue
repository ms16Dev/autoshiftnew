<script setup lang="ts">

import IconButtonLink from "./IconButtonLink.vue";
import UserMenuButton from "./UserMenuButton.vue";

import {ref, watch} from "vue";
import {useStaticDataStore} from "../stores/staticDataStore.ts";
import i18n from "../plugins/i18n.ts";
import CountryMenuButton from "./CountryMenuButton.vue";

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
      <div class="fixed z-20 bottom-0 w-full h-[50px] bg-gradient-to-r from-[#ff80b5] to-[#9089fc] opacity-95
           md:w-[80px] md:h-full md:left-0 md:top-1/2 md:transform md:-translate-y-1/2 md:bg-gradient-to-t   start-auto md:start-0
           xl:w-2/3 xl:h-[50px] xl:top-12 ltr:xl:left-1/2 rtl:xl:right-1/2 xl:transform rtl:xl:translate-x-1/2 ltr:xl:-translate-x-1/2 xl:rounded-full xl:bg-gradient-to-r">
        <router-link to="/" class="fixed z-50 hidden w-fit   md:block  xl:-translate-y-[15px]">
          <img src="../assets/asl.png" alt="Logo" width="120" height="120">
        </router-link>
        <div class="absolute md:w-full z-10 md:start-0 xl:top-2 xl:start-28 hidden md:block md:top-12 ">
          <CountryMenuButton></CountryMenuButton>

        </div>

        <div class="flex justify-center h-full md:h-[100px] md:w-full md:flex-col md:absolute md:top-1/2 md:-translate-y-1/2  xl:flex-row xl:h-full xl:justify-center">
          <IconButtonLink
              :text="'Home'"
              :icon="['fas', 'fa-home']"
              target="/home"
              :class="{ '!bg-pink-600': $route.path === '/home' }"
          />
          <IconButtonLink
              :text="'Cars'"
              :icon="['fas', 'fa-car']"
              target="/cars"
              :class="{ '!bg-pink-600': $route.path.startsWith('/cars') }"

          />
          <div class="hidden xl:block absolute translate-y-12 text-pink-500 font-extrabold ltr:-translate-x-1/2 rtl:translate-x-14"
               :class="{ 'xl:hidden': !$route.path.match(/^\/cars\/[\w-]+$/) || $route.path === '/cars/add-car'}"

          >> Car Details

          </div>
          <div class="hidden xl:block absolute translate-y-12 text-pink-500 font-extrabold ltr:-translate-x-16 rtl:translate-x-16"
               :class="{ 'xl:hidden': $route.path !== '/cars/add-car' }"

          >> Add Car

          </div>
          <IconButtonLink
              :text="'Dealers'"
              :icon="['fas', 'fa-heartbeat']"
              target="/dealers"
              :class="{ '!bg-pink-600': $route.path.startsWith('/dealers') }"

          />
          <div class="hidden xl:block absolute translate-y-12 text-pink-500 font-extrabold ltr:translate-x-1/2 rtl:-translate-x-1/2"
               :class="{ 'xl:hidden': !$route.path.match(/^\/dealers\/[\w-]+$/) }"
          >> Dealer Profile
          </div>
          <IconButtonLink
              :text="'More'"
              :icon="['fas', 'fa-ellipsis-h']"
              target="/more"
              :class="{ '!bg-pink-600': $route.path === '/more' }"

          />
        </div>
        <div class="absolute z-10 md:end-0 xl:top-0 xl:end-12 hidden md:block md:bottom-12 ltr:md:-translate-x-3 rtl:md:translate-x-3 ltr:lg:-translate-x-0 rtl:lg:translate-x-0">
          <div class="flex flex-row justify-between p-2">
            <button
                v-if="currentLang !== 'en'"
                @click="() => { toggleLanguage('en'); $emit('close'); }"
                class="rounded-full text-lg xl:text-sm bg-purple-400 hover:bg-purple-700 text-white w-full m-1 p-1"
            >
              En
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

        <div class="absolute z-50 md:end-0 xl:top-0 hidden md:block md:bottom-2 ltr:md:-translate-x-3 rtl:md:translate-x-3 ltr:lg:-translate-x-0 rtl:lg:translate-x-0">
          <UserMenuButton icon="fas fa-user" text="" ></UserMenuButton>

        </div>
      </div>
</template>

<style scoped>

</style>