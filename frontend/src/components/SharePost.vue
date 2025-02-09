<template>
  <div class="flex justify-center items-center gap-2">
    <button @click="shareOnFacebook" class="flex items-center gap-2 px-2 py-1 bg-pink-500 text-white text-xs rounded-lg shadow-md hover:bg-pink-600 transition">
      <i class="fab fa-facebook-f"></i> Facebook
    </button>
    <button @click="shareOnTwitter" class="flex items-center gap-2 px-2 py-1 bg-pink-500 text-white text-xs rounded-lg shadow-md hover:bg-pink-600 transition">
      <i class="fab fa-twitter"></i> Twitter
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

</script>

