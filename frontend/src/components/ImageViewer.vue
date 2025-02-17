<template>
  <div class="image-viewer flex flex-col  items-center">

    <div class="w-full col-span-2 h-[460px]">
      <img :src="currentImage" class="w-full h-full object-cover" alt="cover image">
    </div>

    <!-- Thumbnails section -->
    <div class="thumbnails flex mt-4 justify-start overflow-x-auto max-w-full space-x-2">
      <!-- Cover photo -->
      <img
          :src="'http://localhost:8080'+coverPhoto"
          alt="Cover Image"
          class="thumbnail border-2 border-transparent hover:border-pink-500 cursor-pointer w-16 h-16 object-cover"
          @click="setImage(coverPhoto)"
      />

      <!-- Other images in imageList -->
      <img
          v-for="(image, index) in imageList"
          :key="index"
          :src="'http://localhost:8080'+image"
          alt="Thumbnail"
          class="thumbnail border-2 border-transparent hover:border-pink-500 cursor-pointer w-16 h-16 object-cover"
          @click="setImage(image)"
      />
    </div>

  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';

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
  currentImage.value = 'http://localhost:8080'+imageUrl;
};

onMounted(() => {
  // Set the cover photo as the current image when the component is mounted
 setImage('http://localhost:8080'+props.coverPhoto);
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
