<template>
        <div class="app-container">
            <div class="mobile-container">
                <!-- Top Bar -->
                <div class="top-bar">
                    <div class="currency">
                        <span>💰</span>
                        <span>{{ formatCurrency(balance) }}</span>
                    </div>
                    <div class="profile-info">
                        <span>{{ user.displayName }}</span>
                        <div class="profile-avatar">{{ user.initials }}</div>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="main-content">
                    <div class="page-title">
                        <div class="sr-logo">SR</div>
                        <h1>Daily Rewards</h1>
                        <p>Dominate the Night. Win Big.</p>
                    </div>

                    <div class="streak-container">
                        <div class="streak-title">LOGIN STREAK</div>
                        <div class="streak-count">{{ streak }}</div>
                        <div class="streak-subtitle">Days in a row! Keep it up!</div>
                    </div>

                    <div class="rewards-grid">
                        <div
                            v-for="(reward, idx) in rewards"
                            :key="reward.day"
                            :class="['reward-day', reward.status]"
                        >
                            <div v-if="reward.status === 'completed'" class="check-mark">✓</div>
                            <div v-if="reward.status === 'current'" class="current-badge">!</div>
                            <div class="day-number">Day {{ reward.day }}</div>
                            <div class="reward-icon">{{ reward.icon }}</div>
                            <div class="reward-amount">{{ formatCurrency(reward.amount) }}</div>
                        </div>
                    </div>

                    <button
                        class="collect-button"
                        :disabled="collectDisabled"
                        @click="collectReward"
                    >
                        {{ collectButtonText }}
                    </button>

                    <div class="bonus-section">
                        <div class="bonus-title">🎉 Weekly Bonuses</div>
                        <div class="bonus-item" v-for="bonus in weeklyBonuses" :key="bonus.desc">
                            <div class="bonus-desc">{{ bonus.desc }}</div>
                            <div class="bonus-reward">{{ bonus.reward }}</div>
                        </div>
                    </div>

                    <div class="bonus-section">
                        <div class="bonus-title">📊 Progress to Next Milestone</div>
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
                            <span style="color: #b0bec5;">Week 1 Bonus</span>
                            <span style="color: #e67e22; font-weight: 600;">{{ streak }}/7 days</span>
                        </div>
                        <div class="progress-bar">
                            <div class="progress-fill" :style="{ width: progressPercent + '%' }"></div>
                        </div>
                        <div style="text-align: center; color: #b0bec5; font-size: 0.9em;">
                            {{ 7 - streak > 0 ? `${7 - streak} more days for $100 bonus!` : 'Bonus unlocked!' }}
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
    import { ref, computed } from 'vue'
    import { useBottomNav } from '../composables/useBottomNav'

    const { navItems, activeTab, setActiveTab } = useBottomNav('home')

    const user = ref({ displayName: 'Marcus J.', initials: 'MJ' })
    const balance = ref(2124.75)
    const streak = ref(5)

    const rewards = ref([
        { day: 1, icon: '💰', amount: 5, status: 'completed' },
        { day: 2, icon: '💎', amount: 10, status: 'completed' },
        { day: 3, icon: '🎯', amount: 15, status: 'completed' },
        { day: 4, icon: '🔥', amount: 20, status: 'completed' },
        { day: 5, icon: '⚡', amount: 25, status: 'current' },
        { day: 6, icon: '🎁', amount: 30, status: 'locked' },
        { day: 7, icon: '👑', amount: 50, status: 'locked' },
        { day: 8, icon: '💥', amount: 75, status: 'locked' },
        { day: 9, icon: '🚀', amount: 100, status: 'locked' },
        { day: 10, icon: '💸', amount: 150, status: 'locked' },
        { day: 11, icon: '⭐', amount: 200, status: 'locked' },
        { day: 12, icon: '🏆', amount: 287, status: 'locked' },
    ])

    const collectDisabled = ref(false)
    const collectButtonText = computed(() => {
        const current = rewards.value.find(r => r.status === 'current')
        if (!current) return 'Come back tomorrow!'
        return collectDisabled.value ? 'Reward Collected!' : `Collect Today's Reward - $${current.amount}`
    })

    function formatCurrency(amount) {
        return '$' + amount.toLocaleString(undefined, { minimumFractionDigits: 2 })
    }

    function collectReward() {
        const idx = rewards.value.findIndex(r => r.status === 'current')
        if (idx === -1) return
        collectDisabled.value = true
        // Add reward to balance
        balance.value += rewards.value[idx].amount
        // Mark current as completed
        rewards.value[idx].status = 'completed'
        // Move to next day if exists
        if (rewards.value[idx + 1]) {
            setTimeout(() => {
                rewards.value[idx + 1].status = 'current'
                streak.value++
                collectDisabled.value = false
            }, 1000)
        }
    }

    const weeklyBonuses = [
        { desc: 'Week 1 Complete', reward: '+$100 Bonus' },
        { desc: 'Week 2 Complete', reward: '+$200 Bonus' },
        { desc: 'Month Complete', reward: '+$500 Bonus' },
    ]

    const progressPercent = computed(() => Math.min(100, Math.round((streak.value / 7) * 100)))
    </script>

    <style scoped>
    /* ...existing styles from SquadRideApp.vue and custom daily rewards styles... */
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
    .main-content {
        padding: 20px;
        flex: 1 1 auto;
        overflow-y: auto;
    }
    .page-title {
        text-align: center;
        margin-bottom: 30px;
    }
    .sr-logo {
        width: 60px;
        height: 60px;
        background: rgba(255,255,255,0.9);
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 15px;
        font-size: 1.5em;
        font-weight: bold;
        color: #666;
    }
    .page-title h1 {
        font-size: 2.2em;
        font-weight: 700;
        margin-bottom: 10px;
        color: white;
    }
    .page-title p {
        color: #b0bec5;
        font-size: 1em;
    }
    .streak-container {
        background: rgba(26,26,26,0.7);
        border: 1px solid rgba(230,126,34,0.3);
        border-radius: 16px;
        padding: 20px;
        margin-bottom: 25px;
        text-align: center;
        backdrop-filter: blur(10px);
    }
    .streak-title {
        font-size: 1.1em;
        color: #b0bec5;
        margin-bottom: 10px;
    }
    .streak-count {
        font-size: 2.5em;
        font-weight: 700;
        color: #e67e22;
        margin-bottom: 10px;
    }
    .streak-subtitle {
        color: #4caf50;
        font-weight: 600;
    }
    .rewards-grid {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 12px;
        margin-bottom: 25px;
    }
    .reward-day {
        background: rgba(26,26,26,0.7);
        border: 1px solid rgba(255,255,255,0.1);
        border-radius: 12px;
        padding: 15px 8px;
        text-align: center;
        transition: all 0.3s ease;
        position: relative;
        overflow: hidden;
    }
    .reward-day.completed {
        background: rgba(76,175,80,0.2);
        border-color: #4caf50;
    }
    .reward-day.current {
        background: linear-gradient(135deg, #e67e22, #d35400);
        border-color: #e67e22;
        transform: scale(1.05);
        box-shadow: 0 5px 20px rgba(230,126,34,0.4);
        animation: pulse 2s infinite;
    }
    .reward-day.locked {
        opacity: 0.5;
    }
    .day-number {
        font-size: 0.9em;
        color: #b0bec5;
        margin-bottom: 8px;
        font-weight: 600;
    }
    .reward-day.current .day-number {
        color: white;
    }
    .reward-icon {
        font-size: 1.5em;
        margin-bottom: 5px;
    }
    .reward-amount {
        font-size: 0.85em;
        font-weight: 600;
        color: #e67e22;
    }
    .reward-day.current .reward-amount {
        color: white;
    }
    .reward-day.completed .reward-amount {
        color: #4caf50;
    }
    .collect-button {
        width: 100%;
        padding: 16px;
        background: linear-gradient(45deg, #e67e22, #d35400);
        border: none;
        border-radius: 25px;
        color: white;
        font-size: 1.1em;
        font-weight: 600;
        cursor: pointer;
        margin-bottom: 20px;
        transition: all 0.3s ease;
        box-shadow: 0 5px 15px rgba(230,126,34,0.3);
    }
    .collect-button:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(230,126,34,0.4);
    }
    .collect-button:disabled {
        background: rgba(255,255,255,0.1);
        color: #666;
        cursor: not-allowed;
        transform: none;
        box-shadow: none;
    }
    .bonus-section {
        background: rgba(26,26,26,0.7);
        border: 1px solid rgba(230,126,34,0.3);
        border-radius: 16px;
        padding: 20px;
        margin-bottom: 20px;
        backdrop-filter: blur(10px);
    }
    .bonus-title {
        display: flex;
        align-items: center;
        gap: 10px;
        font-size: 1.2em;
        font-weight: 600;
        margin-bottom: 15px;
        color: #e67e22;
    }
    .bonus-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px 0;
        border-bottom: 1px solid rgba(255,255,255,0.1);
    }
    .bonus-item:last-child {
        border-bottom: none;
    }
    .bonus-desc {
        color: #b0bec5;
    }
    .bonus-reward {
        color: #e67e22;
        font-weight: 600;
    }
    .progress-bar {
        width: 100%;
        height: 8px;
        background: rgba(255,255,255,0.1);
        border-radius: 4px;
        overflow: hidden;
        margin: 15px 0;
    }
    .progress-fill {
        height: 100%;
        background: linear-gradient(90deg, #e67e22, #d35400);
        border-radius: 4px;
        transition: width 0.3s ease;
    }
    .check-mark {
        position: absolute;
        top: 5px;
        right: 5px;
        color: #4caf50;
        font-size: 1.2em;
    }
    .current-badge {
        position: absolute;
        top: -5px;
        right: -5px;
        background: #4caf50;
        color: white;
        border-radius: 50%;
        width: 20px;
        height: 20px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 0.8em;
        font-weight: bold;
    }
    @keyframes pulse {
        0%, 100% { transform: scale(1); }
        50% { transform: scale(1.05); }
    }
    @media (max-width: 480px) {
        .rewards-grid {
            grid-template-columns: repeat(3, 1fr);
        }
        .reward-day {
            padding: 12px 6px;
        }
    }
    </style>