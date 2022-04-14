const Team = {
  state: {
    tempTeamList: {},
    searchWord: ''
  },
  getters: {
    tempTeamList: state => {
      let tempTeamList = state.tempTeamList
      if (JSON.stringify(tempTeamList) === '{}') {
        tempTeamList = JSON.parse(window.sessionStorage.getItem('tempTeamList') || null)
      }
      return tempTeamList
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
    setTempTeamList: (state, tempTeamList) => {
      state.tempTeamList = tempTeamList
      window.sessionStorage.setItem('tempTeamList', JSON.stringify(tempTeamList))
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