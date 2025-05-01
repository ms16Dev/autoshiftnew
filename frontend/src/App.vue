<script setup lang="ts">
import { onMounted } from 'vue'
import {useStaticDataStore} from "./stores/staticDataStore.ts";

const staticData = useStaticDataStore()

onMounted(() => {
  staticData.initialize()
})
</script>

<template>
<router-view/>
</template>

<style scoped>

</style>
