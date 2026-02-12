import { createRouter, createWebHistory } from 'vue-router'

import SquadRideApp from '../components/SquadRideApp.vue'
import Login from '../components/Login.vue'

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('../components/SignUp.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: SquadRideApp
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router