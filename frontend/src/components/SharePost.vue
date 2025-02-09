<template>
  <div class="flex justify-center items-center gap-2">
    <span class="text-sm">Share post</span>
    <button @click="shareOnWhatsApp" class="flex items-center gap-2 px-2 py-1 bg-green-600 text-white text-xs rounded-lg shadow-md hover:bg-green-400 transition">
      <i class="fab fa-whatsapp"></i> Whatsapp
    </button>
    <button @click="shareOnFacebook" class="flex items-center gap-2 px-2 py-1 bg-blue-800 text-white text-xs rounded-lg shadow-md hover:bg-blue-500 transition">
      <i class="fab fa-facebook-f"></i> Facebook
    </button>
    <button @click="shareOnTwitter" class="flex items-center gap-2 px-2 py-1 bg-black text-white text-xs rounded-lg shadow-md hover:bg-gray-500 transition">
      <i class="fab fa-x"></i> Twitter
    </button>

  </div>
</template>

<script setup>
import { ref } from "vue";

const currentUrl = ref(window.location.href); // Get the current page URL

// Share on Facebook
function shareOnFacebook() {
  const facebookUrl = `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(currentUrl.value)}`;
  window.open(facebookUrl, "_blank");
}

// Share on Twitter
function shareOnTwitter() {
  const twitterUrl = `https://twitter.com/intent/tweet?url=${encodeURIComponent(currentUrl.value)}`;
  window.open(twitterUrl, "_blank");
}

// Share on WhatsApp
function shareOnWhatsApp() {
  const currentUrl = window.location.href;  // Get the current URL
  const whatsappUrl = `https://wa.me/?text=${encodeURIComponent(currentUrl)}`;
  window.open(whatsappUrl, "_blank");
}
</script>

