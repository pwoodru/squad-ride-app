<template>
            <div class="squadride-app-bg">
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

                    <!-- Header -->
                    <div class="page-title">
                        <h1>🚀 Rides</h1>
                        <p>Join the squad. Build parlays together.</p>
                    </div>

                    <div class="rides-container">
                        <div class="active-rides">
                            <div class="section-header">
                                <h2 class="section-title">Active Rides</h2>
                                <button class="create-ride-btn" @click="showModal = true">+ Create Ride</button>
                            </div>

                            <div v-for="ride in rides" :key="ride.id" class="ride-card">
                                <div class="ride-header">
                                    <div>
                                        <div class="ride-title">{{ ride.title }}</div>
                                        <div class="ride-creator">Created by {{ ride.creator }}</div>
                                    </div>
                                    <div class="ride-status" :class="ride.statusClass">{{ ride.status }}</div>
                                </div>

                                <div v-if="ride.picks && ride.picks.length" class="ride-picks">
                                    <div v-for="pick in ride.picks" :key="pick.team" class="pick">
                                        <div class="pick-info">
                                            <div class="pick-team">{{ pick.team }}</div>
                                            <div class="pick-details">{{ pick.details }}</div>
                                        </div>
                                        <div class="pick-odds">{{ pick.odds }}</div>
                                        <div class="pick-contributor">by {{ pick.contributor }}</div>
                                    </div>
                                </div>

                                <div v-else class="no-rides" style="padding: 20px 0;">
                                    <div class="empty-state">🎯</div>
                                    <p>Waiting for picks...</p>
                                    <p style="font-size: 14px; margin-top: 5px; opacity: 0.7;">Invite friends to add their best picks!</p>
                                </div>

                                <div class="ride-stats">
                                    <div class="stat-group">
                                        <div class="stat">
                                            <div class="stat-value">{{ ride.totalOdds }}</div>
                                            <div class="stat-label">TOTAL ODDS</div>
                                        </div>
                                        <div class="stat">
                                            <div class="stat-value">${{ ride.potSize }}</div>
                                            <div class="stat-label">POT SIZE</div>
                                        </div>
                                    </div>
                                    <div class="participants">
                                        <div v-for="p in ride.participants" :key="p" class="participant-avatar">{{ p }}</div>
                                        <div class="participant-count">{{ ride.participants.length }}/{{ ride.maxRiders }} riders</div>
                                    </div>
                                </div>

                                <div v-if="ride.status === 'Building'" style="margin-top: 15px;">
                                    <button class="join-btn">🎯 Add Your Pick</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal -->
                    <div class="modal" v-if="showModal" @click.self="showModal = false">
                        <div class="modal-content">
                            <h2>🚀 Create New Ride</h2>
                            <form @submit.prevent="createRide">
                                <div class="form-group">
                                    <label class="form-label">Ride Name</label>
                                    <input v-model="newRide.title" type="text" class="form-input" placeholder="e.g., Weekend Warriors" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Max Riders</label>
                                    <input v-model.number="newRide.maxRiders" type="number" class="form-input" min="2" max="10" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Entry Amount (per rider)</label>
                                    <input v-model.number="newRide.potSize" type="number" class="form-input" min="5" step="5" required>
                                </div>
                                <div class="modal-buttons">
                                    <button type="button" class="btn-secondary" @click="showModal = false">Cancel</button>
                                    <button type="submit" class="btn-primary">Create Ride</button>
                                </div>
                            </form>
                        </div>
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
        import { ref, reactive } from 'vue'
        import { useBottomNav } from '../composables/useBottomNav'

        const { navItems, activeTab, setActiveTab } = useBottomNav('home')

        const userBalance = ref(2124.75)
        const userProfile = reactive({
            displayName: 'Marcus J.',
            initials: 'MJ',
            userId: 1
        })

        function formatCurrency(amount) {
            return new Intl.NumberFormat('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            }).format(amount)
        }

        const showModal = ref(false)
        const newRide = reactive({
            title: '',
            maxRiders: 6,
            potSize: 25
        })

        const rides = ref([
            {
                id: 1,
                title: 'Saturday Night Bangers 🔥',
                creator: 'TylerB',
                status: 'Building',
                statusClass: 'building',
                picks: [
                    { team: 'Lakers -6.5', details: 'vs Warriors • 8:00 PM', odds: '+110', contributor: 'TylerB' },
                    { team: 'Chiefs ML', details: 'vs Bills • 4:25 PM', odds: '-140', contributor: 'SarahM' }
                ],
                totalOdds: '+850',
                potSize: 125,
                participants: ['TB', 'SM', '+'],
                maxRiders: 6
            },
            {
                id: 2,
                title: 'Sunday Funday Special',
                creator: 'MikeR',
                status: 'Placed',
                statusClass: 'placed',
                picks: [
                    { team: 'Cowboys +3', details: 'vs Giants • 1:00 PM', odds: '-110', contributor: 'MikeR' },
                    { team: 'Over 47.5', details: 'Eagles vs Commanders', odds: '+105', contributor: 'You' },
                    { team: 'Mahomes 2+ TDs', details: 'Player Props', odds: '-150', contributor: 'JenK' }
                ],
                totalOdds: '+420',
                potSize: 200,
                participants: ['MR', 'MJ', 'JK'],
                maxRiders: 3
            },
            {
                id: 3,
                title: 'Monday Night Madness',
                creator: 'You',
                status: 'Open',
                statusClass: '',
                picks: [],
                totalOdds: '--',
                potSize: 0,
                participants: ['MJ'],
                maxRiders: 8
            }
        ])

        function createRide() {
            rides.value.push({
                id: rides.value.length + 1,
                title: newRide.title,
                creator: userProfile.displayName,
                status: 'Building',
                statusClass: 'building',
                picks: [],
                totalOdds: '--',
                potSize: newRide.potSize,
                participants: [userProfile.initials],
                maxRiders: newRide.maxRiders
            })
            newRide.title = ''
            newRide.maxRiders = 6
            newRide.potSize = 25
            showModal.value = false
        }
        </script>

        <style scoped>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap');
        .squadride-app-bg {
            min-height: 100vh;
            background: linear-gradient(135deg, #2c3e50 0%, #e67e22 100%);
            color: #fff;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }
        .mobile-container {
            width: 100%;
            max-width: 430px;
            margin: 0 auto;
            background: transparent;
            min-height: 100vh;
            position: relative;
        }
        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 18px 20px 10px 20px;
        }
        .currency {
            font-weight: bold;
            font-size: 1.1rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        .profile-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .profile-avatar {
            width: 32px;
            height: 32px;
            background: #e67e22;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 1rem;
        }
        .page-title {
            text-align: center;
            padding: 20px 0 10px 0;
        }
        .page-title h1 {
            font-size: 2rem;
            margin-bottom: 5px;
        }
        .page-title p {
            opacity: 0.8;
            font-size: 1rem;
        }
        .rides-container {
            padding: 0 20px 100px;
        }
        .active-rides {
            margin-bottom: 30px;
        }
        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        .section-title {
            font-size: 1.25rem;
            font-weight: bold;
        }
        .create-ride-btn {
            background: #e67e22;
            border: none;
            padding: 8px 16px;
            border-radius: 20px;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .create-ride-btn:hover {
            background: #d35400;
            transform: translateY(-1px);
        }
        .ride-card {
            background: rgba(255,255,255,0.1);
            border-radius: 16px;
            padding: 20px;
            margin-bottom: 15px;
            border: 1px solid rgba(255,255,255,0.2);
            transition: all 0.3s ease;
        }
        .ride-card:hover {
            background: rgba(255,255,255,0.15);
            transform: translateY(-2px);
        }
        .ride-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 15px;
        }
        .ride-title {
            font-size: 1.1rem;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .ride-creator {
            font-size: 0.9rem;
            opacity: 0.8;
        }
        .ride-status {
            background: #4caf50;
            color: white;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 0.8rem;
            font-weight: bold;
        }
        .ride-status.building {
            background: #ff9800;
        }
        .ride-status.placed {
            background: #2196f3;
        }
        .ride-picks {
            margin-bottom: 15px;
        }
        .pick {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            background: rgba(255,255,255,0.1);
            border-radius: 8px;
            margin-bottom: 8px;
        }
        .pick-info {
            flex: 1;
        }
        .pick-team {
            font-weight: bold;
            margin-bottom: 2px;
        }
        .pick-details {
            font-size: 0.85rem;
            opacity: 0.8;
        }
        .pick-odds {
            font-weight: bold;
            color: #4caf50;
        }
        .pick-contributor {
            font-size: 0.75rem;
            opacity: 0.7;
            margin-left: 10px;
        }
        .ride-stats {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-top: 15px;
            border-top: 1px solid rgba(255,255,255,0.2);
        }
        .stat-group {
            display: flex;
            gap: 20px;
        }
        .stat {
            text-align: center;
        }
        .stat-value {
            font-size: 1rem;
            font-weight: bold;
            color: #4caf50;
        }
        .stat-label {
            font-size: 0.7rem;
            opacity: 0.7;
        }
        .participants {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .participant-avatar {
            width: 24px;
            height: 24px;
            background: #e67e22;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.8rem;
            font-weight: bold;
            border: 2px solid white;
        }
        .participant-count {
            font-size: 0.8rem;
            margin-left: 5px;
            opacity: 0.8;
        }
        .join-btn {
            background: #4caf50;
            border: none;
            padding: 10px 20px;
            border-radius: 20px;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .join-btn:hover {
            background: #45a049;
            transform: translateY(-1px);
        }
        .join-btn:disabled {
            background: #666;
            cursor: not-allowed;
        }
        .no-rides {
            text-align: center;
            padding: 40px 20px;
            opacity: 0.7;
        }
        .empty-state {
            font-size: 2.5rem;
            margin-bottom: 15px;
        }
        .modal {
            display: flex;
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
            padding: 30px;
            border-radius: 16px;
            width: 90%;
            max-width: 400px;
        }
        .modal h2 {
            margin-bottom: 20px;
            text-align: center;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        .form-input {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 8px;
            background: rgba(255,255,255,0.1);
            color: white;
            font-size: 1rem;
        }
        .form-input::placeholder {
            color: rgba(255,255,255,0.6);
        }
        .modal-buttons {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        .btn-secondary {
            background: rgba(255,255,255,0.2);
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            color: white;
            cursor: pointer;
        }
        .btn-primary {
            background: #e67e22;
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            color: white;
            font-weight: bold;
            cursor: pointer;
        }
        </style>