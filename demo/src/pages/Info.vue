<template>
  <div style="margin-top: 65px"
       class="info">
    <div class="header">
      <div class="title">{{tempAccepter.username}}</div>
    </div>
    <div class="main">
      <div class="talkshow"
           id="gundong">
        <div v-for="(item,index) in list"
             :key="index"
             :class="item.sid === userId? 'send':'accept'">
          <i v-show="item.sid != userId"
             class="el-icon-info"
             style="color: #409eff;font-size:30px;margin-right:10px"></i>
          <span>{{item.message}}</span>
          <i v-show="item.sid == userId"
             class="el-icon-info"
             style="color: #409eff;font-size:30px;margin-left:10px"></i>
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
  updated () {
    let msg = document.getElementById('gundong')
    msg.scrollTop = msg.scrollHeight - msg.clientHeight
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
          this.getMessages(this.userId)
          this.word = ''
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
          this.list = res.data.reverse()
        }
      })
    }
  }
}
</script>
<style scoped>
.send span {
  display: inline-block;
  position: relative;
  padding: 8px;
  max-width: calc(100% - 90px);
  min-height: 20px;
  line-height: 20px;
  font-size: 15px;
  word-break: break-all;
  border-radius: 4px;
  background-color: #409eff;
  color: white;
}
.send span:before {
  content: ' ';
  position: absolute;
  top: 9px;
  left: 100%;
  border: 6px solid transparent;
  border-left-color: #409eff;
}
.accept span {
  display: inline-block;
  position: relative;
  padding: 8px;
  max-width: calc(100% - 90px);
  min-height: 20px;
  line-height: 20px;
  font-size: 15px;
  word-break: break-all;
  border-radius: 4px;
  text-align: left;
  background-color: #f5f5f5;
}
.accept span:before {
  content: ' ';
  position: absolute;
  top: 9px;
  right: 100%;
  border: 6px solid transparent;
  border-right-color: #f5f5f5;
}
.talkshow {
  height: 300px;
  overflow-y: auto;
  overflow-x: hidden;
}
.talkshow::-webkit-scrollbar {
  display: none;
}
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
  padding: 10px;
}
.accept {
  display: flex;
  align-items: center;
  font-size: 15px;
  margin: 20px 0;
}
.send {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 15px;
  margin: 20px 0;
}
.sendbox {
  margin: 10px 0;
  display: flex;
}
.inputword {
  height: 40px;
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