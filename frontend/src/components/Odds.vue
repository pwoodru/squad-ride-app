<template>
    <div class="odds-container">
        <div class="header">
            <div class="balance">{{ balance }}</div>
            <div class="user-info">
                <span>{{ userName }}</span>
                <div class="user-avatar">{{ userInitials }}</div>
            </div>
        </div>

        <div class="main-content">
            <div class="page-title">
                <h1>Liv Scores</h1>
                <p>Real-time updates from games in progress</p>
            </div>

            <div class="sports-tabs">
                <div
                    v-for="tab in sportsTabs"
                    :key="tab.sport"
                    class="sport-tab"
                    :class="{ active: activeSport === tab.sport }"
                    @click="setActiveSport(tab.sport)"
                >
                    {{ tab.label }}
                </div>
            </div>

            <div class="games-container">
                <div
                    v-for="game in filteredGames"
                    :key="game.id"
                    class="game-card"
                    :data-sport="game.sport"
                >
                    <div class="game-header">
                        <span>{{ game.icon }} {{ game.sportLabel }}</span>
                        <div
                            class="game-status"
                            :class="game.statusClass"
                        >
                            <span v-if="game.statusClass === 'live'" class="live-indicator"></span>
                            {{ game.statusText }}
                        </div>
                    </div>
                    <div class="teams-container">
                        <div class="team">
                            <div class="team-name">{{ game.teamA }}</div>
                            <div class="team-score">{{ game.scoreA }}</div>
                        </div>
                        <div class="vs">VS</div>
                        <div class="team">
                            <div class="team-name">{{ game.teamB }}</div>
                            <div class="team-score">{{ game.scoreB }}</div>
                        </div>
                    </div>
                  <div class="game-info">
                    <span>{{ game.venue }}</span>
                        <div v-if="game.spread"><strong>Spread:</strong> {{ game.spread }}</div>
                        <div v-if="game.moneyline"><strong>Moneyline:</strong> {{ game.moneyline }}</div>
                        <div v-if="game.total"><strong>O/U:</strong> {{ game.total }}</div>
                    <span v-if="!game.spread && !game.moneyline && !game.total">{{ game.info }}</span>
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
</template>

<script setup>
import { ref, computed, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useBottomNav } from '../composables/useBottomNav'

// State for header
const balance = '$2,124.75'
const userName = 'Marcus J.'
const userInitials = 'MJ'

function decimalToAmerican(decimal) {
  if (decimal === null || decimal === undefined) return ''
  if (decimal >= 2) {
    return '+' + Math.round((decimal - 1) * 100)
  } else {
    return '-' + Math.round(100 / (decimal - 1))
  }
}
const { navItems, activeTab, setActiveTab } = useBottomNav('odds')
const oddsApiKey = '18dd125ea634cf309854c1b75934316f' // Replace with your actual API key
const games = ref([])
async function fetchOdds() {
  const sports = [
    { sport: 'all', label: 'All Football' },
    { key: 'americanfootball_nfl', label: 'NFL', icon: '🏈' },
    { key: 'americanfootball_ncaaf', label: 'NCAAF', icon: '🏈' }
  ]
  
  let allGames = []
  for (const sport of sports) {
    const url = `https://api.the-odds-api.com/v4/sports/${sport.key}/odds/?regions=us&markets=h2h,spreads,totals&apiKey=${oddsApiKey}`
    try {
      const res = await fetch(url)
      if (!res.ok) continue
      const data = await res.json()
      console.log('Odds API data for', sport.label, data) // <--- Add this line
      // Map API data to your game format
const mappedGames = data.map(game => {
  const draftkings = game.bookmakers?.find(bm => bm.key === 'draftkings')
  let spread = '', moneyline = '', total = ''
  if (draftkings) {
    // Find markets
    const spreadMarket = draftkings.markets.find(m => m.key === 'spreads')
    const moneylineMarket = draftkings.markets.find(m => m.key === 'h2h')
    const totalMarket = draftkings.markets.find(m => m.key === 'totals')

    // Format spreads
   if (spreadMarket) {
      spread = spreadMarket.outcomes
        .map(o => `${o.name} ${o.point > 0 ? '+' : ''}${o.point} (${decimalToAmerican(o.price)})`)
        .join(' | ')
    }
    // Format moneyline
if (moneylineMarket) {
  moneyline = moneylineMarket.outcomes
    .map(o => `${o.name}: ${decimalToAmerican(o.price)}`)
    .join(' | ')
}
    // Format totals
    if (totalMarket) {
      total = totalMarket.outcomes
        .map(o => `${o.name} ${o.point} (${decimalToAmerican(o.price)})`)
        .join(' | ')
    }
  }
  return {
    id: game.id,
    sport: sport.key,
    icon: sport.icon,
    sportLabel: sport.label,
    statusClass: 'scheduled',
    statusText: game.commence_time ? new Date(game.commence_time).toLocaleString() : '',
    teamA: game.home_team,
    scoreA: '',
    teamB: game.away_team,
    scoreB: '',
    venue: game.venue || '',
    spread,
    moneyline,
    total,
    info: draftkings ? '' : 'DraftKings odds not available'
  }
})
      allGames = allGames.concat(mappedGames)
    } catch (e) {
      console.error('Failed to fetch odds for', sport.label, e)
    }
  }
  games.value = allGames
}

onMounted(() => {
  fetchOdds()
})

// Sports tabs and games
const sportsTabs = [
  { sport: 'all', label: 'All Football' },
  { sport: 'americanfootball_nfl', label: '🏈 NFL' },
  { sport: 'americanfootball_ncaaf', label: '🏈 College Football' }
]

const activeSport = ref('all')

const stats = reactive({
    liveBets: 12 // for notification dot demo, match homepage
})
const router = useRouter()

const filteredGames = computed(() => {
  if (activeSport.value === 'all') return games.value
  return games.value.filter(g => g.sport === activeSport.value)
})

function setActiveSport(sport) {
        activeSport.value = sport
}

function refreshScores() {
        // Simulate score updates for live games
        games.value = games.value.map(game => {
                if (game.statusClass === 'live') {
                        return {
                                ...game,
                                //TO DO: Integrate real score updates from an API

                                //scoreA: game.scoreA + Math.round(Math.random()),
                                //scoreB: game.scoreB + Math.round(Math.random())
                        }

                }
                return game
        })
}
setInterval(refreshScores, 30000)
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
.odds-container {
    min-height: 100vh;
    background: linear-gradient(135deg, #1a1a1a 0%, #2a2a2a 25%, #3a3a3a 50%, #e67e22 75%, #e67e22 100%);
    color: white;
}
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    background: rgba(26, 26, 26, 0.9);
    backdrop-filter: blur(10px);
}
.balance {
    display: flex;
    align-items: center;
    font-weight: 600;
    color: #e67e22;
}
.balance::before {
    content: "🔒";
    margin-right: 8px;
}
.user-info {
    display: flex;
    align-items: center;
    gap: 10px;
}
.user-avatar {
    width: 40px;
    height: 40px;
    background: #e67e22;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    color: #1a1a1a;
}
.main-content {
    padding: 20px;
    padding-bottom: 100px; /* Prevents content from being hidden behind the fixed bottom bar */
}
.page-title {
    text-align: center;
    margin-bottom: 30px;
}
.page-title h1 {
    font-size: 2.5em;
    font-weight: 700;
    margin-bottom: 10px;
    background: linear-gradient(45deg, #e67e22, #d35400);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}
.page-title p {
    color: #b0bec5;
    font-size: 1.1em;
}
.sports-tabs {
    display: flex;
    gap: 10px;
    margin-bottom: 25px;
    overflow-x: auto;
    padding-bottom: 5px;
}
.sport-tab {
    padding: 12px 20px;
    background: rgba(26, 26, 26, 0.6);
    border: 1px solid rgba(230, 126, 34, 0.3);
    border-radius: 25px;
    color: white;
    cursor: pointer;
    transition: all 0.3s ease;
    white-space: nowrap;
    font-weight: 500;
}
.sport-tab.active {
    background: linear-gradient(45deg, #e67e22, #d35400);
    border-color: #e67e22;
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(230, 126, 34, 0.3);
}
.sport-tab:hover:not(.active) {
    background: rgba(230, 126, 34, 0.2);
    transform: translateY(-1px);
}
.games-container {
    display: grid;
    gap: 15px;
}
.game-card {
    background: rgba(26, 26, 26, 0.7);
    border: 1px solid rgba(230, 126, 34, 0.3);
    border-radius: 16px;
    padding: 20px;
    backdrop-filter: blur(10px);
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
}
.game-card::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 3px;
    background: linear-gradient(90deg, #e67e22, #d35400);
    opacity: 0;
    transition: opacity 0.3s ease;
}
.game-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 30px rgba(230, 126, 34, 0.2);
    border-color: #e67e22;
}
.game-card:hover::before {
    opacity: 1;
}
.game-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}
.game-status {
    padding: 6px 12px;
    border-radius: 20px;
    font-size: 0.85em;
    font-weight: 600;
}
.live {
    background: rgba(244, 67, 54, 0.2);
    color: #f44336;
    border: 1px solid #f44336;
    animation: pulse 2s infinite;
}
.halftime {
    background: rgba(255, 193, 7, 0.2);
    color: #ffc107;
    border: 1px solid #ffc107;
}
.final {
    background: rgba(76, 175, 80, 0.2);
    color: #4caf50;
    border: 1px solid #4caf50;
}
@keyframes pulse {
    0%, 100% { opacity: 1; }
    50% { opacity: 0.7; }
}
.teams-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.team {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1;
}
.team-name {
    font-weight: 600;
    font-size: 1.1em;
    margin-bottom: 8px;
}
.team-score {
    font-size: 2em;
    font-weight: 700;
    color: #e67e22;
}
.vs {
    font-size: 1.2em;
    color: #b0bec5;
    margin: 0 20px;
}
.game-info {
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 0.9em;
    color: #b0bec5;
}
.live-indicator {
    display: inline-block;
    width: 8px;
    height: 8px;
    background: #f44336;
    border-radius: 50%;
    margin-right: 8px;
    animation: pulse 1.5s infinite;
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
  color: rgba(255, 255, 255, 0.5);
  font-size: 11px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  padding: 8px 12px;
  border-radius: 12px;
  min-width: 55px;
  position: relative;
}
.refresh-btn {
    position: fixed;
    bottom: 30px;
    right: 30px;
    width: 60px;
    height: 60px;
    background: linear-gradient(45deg, #e67e22, #d35400);
    border: none;
    border-radius: 50%;
    color: white;
    font-size: 1.5em;
    cursor: pointer;
    box-shadow: 0 5px 20px rgba(230, 126, 34, 0.4);
    transition: transform 0.3s ease;
}
.refresh-btn:hover {
    transform: scale(1.1) rotate(180deg);
}
@media (max-width: 768px) {
    .teams-container {
        flex-direction: column;
        gap: 15px;
    }
    .team {
        flex-direction: row;
        justify-content: space-between;
        width: 100%;
        padding: 10px;
        background: rgba(255, 255, 255, 0.05);
        border-radius: 10px;
    }
    .vs {
        margin: 10px 0;
    }
}
</style>