const Team = {
  state: {
    tempTeamId: 0,
    tempTeamOwner: 0,
    searchWord: ''
  },
  getters: {
    tempTeamId: state => {
      let tempTeamId = state.tempTeamId
      if (JSON.stringify(tempTeamId) === 0) {
        tempTeamId = JSON.parse(window.sessionStorage.getItem('tempTeamId') || null)
      }
      return tempTeamId
    },
    tempTeamOwner: state => {
      let tempTeamOwner = state.tempTeamOwner
      if (JSON.stringify(tempTeamOwner) === 0) {
        tempTeamOwner = JSON.parse(window.sessionStorage.getItem('tempTeamOwner') || null)
      }
      return tempTeamOwner
    },
    searchWord: state => {
      let searchWord = state.searchWord
      if (JSON.stringify(searchWord) === '') {
        searchWord = JSON.parse(window.sessionStorage.getItem('searchWord') || null)
      }
      return searchWord
    }
  },
  mutations: {
    setTempTeamId: (state, tempTeamId) => {
      state.tempTeamId = tempTeamId
      window.sessionStorage.setItem('tempTeamId', JSON.stringify(tempTeamId))
    },
    setTempTeamOwner: (state, tempTeamOwner) => {
      state.tempTeamOwner = tempTeamOwner
      window.sessionStorage.setItem('tempTeamOwner', JSON.stringify(tempTeamOwner))
    },
    setSearchWord: (state, searchWord) => {
      state.searchWord = searchWord
      window.sessionStorage.setItem('searchWord', JSON.stringify(searchWord))
    }
  },
  actions: {

  }
}
export default Team