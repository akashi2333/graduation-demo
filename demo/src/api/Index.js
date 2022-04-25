import { get, post, deletes, put } from './Http'

//登录注册
export const register = (data) => post(`/user/register`, data)
export const login = (params) => get(`/user/login`, params)

export const getMyTeams = (params) => get(`/user/getTeams`, params)
export const getMyProjects = (params) => get(`/user/getProjects`, params)

export const editTeam = (data) => put(`/team/update`, data)
export const getTeams = () => get(`/team/getAllTeam`)
export const getAllMembers = (params) => get(`/team/getMembers`, params)
export const newTeam = (data) => post(`/team/add`, data)
export const addTempMember = (data) => post(`/team/addMember`, data)
export const getAllProgects = (params) => get(`/team/getProjectByTid`, params)
export const quitMyTeam = (data) => post(`/team/delete`, data)
export const searchTeamById = (params) => get(`/team/getByTid`, params)

export const getAllResources = (params) => get(`/team-resource/getFileList`, params)
export const newResource = (data) => post(`/team-resource/add`, data)


export const getAllTempMembers = (params) => get(`/team-join/getByTeamId`, params)
export const deleteTempMemberFromList = (data) => deletes(`/team-join/delete`, data)
export const joinTeam = (data) => post(`/team-join/add`, data)

export const newProject = (data) => post(`/project/add`, data)
export const getProjectById = (params) => get(`/project/getSingleProjectDetail`, params)
export const editProject = (data) => put(`/project/update`, data)

export const quitMyProject = (data) => post(`/user-project/delete`, data)

export const getMyTodoList = (params) => get(`/todo/getById`, params)
export const newTodo = (data) => post(`/todo/add`, data)






//首页需求，返回List，每个List里有10条数据
export const getAllNotices = () => get('/notice/getAll')
export const getGoodTeams = () => get(`/team/getGood`)
export const getGoodResources = () => get(`/resource/getGood`)





export const getPAllMembers = (params) => get(`/project/getMembers`, params)
export const getPAllTempMembers = (params) => get(`/project/getTempMembers`, params)
export const getPAllProgects = (params) => get(`/project/getProjects`, params)
export const getPAllResources = (params) => get(`/project/getResources`, params)
export const getAllTasks = (params) => get(`/project/getTasks`, params)

export const deleteTeamFromList = (data) => post(`/team-exit/add`, data)

export const deleteProjectFromList = (data) => post(`/project-exit/add`, data) //从列表中删除项目
export const deleteProject = (data) => deletes(`/project-exit/delete`, data)//删除项目

export const deleteMemberFromList = (data) => post(`/member-exit/add`, data)
export const deleteMember = (data) => deletes(`/member-exit/delete`, data)
export const deleteTempMember = (data) => deletes(`/tempMember-exit`, data)
export const deleteResourceFromList = (data) => post(`/resource-exit/add`, data)
export const deleteResource = (data) => deletes(`/resource-exit/delete`, data)
export const deleteTaskFromList = (data) => post(`/task-exit/add`, data)
export const deletetask = (data) => deletes(`/task-exit/delete`, data)
export const deleteTodoFromList = (data) => post(`/todolist-exit/add`, data)
export const deleteTodo = (data) => deletes(`/todolist-exit/delete`, data)

export const getAllComments = (params) => get(`/resource/getComments`, params)
export const sendComment = (data) => post(`/resource/postComment`, data)
export const sendLike = (data) => post(`/resource/postUp`, data)
export const deleteLike = (data) => post(`/resource/postUp`, data)

export const editTask = (data) => post(`/task/edit`, data)
export const editTodo = (data) => post(`/todo/edit`, data)

