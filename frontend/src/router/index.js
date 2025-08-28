import Odds from '../components/Odds.vue'
import { createRouter, createWebHistory } from 'vue-router'

import SquadRideApp from '../components/SquadRideApp.vue'
import Login from '../components/Login.vue'

const routes = [
  {
    path: '/shop',
    name: 'Shop',
    component: () => import('../components/Shop.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../components/Profile.vue')
  },
  {
    path: '/odds',
    name: 'Odds',
    component: Odds
  },
  {
    path: '/quick/dailywheel',
    name: 'DailyWheel',
    component: () => import('../components/DailyWheel.vue')
  },
  {
    path: '/quick/dailyrewards',
    name: 'DailyRewards',
    component: () => import('../components/DailyRewards.vue')
  },
  {
    path: '/quick/challenges',
    name: 'Challenges',
    component: () => import('../components/Challenges.vue')
  },
  {
    path: '/squadride',
    name: 'SquadRide',
    component: () => import('../components/SquadRide.vue')
  },
  {
    path: '/leaderboard',
    name: 'Leaderboard',
    component: () => import('../components/Leaderboard.vue')
  },
  {
    path: '/mysquad',
    name: 'MySquads',
    component: () => import('../components/MySquads.vue')
  },
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