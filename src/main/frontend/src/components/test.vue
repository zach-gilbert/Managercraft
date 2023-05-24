<template>

</template>

<script setup>
import { useServerStore } from "@/store/serverstore";
import { onMounted } from "vue";

onMounted(() => {
  const store = useServerStore();
  console.log(store.consoleLines)
})
</script>

<style scoped>

</style>
