<script setup lang="ts">
import { ref } from 'vue';

const image = ref<string | null>(null);  // For holding the image URL
const fileInput = ref<HTMLInputElement | null>(null);  // For the file input

// Handle image click to trigger file input
function handleImageClick() {
  fileInput.value?.click();  // Use optional chaining to safely call click
}

// Handle file change event when a user selects a file
function onFileChange(event: Event) {
  const target = event.target as HTMLInputElement;  // Type assertion
  const file = target.files ? target.files[0] : null;  // Ensure files is not null
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      image.value = e.target?.result as string;  // Ensure the result is a string (image URL)
    };
    reader.readAsDataURL(file);
  }
}

// Remove the image and reset the file input
function removeImage() {
  image.value = null;  // Clear the image preview
  if (fileInput.value) {
    fileInput.value.value = '';  // Reset the file input value (ensure fileInput is not null)
  }
}
</script>



<template>
  <div class="flex flex-col items-center space-y-2 ">
    <!-- Image Preview Container -->
    <div
        @click="handleImageClick"
        class="relative aspect-[1/1] overflow-hidden cursor-pointer group bg-gray-100"
    >
      <!-- Placeholder or Selected Image -->
      <img
          v-if="image"
          :src="image"
          alt="Selected"
          class="object-cover w-full h-full"
      />
      <img
          v-else
          src="/public/car-phldr.png"
          alt="Placeholder"
          class="object-cover w-full h-full"
      />

      <!-- Remove Button -->
      <button
          v-if="image"
          @click.stop="removeImage"
          class="absolute w-6 h-6 top-2 right-2 bg-pink-500 text-white  rounded-full text-sm opacity-75 hover:opacity-100"
      >
        ✕
      </button>

      <!-- Upload Icon -->
      <div
          v-if="!image"
          class="absolute inset-0 flex items-center justify-center text-gray-600 group-hover:text-pink-500"
      >
        <i class="fas fa-image text-2xl text-grey-400 pe-3"></i>
          Add image
      </div>
    </div>

    <!-- Hidden File Input -->
    <input
        ref="fileInput"
        type="file"
        class="hidden"
        @change="onFileChange"
        accept="image/*"
    />

    <!-- Select Image Button -->
    <button
        v-if="!image"
        @click="handleImageClick"
        class="hidden py-1 px-3 text-sm font-medium bg-indigo-600 text-white rounded-md shadow hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
    >
      Select Image
    </button>
  </div>
</template>


<style scoped>

</style>
