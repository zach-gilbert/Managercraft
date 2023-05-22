<template>
  <div>
    <v-textarea
      ref="consoleTextArea"
      :label="serverLabel"
      :model-value="consoleLines"
      readonly
      class="console"
    ></v-textarea>
  </div>
</template>

<script>
import { useServerStore } from "@/store/serverstore"

export default {
  name: "ServerConsole",

  data() {
    return {
      store: null
    }
  },

  created() {
    this.store = useServerStore()
  },

  watch: {
    consoleLines() {
      this.scrollToBottom()
    }
  },

  methods: {
    scrollToBottom() {
      this.$nextTick(() => {
        const textAreaComponent = this.$refs.consoleTextArea
        if (textAreaComponent) {
          const textAreaElement = textAreaComponent.$el.querySelector('.console textarea');
          if (textAreaElement) {
            textAreaElement.scrollTop = textAreaElement.scrollHeight
          }
        }
      })
    }
  },

  computed: {
    serverLabel() {
      return this.store.serverName + " Console"
    },

    consoleLines() {
      return this.store && this.store.consoleLines ? this.store.consoleLines.join("\n") : ''
    }
  }
}
</script>

<style scoped>
.console textarea {
  overflow-y: auto !important;
  scroll-behavior: smooth;
}
</style>
