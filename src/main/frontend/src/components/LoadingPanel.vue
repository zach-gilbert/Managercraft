<template>
  <v-dialog
    v-model="store.isLoading"
    :fullscreen="true"
    :persistent="true"
  >
    <v-card class="d-flex align-center justify-center background-card">
      <div class="text-center, loading-text">
        <h1>Loading App</h1>
        <v-progress-linear indeterminate color="white"></v-progress-linear>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
import {useAppIsReadyStore} from '@/store/appisreadystore';

export default {
  name: 'AppLoading',

  data() {
    return {
      store: null
    }
  },

  created() {
    this.store = useAppIsReadyStore();
  }
}
</script>

<style scoped>
.loading-text {
  font-size: 24px;
  -webkit-text-stroke: 0.3px white;
  -webkit-text-fill-color: mediumslateblue;
}
.background-card {
  background-color: rgba(0, 0, 0, 0.3);
}
</style>

