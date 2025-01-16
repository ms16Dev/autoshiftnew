<script setup lang="ts">
import { ref } from 'vue';

const images = ref<string[]>([]); // Array to store multiple images
const fileInput = ref<HTMLInputElement | null>(null);

function handleImageClick() {
  fileInput.value?.click();
}

function onFileChange(event: Event) {
  const target = event.target as HTMLInputElement;
  const files = target.files;

  if (files && files.length > 0) {
    Array.from(files).forEach((file) => {
      const reader = new FileReader();
      reader.onload = (e) => {
        if (e.target?.result) {
          images.value.push(e.target.result as string);
        }
      };
      reader.readAsDataURL(file);
    });
  }
  target.value = ""; // Reset file input
}

function removeImage(index: number) {
  images.value.splice(index, 1); // Remove the image at the specified index
}
</script>

<template>
  <div class="flex flex-col items-center space-y-4">

    <!-- Add Image Button -->
    <button
        @click="handleImageClick"
        class="mt-2 py-1 px-3 text-sm font-medium bg-pink-300 text-white rounded-md shadow hover:bg-pink-700 focus:outline-none"
    >
      More Images
    </button>
    <!-- Image Preview Container -->
    <div class="flex gap-3 w-full h-[100px] overflow-x-scroll">
      <div
          v-for="(image, index) in images"
          :key="index"
          class="flex-shrink-0 w-[80px] h-[80px] relative overflow-hidden cursor-pointer group bg-gray-100"
      >
        <!-- Selected Image -->
        <img
            :src="image"
            alt="Selected"
            class="object-cover w-full h-full"
        />

        <!-- Remove Button -->
        <button
            @click.stop="removeImage(index)"
            class="absolute w-6 h-6 top-2 right-2 bg-pink-500 text-white rounded-full text-sm opacity-75 hover:opacity-100"
        >
          ✕
        </button>
      </div>
    </div>


    <!-- Hidden File Input -->
    <input
        ref="fileInput"
        type="file"
        class="hidden"
        @change="onFileChange"
        accept="image/*"
        multiple
    />
  </div>
</template>

<style scoped>
</style>
