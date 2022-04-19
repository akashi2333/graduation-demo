import Vue from 'vue'
import Vuex from 'vuex'
import Team from './Team'
import User from './User'
import Project from './Project'
import Resource from './Resource'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    Team,
    User,
    Project,
    Resource
  }
})

export default store