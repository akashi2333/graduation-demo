const Project = {
  state: {
    tempProjectId: 0,
    tempProjectOwner: 0,
  },
  getters: {
    tempProjectId: state => {
      let tempProjectId = state.tempProjectId
      if (JSON.stringify(tempProjectId) === 0) {
        tempProjectId = JSON.parse(window.sessionStorage.getItem('tempProjectId') || null)
      }
      return tempProjectId
    },
    tempProjectOwner: state => {
      let tempProjectOwner = state.tempProjectOwner
      if (JSON.stringify(tempProjectOwner) === 0) {
        tempProjectOwner = JSON.parse(window.sessionStorage.getItem('tempProjectOwner') || null)
      }
      return tempProjectOwner
    }
  },
  mutations: {
    setTempProjectId: (state, tempProjectId) => {
      state.tempProjectId = tempProjectId
      window.sessionStorage.setItem('tempProjectId', JSON.stringify(tempProjectId))
    },
    setTempProjectOwner: (state, tempProjectOwner) => {
      state.tempProjectOwner = tempProjectOwner
      window.sessionStorage.setItem('tempProjectOwner', JSON.stringify(tempProjectOwner))
    }
  },
  actions: {

  }
}
export default Project