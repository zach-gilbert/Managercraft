<template>
  <textarea
    ref="consoleTextArea"
    class="console"
    :value="consoleLines"
    readonly
  ></textarea>
</template>

<script>
import { useServerStore } from "@/store/serverstore";

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
        const textAreaElement  = this.$refs.consoleTextArea
        if (textAreaElement ) {
          textAreaElement .scrollTop = textAreaElement .scrollHeight
        }
      })
    }
  },

  computed: {
    consoleLines() {
      return this.store && this.store.consoleLines ? this.store.consoleLines.join("\n") : ''
    }
  }
}
</script>

<style scoped>
.console {
  background-color: black;
  overflow-y: auto;
  scroll-behavior: smooth;
  min-height: 50%;
  width: 100%;
  border: none;
  resize: none;
  color: white;
  border-radius: 15px;
  padding: 5px;
  cursor: default;
}

.console::-webkit-scrollbar {
  width: 15px;
}

.console::-webkit-scrollbar-track {
  background: #202020;
  border-left: 1px solid #2c2c2c;
}

.console::-webkit-scrollbar-thumb {
  background: #3e3e3e;
  border: solid 3px #202020;
  border-radius: 7px;
  height: 50%;
}
</style>
