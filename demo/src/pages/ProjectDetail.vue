<template>
  <div class="project-detail"
       style="margin-top: 65px">
    <div class="intruducation">
      <div class="image">
        <img :src="'data:image/png;base64,'+project.img"
             alt=""
             class="img">
      </div>
      <div class="left">
        <div class="title">
          <div>{{project.name}}</div>
          <el-button v-show="isCreater"
                     type="text"
                     class="edit-project"
                     icon="el-icon-edit"
                     @click="PdialogFormVisible = true">编辑项目</el-button>
          <el-dialog title="新建项目"
                     :visible.sync="PdialogFormVisible"
                     width="50%">
            <el-form label-position="right"
                     label-width="20%"
                     :model="project"
                     :rules="Prules"
                     ref="project">
              <el-form-item label="项目名称"
                            prop="name">
                <el-input v-model="project.name"
                          placeholder="请输入项目名称"></el-input>
              </el-form-item>
              <el-form-item label="项目图片"
                            prop="file">
                <el-upload action="uploadAction"
                           list-type="picture-card"
                           :on-remove="handleRemove"
                           :limit="1"
                           :show-file-list="true"
                           name="img"
                           ref="uploadimg"
                           :data="project"
                           accept="image/png,image/gif,image/jpg,image/jpeg"
                           :on-exceed="handleExceed"
                           :auto-upload="false"
                           :on-error="uploadError"
                           :on-change="PchangeFile">
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="项目状态"
                            prop="status">
                <el-select v-model="project.state"
                           placeholder="请选择">
                  <el-option v-for="item in options"
                             :key="item.value"
                             :label="item.label"
                             :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
              <el-button @click="Pcancel('project')">取 消</el-button>
              <el-button type="primary"
                         @click="editAProject('project')">确 定</el-button>
            </span>
          </el-dialog>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="project-time">创建时间：{{project.timestamp}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="project-isowner">管理员：{{manager}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="project-text">项目状态：{{project.state}}</p>
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
                    :key="member.id"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="member-name">{{member.username}}</div>
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
                    <div class="member-name">{{tempMember.username}}</div>
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
              <div class="resource-manage-top">
                <div>项目资源</div>
                <el-button type="text"
                           class="new-resource"
                           icon="el-icon-plus"
                           @click="RdialogFormVisible = true">上传资源</el-button>
                <el-dialog title="上传资源"
                           :visible.sync="RdialogFormVisible"
                           width="50%">
                  <el-upload ref="Rupload"
                             :limit="1"
                             :on-exceed="RhandleExceed"
                             :before-upload="beforeUpload"
                             :auto-upload="false"
                             :on-change="RchangeFile"
                             :on-error="handleError"
                             action="wgg">
                    <div style="font-size:15px;color:#409eff;margin-bottom:5px"><i class="el-icon-upload" /> 添加文件</div>
                    <div slot="tip"
                         class="el-upload__tip">可上传任意格式文件，且不超过100M</div>
                  </el-upload>
                  <span slot="footer"
                        class="dialog-footer">
                    <el-button @click="Rcancel()">取 消</el-button>
                    <el-button type="primary"
                               :loading="loading"
                               @click="createPResource()">确 定</el-button>
                  </span>
                </el-dialog>
              </div>
              <ul class="list">
                <li v-for="resource in resources.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="resource.rid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{resource.resourceName}}</div>
                  </div>
                  <div class="list-center">
                    {{resource.timestamp}}
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="download(resource)">下载</el-button>
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
              <div class="resource-manage-top">
                <div>项目任务</div>
                <el-button v-show="isCreater"
                           type="text"
                           class="new-resource"
                           icon="el-icon-plus"
                           @click="dialogVisible = true">新建任务</el-button>
                <el-dialog title="新建任务"
                           :visible.sync="dialogVisible"
                           width="50%">
                  <el-form label-position="right"
                           label-width="20%"
                           :model="tempTask"
                           :rules="rules"
                           ref="tempTask">
                    <el-form-item label="任务内容"
                                  prop="detail">
                      <el-input v-model="tempTask.detail"
                                placeholder="请输入任务内容"></el-input>
                    </el-form-item>
                    <el-form-item label="参与人员"
                                  prop="taskMembers">
                      <el-select v-model="taskMembers"
                                 multiple
                                 placeholder="请选择">
                        <el-option v-for="member in members"
                                   :key="member.id"
                                   :label="member.username"
                                   :value="member.id">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="开始时间"
                                  prop="begin">
                      <el-date-picker v-model="tempTask.begin"
                                      type="date"
                                      value-format="yyyy-MM-dd"
                                      placeholder="选择日期">
                      </el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束时间"
                                  prop="end">
                      <el-date-picker v-model="tempTask.end"
                                      type="date"
                                      value-format="yyyy-MM-dd"
                                      placeholder="选择日期">
                      </el-date-picker>
                    </el-form-item>
                    <el-form-item label="任务状态"
                                  prop="state">
                      <el-select v-model="tempTask.state"
                                 placeholder="请选择">
                        <el-option v-for="item in options"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-form>
                  <span slot="footer"
                        class="dialog-footer">
                    <el-button @click="cancel('tempTask')">取 消</el-button>
                    <el-button type="primary"
                               @click="createTask('tempTask')">确 定</el-button>
                  </span>
                </el-dialog>
              </div>
              <ul class="list">
                <li v-for="task in tasks.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="task.taskId"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{task.detail}}</div>
                  </div>
                  <div class="list-center">{{task.state}}</div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="goTask(task)">查看</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="deleteTask(task)">删除</el-button>
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
import { deleteATask, newTask, deleteResourceFromProject, downloadPResource, newPResource, deletePMember, addPTempMember, deleteTempMemberFromProject, getProjectById, editProject, getAllTasks, getPAllMembers, getPAllResources, getPAllTempMembers } from '../api/Index';

export default {
  name: 'ProjectDetail',
  data () {
    return {
      RdialogFormVisible: false,
      loading: false,
      pageSize: 5,
      currentPage: 1,
      isCreater: false,
      project: {},
      members: [],
      tempMembers: [],
      resources: [],
      tasks: [],
      taskMembers: [],
      tempTask: {
        pid: 0,
        detail: '',
        begin: '',
        end: '',
        state: ''
      },
      options: [
        {
          value: '待开始',
          label: '待开始'
        },
        {
          value: '正在进行中',
          label: '正在进行中'
        }, {
          value: '已结束',
          label: '已结束'
        }],
      Prules: {
        name: [{
          required: true,
          message: "请输入项目名称",
          trigger: "blur"
        }]
      },
      rules: {
        detail: [{
          required: true,
          message: "请输入任务内容",
          trigger: "blur"
        }]
      },
      dialogVisible: false,
      PdialogFormVisible: false,
      uploadFile: '',
      manager: ''
    }
  },
  computed: {
    ...mapGetters([
      'tempProjectId',
      'tempProjectOwner',
      'userId',
    ])
  },
  mounted () {
    if (this.userId === this.tempProjectOwner) {
      this.isCreater = true
    }
    this.getProject(this.tempProjectId)
    this.getMembers(this.tempProjectId)
    this.getTempMembers(this.tempProjectId)
    this.getResources(this.tempProjectId)
    this.getTasks(this.tempProjectId)
  },
  methods: {
    deleteTask (task) {
      deleteATask({ taskId: task.taskId }).then(res => {
        if (res.code === 200) {
          this.getTasks(this.tempProjectId)
          this.$message.success(res.msg)
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    createTask (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.tempTask.begin = this.tempTask.begin + ' 00:00:00'
          this.tempTask.end = this.tempTask.end + ' 00:00:00'
          this.tempTask.pid = this.tempProjectId
          newTask({
            projectTask: this.tempTask,
            members: this.taskMembers
          }).then(res => {
            if (res.code === 200) {
              this.$refs[formName].resetFields()
              this.tempTask.begin = ''
              this.tempTask.end = ''
              this.tempTask.state = ''
              this.taskMembers = []
              this.dialogVisible = false
              this.getTasks(this.tempProjectId)
              this.$message.success(res.msg)
            } else {
              this.$message.success(res.msg)
            }
          })
        }
      })
    },
    deleteAResource (resource) {
      var _this = this
      deleteResourceFromProject({
        uid: this.userId,
        pid: this.tempProjectId,
        resourceName: resource.resourceName
      }).then(res => {
        if (res.code === 200) {
          _this.getResources(_this.tempProjectId)
          _this.$message.success('删除成功')
        } else {
          _this.$message.error('删除失败')
        }
      })
    },
    download (resource) {
      downloadPResource({
        uid: this.userId,
        pid: this.tempProjectId,
        resourceName: resource.resourceName
      }).then(res => {
        let url = `http://localhost:8888/project-resource/download?resourceName=${resource.resourceName}&pid=${this.tempProjectId}&uid=${this.userId}`
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.download = decodeURIComponent(resource.resourceName)
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        window.URL.revokeObjectURL(url)
        this.$message.success('下载成功')
      })
    },
    createPResource () {
      let fd = new FormData();
      fd.append('pid', this.tempProjectId)
      fd.append('uid', this.userId)
      fd.append('file', this.uploadFile)
      newPResource(fd).then(res => {
        if (res.code === 200) {
          this.RdialogFormVisible = false
          this.$refs.Rupload.clearFiles()
          this.getResources(this.tempProjectId)
          this.$message.success(res.msg)
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    RchangeFile (file, fileList) {
      this.uploadFile = file.raw
    },
    RhandleExceed (files, fileList) {
      this.$message.error("上传文件不能超过1个!");
    },
    Rcancel () {
      this.RdialogFormVisible = false
      this.$refs.Rupload.clearFiles()
    },
    cancel (formName) {
      this.dialogVisible = false
      this.$refs[formName].resetFields()
      this.tempTask.begin = ''
      this.tempTask.end = ''
      this.tempTask.state = ''
      this.taskMembers = []
    },
    beforeUpload (file) {
      let isLt2M = true
      isLt2M = file.size / 1024 / 1024 < 100
      if (!isLt2M) {
        this.loading = false
        this.$message.error('上传文件大小不能超过 100MB!')
      }
      return isLt2M
    },
    handleError (e, file, fileList) {
      const msg = JSON.parse(e.message)
      this.$message.error(msg.message)
      this.loading = false
    },
    editAProject (formName) {
      this.$refs[formName].validate((valid) => {
        let fd = new FormData();
        fd.append('pid', this.tempProjectId)
        fd.append('uid', this.userId)
        fd.append('name', this.project.name);
        fd.append('state', this.project.state);
        fd.append('file', this.PuploadFiles);

        if (valid) {
          editProject(fd).then(res => {
            if (res.code === 200) {
              this.PdialogFormVisible = false
              this.$message({
                message: res.msg,
                type: 'success'
              })
              this.getProject(this.tempProjectId)
            } else {
              this.$message.error(res.msg);
            }
          }).catch(res => {
            console.log(res)
          })
        } else {
          this.$message({
            message: "error",
            type: 'error'
          })
        }
      })
    },
    Pcancel (formName) {
      this.PdialogFormVisible = false
      this.$refs[formName].resetFields()
      this.$refs.uploadimg.clearFiles()
    },
    handleExceed (files, fileList) {
      this.$message.error("上传图片不能超过1张!");
    },
    handleRemove (file, fileList) {
      this.$message.error("删除成功!");
    },
    // 图片上传失败时
    uploadError () {
      this.$message.error("图片上传失败!");
    },
    PchangeFile (file, fileList) {
      this.PuploadFiles = fileList[0].raw
    },
    deleteAMember (member) {
      var _this = this
      if (member.id === _this.tempProjectOwner) {
        _this.$message.error('管理员不能被删除')
      } else {
        deletePMember({
          pid: _this.tempProjectId,
          uid: member.id
        }).then(res => {
          if (res.code === 200) {
            _this.getMembers(_this.tempProjectId)
            _this.$message.success('删除成功')
          } else {
            _this.$message.error('删除失败')
          }
        })
      }
    },
    agreeTempMember (tempMember) {
      var _this = this
      deleteTempMemberFromProject({
        pid: _this.tempProjectId,
        uid: tempMember.id
      }).then(res => {
        if (res.code === 200) {
          addPTempMember({
            pid: _this.tempProjectId,
            uid: tempMember.id
          }).then(res => {
            if (res.code === 200) {
              _this.getTempMembers(_this.tempProjectId)
              _this.getMembers(_this.tempProjectId)
              _this.$message.success('添加成功')
            } else {
              _this.$message.error('添加失败')
            }
          })
        }
      })
    },
    deleteATempMember (tempMember) {
      var _this = this
      deleteTempMemberFromProject({
        pid: _this.tempProjectId,
        uid: tempMember.id
      }).then(res => {
        if (res.code === 200) {
          _this.getTempMembers(_this.tempProjectId)
          _this.$message.success('拒绝成功')
        } else {
          _this.$message.error('拒绝失败')
        }
      })
    },
    handleCurrentChange (val) {
      this.currentPage = val
    },
    getProject (id) {
      getProjectById({ pid: id }).then(res => {
        if (res.code === 200) {
          this.project = res.data[0]
          this.manager = res.data[1].username
        }
      })
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
    },
    goTask (task) {
      this.$store.commit('setTempMembers', this.members)
      this.$store.commit('setTempTaskId', task.taskId)
      this.$router.push({ path: `/Task/${task.taskId}` });
    }
  }
}
</script>

<style scoped>
.project-detail {
  margin: 10px 30px 0 30px;
}
.edit-project {
  color: white;
  font-size: 15px;
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
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #409eff;
  color: white;
  font-size: 20px;
  padding: 10px;
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
.new-resource {
  color: white;
  font-size: 15px;
}
.project-member-top {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 40px;
  background-color: #409eff;
  font-size: 15px;
  color: white;
}
.resource-manage-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1%;
  width: 98%;
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