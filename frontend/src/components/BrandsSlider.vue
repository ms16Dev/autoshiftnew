<template>
  <div ref="carouselContainer" class="relative h-[250px] flex items-center overflow-hidden ">
    <!-- Carousel Wrapper -->
    <div
        ref="carousel"
        class="flex items-center whitespace-nowrap"
        :style="{ transform: `translateX(${offset}px)`, transition: 'transform 0.02s linear' }"
    >
      <div v-for="(slide, index) in duplicatedSlides" :key="index" class="carousel-item mx-4">
        <img :src="slide.image" class="h-full max-w-[150px]" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from "vue";

const props = defineProps({
  slides: {
    type: Array,
    required: true,
  },
});

const offset = ref(0);
const speed = 1.5; // Adjust speed for smooth scrolling
let animationFrame;
const carouselContainer = ref(null);
const carousel = ref(null);
let itemWidth = 0;

// **Duplicate slides** to create an **infinite loop effect**
const duplicatedSlides = computed(() => [...props.slides, ...props.slides]);

const updateCarouselSettings = () => {
  if (carouselContainer.value && carousel.value) {
    const firstItem = carousel.value.querySelector(".carousel-item");
    if (firstItem) {
      itemWidth = firstItem.clientWidth + 16; // Include margin
    }
  }
};

const animateCarousel = () => {
  offset.value -= speed;

  // Reset when the original list fully scrolls out of view
  if (offset.value <= -props.slides.length * itemWidth) {
    offset.value += props.slides.length * itemWidth; // Move it back to the start smoothly
  }

  animationFrame = requestAnimationFrame(animateCarousel);
};

onMounted(() => {
  updateCarouselSettings();
  animationFrame = requestAnimationFrame(animateCarousel);
  window.addEventListener("resize", updateCarouselSettings);
});

onUnmounted(() => {
  cancelAnimationFrame(animationFrame);
  window.removeEventListener("resize", updateCarouselSettings);
});
</script>

<style>
.carousel-item {
  flex-shrink: 0;
}
</style>
