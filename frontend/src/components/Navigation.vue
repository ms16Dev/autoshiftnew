<script setup lang="ts">




import IconButtonLink from "./IconButtonLink.vue";
import FAB from "./FAB.vue";


</script>

<template>
      <div class="fixed z-50 bottom-0 w-full h-[50px] bg-gradient-to-r from-[#ff80b5] to-[#9089fc] opacity-80
           md:w-[80px] md:h-full md:left-0 md:top-1/2 md:transform md:-translate-y-1/2 md:bg-gradient-to-t
           xl:w-2/3 xl:h-[50px] xl:top-12 xl:left-1/2 xl:transform xl:-translate-x-1/2 xl:rounded-full xl:bg-gradient-to-r">
        <router-link to="/" class="hidden w-fit xl:pl-8  md:block  xl:-translate-y-[15px]">
          <img src="../assets/asl.png" alt="Logo" width="120" height="120">
        </router-link>
        <div class="flex justify-center h-full md:h-[100px] md:w-full md:flex-col md:absolute md:top-1/2 md:-translate-y-1/2  xl:flex-row xl:h-full xl:justify-center">
          <IconButtonLink :text="'Home'" :icon="['fas', 'fa-home']" target="home" :class="{'bg-pink-600': $route.path === '/'}"/>
          <IconButtonLink :text="'Cars'" :icon="['fas', 'fa-car']" target="cars"  />
          <IconButtonLink :text="'Dealers'" :icon="['fas', 'fa-heartbeat']" target="dealers"  />
          <IconButtonLink :text="'More'" :icon="['fas', 'fa-ellipsis-h']" target="more" />
        </div>
        <div class="absolute ltr:right-0 rtl:left-0 top-0 hidden xl:block">
          <FAB icon="fas fa-user" text="Mohammed" ></FAB>

        </div>
      </div>
</template>

<style scoped>

</style>