<template>
  <div style="margin-top: 65px"
       class="info">
    <div class="header">
      <div class="title">{{tempAccepter.username}}</div>
    </div>
    <div class="main">
      <div class="talkshow">
        <div v-for="(item,index) in list"
             :key="index"
             :class="item.sid === userId? 'send':'accept'">
          <i v-show="item.sid != userId"
             class="el-icon-info"
             style="color: #409eff;font-size:30px;margin-right:5px"></i>
          <span>{{item.message}}</span>
          <i v-show="item.sid == userId"
             class="el-icon-info"
             style="color: #409eff;font-size:30px;margin-left:5px"></i>
        </div>
      </div>
      <div class="sendbox">
        <el-input class="comment-input"
                  type="text"
                  v-model="word"></el-input>
        <el-button type="primary"
                   class="sub-btn"
                   @click="sendmsg">发送</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { getMessage, send } from '../api/Index';

export default {
  data () {
    return {
      list: [],
      word: ''
    }
  },
  computed: {
    ...mapGetters([
      'tempAccepter',
      'userId'
    ])
  },
  mounted () {
    console.log(this.tempAccepter)
    this.getMessages(this.userId)
  },
  methods: {
    sendmsg () {
      send({
        message: this.word,
        rid: this.tempAccepter.id,
        sendAll: false,
        sid: this.userId
      }).then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg)
        } else {
          this.$message.error('发送失败')
        }
      })
    },
    getMessages (id) {
      getMessage({
        rid: id,
        sendAll: false
      }).then(res => {
        if (res.code === 200) {
          this.list = res.data
        }
      })
    }
  }
}
</script>
<style scoped>
.info {
  margin: 10px 30px 0 30px;
  background-color: white;
}
.header {
  height: 40px;
  background-color: #409eff;
  display: flex;
  justify-content: center;
  align-items: center;
}
.title {
  color: white;
  font-size: 15px;
}
.main {
  margin: 10px;
}
.accept {
  display: flex;
  align-items: center;
  font-size: 15px;
  margin: 10px 0;
}
.send {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 15px;
  margin: 10px 0;
}
.sendbox {
  margin: 10px 0;
}
.inputword {
  height: 40px;
}
.sendbox {
  display: flex;
}
.comment-input {
  margin-right: 10px;
}
.sub-btn {
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>