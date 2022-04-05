const User = {
  state: {
    userId: '',
    userName: '',
    loginState: false
  },
  getters: {
    userId: state => {
      let userId = state.userId
      if (!userId) {
        userId = JSON.parse(window.localStorage.getItem('userId') || null)
      }
      return userId
    },
    userName: state => {
      let userName = state.userName
      if (!userName) {
        userName = JSON.parse(window.localStorage.getItem('userName') || null)
      }
      return userName
    },
    loginState: state => {
      let loginState = state.loginState
      if (!loginState) {
        loginState = JSON.parse(window.localStorage.getItem('loginState') || null)
      }
      return loginState
    }
  },
  mutations: {
    setUserId: (state, userId) => {
      state.userId = userId
      window.localStorage.setItem('userId', JSON.stringify(userId))
    },
    setUserName: (state, userName) => {
      state.userName = userName
      window.localStorage.setItem('userName', JSON.stringify(userName))
    },
    setLoginState: (state, loginState) => {
      state.loginState = loginState
      window.localStorage.setItem('loginState', JSON.stringify(loginState))
    }
  },
  actions: {}
}

export default User