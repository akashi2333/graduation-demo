<template>
  <div class="project-detail"
       style="margin-top: 65px">
    <div class="intruducation">
      <div class="image">
        <img :src="project.pic"
             alt=""
             class="img">
      </div>
      <div class="left">
        <p class="title">{{project.name}}</p>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="project-time">创建时间：{{project.time}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="project-isowner">管理员：{{project.isowner}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="project-text">介绍：{{project.intruducation}}</p>
        </div>
      </div>
    </div>
    <div class="bottom">
      <el-tabs :stretch="true">
        <el-tab-pane label="项目成员管理">
          <div class="member">
            <div class="project-member"
                 style="  margin-right: 5px;">
              <div class="project-member-top">项目成员</div>
              <ul class="list">
                <li v-for="member in members.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="member.uid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="member-name">{{member.name}}</div>
                  </div>
                  <div class="list-right"
                       v-show="isCreater">
                    <el-button type="primary"
                               size="small"
                               @click="deleteAMember(member)">删除</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="members.length">
              </el-pagination>
            </div>
            <div class="project-member">
              <div class="project-member-top">申请人员</div>
              <ul class="list">
                <li v-for="tempMember in tempMembers.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="tempMember.id"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="member-name">{{tempMember.name}}</div>
                  </div>
                  <div class="list-right"
                       v-show="isCreater">
                    <el-button type="primary"
                               size="small"
                               @click="agreeTempMember(tempMember)">同意</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="deleteATempMember(tempMember)">删除</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="tempMembers.length">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="项目资源管理">
          <div class="resource">
            <div class="resource-manage">
              <div class="resource-manage-top">项目资源</div>
              <ul class="list">
                <li v-for="resource in resources.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="resource.rid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{resource.name}}</div>
                  </div>
                  <div class="list-center">
                    {{resource.time}}
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="goResource(resource)">查看</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="deleteAResource(resource)"
                               v-show="isCreater">删除</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="resources.length">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="项目任务管理">
          <div class="resource">
            <div class="resource-manage">
              <div class="resource-manage-top">项目任务</div>
              <ul class="list">
                <li v-for="(task,index) in tasks.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="task.taId"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{task.content}}</div>
                  </div>
                  <div class="list-center">
                    {{task.time}}
                  </div>
                  <div class="list-center">
                    {{task.deadline}}
                  </div>
                  <div class="list-center">
                    <span v-for="member in task.members"
                          :key="member.index">{{member}} </span>
                  </div>
                  <div class="list-right">
                    <el-button class="finish"
                               ref="finish"
                               type="primary"
                               size="small"
                               @click="finishTask(index)">完成</el-button>
                    <el-button style="margin-left:0"
                               type="primary"
                               size="small"
                               @click="edit(task)">编辑</el-button>
                    <el-dialog title="编辑任务"
                               :visible.sync="dialogVisible"
                               width="50%"
                               :before-close="handleClose">
                      <el-form label-position="right"
                               label-width="15%">
                        <el-form-item label="任务内容">
                          <el-input v-model="tempTask.content"></el-input>
                        </el-form-item>
                        <el-form-item label="任务成员">
                          <el-select v-model="tempTask.members"
                                     multiple
                                     placeholder="请选择"
                                     style="width:100%">
                            <el-option v-for="member in members"
                                       :key="member.uid"
                                       :label="member.name"
                                       :value="member.uid">
                            </el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="任务时间">
                          <el-date-picker v-model="tempTask.time"
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
                                   @click="saveTask()">确 定</el-button>
                      </span>
                    </el-dialog>
                    <el-button type="primary"
                               size="small"
                               @click="deleteATask(task)">删除</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="tasks.length">
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
import { deletetask, deleteTaskFromList, editTask, getAllTasks, getPAllMembers, getPAllResources, getPAllTempMembers } from '../api/Index';

export default {
  name: 'ProjectDetail',
  data () {
    return {
      pageSize: 5,
      currentPage: 1,
      isCreater: false,
      pid: 0,
      project: {},
      members: [],
      tempMembers: [],
      resources: [],
      tasks: [],
      tempMembers: [],
      tempTask: {
        content: "",
        time: "",
        members: []
      },
      dialogVisible: false
    }
  },
  computed: {
    ...mapGetters([
      'tempProjectList',
      'userId',
      'tempTaskList'
    ])
  },
  mounted () {
    if (this.userId === this.tempProjectList.isowner) {
      this.isCreater = true
    }
    this.pid = this.tempProjectList.pid
    this.project = this.tempProjectList
    console.log(this.project)
    this.members = this.tempProjectList.members
    this.tempMembers = this.tempProjectList.tempMembers
    this.resources = this.tempProjectList.resources
    this.tasks = this.tempProjectList.tasks
    // this.getMembers(this.pid)
    // this.getTempMembers(this.pid)
    // this.getResources(this.pid)
    //this.getTasks(this.pid)
  },
  methods: {
    saveTask () {
      editTask({
        content: this.tempTask.content,
        time: this.tempTask.time,
        members: this.tempTask.members
      }).then(res => {
        if (res.code === 200) {
          this.dialogVisible = false
          this.$message("修改成功")
          this.getTasks(this.pid)
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
    edit (task) {
      this.dialogVisible = true
      this.$store.commit('setTempTaskList', task)
      this.tempTask.content = this.tempTaskList.content
      this.tempTask.time = this.tempTaskList.time
      this.tempTask.members = this.tempTaskList.members
    },
    finishTask (index) {
      console.log(document.getElementsByClassName("finish")[index].innerHTML)
      if (document.getElementsByClassName("finish")[index].innerHTML === "已完成") {
        document.getElementsByClassName("finish")[index].innerHTML = "完成"
      } else {
        document.getElementsByClassName("finish")[index].innerHTML = "已完成"
      }
    },
    deleteATask (task) {
      var _this = this
      deleteTaskFromList({
        pid: _this.pid,
        taId: task.taId
      }).then(res => {
        if (res.code === 200) {
          deletetask({
            pid: _this.pid,
            taId: task.taId
          }).then(res => {
            if (res.code === 200) {
              _this.getTasks(_this.pid)
              _this.$message("删除成功")
            } else {
              _this.$message("删除失败")
            }
          })
        }
      })
    },
    deleteAMember (member) {
      var _this = this
      deleteMemberFromList({
        type: 1,
        pid: _this.pid,
        uid: member.uid
      }).then(res => {
        if (res.code === 200) {
          deleteMember({
            type: 1,
            pid: _this.pid,
            uid: member.uid
          }).then(res => {
            if (res.code === 200) {
              _this.getMembers(_this.pid)
              _this.$message('删除成功')
            } else {
              _this.$message('删除失败')
            }
          })
        }
      })
    },
    agreeTempMember (tempMember) {
      var _this = this
      deleteTempMemberFromList({
        type: 1,
        pid: _this.pid,
        uid: tempMember.uid
      }).then(res => {
        if (res.code === 200) {
          addTempMember({
            type: 1,
            pid: _this.pid,
            uid: tempMember.uid
          }).then(res => {
            if (res === 200) {
              _this.getTempMembers(_this.pid)
              _this.$message('添加成功')
            } else {
              _this.$message('添加失败')
            }
          })
        }
      })
    },
    deleteATempMember (tempMember) {
      var _this = this
      deleteTempMemberFromList({
        type: 1,
        pid: _this.pid,
        uid: tempMember.uid
      }).then(res => {
        if (res.code === 200) {
          deleteTempMember({
            type: 1,
            pid: _this.pid,
            uid: tempMember.uid
          }).then(res => {
            if (res.code === 200) {
              _this.getTempMembers(_this.pid)
              _this.$message('拒绝成功')
            } else {
              _this.$message('拒绝失败')
            }
          })
        }
      })
    },
    deleteAresource (resource) {
      var _this = this
      deleteResourceFromList({
        pid: _this.pid,
        rid: resource.rid
      }).then(res => {
        if (res.code === 200) {
          deleteResource({
            pid: _this.pid,
            rid: resource.rid
          }).then(res => {
            if (res.code === 200) {
              _this.getResources(_this.pid)
              _this.$message('删除成功')
            } else {
              _this.$message('删除失败')
            }
          })
        }
      })
    },
    goResource (resource) {
      this.$store.commit('setTempResourceList', resource)
      this.$router.push({ path: `/ResourceDetail/${resource.rid}` })
    },
    handleCurrentChange (val) {
      this.currentPage = val
    },
    getMembers (id) {
      getPAllMembers({ pid: id }).then(res => {
        if (res.code === 200) {
          this.members = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    getTempMembers (id) {
      getPAllTempMembers({ pid: id }).then(res => {
        if (res.code === 200) {
          this.tempMembers = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    getResources (id) {
      getPAllResources({ pid: id }).then(res => {
        if (res.code === 200) {
          this.resources = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    getTasks (id) {
      getAllTasks({ pid: id }).then(res => {
        if (res.code === 200) {
          this.tasks = res.data
        } else {
          console.log(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
.project-detail {
  margin: 10px 30px 0 30px;
}

.intruducation {
  background-color: white;
  width: 100%;
  margin-bottom: 10px;
  display: flex;
}
.image {
  width: 30%;
  height: 300px;
}
.img {
  width: 100%;
  height: inherit;
}
.left {
  width: 70%;
}
.title {
  background-color: #409eff;
  color: white;
  font-size: 20px;
  padding: 10px 0;
  margin-bottom: 10px;
}
.item {
  display: flex;
  align-items: flex-start;
  padding-bottom: 10px;
  text-align: left;
  line-height: 1.5;
  font-size: 15px;
}

.member {
  display: flex;
}
.project-member {
  position: relative;
  width: 50%;
  height: 330px;
}
.resource-manage {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  height: 330px;
}
.project-member-top,
.resource-manage-top {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 40px;
  background-color: #409eff;
  font-size: 15px;
  color: white;
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