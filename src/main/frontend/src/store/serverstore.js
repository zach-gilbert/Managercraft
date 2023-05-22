import { defineStore } from 'pinia'

/**
 * Server Console store
 * @name useServerStore
 * @returns {object} The store object with state and actions
 *
 * @example
 * const serverStore = useServerStore()
 */
export const useServerStore = defineStore('server', {
  state: () => ({
    consoleLines: JSON.parse(localStorage.getItem('consoleLines') || '[]'),
    serverName: localStorage.getItem('serverName') || '',
    isServerRunning: false
  }),

  actions: {
    addLine(line) {
      this.consoleLines.push(line);
      if (this.consoleLines.length > 500) {
        this.consoleLines.shift();
      }

      // Use local storage to retain information upon refresh
      localStorage.setItem('consoleLines', JSON.stringify(this.consoleLines));
    },

    clearAllLines() {
      this.consoleLines = [];
      localStorage.setItem('consoleLines', JSON.stringify(this.consoleLines));
    },

    setServerName(name) {
      this.serverName = name
      localStorage.setItem('serverName', this.serverName);
    },

    clearStoreData() {
      this.consoleLines = [];
      this.serverName = "";
      localStorage.removeItem('consoleLines');
      localStorage.removeItem('serverName');
    }
  }
})
