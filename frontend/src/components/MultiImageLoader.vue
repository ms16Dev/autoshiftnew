<script setup lang="ts">
import { ref } from 'vue';

const emit = defineEmits(['save']);

const images = ref<{ url: string, file: File }[]>([]); // Store file and preview URL
const fileInput = ref<HTMLInputElement | null>(null);

function handleImageClick() {
  fileInput.value?.click();
}

function onFileChange(event: Event) {
  const target = event.target as HTMLInputElement;
  const files = target.files;

  if (files && files.length > 0) {
    const formData = new FormData(); // Create FormData object

    Array.from(files).forEach((file) => {
      const reader = new FileReader();
      reader.onload = (e) => {
        if (e.target?.result) {
          images.value.push({ url: e.target.result as string, file });
        }
      };
      reader.readAsDataURL(file);
      formData.append("file", file); // Append files to FormData
    });

    emit('save', formData); // Emit FormData
  }
  target.value = ""; // Reset file input
}

function removeImage(index: number) {
  images.value.splice(index, 1); // Remove the image at the specified index

  // Recreate FormData and emit the updated list
  const formData = new FormData();
  images.value.forEach(({ file }) => {
    formData.append("files", file);
  });
  emit('save', formData);
}

</script>

<template>
  <div class="flex flex-row items-center space-y-4">



    <!-- Image Preview Container -->
    <div class="flex gap-3 w-full h-[80px] overflow-x-scroll">
      <div
          v-for="(image, index) in images"
          :key="index"
          class="flex-shrink-0 w-[60px] h-[50px] relative overflow-hidden cursor-pointer group bg-gray-100"
      >
        <!-- Selected Image -->
        <img
            :src="image.url"
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

    <!-- Add Image Button -->
    <button
        @click="handleImageClick"
        class="m-2 py-1 px-3 text-sm font-medium bg-pink-300 text-white rounded-md shadow hover:bg-pink-700 focus:outline-none"
    >
      <span class="fas fa-image"></span>
    </button>

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
