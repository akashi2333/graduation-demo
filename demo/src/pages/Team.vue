<template>
  <div class="team"
       style="margin-top: 65px">
    <div class="title">
      <div class="search">
        <input type="text"
               placeholder="搜索团队"
               v-model="keywords">
        <div class="search-btn"
             @click="search()">
          <i class="el-icon-search"
             style="font-size: 20px"></i>
        </div>
      </div>
      <el-button type="text"
                 class="new-team"
                 icon="el-icon-plus"
                 @click="create()">新建团队</el-button>
      <el-dialog title="新建团队"
                 :visible.sync="dialogFormVisible"
                 width="50%">
        <el-form label-position="right"
                 label-width="20%"
                 :model="tempTeam"
                 :rules="rules"
                 ref="tempTeam">
          <el-form-item label="团队名称"
                        prop="name">
            <el-input v-model="tempTeam.name"
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
                       :data="tempTeam"
                       accept="image/png,image/gif,image/jpg,image/jpeg"
                       :on-exceed="handleExceed"
                       :auto-upload="false"
                       :on-error="uploadError"
                       :on-change="changeFile">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="团队介绍"
                        prop="brief">
            <el-input v-model="tempTeam.brief"
                      placeholder="请输入团队介绍"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer"
              class="dialog-footer">
          <el-button @click="cancel('tempTeam')">取 消</el-button>
          <el-button type="primary"
                     @click="createTeam('tempTeam')">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <div class="team-list">
      <ul class="content">
        <li class="content-item"
            v-for="team in teamList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
            :key="team.tid">
          <img :src="'data:image/png;base64,'+team.img"
               alt=""
               class="image"
               style="display:block"
               @click="goTeam(team)">
          <div class="item">
            <p class="item-name">{{team.name}}</p>
            <i class="el-icon-circle-plus"
               style="font-size: 30px; color: #409EFF"
               @click="join(team)"></i>
          </div>
        </li>
      </ul>
    </div>
    <el-pagination style="padding-bottom: 10px"
                   background
                   @current-change="handleCurrentChange"
                   layout="total, prev, pager, next"
                   :current-page="currentPage"
                   :page-size="pageSize"
                   :total="teamList.length">
    </el-pagination>
  </div>
</template>

<script>
import { joinTeam, getTeams, newTeam } from '../api/Index'
import { mapGetters } from 'vuex'
export default {
  name: "Team",
  data () {
    return {
      dialogFormVisible: false,
      keywords: '',
      pageSize: 12,
      currentPage: 1,
      teamList: [],
      tempTeam: {
        name: '',
        img: '',
        brief: ''
      },
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
      'userId',
      'loginState'
    ])
  },
  mounted () {
    this.getAllTeams()
  },
  methods: {
    create () {
      if (this.loginState) {
        this.dialogFormVisible = true
      } else {
        this.$message.error('请先登录')
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
    createTeam (formName) {
      this.$refs[formName].validate((valid) => {
        let fd = new FormData();
        fd.append('uid', this.userId)
        fd.append('name', this.tempTeam.name);
        fd.append('brief', this.tempTeam.brief);
        fd.append('file', this.uploadFiles);

        if (valid) {
          newTeam(fd).then(res => {
            if (res.code === 200) {
              this.dialogFormVisible = false
              this.$message({
                message: res.msg,
                type: 'success'
              })
              //新增完清空表单内容
              setTimeout(() => {
                this.$refs.tempTeam.resetFields()
                this.$refs.upload.clearFiles()
              }, 200)
              this.getAllTeams()
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
    // 取消
    cancel (formName) {
      this.dialogFormVisible = false
      this.$refs[formName].resetFields()
      this.$refs.upload.clearFiles()
    },
    handleCurrentChange (val) {
      this.currentPage = val
    },
    getAllTeams () {
      getTeams().then(res => {
        this.teamList = res.data
      }).catch(err => {
        console.log(err)
      })
    },
    goTeam (team) {
      if (this.loginState) {
        this.$store.commit('setTempTeamId', team.tid)
        this.$store.commit('setTempTeamOwner', team.uid)
        this.$router.push({ path: `/TeamDetail/${team.tid}` });
      } else {
        this.$message.error('请先登录')
      }
    },
    join (team) {
      if (this.loginState) {
        joinTeam({
          tid: team.tid,
          uid: this.userId
        }).then(res => {
          if (res.code === 200) {
            this.$message.success(res.msg)
          } else {
            this.$message.error(res.msg)
          }
        })
      } else {
        this.$message.error('请先登录')
      }

    },
    search () {
      this.$store.commit('setSearchWord', this.keywords)
      this.$router.push({ path: '/Search', query: { keywords: this.keywords } })
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  margin-top: 20px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.avatar-uploader:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: flex;
}
.team {
  margin: 10px 30px 0 30px;
  background-color: white;
}
.title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1%;
  height: 40px;
  background-color: #409eff;
  font-size: 15px;
  color: white;
}
.new-team {
  color: white;
  font-size: 15px;
}
.search {
  display: flex;
  height: 25px;
  align-items: center;
  border-radius: 5px;
  overflow: hidden;
  background-color: white;
}
.search input {
  padding: 0;
  height: 25px;
  width: 200px;
  font-size: 10px;
  border: 0;
  text-indent: 10px;
  background-color: white;
  outline: none;
}
.search-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  color: #409eff;
  height: 25px;
  width: 40px;
  cursor: pointer;
}

.content {
  display: flex;
  justify-content: flex-start;
  align-items: stretch;
  flex-wrap: wrap;
}
.content-item {
  width: 23%;
  margin: 20px 1%;
  overflow: hidden;
  border-radius: 5px;
  box-shadow: 0 0 5px 1px rgba(0, 0, 0, 0.1);
}

.image {
  width: 100%;
  height: inherit;
  cursor: pointer;
}
.item {
  padding: 5px;
  font-size: 15px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.item-name {
  overflow: hidden;
  text-overflow: ellipsis;
  display: block;
  padding: 10px 8px;
}
.item i {
  cursor: pointer;
}
</style>