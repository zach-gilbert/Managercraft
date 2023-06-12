<script>
import { useAppIsReadyStore } from "@/store/appisreadystore";

export default {
  name: "StatusGumball",

  computed: {
    getStatusColor() {
      if (useAppIsReadyStore().isLoading) {
        return "red";
      }

      else {
        return "green";
      }
    },

    getStatusText() {
      if (useAppIsReadyStore().isLoading) {
        return "Disconnected";
      }

      else {
        return "Connected";
      }
    }
  }
}
</script>

<template>
  <div
    id="circle"
    :style="{ backgroundColor: getStatusColor }"
  >
    <!-- TODO: Find a way to change color of tooltip -->
    <v-tooltip
      activator="parent"
      location="bottom"
    >
      <span id="tooltip">{{ getStatusText }}</span>
    </v-tooltip>
  </div>

</template>

<style scoped>
#circle {
  width: 1em;
  height: 1em;
  border-radius: 50%;
  border: 0.1em solid black;

  /* Adds a transition over half a second for the color */
  transition: background-color 0.5s;
}
#tooltip {
  color: white;
}
</style>
