<script setup lang="ts">
import { ref, defineEmits } from 'vue';

const props = defineProps({
  bg: {
    type: String,
    required: false,
    default: "/car-phldr.png"
  },
});


const image = ref<string | null>(null);
const fileInput = ref<HTMLInputElement | null>(null);

const emit = defineEmits(["file-selected"]); // Define the event emitter

function handleImageClick() {
  fileInput.value?.click();
}

function onFileChange(event: Event) {
  const target = event.target as HTMLInputElement;
  const file = target.files ? target.files[0] : null;

  if (file) {
    // Read the file as a preview
    const reader = new FileReader();
    reader.onload = (e) => {
      image.value = e.target?.result as string;
    };
    reader.readAsDataURL(file);

    // Create FormData and emit it
    const formData = new FormData();
    formData.append("file", file);
    emit("file-selected", formData);
  }
}

function removeImage() {
  image.value = null;
  if (fileInput.value) {
    fileInput.value.value = '';
  }
}
</script>

<template>
  <div class="flex flex-col items-center space-y-2">
    <div
        @click="handleImageClick"
        class="relative aspect-[1/1] overflow-hidden cursor-pointer group bg-gray-100"
    >
      <img
          v-if="image"
          :src="image"
          alt="Selected"
          class="object-contain w-full h-full"
      />
      <img
          v-else
          :src="props.bg"
          alt="Placeholder"
          class="object-contain w-full h-full"
      />

      <button
          v-if="image"
          @click.stop="removeImage"
          class="absolute w-6 h-6 top-2 right-2 bg-pink-500 text-white rounded-full text-sm opacity-75 hover:opacity-100"
      >
        ✕
      </button>

      <div
          v-if="!image"
          class="absolute inset-0 flex items-center justify-center text-gray-600 group-hover:text-pink-500"
      >
        <i class="fas fa-image text-2xl text-gray-400 pe-3"></i>
        Add image
      </div>
    </div>

    <input
        ref="fileInput"
        type="file"
        class="hidden"
        @change="onFileChange"
        accept="image/*"
    />
  </div>
</template>
