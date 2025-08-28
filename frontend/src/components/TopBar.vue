<template>
  <div class="top-bar">
    <div class="currency" @click="goToShop" style="cursor:pointer">
      <span>💰</span>
      <span>{{ formattedBalance }}</span>
    </div>
    <div class="profile-info" @click="goToProfile" style="cursor:pointer">
      <span>{{ userProfile.displayName }}</span>
      <div class="profile-avatar">{{ userProfile.initials }}</div>
    </div>
  </div>
</template>

<script setup>
import { computed, toRefs } from 'vue'
import { useRouter } from 'vue-router'

// Props for balance and user profile
const props = defineProps({
  userBalance: {
    type: Number,
    required: true
  },
  userProfile: {
    type: Object,
    required: true
  }
})

const { userBalance, userProfile } = toRefs(props)
const router = useRouter()

const formattedBalance = computed(() => {
  return new Intl.NumberFormat('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }).format(userBalance.value)
})

function goToShop() {
  router.push({ name: 'Shop' })
}
function goToProfile() {
  router.push({ name: 'Profile' })
}
</script>

<style scoped>
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
</style>
