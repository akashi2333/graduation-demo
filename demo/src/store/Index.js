import Vue from 'vue'
import Vuex from 'vuex'
import Team from './Team'
import User from './User'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    Team,
    User
  }
})

export default store