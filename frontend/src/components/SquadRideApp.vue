<template>
  <div class="app-container">
    <!-- Background Elements -->
    <div class="bg-pattern"></div>
    <div class="floating-elements">
      <div class="floating-card card-1">+$350 Won 🔥</div>
      <div class="floating-card card-2">🏈 Live Games</div>
      <div class="floating-card card-3">New Squad! ⚡</div>
    </div>

    <!-- Mobile Container -->
    <div class="mobile-container">
      <!-- Top Bar -->
      <div class="top-bar">
        <div class="currency">
          <span>💰</span>
          <span>${{ formatCurrency(userBalance) }}</span>
        </div>
        <div class="profile-info">
          <span>{{ userProfile.displayName }}</span>
          <div class="profile-avatar">{{ userProfile.initials }}</div>
        </div>
      </div>

      <!-- Header Section -->
      <div class="header-section">
        <div class="header-content">
          <div class="logo-section">
            <div class="logo-icon">
              <div class="logo-text">SR</div>
            </div>
          </div>
          <div class="app-title">Squad Ride</div>
          <div class="app-subtitle">Dominate the Night. Win Big.</div>
        </div>
        <div class="wave-separator"></div>
      </div>

      <!-- Main Content -->
      <div class="main-content">
        <!-- Balance Card -->
        <div class="balance-card">
          <div class="balance-title">Current Balance</div>
          <div class="balance-amount">${{ formatCurrency(userBalance) }}</div>
          <div class="balance-change" :class="{ positive: todayChange >= 0, negative: todayChange < 0 }">
            <span>{{ todayChange >= 0 ? '🚀' : '📉' }}</span>
            <span>{{ todayChange >= 0 ? '+' : '' }}${{ formatCurrency(Math.abs(todayChange)) }} today</span>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="quick-picks">
          <div class="section-title">
            <span>🔥</span>
            <span>Quick Actions</span>
          </div>
          <div class="picks-grid">
            <div class="pick-card" @click="navigateTo('squads')" :class="{ active: activeSection === 'squads' }">
              <div class="pick-icon">💥</div>
              <div class="pick-title">My Squads</div>
              <div class="pick-count">{{ stats.activeSquads }} Active Teams</div>
            </div>
            <div class="pick-card" @click="navigateTo('bets')" :class="{ active: activeSection === 'bets' }">
              <div class="pick-icon">🎯</div>
              <div class="pick-title">Live Bets</div>
              <div class="pick-count">{{ stats.liveBets }} In Progress</div>
              <div v-if="stats.liveBets > 0" class="notification-dot"></div>
            </div>
            <div class="pick-card" @click="navigateTo('scores')" :class="{ active: activeSection === 'scores' }">
              <div class="pick-icon">⚡</div>
              <div class="pick-title">Live Scores</div>
              <div class="pick-count">Week {{ currentWeek }} Games</div>
            </div>
            <div class="pick-card" @click="navigateTo('stats')" :class="{ active: activeSection === 'stats' }">
              <div class="pick-icon">📊</div>
              <div class="pick-title">My Stats</div>
              <div class="pick-count">{{ stats.winRate }}% Win Rate</div>
            </div>
          </div>
        </div>

        <!-- CTA Section -->
        <div class="cta-section">
          <button class="main-cta" @click="makeNewPicks" :disabled="loading">
            {{ loading ? 'Loading...' : 'Make New Picks' }}
          </button>
          <button class="secondary-cta" @click="viewAllGames">View All Games</button>
        </div>
      </div>

      <!-- Bottom Navigation -->
      <div class="bottom-nav">
        <div 
          v-for="navItem in navItems" 
          :key="navItem.id"
          class="nav-item" 
          :class="{ active: activeTab === navItem.id }"
          @click="setActiveTab(navItem.id)"
        >
          <div class="nav-icon">{{ navItem.icon }}</div>
          <span>{{ navItem.label }}</span>
        </div>
      </div>
    </div>

    <!-- Loading Overlay -->
    <div v-if="loading" class="loading-overlay">
      <div class="loading-spinner"></div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted, computed } from 'vue'
import { useSquadRideAPI } from '../composables/useSquadRideAPP'

export default {
  name: 'SquadRideApp',
  setup() {
    // Reactive state
    const loading = ref(false)
    const activeTab = ref('squads')
    const activeSection = ref('squads')
    const currentWeek = ref(8)
    
    const userBalance = ref(2124.75)
    const todayChange = ref(287)
    
    const userProfile = reactive({
      displayName: 'Marcus J.',
      initials: 'MJ',
      userId: 1
    })
    
    const stats = reactive({
      activeSquads: 6,
      liveBets: 12,
      winRate: 82
    })
    
    const navItems = ref([
      { id: 'squads', icon: '💥', label: 'Squads' },
      { id: 'bets', icon: '🎯', label: 'Bets' },
      { id: 'scores', icon: '⚡', label: 'Scores' },
      { id: 'stats', icon: '📊', label: 'Stats' }
    ])

    // API composable
    const { fetchUserData, fetchStats, createBet, fetchGames } = useSquadRideAPI()

    // Computed properties
    const formatCurrency = (amount) => {
      return new Intl.NumberFormat('en-US', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
      }).format(amount)
    }

    // Methods
    const setActiveTab = (tabId) => {
      activeTab.value = tabId
      activeSection.value = tabId
    }

    const navigateTo = (section) => {
      activeSection.value = section
      activeTab.value = section
    }

    const makeNewPicks = async () => {
      loading.value = true
      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 1000))
        console.log('Making new picks...')
        // Navigation logic would go here
      } catch (error) {
        console.error('Error making picks:', error)
      } finally {
        loading.value = false
      }
    }

    const viewAllGames = () => {
      console.log('Viewing all games...')
      // Navigation logic would go here
    }

    const loadUserData = async () => {
      loading.value = true
      try {
        const userData = await fetchUserData(userProfile.userId)
        userBalance.value = userData.balance
        todayChange.value = userData.todayChange
        
        const userStats = await fetchStats(userProfile.userId)
        Object.assign(stats, userStats)
      } catch (error) {
        console.error('Error loading user data:', error)
      } finally {
        loading.value = false
      }
    }

    // Lifecycle
    onMounted(() => {
      loadUserData()
    })

    return {
      // State
      loading,
      activeTab,
      activeSection,
      currentWeek,
      userBalance,
      todayChange,
      userProfile,
      stats,
      navItems,
      
      // Methods
      formatCurrency,
      setActiveTab,
      navigateTo,
      makeNewPicks,
      viewAllGames,
      loadUserData
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.app-container {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  background: linear-gradient(180deg, #000000 0%, #1f2937 40%, #374151 70%, #f97316 100%);
  min-height: 100vh;
  position: relative;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Background pattern */
.bg-pattern {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 1;
  background-image: 
    radial-gradient(circle at 20% 80%, rgba(75, 85, 99, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(249, 115, 22, 0.2) 0%, transparent 50%),
    radial-gradient(circle at 50% 50%, rgba(31, 41, 55, 0.4) 0%, transparent 70%);
}

/* Floating elements */
.floating-elements {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 2;
}

.floating-card {
  position: absolute;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.7), rgba(75, 85, 99, 0.5));
  border: 1px solid rgba(249, 115, 22, 0.3);
  border-radius: 12px;
  padding: 8px 12px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
  animation: floatUp 6s ease-in-out infinite;
  backdrop-filter: blur(10px);
}

.card-1 {
  top: 15%;
  left: 8%;
  animation-delay: 0s;
  border-color: rgba(249, 115, 22, 0.4);
  background: linear-gradient(135deg, rgba(31, 41, 55, 0.8), rgba(249, 115, 22, 0.2));
}

.card-2 {
  top: 55%;
  right: 12%;
  animation-delay: 2s;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.8), rgba(75, 85, 99, 0.6));
}

.card-3 {
  bottom: 25%;
  left: 15%;
  animation-delay: 4s;
  border-color: rgba(249, 115, 22, 0.4);
  background: linear-gradient(135deg, rgba(31, 41, 55, 0.8), rgba(249, 115, 22, 0.2));
}

@keyframes floatUp {
  0%, 100% { transform: translateY(0px) rotate(0deg); opacity: 0.4; }
  50% { transform: translateY(-15px) rotate(2deg); opacity: 0.8; }
}

/* Mobile container */
.mobile-container {
  width: 375px;
  height: 812px;
  background: linear-gradient(180deg, #000000 0%, #111827 50%, #1f2937 100%);
  border-radius: 25px;
  overflow: hidden;
  position: relative;
  z-index: 10;
  box-shadow: 
    0 20px 60px rgba(0, 0, 0, 0.6),
    0 0 0 1px rgba(249, 115, 22, 0.2);
}

/* Top bar */
.top-bar {
  height: 44px;
  background: linear-gradient(135deg, #000000, #374151, #f97316);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  font-size: 13px;
  color: white;
  font-weight: 600;
}

.currency {
  display: flex;
  align-items: center;
  gap: 6px;
  background: rgba(255, 255, 255, 0.15);
  padding: 4px 10px;
  border-radius: 12px;
  backdrop-filter: blur(10px);
}

.profile-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.profile-avatar {
  width: 26px;
  height: 26px;
  background: linear-gradient(135deg, #ffffff, #f97316);
  color: #000000;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 11px;
  font-weight: bold;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
}

/* Header section */
.header-section {
  background: linear-gradient(135deg, #000000, #374151, #f97316);
  padding: 30px 20px 40px;
  position: relative;
}

.header-content {
  text-align: center;
  color: white;
}

.logo-section {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  margin-bottom: 20px;
}

.logo-icon {
  width: 60px;
  height: 60px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
  animation: logoPulse 3s ease-in-out infinite;
}

.logo-text {
  font-size: 28px;
  font-weight: 900;
  background: linear-gradient(135deg, #000000, #6b7280, #f97316);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

@keyframes logoPulse {
  0%, 100% { transform: scale(1); box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3); }
  50% { transform: scale(1.05); box-shadow: 0 8px 30px rgba(249, 115, 22, 0.4); }
}

.app-title {
  font-size: 32px;
  font-weight: 800;
  margin-bottom: 8px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.app-subtitle {
  font-size: 16px;
  opacity: 0.9;
  font-weight: 500;
}

/* Wave separator */
.wave-separator {
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 100%;
  height: 20px;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 1200 120' preserveAspectRatio='none'%3E%3Cpath d='M0,0V46.29c47.79,22.2,103.59,32.17,158,28,70.36-5.37,136.33-33.31,206.8-37.5C438.64,32.43,512.34,53.67,583,72.05c69.27,18,138.3,24.88,209.4,13.08,36.15-6,69.85-17.84,104.45-29.34C989.49,25,1113-14.29,1200,52.47V0Z' opacity='.25' fill='%23111827'%3E%3C/path%3E%3Cpath d='M0,0V15.81C13,36.92,27.64,56.86,47.69,72.05,99.41,111.27,165,111,224.58,91.58c31.15-10.15,60.09-26.07,89.67-39.8,40.92-19,84.73-46,130.83-49.67,36.26-2.85,70.9,9.42,98.6,31.56,31.77,25.39,62.32,62,103.63,73,40.44,10.79,81.35-6.69,119.13-24.28s75.16-39,116.92-43.05c59.73-5.85,113.28,22.88,168.9,38.84,30.2,8.66,59,6.17,87.09-7.5,22.43-10.89,48-26.93,60.65-49.24V0Z' opacity='.5' fill='%23111827'%3E%3C/path%3E%3Cpath d='M0,0V5.63C149.93,59,314.09,71.32,475.83,42.57c43-7.64,84.23-20.12,127.61-26.46,59-8.63,112.48,12.24,165.56,35.4C827.93,77.22,886,95.24,951.2,90c86.53-7,172.46-45.71,248.8-84.81V0Z' fill='%23111827'%3E%3C/path%3E%3C/svg%3E") no-repeat;
  background-size: cover;
}

/* Main content */
.main-content {
  padding: 30px 20px;
  background: linear-gradient(180deg, #111827 0%, #1f2937 50%, #374151 100%);
}

.balance-card {
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.8), rgba(107, 114, 128, 0.3), rgba(249, 115, 22, 0.1));
  border: 2px solid rgba(249, 115, 22, 0.4);
  border-radius: 20px;
  padding: 25px;
  margin-bottom: 25px;
  text-align: center;
  position: relative;
  overflow: hidden;
  backdrop-filter: blur(15px);
}

.balance-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(249, 115, 22, 0.2), transparent);
  animation: shimmer 3s infinite;
}

@keyframes shimmer {
  0% { left: -100%; }
  100% { left: 100%; }
}

.balance-title {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 8px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.balance-amount {
  font-size: 36px;
  font-weight: 800;
  color: white;
  margin-bottom: 15px;
  text-shadow: 0 2px 10px rgba(249, 115, 22, 0.4);
}

.balance-change {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.balance-change.positive {
  background: linear-gradient(135deg, #f97316, #ea580c);
  color: white;
  box-shadow: 0 4px 15px rgba(249, 115, 22, 0.4);
}

.balance-change.negative {
  background: linear-gradient(135deg, #dc2626, #b91c1c);
  color: white;
  box-shadow: 0 4px 15px rgba(220, 38, 38, 0.4);
}

.quick-picks {
  margin-bottom: 25px;
}

.section-title {
  font-size: 20px;
  font-weight: 700;
  color: white;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.picks-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.pick-card {
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.6), rgba(75, 85, 99, 0.4));
  border: 2px solid rgba(107, 114, 128, 0.3);
  border-radius: 16px;
  padding: 20px 15px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  backdrop-filter: blur(10px);
}

.pick-card:hover, .pick-card.active {
  border-color: rgba(249, 115, 22, 0.6);
  background: linear-gradient(135deg, rgba(31, 41, 55, 0.8), rgba(249, 115, 22, 0.2));
  transform: translateY(-3px);
  box-shadow: 0 10px 30px rgba(249, 115, 22, 0.3);
}

.pick-icon {
  font-size: 24px;
  margin-bottom: 10px;
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.3));
}

.pick-title {
  font-size: 14px;
  font-weight: 700;
  color: white;
  margin-bottom: 4px;
}

.pick-count {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.cta-section {
  text-align: center;
}

.main-cta {
  background: linear-gradient(135deg, #000000, #6b7280, #f97316);
  border: none;
  border-radius: 25px;
  padding: 18px 40px;
  color: white;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 6px 25px rgba(249, 115, 22, 0.4);
  width: 100%;
  margin-bottom: 15px;
}

.main-cta:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 35px rgba(249, 115, 22, 0.5);
}

.main-cta:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.secondary-cta {
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.6), rgba(75, 85, 99, 0.4));
  border: 2px solid rgba(107, 114, 128, 0.5);
  border-radius: 25px;
  padding: 15px 40px;
  color: white;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 100%;
  backdrop-filter: blur(10px);
}

.secondary-cta:hover {
  background: linear-gradient(135deg, rgba(31, 41, 55, 0.8), rgba(249, 115, 22, 0.2));
  border-color: #f97316;
  box-shadow: 0 4px 20px rgba(249, 115, 22, 0.3);
}

/* Bottom navigation */
.bottom-nav {
  height: 80px;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.9), rgba(31, 41, 55, 0.8));
  backdrop-filter: blur(20px);
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-top: 1px solid rgba(249, 115, 22, 0.3);
  padding: 0 10px;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  color: rgba(255, 255, 255, 0.5);
  font-size: 11px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  padding: 8px 12px;
  border-radius: 12px;
  min-width: 55px;
}

.nav-item.active {
  color: #f97316;
  background: linear-gradient(135deg, rgba(31, 41, 55, 0.6), rgba(249, 115, 22, 0.2));
  border: 1px solid rgba(249, 115, 22, 0.3);
}

.nav-item:hover:not(.active) {
  color: rgba(255, 255, 255, 0.8);
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.6), rgba(75, 85, 99, 0.4));
  transform: translateY(-1px);
}

.nav-icon {
  font-size: 20px;
  margin-bottom: 2px;
  filter: drop-shadow(0 1px 2px rgba(0, 0, 0, 0.3));
}

/* Notification dot */
.notification-dot {
  position: absolute;
  top: -2px;
  right: -2px;
  width: 8px;
  height: 8px;
  background: #f97316;
  border-radius: 50%;
  border: 2px solid #0f172a;
  box-shadow: 0 0 10px rgba(249, 115, 22, 0.6);
  animation: notificationPulse 2s infinite;
}

@keyframes notificationPulse {
  0%, 100% { opacity: 1; transform: scale(1); }
  50% { opacity: 0.7; transform: scale(1.2); }
}

/* Loading overlay */
.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  border-radius: 25px;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid rgba(249, 115, 22, 0.3);
  border-top: 3px solid #f97316;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* Enhanced glow effects */
.glow-blue {
  box-shadow: 0 0 20px rgba(37, 99, 235, 0.3);
}

.glow-orange {
  box-shadow: 0 0 20px rgba(251, 146, 60, 0.3);
}

/* Responsive design */
@media (max-width: 768px) {
  .mobile-container {
    width: 100vw !important;
    height: 100vh !important;
    border-radius: 0 !important;
  }
}
</style>