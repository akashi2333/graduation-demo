<template>
  <div class="team-detail"
       style="margin-top: 65px">
    <div class="intruducation">
      <div class="image">
        <img :src="'data:image/png;base64,'+team.img"
             alt=""
             class="img">
      </div>
      <div class="left">
        <div class="title">
          <div>{{team.name}}</div>
          <el-button type="text"
                     class="edit-team"
                     icon="el-icon-edit"
                     @click="dialogFormVisible = true">编辑团队</el-button>
          <el-dialog title="编辑团队"
                     :visible.sync="dialogFormVisible"
                     width="50%">
            <el-form label-position="right"
                     label-width="20%"
                     :model="team"
                     :rules="rules"
                     ref="team">
              <el-form-item label="团队名称"
                            prop="name">
                <el-input v-model="team.name"
                          placeholder="请输入团队名称"></el-input>
              </el-form-item>
              <el-form-item label="团队图片"
                            prop="file">
                <el-upload action="uploadAction"
                           list-type="picture-card"
                           :on-remove="handleRemove"
                           :limit="1"
                           :show-file-list="true"
                           name="img"
                           ref="upload"
                           :data="team"
                           accept="image/png,image/gif,image/jpg,image/jpeg"
                           :on-exceed="handleExceed"
                           :auto-upload="false"
                           :on-error="uploadError"
                           :before-upload="handleBeforeUpload"
                           :on-change="changeFile">
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="团队介绍"
                            prop="brief">
                <el-input v-model="team.brief"
                          placeholder="请输入团队介绍"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary"
                         @click="edit('team')">确 定</el-button>
            </span>
          </el-dialog>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="team-time">创建时间：{{team.timestamp}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="team-isowner">管理员：{{team.isowner}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="team-text">介绍：{{team.brief}}</p>
        </div>
      </div>
    </div>
    <div class="bottom">
      <el-tabs :stretch="true">
        <el-tab-pane label="团员管理">
          <div class="member">
            <div class="team-member"
                 style="  margin-right: 5px;">
              <div class="team-member-top">团队成员</div>
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
            <div class="team-member">
              <div class="team-member-top">申请人员</div>
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
        <el-tab-pane label="项目管理">
          <div class="project">
            <div class="project-manage">
              <div class="project-manage-top">团队项目</div>
              <ul class="list">
                <li v-for="project in projects.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="project.pid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="project-name">{{project.name}}</div>
                  </div>
                  <div class="list-center">
                    {{project.time}}
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="goProject(project)">查看</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="deleteAProject(project)"
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
                             :total="projects.length">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="团队资源管理">
          <div class="resource">
            <div class="resource-manage">
              <div class="resource-manage-top">团队资源</div>
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
        <el-tab-pane label="团队知识图谱">团队知识图谱</el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getAllMembers, editTeam, getAllProgects, getAllResources, getAllTempMembers, deleteProjectFromList, deleteProject, deleteMemberFromList, deleteMember, deleteTempMemberFromList, addTempMember, deleteTempMember, deleteResourceFromList, deleteResource, searchTeamById } from '../api/Index';

export default {
  name: 'TeamDetail',
  data () {
    return {
      dialogFormVisible: false,
      pageSize: 5,
      currentPage: 1,
      isCreater: false,
      team: {},
      members: [],
      projects: [],
      resources: [],
      tempMembers: [],
      rules: {
        name: [{
          required: true,
          message: "请输入团队名称",
          trigger: "blur"
        }],
        brief: [{
          required: true,
          message: "请输入团队介绍",
          trigger: "blur"
        }]
      },
      dialogImageUrl: '',
      uploadFiles: ''
    }
  },
  computed: {
    ...mapGetters([
      'tempTeamId',
      'tempTeamOwner',
      'userId'
    ])
  },
  mounted () {
    if (this.userId === this.tempTeamOwner) {
      this.isCreater = true
    }
    this.getTeam(this.tempTeamId)
    this.getMembers(this.tempTeamId)
    this.getTempMembers(this.tempTeamId)
    this.getProjects(this.tempTeamId)
    this.getResources(this.tempTeamId)
  },
  methods: {
    handleBeforeUpload (file) {
      if (!(file.type === 'image/png' || file.type === 'image/gif' || file.type === 'image/jpg' || file.type ===
        'image/jpeg')) {
        this.$message.error("请上传格式为image/png, image/gif, image/jpg, image/jpeg的图片");
      }
      let size = file.size / 1024 / 1024 / 2
      if (size > 2) {
        this.$message.error("图片大小必须小于2M");
      }
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
    changeFile (file, fileList) {
      this.uploadFiles = fileList[0].raw
    },
    edit (formName) {
      this.$refs[formName].validate((valid) => {
        let fd = new FormData()
        fd.append('name', this.team.name)
        fd.append('brief', this.team.brief)
        fd.append('file', this.uploadFiles)
        fd.append('tid', this.tempTeamId)

        if (valid) {
          editTeam(fd).then(res => {
            if (res.code === 200) {
              this.dialogFormVisible = false
              this.$message({
                message: res.msg,
                type: 'success'
              })
              this.getTeam(this.tempTeamId)
            } else {
              this.$message.error(res.msg);
            }
          }).catch(res => {
            console.log(res)
          })
        } else {
          this.message({
            message: "error",
            type: 'error'
          })
        }
      })
    },
    deleteAMember (member) {
      var _this = this
      deleteMemberFromList({
        type: 0,
        tid: _this.tid,
        uid: member.uid
      }).then(res => {
        if (res.code === 200) {
          deleteMember({
            type: 0,
            tid: _this.tid,
            uid: member.uid
          }).then(res => {
            if (res.code === 200) {
              _this.getMembers(_this.tid)
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
        type: 0,
        tid: _this.tid,
        uid: tempMember.uid
      }).then(res => {
        if (res.code === 200) {
          addTempMember({
            type: 0,
            tid: _this.tid,
            uid: tempMember.uid
          }).then(res => {
            if (res === 200) {
              _this.getTempMembers(_this.tid)
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
        type: 0,
        tid: _this.tid,
        uid: tempMember.uid
      }).then(res => {
        if (res.code === 200) {
          deleteTempMember({
            type: 0,
            tid: _this.tid,
            uid: tempMember.uid
          }).then(res => {
            if (res.code === 200) {
              _this.getTempMembers(_this.tid)
              _this.$message('拒绝成功')
            } else {
              _this.$message('拒绝失败')
            }
          })
        }
      })
    },
    goProject (project) {
      this.$store.commit('setTempProjectList', project)
      this.$router.push({ path: `/ProjectDetail/${project.pid}` });
    },
    deleteAProject (project) {
      var _this = this
      deleteProjectFromList({
        tid: _this.tid,
        pid: project.pid
      }).then(res => {
        if (res === 200) {
          deleteProject({
            tid: _this.tid,
            pid: project.pid
          }).then(res => {
            if (res === 200) {
              _this.getProjects(_this.tid)
              _this.$message('删除成功')
            } else {
              _this.$message('删除失败')
            }
          })
        }
      })
    },
    deleteAresource (resource) {
      var _this = this
      deleteResourceFromList({
        type: 0,
        tid: _this.tid,
        rid: resource.rid
      }).then(res => {
        if (res.code === 200) {
          deleteResource({
            type: 0,
            tid: _this.tid,
            rid: resource.rid
          }).then(res => {
            if (res.code === 200) {
              _this.getResources(_this.tid)
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
    getTeam (id) {
      searchTeamById({ tid: id }).then(res => {
        if (res.code === 200) {
          this.team = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    getMembers (id) {
      getAllMembers({ tid: id }).then(res => {
        if (res.code === 200) {
          this.members = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    getTempMembers (id) {
      getAllTempMembers({ tid: id }).then(res => {
        if (res.code === 200) {
          this.tempMembers = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    getProjects (id) {
      getAllProgects({ tid: id }).then(res => {
        if (res.code === 200) {
          this.projects = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    getResources (id) {
      getAllResources({ tid: id }).then(res => {
        if (res.code === 200) {
          this.resources = res.data
        } else {
          console.log(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
.team-detail {
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
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #409eff;
  color: white;
  font-size: 20px;
  padding: 10px;
  margin-bottom: 10px;
}
.edit-team {
  color: white;
  font-size: 15px;
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
.team-member {
  position: relative;
  width: 50%;
  height: 330px;
}
.project-manage,
.resource-manage {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  height: 330px;
}
.team-member-top,
.project-manage-top,
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