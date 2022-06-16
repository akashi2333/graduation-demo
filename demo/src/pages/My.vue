<template>
  <div style="margin-top: 65px"
       class="my">
    <div class="title">
      <div class="info">
        <i class="el-icon-info"
           style="margin-right:10px;font-size:80px"></i>
        <div class="intruducation">
          <p style="font-weight: bold">{{userName}}</p>
          <p style="font-size: 15px;margin-top:10px">Email：{{userEmail}}</p>
        </div>
      </div>
    </div>
    <div class="bottom">
      <el-tabs :stretch="true">
        <el-tab-pane label="我的团队">
          <div class="myTeam">
            <div class="team"
                 style="margin-right: 5px;">
              <ul class="list">
                <li v-for="myTeam in myTeams.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="myTeam.tid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="team-name">{{myTeam.name}}</div>
                  </div>
                  <div class="list-center">
                    {{myTeam.timestamp}}
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="goTeam(myTeam)">查看</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="quitTeam(myTeam)">退出</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="myTeams.length">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的项目">
          <div class="resource">
            <div class="resource-manage">
              <ul class="list">
                <li v-for="myProject in myProjects.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="myProject.pid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{myProject.name}}</div>
                  </div>
                  <div class="list-center">
                    {{myProject.timestamp}}
                  </div>
                  <div class="list-center">
                    {{myProject.state}}
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="goProject(myProject)">查看</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="quitProject(myProject)">退出</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="myProjects.length">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="待办事项">
          <div class="resource">
            <div class="resource-manage">
              <ul class="list">
                <li class="list-new">
                  <el-button type="text"
                             class="new-todo"
                             icon="el-icon-plus"
                             @click="dialogVisible = true">新建待办事项</el-button>
                  <el-dialog title="新建待办事项"
                             :visible.sync="dialogVisible"
                             width="50%">
                    <el-input v-model="tempTodo"
                              placeholder="请输入内容"></el-input>
                    <span slot="footer"
                          class="dialog-footer">
                      <el-button @click="cancel()">取 消</el-button>
                      <el-button type="primary"
                                 @click="createTodo()">确 定</el-button>
                    </span>
                  </el-dialog>
                </li>
                <li v-for="(todo,index) in todoList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="index"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{todo}}</div>
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="deleteATodo(todo)">删除</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="todoList.length">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的任务">
          <div class="myTask">
            <div class="task">
              任务总数：{{taskNum}}
            </div>
            <div class="num">
              <div class="num_1">
                <img src="../assets/start.svg"
                     class="image">
                <div class="tasknum">
                  待开始任务数：{{taskNum1}}
                </div>
              </div>
              <div class="num_1">
                <img src="../assets/ing.svg"
                     class="image">
                <div class="tasknum">
                  正在进行中任务数：{{taskNum2}}
                </div>
              </div>
              <div class="num_1">
                <img src="../assets/finished.svg"
                     class="image">
                <div class="tasknum">
                  已结束任务数：{{taskNum3}}
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getMyTeams, getMyTasks, newTodo, getMyProjects, quitMyProject, quitMyTeam, getMyTodoList, deleteTodoFromList, deleteTodo } from '../api/Index';
export default {
  name: "My",
  data () {
    return {
      pageSize: 5,
      currentPage: 1,
      dialogVisible: false,
      tempTodo: '',
      todoList: [],
      myTeams: [],
      myProjects: [],
      taskNum: 0,
      taskNum1: 0,
      taskNum2: 0,
      taskNum3: 0,
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'userName',
      'userEmail',
    ])
  },
  mounted () {
    this.getMyTeams(this.userId)
    this.getMyProjects(this.userId)
    this.getMyTodoList(this.userId)
    this.getMyTasks(this.userId)
  },
  methods: {
    createTodo () {
      newTodo({
        id: this.userId,
        todo: this.tempTodo
      }).then(res => {
        if (res.code === 200) {
          this.dialogVisible = false
          this.tempTodo = ''
          this.$message.success(res.msg)
          this.getMyTodoList(this.userId)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    cancel () {
      this.dialogVisible = false
      this.tempTodo = ''
    },
    handleCurrentChange (val) {
      this.currentPage = val
    },
    deleteATodo (todo) {
      var _this = this
      deleteTodo({
        id: _this.userId,
        todo: todo
      }).then(res => {
        if (res.code === 200) {
          _this.$message.success("删除成功")
          _this.getMyTodoList(_this.userId)
        } else {
          _this.$message.error("删除失败")
        }
      })
    },
    getMyTeams (uid) {
      getMyTeams({
        uid: uid
      }).then(res => {
        if (res.code === 200) {
          this.myTeams = res.data
        }
      })
    },
    getMyProjects (uid) {
      getMyProjects({
        uid: uid
      }).then(res => {
        if (res.code === 200) {
          this.myProjects = res.data
        }
      })
    },
    getMyTodoList (uid) {
      getMyTodoList({
        id: uid
      }).then(res => {
        if (res.code === 200) {
          this.todoList = res.data
        }
      })
    },
    goTeam (team) {
      this.$store.commit('setTempTeamId', team.tid)
      this.$store.commit('setTempTeamOwner', team.uid)
      this.$router.push({ path: `/TeamDetail/${team.tid}` });
    },
    goTask (task) {
      this.$store.commit('setTempTaskId', task.taskId)
      this.$router.push({ path: `/Task/${task.taskId}` });
    },
    quitTeam (team) {
      var _this = this
      if (team.isowner === 1) {
        _this.$message.error("管理员不能退出团队")
      } else {
        quitMyTeam({
          tid: team.tid,
          uid: _this.userId
        }).then(res => {
          if (res.code === 200) {
            _this.getMyTeams(_this.userId)
            _this.$message.success('已退出团队')
          } else {
            _this.$message.error('退出失败')
          }
        })
      }
    },
    goProject (project) {
      this.$store.commit('setTempProjectId', project.pid)
      this.$store.commit('setTempProjectOwner', project.uid)
      this.$router.push({ path: `/ProjectDetail/${project.pid}` });
    },
    quitProject (project) {
      var _this = this
      if (project.uid === _this.userId) {
        _this.$message.error("管理员不能退出项目")
      } else {
        quitMyProject({
          pid: project.pid,
          uid: _this.userId
        }).then(res => {
          if (res.code === 200) {
            _this.getMyProjects(_this.userId)
            _this.$message.success('已退出项目')
          } else {
            _this.$message.error('退出失败')
          }
        })
      }
    },
    getMyTasks (uid) {
      getMyTasks({ uid: uid }).then(res => {
        if (res.code === 200) {
          this.taskNum = res.data.length
          for (let i = 0; i < res.data.length; i++) {
            if (res.data[i].state === "待开始") {
              this.taskNum1++
            } else if (res.data[i].state === "正在进行中") {
              this.taskNum2++
            } else {
              this.taskNum3++
            }
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.num_1 {
  height: 32px;
  align-items: center;
  margin: 10px;
  display: flex;
}
.tasknum {
  font-size: 15px;
  margin-left: 10px;
}
.image {
  height: 20px;
  width: inherit;
}

.task {
  font-size: 15px;
  height: 32px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.my {
  margin: 0 30px;
}
.title {
  position: relative;
  background-image: url('../assets/7.jpg');
  background-size: 100%;
  background-repeat: no-repeat;
  height: 220px;
  width: 100%;
}
.info {
  display: flex;
  align-items: center;
  position: absolute;
  left: 10px;
  bottom: 10px;
  font-size: 30px;
  color: white;
}
.intruducation {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.team,
.resource-manage {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  height: 330px;
}
.list {
  width: 100%;
  padding: 0 0 20px 0;
}
.list-new {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}
.new-todo {
  font-size: 15px;
}
.list-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 15px;
  margin-bottom: 10px;
  margin-right: 10px;
}

.list-left {
  display: flex;
  justify-content: center;
  align-items: center;
}

.bottom {
  width: 100%;
  background-color: white;
}
.bottom-bottom {
  position: absolute;
  bottom: 10px;
  right: 0;
}
</style>