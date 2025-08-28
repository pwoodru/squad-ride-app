<template>
  <div class="app-container">
    <div class="mobile-container">
  <!-- Top Bar -->
  <TopBar :user-balance="balance" :user-profile="user" />

      <!-- Header Section -->
      <div class="header-section">
        <div class="header-content">
          <div class="logo-section">
            <div class="logo-icon">
              <div class="logo-text">SR</div>
            </div>
          </div>
          <div class="app-title">My Squads</div>
          <div class="app-subtitle">Manage your teams and invites</div>
        </div>
        <div class="wave-separator"></div>
      </div>

      <!-- Main Content -->
      <div class="main-content">
        <!-- Tab Navigation -->
        <div class="mysquads-tabs">
          <button
            :class="['mysquads-tab-btn', { active: activeTab === 'active' }]"
            @click="activeTab = 'active'"
          >
            Active Squads
          </button>
          <button
            :class="['mysquads-tab-btn', { active: activeTab === 'invites' }]"
            @click="activeTab = 'invites'"
          >
            Invites
            <span v-if="invites.length" class="invite-badge">{{ invites.length }}</span>
          </button>
        </div>

        <!-- Tab Content -->
        <div v-if="activeTab === 'active'" class="space-y-4">
          <div v-for="squad in activeSquads" :key="squad.id" class="mysquads-card mb-6">
            <div class="mysquads-card-header">
              <div class="mysquads-avatar">{{ squad.avatar }}</div>
              <div class="mysquads-card-title">
                <span class="mysquads-name">{{ squad.name }}</span>
                <span class="mysquads-meta">
                  <span class="mysquads-meta-item"><span class="icon">👥</span> {{ squad.members }} members</span>
                  <span class="mysquads-meta-item"><span class="icon">🏆</span> Rank #{{ squad.rank }}</span>
                </span>
              </div>
              <div class="mysquads-winrate">
                <span class="mysquads-winrate-value">{{ squad.winRate }}%</span>
                <span class="mysquads-winrate-label">Win Rate</span>
              </div>
            </div>
            <div class="mysquads-info-row">
              <div class="mysquads-info-card">
                <div class="mysquads-info-value">{{ squad.totalWins }}</div>
                <div class="mysquads-info-label">Total Wins</div>
              </div>
              <div class="mysquads-info-card">
                <div class="mysquads-info-value mysquads-nextgame">{{ squad.nextGame }}</div>
                <div class="mysquads-info-label">Next Game</div>
              </div>
            </div>
            <div class="mysquads-actions">
              <button class="mysquads-btn-primary">Make Picks</button>
              <button class="mysquads-btn-secondary">View Squad</button>
            </div>
          </div>
          <!-- Create New Squad Card -->
          <div class="mysquads-create-card">
            <div class="w-16 h-16 bg-orange-500/20 rounded-full flex items-center justify-center mx-auto mb-4">
              <span class="icon text-orange-400 text-2xl">➕</span>
            </div>
            <h3 class="text-lg font-semibold mb-2">Create New Squad</h3>
            <p class="text-gray-400 mb-4">Start your own squad and invite friends</p>
            <button class="bg-orange-500 text-white px-6 py-3 rounded-lg font-medium hover:bg-orange-600 transition-colors">
              Create Squad
            </button>
          </div>
        </div>
        <div v-else class="space-y-4">
          <div v-for="invite in invites" :key="invite.id" class="mysquads-card mb-6">
            <div class="mysquads-card-header">
              <div class="mysquads-avatar mysquads-avatar-invite">{{ invite.avatar }}</div>
              <div class="mysquads-card-title">
                <span class="mysquads-name">{{ invite.name }}</span>
                <span class="mysquads-meta">
                  <span class="mysquads-meta-item">Invited by {{ invite.invitedBy }}</span>
                  <span class="mysquads-meta-item"><span class="icon">👥</span> {{ invite.members }} members</span>
                </span>
              </div>
              <div class="mysquads-winrate">
                <span class="mysquads-winrate-value">{{ invite.winRate }}%</span>
                <span class="mysquads-winrate-label">Win Rate</span>
              </div>
            </div>
            <div class="mysquads-info-row">
              <div class="mysquads-info-card">
                <div class="mysquads-info-value">{{ invite.members }}</div>
                <div class="mysquads-info-label">Members</div>
              </div>
            </div>
            <div class="mysquads-actions">
              <button class="mysquads-btn-primary mysquads-btn-accept">Accept</button>
              <button class="mysquads-btn-secondary mysquads-btn-decline">Decline</button>
            </div>
          </div>
        </div>
      </div>

      <!-- Bottom Navigation -->
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
import TopBar from './TopBar.vue'
const user = { displayName: 'Marcus J.', initials: 'MJ' }
const balance = 2124.75
import { ref } from 'vue'
import { useBottomNav } from '../composables/useBottomNav'

const { navItems, activeTab, setActiveTab } = useBottomNav('mysquads')

const activeSquads = [
  {
    id: 1,
    name: "Night Hawks",
    members: 12,
    winRate: 78,
    totalWins: 245,
    rank: 1,
    avatar: "🦅",
    status: "active",
    nextGame: "NBA Finals G4"
  },
  {
    id: 2,
    name: "Profit Squad",
    members: 8,
    winRate: 65,
    totalWins: 189,
    rank: 3,
    avatar: "💰",
    status: "active",
    nextGame: "MLB Tonight"
  },
  {
    id: 3,
    name: "The Favorites",
    members: 15,
    winRate: 72,
    totalWins: 298,
    rank: 2,
    avatar: "⭐",
    status: "active",
    nextGame: "UFC 299"
  }
]

const invites = [
  {
    id: 4,
    name: "High Rollers",
    members: 20,
    winRate: 82,
    invitedBy: "Marcus J",
    avatar: "🎲"
  },
  {
    id: 5,
    name: "Weekend Warriors",
    members: 6,
    winRate: 59,
    invitedBy: "Sarah K",
    avatar: "⚔️"
  }
]
</script>

<style scoped>
.mysquads-avatar-invite {
  background: linear-gradient(135deg, #60a5fa 0%, #2563eb 100%);
  color: #fff;
}
.mysquads-btn-accept {
  background: #22c55e;
}
.mysquads-btn-accept:hover {
  background: #16a34a;
}
.mysquads-btn-decline {
  background: #232b3a;
  color: #f87171;
}
.mysquads-btn-decline:hover {
  background: #374151;
  color: #fff;
}
.mysquads-card {
  background: #111827;
  border: 1.5px solid #232b3a;
  border-radius: 18px;
  padding: 28px 24px 20px 24px;
  margin-bottom: 1.5rem;
  box-shadow: 0 6px 24px 0 rgba(249,115,22,0.10), 0 1.5px 4px 0 rgba(0,0,0,0.10);
}
.mysquads-card-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 18px;
}
.mysquads-avatar {
  width: 32px;
  height: 32px;
  font-size: 1.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background: transparent;
  margin-right: 12px;
}
.mysquads-card-title {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2px;
}
.mysquads-name {
  font-size: 1.35rem;
  font-weight: 700;
  color: #fff;
  margin-bottom: 2px;
}
.mysquads-meta {
  display: flex;
  gap: 16px;
  font-size: 0.98rem;
  color: #b0bec5;
  font-weight: 500;
}
.mysquads-meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
}
.mysquads-winrate {
  text-align: right;
  min-width: 60px;
}
.mysquads-winrate-value {
  font-size: 1.5rem;
  font-weight: 800;
  color: #4ade80;
  letter-spacing: 0.5px;
}
.mysquads-winrate-label {
  font-size: 0.95rem;
  color: #b0bec5;
  font-weight: 500;
  display: block;
}
.mysquads-info-row {
  display: flex;
  gap: 18px;
  margin-bottom: 18px;
}
.mysquads-info-card {
  flex: 1;
  background: #181f2c;
  border-radius: 10px;
  padding: 18px 16px 12px 16px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.mysquads-info-value {
  font-size: 1.25rem;
  font-weight: 700;
  color: #fff;
  margin-bottom: 2px;
}
.mysquads-nextgame {
  color: #fbbf24;
}
.mysquads-info-label {
  font-size: 0.95rem;
  color: #b0bec5;
  font-weight: 500;
}
.mysquads-actions {
  display: flex;
  gap: 14px;
  margin-top: 8px;
}
.mysquads-btn-primary {
  flex: 1;
  background: #f97316;
  color: #fff;
  font-weight: 700;
  font-size: 1.08rem;
  border: none;
  border-radius: 8px;
  padding: 13px 0;
  transition: background 0.2s;
  cursor: pointer;
}
.mysquads-btn-primary:hover {
  background: #ea580c;
}
.mysquads-btn-secondary {
  flex: 0 0 120px;
  background: #232b3a;
  color: #b0bec5;
  font-weight: 700;
  font-size: 1.08rem;
  border: none;
  border-radius: 8px;
  padding: 13px 0;
  transition: background 0.2s;
  cursor: pointer;
}
.mysquads-btn-secondary:hover {
  background: #374151;
  color: #fff;
}
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;700;900&display=swap');

.app-container {
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
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
  min-height: 812px;
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
}
.mysquads-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 25px;
  overflow-x: auto;
  padding-bottom: 5px;
}
.mysquads-tab-btn {
  padding: 12px 20px;
  background: rgba(26,26,26,0.6);
  border: 1px solid rgba(230,126,34,0.3);
  border-radius: 25px;
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  white-space: nowrap;
  font-weight: 500;
}
.mysquads-tab-btn.active {
  background: linear-gradient(45deg, #f97316, #ea580c);
  border-color: #f97316;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(249,115,22,0.3);
}
.mysquads-tab-btn:hover:not(.active) {
  background: rgba(249,115,22,0.2);
  transform: translateY(-1px);
}
.invite-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #ef4444;
  color: #fff;
  font-size: 12px;
  border-radius: 50%;
  width: 22px;
  height: 22px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  border: 2px solid #fff7ed;
  box-shadow: 0 0 8px rgba(239,68,68,0.3);
}
.mysquads-card {
  background: rgba(26,26,26,0.7);
  border: 1px solid rgba(230,126,34,0.3);
  border-radius: 16px;
  padding: 20px;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  margin-bottom: 1.5rem;
  box-shadow: 0 6px 24px 0 rgba(249,115,22,0.10), 0 1.5px 4px 0 rgba(0,0,0,0.10);
}
.mysquads-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(249,115,22,0.2);
  border-color: #f97316;
}
.mysquads-create-card {
  background: linear-gradient(90deg, #f97316 0%, #ea580c 100%);
  border: 2px dashed #f97316;
  border-radius: 16px;
  padding: 32px 0;
  text-align: center;
  color: #fff;
  margin-top: 24px;
}
.mysquads-create-card .icon {
  font-size: 2.5em;
  margin-bottom: 12px;
}
.mysquads-create-card button {
  margin-top: 12px;
}
.bottom-nav {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100vw;
  height: 80px;
  background: linear-gradient(135deg, rgba(0, 0, 0, 0.9), rgba(31, 41, 55, 0.8));
  backdrop-filter: blur(20px);
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-top: 1px solid rgba(249, 115, 22, 0.3);
  padding: 0 10px;
  z-index: 100;
}
.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  color: rgba(255,255,255,0.5);
  font-size: 11px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  padding: 8px 12px;
  border-radius: 12px;
  min-width: 55px;
  position: relative;
}
.nav-item.home-tab {
  background: linear-gradient(135deg, #f97316 60%, #fff7ed 100%);
  color: #111827;
  box-shadow: 0 2px 16px rgba(249,115,22,0.25);
  border-radius: 50%;
  width: 60px;
  height: 60px;
  margin-top: -30px;
  z-index: 101;
  justify-content: center;
  align-items: center;
  font-size: 22px;
  border: 3px solid #fff7ed;
}
.nav-item.home-tab .nav-icon {
  font-size: 32px;
  margin-bottom: 0;
  color: #f97316;
  filter: none;
}
.nav-item.active {
  color: #f97316;
  background: linear-gradient(135deg, rgba(31,41,55,0.6), rgba(249,115,22,0.2));
  border: 1px solid rgba(249,115,22,0.3);
}
.nav-item:hover:not(.active) {
  color: rgba(255,255,255,0.8);
  background: linear-gradient(135deg, rgba(0,0,0,0.6), rgba(75,85,99,0.4));
  transform: translateY(-1px);
}
.nav-icon {
  font-size: 20px;
  margin-bottom: 2px;
  filter: drop-shadow(0 1px 2px rgba(0,0,0,0.3));
}
@media (max-width: 768px) {
  .mobile-container {
    width: 100vw !important;
    min-height: 100vh !important;
    border-radius: 0 !important;
  }
}
</style>