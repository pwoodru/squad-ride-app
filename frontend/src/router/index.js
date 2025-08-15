import { createRouter, createWebHistory } from 'vue-router'
import SquadRideApp from '../components/SquadRideApp.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: SquadRideApp
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router