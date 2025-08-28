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
                                    <div class="logo-text">🏆</div>
                                </div>
                            </div>
                            <div class="app-title">Challenges</div>
                            <div class="app-subtitle">Complete challenges to earn rewards</div>
                        </div>
                        <div class="wave-separator"></div>
                    </div>

                    <!-- Main Content -->
                    <div class="main-content">
                        <div class="challenges-section">
                            <!-- Daily Challenges -->
                                            <div class="daily-challenges">
                                                    <div class="section-title">
                                                        <span>🔥</span>
                                                        <span>Daily Challenges</span>
                                                    </div>
                                                    <div class="daily-grid">
                                                        <div v-for="challenge in dailyChallenges" :key="challenge.title" :class="['challenge-card', { completed: challenge.completed }]">
                                                            <div v-if="challenge.completed" class="completed-badge">✓ Complete</div>
                                                            <div class="challenge-header">
                                                                <div :class="['challenge-icon', challenge.iconClass]">{{ challenge.icon }}</div>
                                                                <div class="challenge-info">
                                                                    <div class="challenge-title">{{ challenge.title }}</div>
                                                                    <div class="challenge-description">{{ challenge.description }}</div>
                                                                    <div class="challenge-reward">💰 +{{ challenge.reward }} coins</div>
                                                                </div>
                                                            </div>
                                                            <div class="progress-bar">
                                                                <div class="progress-fill" :style="{ width: challenge.progress + '%' }"></div>
                                                            </div>
                                                            <div class="progress-text">{{ challenge.progressText }}</div>
                                                        </div>
                                                    </div>
                            </div>

                            <!-- Weekly Challenges -->
                            <div class="weekly-challenges">
                                <div class="category-badge weekly-badge">Weekly Challenges</div>
                                <div v-for="challenge in weeklyChallenges" :key="challenge.title" :class="['challenge-card', { completed: challenge.completed }]">
                                    <div v-if="challenge.completed" class="completed-badge">✓ Complete</div>
                                    <div class="challenge-header">
                                        <div :class="['challenge-icon', challenge.iconClass]">{{ challenge.icon }}</div>
                                        <div class="challenge-info">
                                            <div class="challenge-title">{{ challenge.title }}</div>
                                            <div class="challenge-description">{{ challenge.description }}</div>
                                            <div class="challenge-reward">💰 +{{ challenge.reward }} coins</div>
                                        </div>
                                    </div>
                                    <div class="progress-bar">
                                        <div class="progress-fill" :style="{ width: challenge.progress + '%' }"></div>
                                    </div>
                                    <div class="progress-text">{{ challenge.progressText }}</div>
                                </div>
                            </div>

                            <!-- Lifetime Challenges -->
                            <div class="lifetime-challenges">
                                <div class="category-badge lifetime-badge">Lifetime Challenges</div>
                                <div v-for="challenge in lifetimeChallenges" :key="challenge.title" :class="['challenge-card', { completed: challenge.completed }]">
                                    <div v-if="challenge.completed" class="completed-badge">✓ Complete</div>
                                    <div class="challenge-header">
                                        <div :class="['challenge-icon', challenge.iconClass]">{{ challenge.icon }}</div>
                                        <div class="challenge-info">
                                            <div class="challenge-title">{{ challenge.title }}</div>
                                            <div class="challenge-description">{{ challenge.description }}</div>
                                            <div class="challenge-reward">💰 +{{ challenge.reward }} coins</div>
                                        </div>
                                    </div>
                                    <div class="progress-bar">
                                        <div class="progress-fill" :style="{ width: challenge.progress + '%' }"></div>
                                    </div>
                                    <div class="progress-text">{{ challenge.progressText }}</div>
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
import { ref } from 'vue'
import TopBar from './TopBar.vue'
import { useBottomNav } from '../composables/useBottomNav'

const { navItems, activeTab, setActiveTab } = useBottomNav('home')

    const user = ref({
        displayName: 'Alex Rider',
        initials: 'AR'
    })
    const balance = ref(1200)

    function formatCurrency(amount) {
        return amount.toLocaleString()
    }

    const dailyChallenges = ref([
        {
            title: 'Daily Winner',
            description: 'Win a bet today',
            reward: 50,
            icon: '🎯',
            iconClass: 'betting',
            progress: 100,
            progressText: '1/1',
            completed: true
        },
        {
            title: 'Login Streak',
            description: 'Log in 3 days in a row',
            reward: 30,
            icon: '🔥',
            iconClass: 'streak',
            progress: 33,
            progressText: '1/3',
            completed: false
        }
    ])

    const weeklyChallenges = ref([
        {
            title: 'Squad Domination',
            description: 'Win 5 squad bets this week',
            reward: 100,
            icon: '👥',
            iconClass: 'squad',
            progress: 20,
            progressText: '1/5',
            completed: false
        },
        {
            title: 'High Roller',
            description: 'Place 10 bets this week',
            reward: 80,
            icon: '💸',
            iconClass: 'betting',
            progress: 40,
            progressText: '4/10',
            completed: false
        }
    ])

    const lifetimeChallenges = ref([
        {
            title: 'Betting Legend',
            description: 'Place 100 bets',
            reward: 500,
            icon: '🏅',
            iconClass: 'betting',
            progress: 34,
            progressText: '34/100',
            completed: false
        },
        {
            title: 'Squad Master',
            description: 'Win 25 squad bets',
            reward: 300,
            icon: '👑',
            iconClass: 'squad',
            progress: 48,
            progressText: '12/25',
            completed: false
        },
        {
            title: 'Social Butterfly',
            description: 'Invite 50 friends',
            reward: 200,
            icon: '🦋',
            iconClass: 'social',
            progress: 60,
            progressText: '30/50',
            completed: false
        },
        {
            title: 'First Steps',
            description: 'Complete your first challenge',
            reward: 20,
            icon: '👣',
            iconClass: 'completed',
            progress: 100,
            progressText: '1/1',
            completed: true
        }
    ])

        </script>

        <style scoped>
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

                .wave-separator {
                    position: absolute;
                    left: 0;
                    bottom: -1px;
                    width: 100%;
                    height: 32px;
                    background: url('data:image/svg+xml;utf8,<svg width="100%" height="32" viewBox="0 0 375 32" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M0 32V0C62.5 16 125 16 187.5 0C250 16 312.5 16 375 0V32H0Z" fill="%23f97316" fill-opacity="0.12"/></svg>');
                    background-repeat: no-repeat;
                    background-size: cover;
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
        .daily-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
        }
            .header {
                padding: 60px 20px 30px;
                text-align: center;
            }

        .header-icon {
            width: 50px;
            height: 50px;
            background: rgba(255,138,46,0.2);
            border-radius: 12px;
            margin: 0 auto 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
        }

        .header h1 {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 8px;
            color: #ffffff;
        }

        .header p {
            color: rgba(255,255,255,0.8);
            font-size: 16px;
            font-weight: 400;
        }

        .balance-card {
            margin: 20px;
            padding: 20px;
            background: rgba(255,255,255,0.05);
            border-radius: 16px;
            border: 1px solid rgba(255,138,46,0.2);
            text-align: center;
        }

        .balance-label {
            color: rgba(255,255,255,0.7);
            font-size: 14px;
            margin-bottom: 8px;
        }

        .balance-amount {
            font-size: 24px;
            font-weight: 700;
            color: #ff8a2e;
        }

        .challenges-section {
            padding: 20px 10px 100px;
        }

        .section-title {
            font-size: 22px;
            font-weight: 700;
            margin-bottom: 20px;
            color: #ffffff;
        }

        .challenge-card {
            background: rgba(255,255,255,0.1);
            border-radius: 20px;
            padding: 24px;
            margin-bottom: 16px;
            border: 1px solid rgba(255,255,255,0.15);
            position: relative;
            overflow: hidden;
            transition: all 0.3s ease;
            margin-left: 0;
            margin-right: 0;
            backdrop-filter: blur(10px);
        }

        .challenge-card:hover {
            transform: translateY(-2px);
            border-color: rgba(255,138,46,0.3);
        }

        .challenge-card.completed {
            border-color: #4ade80;
            background: rgba(74,222,128,0.1);
        }

        .challenge-header {
            display: flex;
            align-items: center;
            margin-bottom: 12px;
        }

        .challenge-icon {
            width: 40px;
            height: 40px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
                font-size: 18px;
        }

        .challenge-icon.betting {
            background: linear-gradient(135deg, #ff8a2e, #ff6b35);
        }

        .challenge-icon.squad {
            background: linear-gradient(135deg, #8b5cf6, #a855f7);
        }

        .challenge-icon.streak {
            background: linear-gradient(135deg, #ef4444, #dc2626);
        }

        .challenge-icon.social {
            background: linear-gradient(135deg, #06b6d4, #0891b2);
        }

        .challenge-icon.completed {
            background: linear-gradient(135deg, #4ade80, #22c55e);
        }

        .challenge-info {
            flex: 1;
        }

        .challenge-title {
            font-size: 18px;
            font-weight: 700;
            margin-bottom: 4px;
            color: #ffffff;
        }

        .challenge-description {
            color: rgba(255,255,255,0.8);
            font-size: 15px;
            margin-bottom: 8px;
            font-weight: 400;
        }

        .challenge-reward {
            display: flex;
            align-items: center;
            color: #ff8a2e;
            font-weight: 600;
            font-size: 14px;
        }

        .progress-bar {
            width: 100%;
            height: 6px;
            background: rgba(255,255,255,0.1);
            border-radius: 3px;
            margin-top: 12px;
            overflow: hidden;
        }

        .progress-fill {
            height: 100%;
            background: linear-gradient(90deg, #ff8a2e, #ff6b35);
            border-radius: 3px;
            transition: width 0.3s ease;
        }

        .progress-text {
            font-size: 12px;
            color: rgba(255,255,255,0.6);
            margin-top: 8px;
        }

        .completed-badge {
            position: absolute;
            top: 15px;
            right: 15px;
            background: #4ade80;
            color: white;
            padding: 4px 8px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 600;
        }

        .daily-challenges {
            margin-bottom: 30px;
        }

        .weekly-challenges {
            margin-bottom: 30px;
        }

        .lifetime-challenges {
            margin-bottom: 30px;
        }

        .category-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            margin-bottom: 15px;
        }

        .daily-badge {
            background: rgba(255,138,46,0.2);
            color: #ff8a2e;
        }

        .weekly-badge {
            background: rgba(139,92,246,0.2);
            color: #8b5cf6;
        }

        .lifetime-badge {
            background: rgba(34,197,94,0.2);
            color: #22c55e;
        }

    </style>
