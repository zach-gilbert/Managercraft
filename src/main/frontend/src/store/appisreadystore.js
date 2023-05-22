import { defineStore } from 'pinia';

export const useAppIsReadyStore = defineStore('appIsReady', {
  state: () => ({
    isLoading: true
  }),

  actions: {
    setIsLoading(value) {
      this.isLoading = value;
    }
  },

})
