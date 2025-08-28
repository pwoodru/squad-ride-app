// composables/useBottomNav.js
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export function useBottomNav(initialTab = 'home') {
  const router = useRouter()
  const activeTab = ref(initialTab)
  const navItems = [
    { id: 'odds', icon: '💥', label: 'Odds' },
    { id: 'mysquad', icon: '🎯', label: 'My Squads' },
    { id: 'home', icon: '🏠', label: 'Home', isHome: true },
    { id: 'leaderboard', icon: '⚡', label: 'Leaderboard' },
    { id: 'stats', icon: '📊', label: 'Stats' }
  ]

  function setActiveTab(tabId) {
    activeTab.value = tabId
    if (tabId === 'odds') {
      router.push('/odds')
    } else if (tabId === 'home') {
      router.push('/home')
    } else if (tabId === 'mysquad') {
      router.push('/mysquad')
    } else if (tabId === 'leaderboard') {
      router.push('/leaderboard')
    } else if (tabId === 'stats') {
      // router.push('/stats')
    }
  }

  return {
    navItems,
    activeTab,
    setActiveTab
  }
}
