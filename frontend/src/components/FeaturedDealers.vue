<template>
  <div class="relative w-full overflow-hidden">
    <!-- Scrollable Container -->
    <div
        ref="slider"
        class="flex gap-4 overflow-x-auto scroll-smooth no-scrollbar"
    >
      <DealerItem
          v-for="(post, index) in posts"
          :key="index"
          :data="post"
          class="flex-shrink-0 w-[300px]"
      />
    </div>

    <!-- Navigation Buttons -->
    <button
        @click="scrollLeft"
        class="absolute start-2 top-1/2 transform -translate-y-1/2 bg-pink-500 text-white p-2 rounded-full shadow-md"
    >
      ❮
    </button>
    <button
        @click="scrollRight"
        class="absolute end-2 top-1/2 transform -translate-y-1/2 bg-pink-500 text-white p-2 rounded-full shadow-md"
    >
      ❯
    </button>
  </div>
</template>

<script setup>
import { ref } from "vue";
import DealerItem from "./dealer/DealerItem.vue"; // Ensure this path is correct

const props = defineProps({
  posts: {
    type: Array,
    required: true,
  },
});

const slider = ref(null);

const scrollLeft = () => {
  slider.value.scrollBy({ left: -300, behavior: "smooth" });
};

const scrollRight = () => {
  slider.value.scrollBy({ left: 300, behavior: "smooth" });
};
</script>

<style scoped>
/* Hide default scrollbar */
.no-scrollbar::-webkit-scrollbar {
  display: none;
}
</style>
