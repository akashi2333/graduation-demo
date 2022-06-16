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
          <el-dialog title="编辑任务"
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
    <div class="comment">
      <div class="comment-title">讨论</div>
      <div class="comment-msg">
        <i class="el-icon-info icon"></i>
        <el-input class="comment-input"
                  type="textarea"
                  :rows="1"
                  placeholder="请输入评论"
                  v-model="commentText"></el-input>
        <el-button type="primary"
                   class="sub-btn"
                   @click="postComment()">评论</el-button>
      </div>
      <div class="showComments">
        <ul class="commentList"
            v-for="(comment,index) in comments"
            :key="comment.id">
          <li class="comment-item">
            <div class="comment-img">
              <i class="el-icon-info info-icon"></i>
            </div>
            <div class="comment-content">
              <ul>
                <li class="name">{{comment.publisher}}</li>
                <li class="time">{{comment.time}}</li>
                <li class="content">
                  <p>{{comment.content}}</p>
                  <p style="color:#409EFF; margin-left: 5px;">{{comment.accepter}}</p>
                </li>
              </ul>
            </div>
            <div class="answer">
              <el-button type="text"
                         class="btn"
                         @click="answer(index)">回复</el-button>
            </div>
            <div class="likes"
                 ref="likes"
                 @click="postUp(comment.cid,comment.likes,index)">
              <img src="../assets/like.svg"
                   alt="zan"
                   class="zan">
              <p>{{comment.likes}}</p>
            </div>
          </li>
          <li class="comment-item"
              v-if="isAnswer == index">
            <el-input class="comment-input"
                      type="textarea"
                      :rows="1"
                      placeholder="请输入评论"
                      v-model="commentText"></el-input>
            <el-button type="primary"
                       class="sub-btn"
                       @click="postChildComment(comment.id)">评论</el-button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getATask, getTaskMembers, editTask, getTaskComments, sendComment, sendLike, deleteLike } from '../api/Index'

export default {
  name: 'Task',
  data () {
    return {
      isCreater: false,
      task: {},
      isAnswer: -1,
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
      },
      comments: [
        { id: 1, content: 'hi', time: '2020-2-11', publisher: 'akashi', likes: 1, accepter:"" }, 
        { id: 2, content: 'hello', time: '2020-2-11', publisher: 'hy', likes: 0, accepter:"" },
        { id: 3, content: '你好啊akashi', time: '2020-2-11', publisher: 'hy', likes: 0, accepter:"@akashi" }
      ],
      commentText: '',
    }
  },
  computed: {
    ...mapGetters([
      'tempTaskId',
      'userId',
      'userName',
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
    // this.getComments(this.tempTaskId)
  },
  methods: {
    answer (index) {
      this.isAnswer = this.isAnswer == index ? -1 : index
    },
    postComment () {
      sendComment({
        publisher: this.userName,
        content: this.commentText,
        likes: 0
      }).then(res => {
        if (res.code === 200) {
          this.commentText = ''
          this.getComments()
          this.$message.success('评论成功')
        } else {
          this.$message.error('评论失败')
        }
      }).catch(err => {
        console.log(err)
      })
    },
    postUp (id, likes, index) {
      console.log(this.$refs.likes[index].children[0].src)
      if (this.$refs.likes[index].children[0].src === 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjUxNDgwMzI3OTU4IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjI5NjEiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj5AZm9udC1mYWNlIHsgZm9udC1mYW1pbHk6IGZlZWRiYWNrLWljb25mb250OyBzcmM6IHVybCgiLy9hdC5hbGljZG4uY29tL3QvZm9udF8xMDMxMTU4X3U2OXc4eWh4ZHUud29mZjI/dD0xNjMwMDMzNzU5OTQ0IikgZm9ybWF0KCJ3b2ZmMiIpLCB1cmwoIi8vYXQuYWxpY2RuLmNvbS90L2ZvbnRfMTAzMTE1OF91Njl3OHloeGR1LndvZmY/dD0xNjMwMDMzNzU5OTQ0IikgZm9ybWF0KCJ3b2ZmIiksIHVybCgiLy9hdC5hbGljZG4uY29tL3QvZm9udF8xMDMxMTU4X3U2OXc4eWh4ZHUudHRmP3Q9MTYzMDAzMzc1OTk0NCIpIGZvcm1hdCgidHJ1ZXR5cGUiKTsgfQo8L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNjcyIDE5MmEyMjIuNzIgMjIyLjcyIDAgMCAwLTE2MCA2Ny42OEEyMjIuNTkyIDIyMi41OTIgMCAwIDAgMzUyIDE5MmMtMTIzLjUyIDAtMjI0IDEwMS4xODQtMjI0IDIyNS42IDAgNTIuMjU2IDE4LjE0NCAxMDMuMiA1Mi45MjggMTQ1LjUzNmwyODUuOTUyIDI5My45ODRhNjIuNTI4IDYyLjUyOCAwIDAgMCA5MC4yMDggMGwyODcuODA4LTI5Ni4wMzJBMjI3LjEzNiAyMjcuMTM2IDAgMCAwIDg5NiA0MTcuNkM4OTYgMjkzLjE4NCA3OTUuNTIgMTkyIDY3MiAxOTIiIHAtaWQ9IjI5NjIiIGZpbGw9IiNkODFlMDYiPjwvcGF0aD48L3N2Zz4=') {
        deleteLike({
          cid: id,
          likes: likes - 1
        }).then(res => {
          if (res.code === 200) {
            this.$refs.likes[index].children[0].src = 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjUxNDgwMzI3OTU4IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjI5NjEiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj5AZm9udC1mYWNlIHsgZm9udC1mYW1pbHk6IGZlZWRiYWNrLWljb25mb250OyBzcmM6IHVybCgiLy9hdC5hbGljZG4uY29tL3QvZm9udF8xMDMxMTU4X3U2OXc4eWh4ZHUud29mZjI/dD0xNjMwMDMzNzU5OTQ0IikgZm9ybWF0KCJ3b2ZmMiIpLCB1cmwoIi8vYXQuYWxpY2RuLmNvbS90L2ZvbnRfMTAzMTE1OF91Njl3OHloeGR1LndvZmY/dD0xNjMwMDMzNzU5OTQ0IikgZm9ybWF0KCJ3b2ZmIiksIHVybCgiLy9hdC5hbGljZG4uY29tL3QvZm9udF8xMDMxMTU4X3U2OXc4eWh4ZHUudHRmP3Q9MTYzMDAzMzc1OTk0NCIpIGZvcm1hdCgidHJ1ZXR5cGUiKTsgfQo8L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNjcyIDE5MmEyMjIuNzIgMjIyLjcyIDAgMCAwLTE2MCA2Ny42OEEyMjIuNTkyIDIyMi41OTIgMCAwIDAgMzUyIDE5MmMtMTIzLjUyIDAtMjI0IDEwMS4xODQtMjI0IDIyNS42IDAgNTIuMjU2IDE4LjE0NCAxMDMuMiA1Mi45MjggMTQ1LjUzNmwyODUuOTUyIDI5My45ODRhNjIuNTI4IDYyLjUyOCAwIDAgMCA5MC4yMDggMGwyODcuODA4LTI5Ni4wMzJBMjI3LjEzNiAyMjcuMTM2IDAgMCAwIDg5NiA0MTcuNkM4OTYgMjkzLjE4NCA3OTUuNTIgMTkyIDY3MiAxOTIiIHAtaWQ9IjI5NjIiIGZpbGw9IiM4YThhOGEiPjwvcGF0aD48L3N2Zz4='
            this.getComments()
          }
        })
      } else {
        sendLike({
          cid: id,
          likes: likes + 1
        }).then(res => {
          if (res.code === 200) {
            this.$refs.likes[index].children[0].src = 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjUxNDgwMzI3OTU4IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjI5NjEiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj5AZm9udC1mYWNlIHsgZm9udC1mYW1pbHk6IGZlZWRiYWNrLWljb25mb250OyBzcmM6IHVybCgiLy9hdC5hbGljZG4uY29tL3QvZm9udF8xMDMxMTU4X3U2OXc4eWh4ZHUud29mZjI/dD0xNjMwMDMzNzU5OTQ0IikgZm9ybWF0KCJ3b2ZmMiIpLCB1cmwoIi8vYXQuYWxpY2RuLmNvbS90L2ZvbnRfMTAzMTE1OF91Njl3OHloeGR1LndvZmY/dD0xNjMwMDMzNzU5OTQ0IikgZm9ybWF0KCJ3b2ZmIiksIHVybCgiLy9hdC5hbGljZG4uY29tL3QvZm9udF8xMDMxMTU4X3U2OXc4eWh4ZHUudHRmP3Q9MTYzMDAzMzc1OTk0NCIpIGZvcm1hdCgidHJ1ZXR5cGUiKTsgfQo8L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNjcyIDE5MmEyMjIuNzIgMjIyLjcyIDAgMCAwLTE2MCA2Ny42OEEyMjIuNTkyIDIyMi41OTIgMCAwIDAgMzUyIDE5MmMtMTIzLjUyIDAtMjI0IDEwMS4xODQtMjI0IDIyNS42IDAgNTIuMjU2IDE4LjE0NCAxMDMuMiA1Mi45MjggMTQ1LjUzNmwyODUuOTUyIDI5My45ODRhNjIuNTI4IDYyLjUyOCAwIDAgMCA5MC4yMDggMGwyODcuODA4LTI5Ni4wMzJBMjI3LjEzNiAyMjcuMTM2IDAgMCAwIDg5NiA0MTcuNkM4OTYgMjkzLjE4NCA3OTUuNTIgMTkyIDY3MiAxOTIiIHAtaWQ9IjI5NjIiIGZpbGw9IiNkODFlMDYiPjwvcGF0aD48L3N2Zz4='
            this.getComments()
          }
        })
      }
    },
    getComments (id) {
      getTaskComments({ taskId: id }).then(res => {
        if (res.code === 200) {
          this.comments = res.data
        }
      })
    },
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
          this.taskMembers = res.data
        }
      })
    }
  }
}
</script>

<style scoped>
.comment {
  background-color: white;
  padding-bottom: 10px;
}
.comment-title {
  background-color: #409eff;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  color: white;
}
.comment-msg {
  display: flex;
  margin: 10px 10px;
}
.icon {
  font-size: 30px;
  color: #409eff;
  margin-right: 10px;
}
.comment-input {
  margin-right: 10px;
}
.comment-input /deep/ .el-textarea__inner {
  height: 30px !important;
}
.sub-btn {
  height: 33px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.comment-item {
  padding: 15px 10px;
  display: flex;
  position: relative;
}

.comment-content ul {
  padding: 0 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.info-icon {
  font-size: 30px;
  color: #409eff;
}
.content {
  font-size: 15px;
  display: flex;
}
.name {
  font-weight: bold;
  font-size: 15px;
}
.time {
  margin-top: 5px;
  margin-bottom: 10px;
  font-size: 5px;
  color: #8a8a8a;
}
.answer {
  position: absolute;
  right: 10px;
  bottom: 5px;
}
.btn {
  font-size: 15px;
  color: #8a8a8a;
}
.likes {
  display: flex;
  align-items: flex-end;
  position: absolute;
  right: 10px;
  font-size: 15px;
}
.zan {
  width: inherit;
  height: 20px;
  overflow: hidden;
  position: relative;
}
.likes p {
  margin-left: 5px;
  color: rgba(0, 0, 0, 0.5);
}
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