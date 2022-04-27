import Vue from 'vue'
import Router from 'vue-router'
import Home from '../pages/Home'
import Team from '../pages/Team'
import Info from '../pages/Info'
import My from '../pages/My'
import TeamDetail from '../pages/TeamDetail'
import Login from '../pages/Login'
import Register from '../pages/Register'
import Search from '../pages/Search'
import ProjectDetail from '../pages/ProjectDetail'
import Task from '../pages/Task'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/Team',
      name: 'Team',
      component: Team
    },
    {
      path: '/Search',
      name: 'Search',
      component: Search
    },
    {
      path: '/Info',
      name: 'Info',
      component: Info
    },
    {
      path: '/My',
      name: 'My',
      component: My
    },
    {
      path: '/TeamDetail/:tid',
      name: '/TeamDetail',
      component: TeamDetail
    },
    {
      path: '/ProjectDetail/:pid',
      name: '/ProjectDetail',
      component: ProjectDetail
    },
    {
      path: '/Task/:taskId',
      name: '/Task',
      component: Task
    },
    {
      path: '/Login',
      name: '/Login',
      component: Login
    },
    {
      path: '/Register',
      name: '/Register',
      component: Register
    }
  ]
})
