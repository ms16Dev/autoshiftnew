<template>
  <div class="image-viewer flex flex-col  items-center">

    <div class="w-full col-span-2 h-[460px]">
      <img :src="currentImage" class="w-full h-full object-contain" alt="cover image">
    </div>

    <!-- Thumbnails section -->
    <div class="thumbnails flex mt-4 justify-start overflow-x-auto max-w-full space-x-2">
      <!-- Cover photo -->
      <img
          :src="config.apiBaseUrl+coverPhoto"
          alt="Cover Image"
          class="thumbnail border-2 border-transparent hover:border-pink-500 cursor-pointer w-16 h-16 object-contain"
          @click="setImage(coverPhoto)"
      />

      <!-- Other images in imageList -->
      <img
          v-for="(image, index) in imageList"
          :key="index"
          :src="config.apiBaseUrl+image"
          alt="Thumbnail"
          class="thumbnail border-2 border-transparent hover:border-pink-500 cursor-pointer w-16 h-16 object-contain"
          @click="setImage(image)"
      />
    </div>

  </div>
</template>

<script setup>
import {onMounted, ref, watch} from 'vue';
import {config} from "../../config.js";

// Props definition
const props = defineProps({
  coverPhoto: {
    type: String,
    required: true,
  },
  imageList: {
    type: Array,
    required: true,
  },
});

// Reactive reference for current image
const currentImage = ref(props.coverPhoto);

// Method to change the current image when a thumbnail is clicked
const setImage = (imageUrl) => {
  currentImage.value = config.apiBaseUrl+imageUrl;
};

// Watch for changes in coverPhoto and update currentImage when it becomes available
watch(() => props.coverPhoto, (newCover) => {
  if (newCover) {
    setImage(newCover);
  }
}, { immediate: true });

onMounted(() => {
  // Ensure the cover image is set on mount if it's already available
  if (props.coverPhoto) {
    setImage(props.coverPhoto);
  }
});
</script>

<style scoped>
.thumbnail {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin: 0 5px;
  transition: border 0.3s;
}
</style>
