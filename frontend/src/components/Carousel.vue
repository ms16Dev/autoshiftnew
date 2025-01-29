<template>
  <div class="relative w-full h-[250px] flex items-center justify-center bg-pink-500 text-white overflow-hidden backdrop-blur-lg shadow-[0_8px_32px_rgba(0,0,0,0.37)]">
    <!-- Carousel Wrapper -->
    <div class="w-full h-full flex transition-transform duration-500 ease-in-out" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
      <div v-for="(slide, index) in slides" :key="index" class="w-full flex-shrink-0 flex">
        <!-- Left Section: Text -->
        <div class="w-1/2 flex flex-col justify-center px-10">
          <h2 class="text-4xl font-bold">{{ slide.title }}</h2>
          <h3 class="text-xl mt-2">{{ slide.subtitle }}</h3>
          <p class="mt-4 text-gray-300">{{ slide.description }}</p>
        </div>

        <!-- Right Section: Image -->
        <div class="w-1/2">
          <img :src="slide.image" class="w-full h-full object-cover" />
        </div>
      </div>
    </div>

    <!-- Previous Button -->
    <button @click="prevSlide" class="absolute left-4 bg-white text-gray-900 p-3 rounded-full shadow-md hover:bg-gray-200 text-sm">
      ❮
    </button>

    <!-- Next Button -->
    <button @click="nextSlide" class="absolute right-4 bg-white text-gray-900 p-3 rounded-full shadow-md hover:bg-gray-200 text-sm">
      ❯
    </button>

    <!-- Indicators -->
    <div class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2">
      <span v-for="(slide, index) in slides" :key="index" class="w-3 h-3 rounded-full cursor-pointer"
            :class="index === currentIndex ? 'bg-white' : 'bg-pink-300'"
            @click="goToSlide(index)">
      </span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";

const props = defineProps({
  slides: {
    type: Array,
    required: true,
  },
});

const currentIndex = ref(0);
let autoPlayInterval;

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % props.slides.length;
};

const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 1 + props.slides.length) % props.slides.length;
};

const goToSlide = (index) => {
  currentIndex.value = index;
};

const startAutoPlay = () => {
  autoPlayInterval = setInterval(() => {
    nextSlide();
  }, 5000);
};

const stopAutoPlay = () => {
  clearInterval(autoPlayInterval);
};

onMounted(() => {
  startAutoPlay();
});

onUnmounted(() => {
  stopAutoPlay();
});
</script>
