// composables/useSquadRideAPI.js
import { ref } from 'vue'

export function useSquadRideAPI() {
  const loading = ref(false)
  const error = ref(null)
  
  // Base API URL - adjust this to match your Java backend
  const API_BASE_URL = 'http://localhost:8080/api'

  const apiCall = async (endpoint, options = {}) => {
    loading.value = true
    error.value = null
    
    try {
      const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        headers: {
          'Content-Type': 'application/json',
          ...options.headers,
        },
        ...options,
      })
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      return await response.json()
    } catch (err) {
      error.value = err.message
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchUserData = async (userId) => {
    return await apiCall(`/users/${userId}`)
  }

  const fetchStats = async (userId) => {
    return await apiCall(`/users/${userId}/stats`)
  }

  const fetchSquads = async (userId) => {
    return await apiCall(`/users/${userId}/squads`)
  }

  const fetchBets = async (userId) => {
    return await apiCall(`/users/${userId}/bets`)
  }

  const fetchGames = async () => {
    return await apiCall('/games')
  }

  const fetchLiveScores = async () => {
    return await apiCall('/scores/live')
  }

  const createBet = async (betData) => {
    return await apiCall('/bets', {
      method: 'POST',
      body: JSON.stringify(betData),
    })
  }

  const createSquad = async (squadData) => {
    return await apiCall('/squads', {
      method: 'POST',
      body: JSON.stringify(squadData),
    })
  }

  const updateSquad = async (squadId, squadData) => {
    return await apiCall(`/squads/${squadId}`, {
      method: 'PUT',
      body: JSON.stringify(squadData),
    })
  }

  const deleteSquad = async (squadId) => {
    return await apiCall(`/squads/${squadId}`, {
      method: 'DELETE',
    })
  }

  const updateUserBalance = async (userId, balanceData) => {
    return await apiCall(`/users/${userId}/balance`, {
      method: 'PUT',
      body: JSON.stringify(balanceData),
    })
  }

  return {
    loading,
    error,
    fetchUserData,
    fetchStats,
    fetchSquads,
    fetchBets,
    fetchGames,
    fetchLiveScores,
    createBet,
    createSquad,
    updateSquad,
    deleteSquad,
    updateUserBalance,
  }
}