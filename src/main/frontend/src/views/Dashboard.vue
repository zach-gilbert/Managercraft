<!-- Recreate this to use separate views, not sure all of this in dashboard is advisable. Likely will need to recreate some of this -->
<template>
  <v-app id="inspire">
    <v-system-bar class="system-bar">
      <span>Managercraft</span>
      <v-spacer></v-spacer>
      <span>test</span> <!-- TODO server ip copy/paste -->
    </v-system-bar>

    <v-navigation-drawer
      width="244"
    >
      <!-- TODO: replace with card and add  -->
      <v-sheet
        color="grey-lighten-5"
        height="128"
        width="100%"
      ></v-sheet>

      <v-list>
        <v-list-item
          v-for="n in 5"
          :key="n"
          :title="`Item ${ n }`"
          link
        >
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
      class="px-3"
      color="grey-lighten-4"
      flat
      height="72"
    >
      <!-- TODO: started server name/version, cpu, ram, usage -->
    </v-app-bar>

    <v-main>
      <!-- TODO: add server start/stop buttons -->
      <ServerConsole />
    </v-main>

    <v-navigation-drawer location="right">
      <v-list>
        <v-list-item
          v-for="n in 5"
          :key="n"
          :title="`Item ${ n }`"
          link
        >
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-footer
      app
      height="72"
    >
      <v-text-field
        bg-color="grey-lighten-1"
        class="rounded-pill overflow-hidden"
        density="compact"
        hide-details
        variant="solo"
      ></v-text-field>
    </v-footer>
  </v-app>
</template>

<script>
import ServerConsole from "@/components/ServerConsole";

export default {
  name: "Dashboard",
  components: {
    ServerConsole
  }
}
</script>

<style scoped>
.system-bar {
  background-color: #1E293B;
}
</style>
