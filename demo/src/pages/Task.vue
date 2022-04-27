<template>
  <div class="resource-detail"
       style="margin-top: 65px">
    <div class="intruducation">
      <div class="left">
        <div class="title">
          <div>{{task.detail}}</div>
          <el-button v-show="isCreater"
                     type="text"
                     class="edit-project"
                     icon="el-icon-edit"
                     @click="dialogVisible = true">编辑任务</el-button>
          <el-dialog title="新建任务"
                     :visible.sync="dialogVisible"
                     width="50%">
            <el-form label-position="right"
                     label-width="20%"
                     :model="task"
                     :rules="rules"
                     ref="task">
              <el-form-item label="任务内容"
                            prop="detail">
                <el-input v-model="task.detail"
                          placeholder="请输入任务内容"></el-input>
              </el-form-item>

              <el-form-item label="开始时间"
                            prop="begin">
                <el-date-picker v-model="task.begin"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="结束时间"
                            prop="end">
                <el-date-picker v-model="task.end"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="任务状态"
                            prop="state">
                <el-select v-model="task.state"
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
              <el-button @click="cancel('task')">取 消</el-button>
              <el-button type="primary"
                         @click="edit('task')">确 定</el-button>
            </span>
          </el-dialog>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="resource-time">开始时间：{{task.begin}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="resource-isowner">结束：{{task.end}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="resource-text">状态：{{task.state}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="resource-text">参与人员：</p>
          <p v-for="member in taskMembers"
             :key="member.id">{{member.username}}&nbsp;&nbsp;</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getATask, getTaskMembers, editTask } from '../api/Index'

export default {
  name: 'ResourceDetail',
  data () {
    return {
      isCreater: false,
      task: {},
      taskMembers: [],
      dialogVisible: false,
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
        }
      ],
      rules: {
        detail: [{
          required: true,
          message: "请输入任务内容",
          trigger: "blur"
        }]
      }
    }
  },
  computed: {
    ...mapGetters([
      'tempTaskId',
      'userId',
      'tempMembers',
      'tempProjectId',
      'tempProjectOwner'
    ])
  },
  mounted () {
    if (this.userId === this.tempProjectOwner) {
      this.isCreater = true
    }
    this.getTask(this.tempTaskId)
    this.getTaskMembers(this.tempTaskId)
  },
  methods: {
    edit (formName) {
      this.$refs[formName].validate((valid) => {
        let fd = new FormData();
        fd.append('pid', this.tempProjectId)
        fd.append('detail', this.task.detail)
        fd.append('begin', this.task.begin)
        fd.append('end', this.task.end)
        fd.append('taskId', this.task.taskId)
        fd.append('state', this.task.state)
        if (valid) {
          editTask(fd).then(res => {
            if (res.code === 200) {
              this.dialogVisible = false
              this.getTask(this.tempTaskId)
              this.$message.success(res.msg)
            } else {
              this.$message.success(res.msg)
            }
          })
        }
      })
    },
    cancel (formName) {
      this.dialogVisible = false
    },
    getTask (id) {
      getATask({ taskId: id }).then(res => {
        if (res.code === 200) {
          this.task = res.data[0]
        } else {
          console.log('error')
        }
      })
    },
    getTaskMembers (id) {
      getTaskMembers({ taskId: id }).then(res => {
        if (res.code === 200) {
          res.data.shift()
          this.taskMembers = res.data
        }
      })
    }
  }
}
</script>

<style scoped>
.resource-detail {
  margin: 10px 30px 0 30px;
}

.intruducation {
  background-color: white;
  width: 100%;
  margin-bottom: 10px;
  display: flex;
}
.left {
  width: 100%;
}
.edit-project {
  color: white;
  font-size: 15px;
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
.item {
  display: flex;
  align-items: flex-start;
  padding-bottom: 10px;
  text-align: left;
  line-height: 1.5;
  font-size: 15px;
}
</style>