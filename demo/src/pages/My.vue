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
                <li v-for="(todo,index) in todoList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="todo.todoId"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{todo.content}}</div>
                  </div>
                  <div class="list-center">
                    {{todo.time}}
                  </div>
                  <div class="list-center">
                    {{todo.deadline}}
                  </div>
                  <div class="list-right">
                    <el-button class="finish"
                               ref="finish"
                               type="primary"
                               size="small"
                               @click="finishTodo(index)">完成</el-button>
                    <el-button style="margin-left:0"
                               type="primary"
                               size="small"
                               @click="edit(todo)">编辑</el-button>
                    <el-dialog title="编辑待办事项"
                               :visible.sync="dialogVisible"
                               width="50%"
                               :before-close="handleClose">
                      <el-form label-position="right"
                               label-width="20%">
                        <el-form-item label="待办事项内容">
                          <el-input v-model="tempTodo.content"></el-input>
                        </el-form-item>
                        <el-form-item label="待办事项时间">
                          <el-date-picker v-model="tempTodo.time"
                                          value-format="yyyy-MM-dd"
                                          type="daterange"
                                          range-separator="至"
                                          start-placeholder="开始日期"
                                          end-placeholder="结束日期">
                          </el-date-picker>
                        </el-form-item>
                      </el-form>
                      <span slot="footer"
                            class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary"
                                   @click="saveTodo()">确 定</el-button>
                      </span>
                    </el-dialog>
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
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { deleteProjectFromList, deleteTeamFromList, getMyTeams, getMyProjects, quitMyProject, quitMyTeam, getMyTodoList, deleteTodoFromList, deleteTodo, editTodo } from '../api/Index';
export default {
  name: "My",
  data () {
    return {
      pageSize: 5,
      currentPage: 1,
      dialogVisible: false,
      tempTodo: {
        content: "",
        time: ""
      },
      todoList: [],
      myTeams: [],
      myProjects: []
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'userName',
      'userEmail',
      'tempTeamList',
      'tempTodoList',
    ])
  },
  mounted () {
    this.getMyTeams(this.userId)
    this.getMyProjects(this.userId)
    // this.todoList = this.getMyTodoList(this.userId)
  },
  methods: {
    saveTodo () {
      editTodo({
        content: this.tempTodo.content,
        time: this.tempTodo.time
      }).then(res => {
        if (res.code === 200) {
          this.dialogVisible = false
          this.$message("修改成功")
          this.getMyTodoList(this.userId)
        }
      })
    },
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => { });
    },
    edit (todo) {
      this.$store.commit('setTempTodoList', todo)
      this.tempTodo.content = this.tempTodoList.content
      this.tempTodo.time = this.tempTodoList.time
      this.dialogVisible = true
    },
    handleCurrentChange (val) {
      this.currentPage = val
    },
    deleteATodo (todo) {
      var _this = this
      deleteTodoFromList({
        uid: _this.userId,
        todoId: todo.todoId
      }).then(res => {
        if (res.code === 200) {
          deleteTodo({
            uid: _this.userId,
            todoId: todo.todoId
          }).then(res => {
            if (res.code === 200) {
              _this.$message("删除成功")
              _this.getMyTodoList(_this.userId)
            } else {
              _this.$message("删除失败")
            }
          })
        }
      })
    },
    finishTodo (index) {
      console.log(document.getElementsByClassName("finish")[index].innerHTML)
      if (document.getElementsByClassName("finish")[index].innerHTML === "已完成") {
        document.getElementsByClassName("finish")[index].innerHTML = "完成"
      } else {
        document.getElementsByClassName("finish")[index].innerHTML = "已完成"
      }
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
          console.log(res.data)
        }
      })
    },
    getMyTodoList (uid) {
      getMyTodoList({
        uid: uid
      }).then(res => {
        if (res.code === 200) {
          this.todoList = res.data
        }
      })
    },
    goTeam (team) {
      this.$store.commit('setTempTeamList', team);
      this.$router.push({ path: `/TeamDetail/${team.tid}` });
    },
    quitTeam (team) {
      var _this = this
      deleteTeamFromList({
        tid: team.tid,
        uid: _this.userId
      }).then(res => {
        if (res === 200) {
          quitMyTeam({
            tid: team.tid,
            uid: _this.userId
          }).then(res => {
            if (res.code === 200) {
              _this.getMyTeams(_this.userId)
              _this.$message('已退出团队')
            } else {
              _this.$message('退出失败')
            }
          })
        }
      })
    },
    goProject (project) {
      this.$store.commit('setTempProjectList', project)
      this.$router.push({ path: `/ProjectDetail/${project.pid}` });
    },
    quitProject (project) {
      var _this = this
      deleteProjectFromList({
        pid: project.pid,
        uid: _this.userId
      }).then(res => {
        if (res === 200) {
          quitMyProject({
            pid: project.pid,
            uid: _this.userId
          }).then(res => {
            if (res.code === 200) {
              _this.getMyProjects(_this.userId)
              _this.$message('已退出项目')
            } else {
              _this.$message('退出失败')
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
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
  padding: 20px 0;
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