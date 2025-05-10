<template>
  <footer class="bg-gradient-to-br from-gray-900 via-gray-800 to-black text-gray-300 py-12 px-4 sm:ms-20 lg:ms-0">
    <div class="container mx-auto text-center">
      <!-- Logo or Title -->
      <div class="mb-6 text-center">
        <!-- Center the logo inside the router-link -->
        <router-link to="/" class="flex justify-center">
          <img src="../assets/asl.png" alt="Logo" width="120" height="120">
        </router-link>
        <h3 class="text-3xl font-bold text-white">{{t('autoshift')}}</h3>



        <p class="mt-2 text-gray-500">{{t('site_subtitle')}}</p>
      </div>

      <!-- Navigation Links -->
      <div class="grid grid-cols-1 sm:grid-cols-3 gap-6 mb-6">
        <div>
          <h4 class="font-semibold text-lg text-white">{{t('quick_links')}}</h4>
          <ul class="space-y-2 mt-3 text-sm">
            <li><router-link to="/home#" class="hover:text-pink-500">{{t('home')}}</router-link></li>
            <li><router-link to="/cars#" class="hover:text-pink-500">{{t('cars')}}</router-link></li>
            <li><router-link to="/dealers" class="hover:text-pink-500">{{t('dealers')}}</router-link></li>
            <li><router-link to="/more" class="hover:text-pink-500">{{t('more')}}</router-link></li>
          </ul>
        </div>
        <div>
          <h4 class="font-semibold text-lg text-white">{{t('connect')}}</h4>
          <ul class="space-y-2 mt-3 text-sm">
            <li><a href="#" class="hover:text-pink-500">{{t('facebook')}}</a></li>
            <li><a href="#" class="hover:text-pink-500">{{t('instagram')}}</a></li>
            <li><a href="#" class="hover:text-pink-500">{{t('twitter')}}</a></li>
          </ul>
        </div>
        <div>
          <h4 class="font-semibold text-lg text-white">{{t('contact_us')}}</h4>
          <ul class="space-y-2 mt-3 text-sm">
            <li><a href="mailto:fs.vue.info@gmail.com" class="hover:text-pink-500">fs.vue.info@gmail.com</a></li>
          </ul>
        </div>
      </div>

      <!-- Social Media Icons -->
      <div class="flex justify-center gap-6 mb-6">
        <a href="#" class="text-xl hover:text-pink-500"><i class="fab fa-facebook"></i></a>
        <a href="#" class="text-xl hover:text-pink-500"><i class="fab fa-twitter"></i></a>
        <a href="#" class="text-xl hover:text-pink-500"><i class="fab fa-instagram"></i></a>
      </div>

      <!-- Copyright -->
      <div class="text-sm text-gray-500 pb-4">
        <p>&copy; {{t('copyright')}}</p>
      </div>
    </div>
  </footer>
</template>

<script setup>
import {useI18n} from "vue-i18n";
const { t } = useI18n();

</script>

<style scoped>

</style>
