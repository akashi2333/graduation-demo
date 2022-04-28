const Message = {
  state: {
    tempAccepter: {}
  },
  getters: {
    tempAccepter: state => {
      let tempAccepter = state.tempAccepter
      if (JSON.stringify(tempAccepter) === []) {
        tempAccepter = JSON.parse(window.sessionStorage.getItem('tempAccepter') || null)
      }
      return tempAccepter
    }
  },
  mutations: {
    setTempAccepter: (state, tempAccepter) => {
      state.tempAccepter = tempAccepter
      window.sessionStorage.setItem('SetTempAccepter', JSON.stringify(tempAccepter))
    }
  },
  actions: {

  }
}
export default Message