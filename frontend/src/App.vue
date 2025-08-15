<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { onMounted } from 'vue'
import { useSquadRideStore } from './stores'

export default {
  name: 'App',
  setup() {
    const store = useSquadRideStore()

    onMounted(() => {
      // Initialize the app - could load user data here if authenticated
      console.log('Squad Ride App initialized')
    })

    return {
      store
    }
  }
}
</script>

<style>
/* Global app styles are imported in main.js */
</style>