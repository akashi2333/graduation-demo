const Project = {
  state: {
    tempProjectList: {},
  },
  getters: {
    tempProjectList: state => {
      let tempProjectList = state.tempProjectList
      if (JSON.stringify(tempProjectList) === '{}') {
        tempProjectList = JSON.parse(window.sessionStorage.getItem('tempProjectList') || null)
      }
      return tempProjectList
    }
  },
  mutations: {
    setTempProjectList: (state, tempProjectList) => {
      state.tempProjectList = tempProjectList
      window.sessionStorage.setItem('tempProjectList', JSON.stringify(tempProjectList))
    }
  },
  actions: {

  }
}
export default Project