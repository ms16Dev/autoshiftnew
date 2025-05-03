<template>
  <footer class="bg-gradient-to-br from-gray-900 via-gray-800 to-black text-gray-300 py-12 px-4 sm:ms-20">
    <div class="container mx-auto text-center">
      <!-- Logo or Title -->
      <div class="mb-6 text-center">
        <!-- Center the logo inside the router-link -->
        <router-link to="/" class="flex justify-center">
          <img src="../assets/asl.png" alt="Logo" width="120" height="120">
        </router-link>
        <h3 class="text-3xl font-bold text-white">AUTOSHIFT</h3>



        <p class="mt-2 text-gray-500">Connecting buyers and sellers in the car market</p>
      </div>

      <!-- Navigation Links -->
      <div class="grid grid-cols-2 sm:grid-cols-4 gap-6 mb-6">
        <div>
          <h4 class="font-semibold text-lg text-white">Quick Links</h4>
          <ul class="space-y-2 mt-3 text-sm">
            <li><a href="/home" class="hover:text-pink-500">Home</a></li>
            <li><a href="/about" class="hover:text-pink-500">About Us</a></li>
            <li><a href="/contact" class="hover:text-pink-500">Contact</a></li>
            <li><a href="/privacy" class="hover:text-pink-500">Privacy Policy</a></li>
          </ul>
        </div>
        <div>
          <h4 class="font-semibold text-lg text-white">Resources</h4>
          <ul class="space-y-2 mt-3 text-sm">
            <li><a href="/help" class="hover:text-pink-500">Help Center</a></li>
            <li><a href="/terms" class="hover:text-pink-500">Terms of Service</a></li>
            <li><a href="/blog" class="hover:text-pink-500">Blog</a></li>
          </ul>
        </div>
        <div>
          <h4 class="font-semibold text-lg text-white">Connect</h4>
          <ul class="space-y-2 mt-3 text-sm">
            <li><a href="#" class="hover:text-pink-500">Facebook</a></li>
            <li><a href="#" class="hover:text-pink-500">Instagram</a></li>
            <li><a href="#" class="hover:text-pink-500">Twitter</a></li>
            <li><a href="#" class="hover:text-pink-500">LinkedIn</a></li>
          </ul>
        </div>
        <div>
          <h4 class="font-semibold text-lg text-white">Contact Us</h4>
          <ul class="space-y-2 mt-3 text-sm">
            <li><a href="mailto:info@yourdomain.com" class="hover:text-pink-500">Email: info@yourdomain.com</a></li>
            <li><a href="tel:+1234567890" class="hover:text-pink-500">Phone: +123 456 7890</a></li>
          </ul>
        </div>
      </div>

      <!-- Social Media Icons -->
      <div class="flex justify-center gap-6 mb-6">
        <a href="#" class="text-xl hover:text-pink-500"><i class="fab fa-facebook"></i></a>
        <a href="#" class="text-xl hover:text-pink-500"><i class="fab fa-twitter"></i></a>
        <a href="#" class="text-xl hover:text-pink-500"><i class="fab fa-instagram"></i></a>
        <a href="#" class="text-xl hover:text-pink-500"><i class="fab fa-linkedin"></i></a>
      </div>

      <!-- Copyright -->
      <div class="text-sm text-gray-500">
        <p>&copy; 2025 AUTOSHIFT. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>

<script setup>

</script>

<style scoped>

</style>
