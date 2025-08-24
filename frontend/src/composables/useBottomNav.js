// composables/useBottomNav.js
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export function useBottomNav(initialTab = 'home') {
  const router = useRouter()
  const activeTab = ref(initialTab)
  const navItems = [
    { id: 'odds', icon: '💥', label: 'Odds' },
    { id: 'bets', icon: '🎯', label: 'Bets' },
    { id: 'home', icon: '🏠', label: 'Home', isHome: true },
    { id: 'scores', icon: '⚡', label: 'Scores' },
    { id: 'stats', icon: '📊', label: 'Stats' }
  ]

  function setActiveTab(tabId) {
    activeTab.value = tabId
    if (tabId === 'odds') {
      router.push('/odds')
    } else if (tabId === 'home') {
      router.push('/home')
    } else if (tabId === 'bets') {
      // router.push('/bets')
    } else if (tabId === 'scores') {
      // router.push('/scores')
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
