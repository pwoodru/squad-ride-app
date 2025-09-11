import { ref } from 'vue'
const user = ref(null)


export function useUserStore() {
  function setUser(u) {
    user.value = u
  }
  return { user, setUser }
}