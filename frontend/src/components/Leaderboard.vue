<template>
  <div class="app-container">
    <div class="mobile-container">
      <!-- Top Bar -->
      <div class="top-bar">
        <div class="currency">
          <span>🏆</span>
          <span>Leaderboard</span>
        </div>
        <div class="profile-info">
          <span>Your Rank</span>
          <div class="profile-avatar">#47</div>
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
          <div class="app-title">Leaderboard</div>
          <div class="app-subtitle">Top performers</div>
        </div>
        <div class="wave-separator"></div>
      </div>

      <!-- Main Content -->
      <div class="main-content">
        <!-- Filter Pills -->
        <div class="filter-pills">
          <button
            v-for="filter in filters"
            :key="filter.id"
            :class="['filter-pill', { active: activeFilter === filter.id }]"
            @click="activeFilter = filter.id"
          >
            <span class="filter-icon">{{ filter.icon }}</span>
            {{ filter.label }}
          </button>
        </div>
        <!-- Category Selector -->
        <div class="category-selector">
          <button
            v-for="category in categories"
            :key="category.id"
            :class="['category-btn', { active: selectedCategory === category.id }]"
            @click="selectedCategory = category.id"
          >
            {{ category.label }}
          </button>
        </div>

        <!-- Top 3 Podium -->
        <div class="podium-row">
          <div class="podium-card second">
            <div class="podium-avatar second">{{ leaderboardData[1].avatar }}</div>
            <div class="podium-info">
              <div class="podium-icon">🥈</div>
              <div class="podium-name">{{ leaderboardData[1].name }}</div>
              <div class="podium-winnings">{{ formatCurrency(leaderboardData[1].winnings) }}</div>
            </div>
          </div>
          <div class="podium-card first">
            <div class="podium-avatar first">{{ leaderboardData[0].avatar }}</div>
            <div class="podium-info">
              <div class="podium-icon">🏆</div>
              <div class="podium-name">{{ leaderboardData[0].name }}</div>
              <div class="podium-winnings">{{ formatCurrency(leaderboardData[0].winnings) }}</div>
            </div>
          </div>
          <div class="podium-card third">
            <div class="podium-avatar third">{{ leaderboardData[2].avatar }}</div>
            <div class="podium-info">
              <div class="podium-icon">🥉</div>
              <div class="podium-name">{{ leaderboardData[2].name }}</div>
              <div class="podium-winnings">{{ formatCurrency(leaderboardData[2].winnings) }}</div>
            </div>
          </div>
        </div>

        <!-- Full Leaderboard List -->
        <div class="leaderboard-list">
          <div
            v-for="(user, index) in leaderboardData"
            :key="user.rank"
            :class="['leaderboard-row', { top3: user.rank <= 3 }]"
          >
            <div class="rank-icon">
              <span v-if="user.rank === 1">🏆</span>
              <span v-else-if="user.rank === 2">🥈</span>
              <span v-else-if="user.rank === 3">🥉</span>
              <span v-else> #{{ user.rank }} </span>
            </div>
            <div :class="['user-avatar', { first: user.rank === 1, second: user.rank === 2, third: user.rank === 3 }]">
              {{ user.avatar }}
            </div>
            <div class="user-info">
              <div class="user-name">{{ user.name }}</div>
              <div class="user-meta">
                <span>{{ user.squads }} squads</span>
                <span>{{ user.winRate }}% win rate</span>
                <span class="streak">{{ user.streak }} streak</span>
              </div>
            </div>
            <div class="user-winnings">
              <div class="winnings-amount">{{ formatCurrency(user.winnings) }}</div>
              <div class="winnings-label">total winnings</div>
            </div>
          </div>
        </div>
        <button class="load-more">Load More Players</button>
      </div>

      <div class="bottom-nav">
        <div
          v-for="navItem in navItems"
          :key="navItem.id"
          class="nav-item"
          :class="[{ active: activeTab === navItem.id }, navItem.isHome ? 'home-tab' : '']"
          @click="setActiveTab(navItem.id)"
        >
          <div class="nav-icon">{{ navItem.icon }}</div>
          <span v-if="!navItem.isHome">{{ navItem.label }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useBottomNav } from '../composables/useBottomNav'

const { navItems, activeTab, setActiveTab } = useBottomNav('home')

const activeFilter = ref('all-time')
const selectedCategory = ref('winnings')

const filters = [
  { id: 'all-time', label: 'All Time', icon: '📅' },
  { id: 'monthly', label: 'This Month', icon: '📈' },
  { id: 'weekly', label: 'This Week', icon: '👥' },
]

const categories = [
  { id: 'winnings', label: 'Total Winnings' },
  { id: 'winRate', label: 'Win Rate' },
  { id: 'streak', label: 'Win Streak' },
]

const leaderboardData = [
  { rank: 1, name: 'Marcus J.', avatar: 'MJ', winnings: 15847.50, winRate: 94, squads: 23, streak: 12 },
  { rank: 2, name: 'Sarah K.', avatar: 'SK', winnings: 14234.25, winRate: 89, squads: 19, streak: 8 },
  { rank: 3, name: 'Alex R.', avatar: 'AR', winnings: 12956.75, winRate: 91, squads: 21, streak: 15 },
  { rank: 4, name: 'Jordan P.', avatar: 'JP', winnings: 11483.00, winRate: 87, squads: 17, streak: 6 },
  { rank: 5, name: 'Taylor M.', avatar: 'TM', winnings: 10829.50, winRate: 85, squads: 16, streak: 4 },
  { rank: 6, name: 'Chris L.', avatar: 'CL', winnings: 9654.25, winRate: 88, squads: 14, streak: 9 },
  { rank: 7, name: 'Jamie W.', avatar: 'JW', winnings: 8947.75, winRate: 82, squads: 12, streak: 3 },
  { rank: 8, name: 'Morgan D.', avatar: 'MD', winnings: 8234.00, winRate: 79, squads: 13, streak: 7 },
]

function formatCurrency(amount) {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
  }).format(amount)
}
</script>

<style scoped>
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
.mobile-container {
  width: 375px;
  height: 812px;
  background: linear-gradient(180deg, #000000 0%, #111827 50%, #1f2937 100%);
  border-radius: 25px;
  overflow: hidden;
  position: relative;
  z-index: 10;
  box-shadow: 0 20px 60px rgba(0,0,0,0.6), 0 0 0 1px rgba(249,115,22,0.2);
  display: flex;
  flex-direction: column;
}
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
  background: rgba(255,255,255,0.15);
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
  box-shadow: 0 2px 8px rgba(0,0,0,0.3);
}
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
  background: rgba(255,255,255,0.95);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 20px rgba(0,0,0,0.3);
}
.logo-text {
  font-size: 28px;
  font-weight: 900;
  background: linear-gradient(135deg, #000000, #6b7280, #f97316);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.app-title {
  font-size: 32px;
  font-weight: 800;
  margin-bottom: 8px;
  text-shadow: 0 2px 10px rgba(0,0,0,0.3);
}
.app-subtitle {
  font-size: 16px;
  opacity: 0.9;
  font-weight: 500;
}
.wave-separator {
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 100%;
  height: 20px;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 1200 120' preserveAspectRatio='none'%3E%3Cpath d='M0,0V46.29c47.79,22.2,103.59,32.17,158,28,70.36-5.37,136.33-33.31,206.8-37.5C438.64,32.43,512.34,53.67,583,72.05c69.27,18,138.3,24.88,209.4,13.08,36.15-6,69.85-17.84,104.45-29.34C989.49,25,1113-14.29,1200,52.47V0Z' opacity='.25' fill='%23111827'%3E%3C/path%3E%3Cpath d='M0,0V15.81C13,36.92,27.64,56.86,47.69,72.05,99.41,111.27,165,111,224.58,91.58c31.15-10.15,60.09-26.07,89.67-39.8,40.92-19,84.73-46,130.83-49.67,36.26-2.85,70.9,9.42,98.6,31.56,31.77,25.39,62.32,62,103.63,73,40.44,10.79,81.35-6.69,119.13-24.28s75.16-39,116.92-43.05c59.73-5.85,113.28,22.88,168.9,38.84,30.2,8.66,59,6.17,87.09-7.5,22.43-10.89,48-26.93,60.65-49.24V0Z' opacity='.5' fill='%23111827'%3E%3C/path%3E%3Cpath d='M0,0V5.63C149.93,59,314.09,71.32,475.83,42.57c43-7.64,84.23-20.12,127.61-26.46,59-8.63,112.48,12.24,165.56,35.4C827.93,77.22,886,95.24,951.2,90c86.53-7,172.46-45.71,248.8-84.81V0Z' fill='%23111827'%3E%3C/path%3E%3C/svg%3E") no-repeat;
  background-size: cover;
}
.main-content {
  padding: 30px 20px;
  background: linear-gradient(180deg, #111827 0%, #1f2937 50%, #374151 100%);
  flex: 1 1 auto;
  overflow-y: auto;
}
.filter-pills {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
  overflow-x: auto;
}
.filter-pill {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 18px;
  border-radius: 999px;
  font-size: 14px;
  background: rgba(30,41,59,0.5);
  color: #d1d5db;
  border: none;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}
.filter-pill.active {
  background: #f97316;
  color: #fff;
}
.filter-icon {
  font-size: 16px;
}
.category-selector {
  display: flex;
  gap: 8px;
  margin-bottom: 24px;
}
.category-btn {
  padding: 6px 16px;
  border-radius: 8px;
  font-size: 13px;
  background: none;
  color: #9ca3af;
  border: none;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}
.category-btn.active {
  background: #374151;
  color: #fff;
}
.podium-row {
  display: flex;
  align-items: flex-end;
  justify-content: center;
  gap: 18px;
  margin-bottom: 32px;
}
.podium-card {
  text-align: center;
}
.podium-avatar {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 8px auto;
  font-weight: bold;
  color: #fff;
}
.podium-avatar.first {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #f97316, #fbbf24);
  border: 3px solid #fde68a;
  font-size: 2rem;
  border-radius: 50%;
}
.podium-avatar.second {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #6b7280, #9ca3af);
  font-size: 1.5rem;
  border-radius: 50%;
}
.podium-avatar.third {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #f59e42, #f97316);
  font-size: 1.5rem;
  border-radius: 50%;
}
.podium-info {
  background: rgba(30,41,59,0.5);
  border-radius: 12px;
  padding: 10px 0 6px 0;
  min-height: 70px;
}
.podium-icon {
  font-size: 1.5rem;
  margin-bottom: 2px;
}
.podium-name {
  font-size: 1rem;
  font-weight: 600;
}
.podium-winnings {
  color: #f97316;
  font-weight: bold;
  font-size: 1.1rem;
}
.leaderboard-list {
  background: rgba(30,41,59,0.3);
  border-radius: 18px;
  overflow: hidden;
  border: 1px solid rgba(30,41,59,0.4);
}
.leaderboard-row {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 16px 14px;
  border-bottom: 1px solid rgba(30,41,59,0.2);
  transition: background 0.2s;
}
.leaderboard-row:last-child {
  border-bottom: none;
}
.leaderboard-row.top3 {
  background: rgba(249,115,22,0.07);
}
.rank-icon {
  width: 36px;
  text-align: center;
  font-size: 1.2rem;
  font-weight: bold;
  color: #fbbf24;
}
.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.1rem;
  font-weight: bold;
  color: #fff;
  background: linear-gradient(135deg, #6b7280, #9ca3af);
}
.user-avatar.first {
  background: linear-gradient(135deg, #f97316, #fbbf24);
  border: 2px solid #fde68a;
}
.user-avatar.second {
  background: linear-gradient(135deg, #6b7280, #9ca3af);
}
.user-avatar.third {
  background: linear-gradient(135deg, #f59e42, #f97316);
}
.user-info {
  flex: 1 1 auto;
}
.user-name {
  font-weight: 600;
  color: #fff;
  font-size: 1rem;
}
.user-meta {
  display: flex;
  gap: 12px;
  font-size: 0.92rem;
  color: #9ca3af;
}
.streak {
  color: #34d399;
}
.user-winnings {
  text-align: right;
}
.winnings-amount {
  color: #f97316;
  font-weight: bold;
  font-size: 1.1rem;
}
.winnings-label {
  color: #9ca3af;
  font-size: 0.8rem;
}
.load-more {
  width: 100%;
  margin-top: 18px;
  padding: 12px 0;
  background: rgba(30,41,59,0.5);
  color: #d1d5db;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}
.load-more:hover {
  background: #374151;
  color: #fff;
}
</style>