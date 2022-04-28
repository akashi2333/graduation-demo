const Project = {
  state: {
    tempProjectId: 0,
    tempProjectOwner: 0,
    tempMembers: []
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
    },
    tempMembers: state => {
      let tempMembers = state.tempMembers
      if (JSON.stringify(tempMembers) === []) {
        tempMembers = JSON.parse(window.sessionStorage.getItem('tempMembers') || null)
      }
      return tempMembers
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
    },
    setTempMembers: (state, tempMembers) => {
      state.tempMembers = tempMembers
      window.sessionStorage.setItem('tempMembers', JSON.stringify(tempMembers))
    }
  },
  actions: {

  }
}
export default Project