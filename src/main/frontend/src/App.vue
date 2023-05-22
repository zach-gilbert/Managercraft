<template>
  <v-app>
    <v-main>
      <LoadingPanel />
      <ServerConsole />
    </v-main>
  </v-app>
</template>

<script setup>
  import LoadingPanel from '@/components/LoadingPanel';
  import ServerConsole from '@/components/ServerConsole';
  import { onMounted } from 'vue';
  import { connectWebSocket } from '@/api/websocket.js';

  // Establish a WebSocket connection
  onMounted(connectWebSocket);
</script>
