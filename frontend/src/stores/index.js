import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useSquadRideStore = defineStore('squadRide', () => {
  // State
  const user = ref(null)
  const balance = ref(0)
  const squads = ref([])
  const bets = ref([])
  const games = ref([])
  const loading = ref(false)
  const error = ref(null)

  // Getters
  const isLoggedIn = computed(() => user.value !== null)
  const activeSquads = computed(() => squads.value.filter(squad => squad.status === 'ACTIVE'))
  const pendingBets = computed(() => bets.value.filter(bet => bet.status === 'PENDING'))

  // Actions
  function setUser(userData) {
    user.value = userData
    balance.value = userData.balance
  }

  function updateBalance(newBalance) {
    balance.value = newBalance
    if (user.value) {
      user.value.balance = newBalance
    }
  }

  function setSquads(squadData) {
    squads.value = squadData
  }

  function setBets(betData) {
    bets.value = betData
  }

  function setGames(gameData) {
    games.value = gameData
  }

  function setLoading(state) {
    loading.value = state
  }

  function setError(errorMessage) {
    error.value = errorMessage
  }

  function clearError() {
    error.value = null
  }

  return {
    // State
    user,
    balance,
    squads,
    bets,
    games,
    loading,
    error,
    // Getters
    isLoggedIn,
    activeSquads,
    pendingBets,
    // Actions
    setUser,
    updateBalance,
    setSquads,
    setBets,
    setGames,
    setLoading,
    setError,
    clearError
  }
})