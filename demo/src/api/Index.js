import { get, post } from './Http'

export const register = (data) => post(`/user/register`, data)
export const login = (data) => post(`/user/login`, data)

export const getTeams = () => get(`/team/getAll`)
export const joinTeam = (data) => post(`/team/join`, data)