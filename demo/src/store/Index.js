import Vue from 'vue'
import Vuex from 'vuex'
import Team from './Team'
import User from './User'
import Project from './Project'
import Task from './Task'
import Message from './Message'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    Team,
    User,
    Project,
    Task,
    Message
  }
})

export default store