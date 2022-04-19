const Resource = {
  state: {
    tempResourceList: {},
  },
  getters: {
    tempResourceList: state => {
      let tempResourceList = state.tempResourceList
      if (JSON.stringify(tempResourceList) === '{}') {
        tempResourceList = JSON.parse(window.sessionStorage.getItem('tempResourceList') || null)
      }
      return tempResourceList
    }
  },
  mutations: {
    setTempResourceList: (state, tempResourceList) => {
      state.tempResourceList = tempResourceList
      window.sessionStorage.setItem('tempResourceList', JSON.stringify(tempResourceList))
    }
  },
  actions: {

  }
}
export default Resource