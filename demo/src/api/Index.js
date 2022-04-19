import { get, post, deletes } from './Http'

//登录注册
export const register = (data) => post(`/user/register`, data)
export const login = (params) => get(`/user/login`, params)

//首页需求，返回List，每个List里有10条数据
export const getAllNotices = () => get('/notice/getAll')
export const getGoodTeams = () => get(`/team/getGood`)
export const getGoodResources = () => get(`/resource/getGood`)

export const getMyTeams = (params) => get(`/team/getMy`, params)
export const getMyProjects = (params) => get(`/project/getMy`, params)
export const getTeams = () => get(`/team/getAll`) //返回List，里面有着团队基本信息
export const joinTeam = (data) => post(`/team/join`, data)
export const searchTeamById = (data) => post(`/team/search`, data) //返回List，里面有着团队基本信息

export const getAllMembers = (params) => get(`/team/getMembers`, params)
export const getAllTempMembers = (params) => get(`/team/getTempMembers`, params)
export const getAllProgects = (params) => get(`/team/getProjects`, params)
export const getAllResources = (params) => get(`/team/getResources`, params)

export const deleteTeamFromList = (data) => post(`/team-exit/add`, data)
export const quitMyTeam = (data) => post(`/team/quit`, data)
export const deleteProjectFromList = (data) => post(`/project-exit/add`, data) //从列表中删除项目
export const deleteProject = (data) => deletes(`/project-exit/delete`, data)//删除项目
export const quitMyProject = (data) => post(`/project/quit`, data)
export const deleteMemberFromList = (data) => post(`/member-exit/add`, data)
export const deleteMember = (data) => deletes(`/member-exit/delete`, data)
export const deleteTempMemberFromList = (data) => post(`/tempMember-exit/add`, data)
export const deleteTempMember = (data) => deletes(`/tempMember-exit`, data)
export const addTempMember = (data) => post(`/tempMember/add`, data)
export const deleteResourceFromList = (data) => post(`/resource-exit/add`, data)
export const deleteResource = (data) => deletes(`/resource-exit/delete`, data)

export const getAllComments = (params) => get(`/resource/getComments`, params)
export const sendComment = (data) => post(`/resource/postComment`, data)
export const sendLike = (data) => post(`/resource/postUp`, data)
export const deleteLike = (data) => post(`/resource/postUp`, data)