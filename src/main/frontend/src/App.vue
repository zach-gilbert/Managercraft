<template>
  <v-app>
    <v-main>
      <LoadingPanel />
      <Dashboard />
    </v-main>
  </v-app>
</template>

<script setup>
  import LoadingPanel from '@/components/LoadingPanel';
  import Dashboard from '@/views/Dashboard';
  import { onMounted } from 'vue';
  import { connectWebSocket } from '@/api/websocket.js';

  // Establish a WebSocket connection
  onMounted(connectWebSocket);
</script>
