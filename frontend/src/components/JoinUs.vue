<template>
  <section class="relative w-full mx-auto flex flex-col md:flex-row items-center justify-center mb-24 p-6">
    <!-- Glowing border wrapper -->
    <div class="relative w-full max-w-4xl rounded-2xl overflow-visible">
      <!-- Glow border ring -->
      <div class="absolute inset-0 rounded-2xl border-[3px] border-transparent z-0 pointer-events-none animate-glow-border"></div>

      <!-- Card Content -->
      <div class="relative z-10 w-full bg-gradient-to-br from-gray-900 via-gray-800 to-black rounded-2xl p-10 text-white text-center shadow-2xl">
        <h3 class="text-3xl font-extrabold mb-2">
          {{ t('joinUsNow') }}
        </h3>
        <p class="text-lg mb-4">
          {{ t('freeSpaceText') }}
        </p>
        <router-link to="/auth/sign-up">
          <button class="bg-white hover:bg-gray-200 text-pink-700 font-semibold py-2 px-5 rounded-xl transition-all duration-300">
            {{ t('registerNow') }}
          </button>
        </router-link>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n'
const { t } = useI18n()
</script>

<style scoped>
@keyframes glowAnimation {
  0% {
    box-shadow: 0 0 10px 2px rgba(236, 72, 153, 0.2),
    0 0 20px 4px rgba(236, 72, 153, 0.15),
    0 0 30px 6px rgba(236, 72, 153, 0.1);
  }
  50% {
    box-shadow: 0 0 20px 4px rgba(236, 72, 153, 0.5),
    0 0 40px 8px rgba(236, 72, 153, 0.3),
    0 0 60px 12px rgba(236, 72, 153, 0.2);
  }
  100% {
    box-shadow: 0 0 10px 2px rgba(236, 72, 153, 0.2),
    0 0 20px 4px rgba(236, 72, 153, 0.15),
    0 0 30px 6px rgba(236, 72, 153, 0.1);
  }
}

.animate-glow-border {
  animation: glowAnimation 6s ease-in-out infinite;
  border-radius: 1rem;
}
</style>
