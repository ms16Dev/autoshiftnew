<script setup lang="ts">
import { onMounted, onBeforeUnmount } from 'vue';
import { onBeforeRouteLeave } from 'vue-router';
import LayoutNav from '../components/landing/Nav.vue';
import Footer from '../components/Footer.vue';

// Import global styles
import '../assets/css/style-landing.css';

onMounted(async () => {
  // Dynamically import AOS and AOS CSS
  const { default: AOS } = await import('aos');
  await import('aos/dist/aos.css');
  AOS.init();
});

const removeAOS = () => {
  const aosCss = document.querySelector('link[href*="aos"]');
  if (aosCss) aosCss.remove();

  const aosScript = document.querySelector('script[src*="aos"]');
  if (aosScript) aosScript.remove();
};

onBeforeRouteLeave(() => {
  removeAOS();
});

onBeforeUnmount(() => {
  removeAOS();
});
</script>

<template>
  <div class="leading-normal tracking-normal text-white gradient">
    <LayoutNav />
    <RouterView />
    <Footer />
  </div>
</template>
