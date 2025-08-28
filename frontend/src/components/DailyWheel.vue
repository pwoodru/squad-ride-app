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

                        <!-- Header Section -->
                        <div class="header-section">
                            <div class="header-content">
                                <div class="logo-section">
                                    <div class="logo-icon">
                                        <div class="logo-text">🎡</div>
                                    </div>
                                </div>
                                <div class="app-title">Daily Wheel</div>
                                <div class="app-subtitle">Spin once every day for amazing rewards!</div>
                            </div>
                            <div class="wave-separator"></div>
                        </div>

                        <!-- Main Content -->
                        <div class="main-content daily-wheel-content">
                            <div class="wheel-container">
                                <div class="daily-status">
                                    <div class="status-icon">{{ hasSpunToday ? '⏰' : '✅' }}</div>
                                    <div class="status-text">{{ hasSpunToday ? 'Come Back Tomorrow!' : 'Ready to Spin!' }}</div>
                                    <div class="status-subtext">{{ hasSpunToday ? 'Next spin available in:' : 'Your daily spin is available' }}</div>
                                    <div v-if="hasSpunToday" class="countdown">{{ countdown }}</div>
                                </div>
                                <div class="wheel-wrapper">
                                    <div class="wheel-pointer"></div>
                                    <div class="wheel" :class="{ spinning: isSpinning }" ref="wheelRef">
                                        <div v-for="(segment, idx) in rewards" :key="idx"
                                            class="wheel-segment"
                                            :style="getSegmentStyle(idx)">
                                            <div class="segment-content">
                                                <div class="segment-icon">{{ segment.icon }}</div>
                                                <div class="segment-text">{{ segment.value }}</div>
                                            </div>
                                        </div>
                                        <div class="wheel-center">🎡</div>
                                    </div>
                                </div>
                                <button class="spin-button" :disabled="isSpinning || hasSpunToday" @click="spinWheel">
                                    {{ isSpinning ? '🌟 SPINNING...' : (hasSpunToday ? '⏰ COME BACK TOMORROW' : '🎡 SPIN THE WHEEL') }}
                                </button>
                                <div class="rewards-list">
                                    <h3 class="rewards-title">🏆 Possible Rewards</h3>
                                    <div v-for="(reward, idx) in rewards" :key="idx" class="reward-item">
                                        <div class="reward-icon" :style="{ background: reward.bg }">{{ reward.icon }}</div>
                                        <div class="reward-info">
                                            <div class="reward-name">{{ reward.name }} - {{ reward.value }}</div>
                                            <div class="reward-desc">{{ reward.desc }}</div>
                                        </div>
                                        <div class="reward-chance">{{ reward.chance }}</div>
                                    </div>
                                </div>
                            </div>
                            <!-- Win Modal -->
                            <div class="modal" v-if="showModal" @click.self="closeModal">
                                <div class="modal-content">
                                    <div class="modal-icon">{{ modalReward.icon }}</div>
                                    <h2 class="modal-title">You Won {{ modalReward.name }}!</h2>
                                    <p class="modal-description">{{ modalReward.value }} - {{ modalReward.desc }}</p>
                                    <button class="modal-button" @click="closeModal">Awesome!</button>
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
// Helper to rotate each segment around the wheel
function getSegmentStyle(idx) {
    const angle = (360 / rewards.length) * idx;
    return {
        position: 'absolute',
        left: '50%',
        top: '50%',
        width: '110px',
        height: '36px',
        transform: `rotate(${angle}deg) translateY(-110px)`,
        'transform-origin': '0 0',
        display: 'flex',
        'align-items': 'center',
        'justify-content': 'center',
        'pointer-events': 'none',
    }
}
    import { ref } from 'vue'
    import { useBottomNav } from '../composables/useBottomNav'

    const { navItems, activeTab, setActiveTab } = useBottomNav('dailywheel')

    const user = ref({
        displayName: 'Marcus J.',
        initials: 'MJ'
    })
    const balance = ref(2124.75)
    function formatCurrency(amount) {
        return amount.toLocaleString()
    }
    const rewards = [
        { name: 'Cash Bonus', value: '$25', icon: '💰', desc: 'Instant cash added to your balance', chance: '15%', bg: '#4CAF50' },
        { name: 'Free Bet Token', value: '$20', icon: '🎯', desc: 'Risk-free bet unlocked!', chance: '20%', bg: '#2196F3' },
        { name: 'Quick Cash', value: '$10', icon: '⚡', desc: 'Small instant cash reward', chance: '25%', bg: '#FF9800' },
        { name: 'Mystery Bonus', value: 'Special', icon: '🎁', desc: 'Random special reward', chance: '10%', bg: '#9C27B0' },
        { name: 'Jackpot', value: '$50', icon: '💎', desc: 'Big cash prize!', chance: '5%', bg: '#F44336' },
        { name: '2x Multiplier', value: 'Active', icon: '🔥', desc: 'Double your next win', chance: '10%', bg: '#00BCD4' },
        { name: 'Small Cash', value: '$5', icon: '💸', desc: 'Better luck tomorrow!', chance: '10%', bg: '#FFC107' },
        { name: 'Try Again', value: '4hrs', icon: '🎲', desc: 'Spin again in 4 hours', chance: '5%', bg: '#795548' }
    ]
    const isSpinning = ref(false)
    const hasSpunToday = ref(false)
    const countdown = ref('23:45:12')
    const showModal = ref(false)
    const modalReward = ref({})
    const wheelRef = ref(null)
    function spinWheel() {
        if (isSpinning.value || hasSpunToday.value) return
        isSpinning.value = true
        const minRotation = 1080
        const maxRotation = 1800
        const segmentAngle = 45
        const randomSegment = Math.floor(Math.random() * 8)
        const segmentRotation = (7 - randomSegment) * segmentAngle + 22.5
        const totalRotation = minRotation + Math.random() * (maxRotation - minRotation) + segmentRotation
        wheelRef.value.style.transform = `rotate(${totalRotation}deg)`
        setTimeout(() => {
            modalReward.value = rewards[randomSegment]
            showModal.value = true
            hasSpunToday.value = true
            isSpinning.value = false
            wheelRef.value.style.transform = `rotate(${segmentRotation}deg)`
            startCountdown()
        }, 4000)
    }
    function closeModal() {
        showModal.value = false
    }
    function startCountdown() {
        let hours = 23, minutes = 45, seconds = 12
        countdown.value = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
        const timer = setInterval(() => {
            seconds--
            if (seconds < 0) {
                seconds = 59
                minutes--
                if (minutes < 0) {
                    minutes = 59
                    hours--
                    if (hours < 0) {
                        clearInterval(timer)
                        hasSpunToday.value = false
                        countdown.value = '23:59:59'
                        return
                    }
                }
            }
            countdown.value = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
        }, 1000)
    }
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
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background: linear-gradient(135deg, #2c3e50 0%, #e67e22 100%);
            min-height: 100vh;
            color: white;
            overflow-x: hidden;
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 20px;
            background: rgba(0,0,0,0.2);
        }
        
        .balance {
            font-weight: bold;
            font-size: 16px;
        }
        
        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .user-avatar {
            width: 32px;
            height: 32px;
            background: #e67e22;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
        }
        
        .page-title {
            text-align: center;
            padding: 20px;
            margin-bottom: 20px;
        }
        
        .page-title h1 {
            font-size: 28px;
            margin-bottom: 5px;
        }
        
        .page-title p {
            opacity: 0.8;
            font-size: 16px;
        }
        
        .wheel-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            position: relative;
        }
        
        .wheel-wrapper {
            position: relative;
            margin-bottom: 30px;
        }
        
        .wheel {
            width: 260px;
            height: 260px;
            border-radius: 50%;
            position: relative;
            background: #fff8f1;
            border: 8px solid #f97316;
            margin-bottom: 18px;
            transition: transform 3s cubic-bezier(0.33,1,0.68,1);
            box-shadow: 0 8px 32px rgba(249, 115, 22, 0.12);
            overflow: visible;
        }

        .wheel-pointer {
            position: absolute;
            left: 50%;
            top: -32px;
            transform: translateX(-50%);
            width: 0;
            height: 0;
            border-left: 18px solid transparent;
            border-right: 18px solid transparent;
            border-bottom: 32px solid #f97316;
            z-index: 10;
            filter: drop-shadow(0 2px 4px rgba(0,0,0,0.12));
        }

        .wheel-segment {
            background: linear-gradient(90deg, #f97316 60%, #fbbf24 100%);
            color: #fff;
            font-weight: 700;
            font-size: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(249, 115, 22, 0.08);
            padding: 0 10px;
            height: 36px;
            min-width: 90px;
            text-align: center;
        }

        .segment-content {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .wheel-center {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 60px;
            height: 60px;
            background: #f97316;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            font-weight: bold;
            border: 4px solid white;
            box-shadow: 0 4px 8px rgba(0,0,0,0.3);
            z-index: 5;
            color: #fff;
        }

        .main-content.daily-wheel-content {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            padding: 16px 0 80px;
        }

        .wheel-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 24px;
            padding: 0;
        }
        .rewards-list {
            margin-top: 30px;
            padding: 20px;
        }
        
        .rewards-title {
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        
        .reward-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 12px;
            background: rgba(255,255,255,0.1);
            border-radius: 8px;
            margin-bottom: 10px;
            border: 1px solid rgba(255,255,255,0.2);
        }
        
        .reward-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
        }
        
        .reward-info {
            flex: 1;
        }
        
        .reward-name {
            font-weight: bold;
            margin-bottom: 2px;
        }
        
        .reward-desc {
            font-size: 12px;
            opacity: 0.8;
        }
        
        .reward-chance {
            font-size: 12px;
            opacity: 0.7;
        }
        
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0,0,0,0.8);
            z-index: 1000;
            justify-content: center;
            align-items: center;
        }
        
        .modal-content {
            background: linear-gradient(135deg, #2c3e50 0%, #e67e22 100%);
            padding: 40px;
            border-radius: 20px;
            text-align: center;
            max-width: 320px;
            width: 90%;
            animation: modalSlideIn 0.5s ease;
        }
        
        @keyframes modalSlideIn {
            from {
                transform: translateY(-50px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }
        
        .modal-icon {
            font-size: 60px;
            margin-bottom: 20px;
        }
        
        .modal-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .modal-description {
            font-size: 16px;
            margin-bottom: 30px;
            opacity: 0.9;
        }
        
        .modal-button {
            background: #e67e22;
            border: none;
            padding: 12px 30px;
            border-radius: 25px;
            color: white;
            font-weight: bold;
            cursor: pointer;
            font-size: 16px;
        }
        
        .bottom-nav {
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            background: rgba(0,0,0,0.9);
            display: flex;
            justify-content: space-around;
            padding: 15px 0;
            border-top: 1px solid rgba(255,255,255,0.1);
        }
        
        .nav-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-decoration: none;
            color: rgba(255,255,255,0.6);
            transition: color 0.3s ease;
        }
        
        .nav-icon {
            font-size: 20px;
            margin-bottom: 4px;
        }
        
        .nav-label {
            font-size: 11px;
        }
        
        .confetti {
            position: fixed;
            width: 10px;
            height: 10px;
            background: #e67e22;
            animation: confetti-fall 3s linear infinite;
        }
        
        @keyframes confetti-fall {
            to {
                transform: translateY(100vh) rotate(360deg);
            }
        }
    </style>