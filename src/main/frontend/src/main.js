/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from '@/App'
import vuetifyOptions from '@/plugins/vuetify.js'

// Plugins
import { registerPlugins } from '@/plugins'

const pinia = createPinia()
const app = createApp(App)

registerPlugins(app)

const vuetify = vuetifyOptions

app.use(pinia)
app.use(vuetify, {
  customProperties: true
})
app.mount('#app')
