const Project = {
  state: {
    tempProjectList: {},
    tempTaskList: {},
    tempTodoList: {},
  },
  getters: {
    tempProjectList: state => {
      let tempProjectList = state.tempProjectList
      if (JSON.stringify(tempProjectList) === '{}') {
        tempProjectList = JSON.parse(window.sessionStorage.getItem('tempProjectList') || null)
      }
      return tempProjectList
    },
    tempTaskList: state => {
      let tempTaskList = state.tempTaskList
      if (JSON.stringify(tempTaskList) === '{}') {
        tempTaskList = JSON.parse(window.sessionStorage.getItem('tempTaskList') || null)
      }
      return tempTaskList
    },
    tempTodoList: state => {
      let tempTodoList = state.tempTodoList
      if (JSON.stringify(tempTodoList) === '{}') {
        tempTodoList = JSON.parse(window.sessionStorage.getItem('tempTodoList') || null)
      }
      return tempTodoList
    }
  },
  mutations: {
    setTempProjectList: (state, tempProjectList) => {
      state.tempProjectList = tempProjectList
      window.sessionStorage.setItem('tempProjectList', JSON.stringify(tempProjectList))
    },
    setTempTaskList: (state, tempTaskList) => {
      state.tempTaskList = tempTaskList
      window.sessionStorage.setItem('tempTaskList', JSON.stringify(tempTaskList))
    },
    setTempTodoList: (state, tempTodoList) => {
      state.tempTodoList = tempTodoList
      window.sessionStorage.setItem('tempTodoList', JSON.stringify(tempTodoList))
    }
  },
  actions: {

  }
}
export default Project