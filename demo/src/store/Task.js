const Task = {
  state: {
    tempTaskId: 0,
  },
  getters: {
    tempTaskId: state => {
      let tempTaskId = state.tempTaskId
      if (JSON.stringify(tempTaskId) === 0) {
        tempTaskId = JSON.parse(window.sessionStorage.getItem('tempTaskId') || null)
      }
      return tempTaskId
    }
  },
  mutations: {
    setTempTaskId: (state, tempTaskId) => {
      state.tempTaskId = tempTaskId
      window.sessionStorage.setItem('tempTaskId', JSON.stringify(tempTaskId))
    }
  },
  actions: {

  }
}
export default Task