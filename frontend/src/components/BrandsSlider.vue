<template>
  <div ref="carouselContainer" class="relative h-[250px] flex items-center overflow-hidden">
    <!-- Carousel Wrapper -->
    <div
        ref="carousel"
        class="flex items-center whitespace-nowrap"
        :style="{
        transform: `translateX(${offset}px)`,
        transition: 'transform 0.02s linear'
      }"
    >
      <div
          v-for="(slide, index) in duplicatedSlides"
          :key="index"
          class="carousel-item mx-4"
      >
        <img
            :src="config.endpoints.api + slide.image"
            class="h-full w-[150px] object-contain"
            @load="updateCarouselSettings"
            alt="brands"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed, nextTick } from "vue";
import { config } from "../../config.ts";

const props = defineProps<{
  slides: Array<{ image: string }>;
}>();

const offset = ref(0);
const speed = 1.5;
let animationFrame: number;
const carouselContainer = ref<HTMLElement | null>(null);
const carousel = ref<HTMLElement | null>(null);
let itemWidth = 0;

const duplicatedSlides = computed(() => [...props.slides, ...props.slides]);

const updateCarouselSettings = () => {
  if (carousel.value) {
    const firstItem = carousel.value.querySelector(".carousel-item");
    if (firstItem instanceof HTMLElement) {
      const margin = 32; // total horizontal margin (mx-4 = 1rem * 2 = 16px * 2)
      itemWidth = firstItem.offsetWidth + margin;
    }
  }
};

const animateCarousel = () => {
  offset.value -= speed;

  const totalWidth = (duplicatedSlides.value.length * itemWidth);

  if (Math.abs(offset.value) >= totalWidth / 2) {
    offset.value = 0;
  }

  animationFrame = requestAnimationFrame(animateCarousel);
};

onMounted(() => {
  nextTick(() => {
    updateCarouselSettings();
    setTimeout(updateCarouselSettings, 300); // Ensure images have loaded
    animationFrame = requestAnimationFrame(animateCarousel);
    window.addEventListener("resize", updateCarouselSettings);
  });
});

onUnmounted(() => {
  cancelAnimationFrame(animationFrame);
  window.removeEventListener("resize", updateCarouselSettings);
});
</script>

<style scoped>
.carousel-item {
  flex-shrink: 0;
  width: 150px; /* Ensures uniformity */
}
</style>
