<template>
  <div class="resource-detail"
       style="margin-top: 65px">
    <div class="intruducation">
      <div class="left">
        <p class="title">{{resource.name}}</p>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="resource-time">创建时间：{{resource.time}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="resource-isowner">管理员：{{resource.isowner}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="resource-text">介绍：{{resource.intruducation}}</p>
        </div>
      </div>
    </div>
    <div class="comment">
      <div class="comment-title">评论</div>
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
            :key="comment.cid">
          <li class="comment-item">
            <div class="comment-img">
              <i class="el-icon-info info-icon"></i>
            </div>
            <div class="comment-content">
              <ul>
                <li class="name">{{comment.publisher}}</li>
                <li class="time">{{comment.time}}</li>
                <li class="content">{{comment.content}}</li>
              </ul>
            </div>
            <div class="likes"
                 ref="likes"
                 @click="postUp(comment.cid,comment.likes,index)">
              <img src="../assets/zan.png"
                   alt="zan"
                   class="zan">
              <p>{{comment.likes}}</p>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getAllComments, sendComment, sendLike, deleteLike } from '../api/Index'

export default {
  name: 'ResourceDetail',
  data () {
    return {
      pageSize: 5,
      currentPage: 1,
      rid: 0,
      resource: {},
      comments: [],
      commentText: '',
    }
  },
  computed: {
    ...mapGetters([
      'tempResourceList',
      'userId'
    ])
  },
  mounted () {
    this.rid = this.tempResourceList.rid
    this.resource = this.tempResourceList
    console.log(this.resource)
    this.comments = this.tempResourceList.comments
    // this.getComments(this.rid)
  },
  methods: {
    handleCurrentChange (val) {
      this.currentPage = val
    },
    getComments (id) {
      getAllComments(id).then(res => {
        if (res.code === 200) {
          this.comments = res.data
        } else {
          console.log(res.msg)
        }
      })
    },
    postComment () {
      sendComment({
        publisher: this.userName,
        time: this.getDate('y-m-d h:i:s', new Date()),
        content: this.commentText,
        likes: 0
      }).then(res => {
        if (res.code === 200) {
          this.commentText = ''
          this.getComments()
          this.$message('评论成功')
        } else {
          this.$message('评论失败')
        }
      }).catch(err => {
        console.log(err)
      })
    },
    getDate (formatStr, fdate) {
      var fTime, fStr = 'ymdhis';
      if (!formatStr)
        formatStr = "y-m-d h:i:s";
      if (fdate)
        fTime = new Date(fdate);
      else
        fTime = new Date();
      var formatArr = [
        fTime.getFullYear().toString(),
        (fTime.getMonth() + 1).toString(),
        fTime.getDate().toString(),
        fTime.getHours().toString(),
        fTime.getMinutes().toString(),
        fTime.getSeconds().toString()
      ];
      for (var i = 0; i < formatArr.length; i++) {
        formatStr = formatStr.replace(fStr.charAt(i), formatArr[i]);
      }
      return formatStr;
    },
    postUp (id, likes, index) {
      console.log(this.$refs.likes[index].children[0].src)
      if (this.$refs.likes[index].children[0].src === 'data:image/png;base64,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') {
        deleteLike({
          cid: id,
          likes: likes - 1
        }).then(res => {
          if (res.code === 200) {
            this.$refs.likes[index].children[0].src = 'data:image/png;base64,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'
            this.getComments()
          }
        }).catch(err => {
          console.log(err)
        })
      } else {
        sendLike({
          cid: id,
          likes: likes + 1
        }).then(res => {
          if (res.code === 200) {
            this.$refs.likes[index].children[0].src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAYAAACtWK6eAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAFiUAABYlAUlSJPAAAAuPSURBVHhe7d17TFfnGQfwR8Uqw7sz4nWiRDEKU/ACTOnM1A7qWtes2koMqWWdl9HauFWdRvuHcZkGtWNt06gwjS6d1aol3hvvxXmZ1NtKiHiZF3QZiop4j/P95fllFPgd4fd7zznPe/h+EuPzvP7L1/d9zjmc0+jpMwQAtWrMfwNALRAQAAsICIAFBATAAgICYAEBAbCAgABYQEAALCAgABYQEAALCAiABTyLZbjy8nIqLS2l8PBw6tGjB6+CLgiIYQ4ePEj5+flUVFTkC0bElCP8L0StNr1KI0eOpFGjRlFMTAyvQigQEENs2rSJli9fTpW/2sIr1mIL36XMzEyKi4vjFQgGAmKAvLw8Wk1vcVd3t3LifSGZOnUqr0B9ISDC7du3jz489yJ3wYk6MIlyc3O5g/rAVSzh1q9fz1Xwzg/PpRF53EC9ICCCnThxgk7H53AXupkzZ3IFdYWACLZ3716u9DgS8yfKydEXuIYAARHsyJH/X8LV5csWWb7LxFA3CIhgFy9e5Eqv7OxsKi4u5g6sICBCVVZWUtO3D3KnV6OMvb6QwPMhIEKVlZVxZY/ixM9o8+bN3EEgCIhQdgdE2bhxI1cQCAIiVEVFBVf2ufjiX2nXrl3cQW0QEKHu37/Plb1wzLKGgAh17949rux14MABunLlCndQHQIilFMBaTf9BB06dIg7qA4BEcqpI5ZSUFDAFVSHgAjl1A6iqIDcvHmTO6gKARGqpKSEK/uF/+YfdOzYMe6gKgREqNOnT3PljDNnznAFVSEgAl27do3uvb6VO2cgILVDQARy44cVAakdAiKQGz+szX5d4HtTCnwfAiKQW/+bOz33mAABEebq1at0+PBh7px16dIlrsAPARFG3ZNo+9633DlLvaURvg8BEcbNu9oISE0IiCA3btxAQIRBQARR4VB3td1y+/ZtrsAPARFk9+7dXLkDO0hNCIgQ6i0jhbFLuHMHAlITAiLEjh07uHJPm3cLuQI/BESAhw8f0vbt27kDSRAQAVQ46vrdD3AWAiKAhOMV1A4BcZn6/se/Bn3MHUiDgLhMx/c/wD4IiIvU7qHz+x+gHwLiIuwe8iEgLtmzZ4+43aPJ2pFcgR8C4oIHDx74PuksTWRkJFfgh4C4QIWjdNTfuJOjY8eOXIEfAuKwwsJCWrFiBXeyICA1ISAOU7uHW78x+DydOnXiCvwQEAetWrWKvhv8CXfyYAepCQFxiHoRw9KlS7mTCQGpCQFxwJ07d3zhkHq08sNVrJoQEAeoL8pef+lz7uTCDlITAmKzdevW0b4ffcidXG2++iVXUBUCYqOTJ0+Knzv8evbsyRVUhYDY5PHjx75wNH/HjM+b9erViyuoCgGxyZIlS+hCSh538kVHR3MFVSEgNsjPz6dtHT7gzgzYQWqHgGi2f/9+mj9/PnfmQEBqh4BopJ6zmjNnDrXOOs4rZsAVrMAQEE3UV2JnzZpFYZMO8Io5cAUrMAREk0mTJtHDN8x8OwmOV4EhIBqkp6dT+SsbuTMPrmAFhoCEKCsri66OXMudmWJjY7mC6hCQEKgbgaa/lSRiwxj8HogFBCRIKhxftXmfO3MlJiZyBbVBQIKwcOFCT4RDQUCsISD1NHfuXNrV6Q/cma9fv35cQW0QkHqYMWMGfdNrAXfmi9z5JuaP50BA6mjq1Kl0vH82d96A49XzISB1kJGRIfplC8Hq27cvVxAIAmLh7t27lJqaSv/+6Spe8Y6ypXEISB0gIAEUFRVRSkoK3R+3jVe8ZejQodStWzfuIBAEpBY7d+6kKYdiqNVv/8kr3hMTE8MVWGn09BmutVBPtZ49e5Y757Rr107LQ3crV66kNY3f5s67ft9xK3Xo0IE7M6m3sHTv3p07e2gJyOXLlyk3N5dOnTpF/037gledp36vISkpicaNG0c9evTg1fpRT+WeH57LHZjgJyVzKTk5mdLS0nhFn5ADUlFRQdOmTRM1yD7JS6HFixf7ztn1hYCYK+bIFMrMzKSEhAReCV3IM8i8efPEXeVp8tZ+WrBgAV2/fp1XoCEoGvIp/e5kgm+G1CWkgJSUlFBh7BLuZKl4Ld/3smhoeP54ZbS2kIQUEOnf91ZvFqmsrOQOGpJFixZReXk5d8ELKSBNmjThSq6ysjKuoCF5kv617/VLoQopIK1ateJKLgSk4dq8eTNXwQspIC1btuRKrkePHnEFDU3Zy+uptLSUu+B4PiDQsCEgABauXbvGVXAQEPA07CAAFsLCwrgKDgICnhbqldaQAhIREUE3PxrAHYA8rgZEadGiBVcA8rgeEByzQLJQ31yPgIBnPVo5jNq3b89dcEIOiAmPm0DDpOO7J5hBwLOioqK4Ch6OWOBZInYQHLFAKhEBwQ4CUmEGAQjgzseDqEuXLtwFDzsIeJKOAV3BDAKepON4pWAHAU9CQAAsiAkIhnSQqE+fPlyFBjMIeM7j3OHaXmqNIxZ4Tv/+/bkKXcgBUR4sT+YKwH1xcXFchU5LQLCLgCSxsbFchQ4BAc/BDgIQQMSGMVovHCEg4Ck6dw8FAQFP0Tl/KAgIeAoCAhDAjWU/1noPRNESENxNBwl07x6KloDgeSyQQPeAruCIBZ4hdgdBQEACsQHBDAJue5KXQl27duVOH+wg4Al2zB8KhnTwBDuOVwqOWOAJoneQxo0b073PErkDcJ7oHUTBHAJuabnxFduO+doCgjkE3GLX8UrRFhDMIeAWu45XCo5YYLyBAwdypR8CAmZbPYJ69+7NjX6YQcBoSUlJXNkDMwgYLTnZ3ldO4YgFRjNmB0FAwGk/3Pp6yJ95fh4cscBYdh+vFAzpYCyjAoIjFjjp5kcDKCEhgTv7ICBgpMTERAoLC+POPphBwEhOHK8UzCBgpKFDh3JlL20Badq0KVV8Mpg7APs0XvMzbZ9Yex5tAVFwzAInOHW8UrQGBIM6OEEN6E7RGhDMIeAEJy7v+mEHAaN03PEGRUZGcmc/zCBgFLsfTqwOOwgYZdCgQVw5AzMIGONWTjzFx8dz5wzsIGAMdXk3PDycO2dgBgFjDBkyhCvnYAcBYzh9vFIQEDDCC5+/pP37g3WBgIARnLx7XhUCAkaw8+VwVhAQMIKTj5dUhYCAeJ2/Tqdu3bpx5yytAWnWrBnd/os7SQfvcmv3ULQGRMEuArq5cXnXDwEB0dRvqXoqILibDjoNHjzY1Z8p7QHBA4ugk5vzh4IjFojm5vFKQUBALPV4iVs3CP0wg4BYbh+vFMwgIJbbxysFRywQqfzPA117QLEqHLFApBEjRlDXrl25cw92EBBJBUQCBATECf8ijUaPHs2duzCkgzipqam+B18lwAxSTefOnbkCt6iASIEjVjU9e/bkCtww+LsPKDo6mjv3aQ+Iem+RukRnKglXThoySbuHoj0gislziLp7qx5xAOdF7nxTzNUrP1sCYvIcoj5Mn56ezh04aeLEiVzJYUtATJ9DJkyYQB22jeMOnDDg5PuUlpbGnRy2BKRt27ZcmUl9XnjZsmUUdWASr4DdMjIyuJLFloB4YdBVl3tzcnIo/vQMXgG7vFaRQwMGDOBOFlsC4tQXSO0WERFB2dnZNPFprtFX5iRTg3lWVhZ38jR6+gzXWo0fP57+8/O/c+eOFl/+glavXq3lyHf8+HFau3YtHe+fzSsQqgfLk6mgoIA7mWzZQZTp06fTk7wU7twxefJkbfOQ+t0EtZt8mlREr95a5gsfBC/u2/fEh0OxbQdRLly4QGvWrKEtW7ZQ83cO8ar9IjaMobFjx1JmZiav2KOoqIiOHj1KxcXFdP78ed+fFzK/4X+F2vxg/cs0bNgwmj17Nq/IZmtAqlI/SHZr3rw5RUVFuXqjsqysjM6dO8cdVNW6dWvq3bs3d2ZwLCAAJrJtBgHwAgQEwAICAmABAQGwgIAAWEBAACwgIAAWEBAACwgIgAUEBMACAgIQENH/AFDH1jEJ7homAAAAAElFTkSuQmCC'
            this.getComments()
          }
        }).catch(err => {
          console.log(err)
        })
      }
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

.comment {
  background-color: white;
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
  border-bottom: solid 1px rgba(0, 0, 0, 0.1);
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
}
.name {
  font-weight: bold;
  font-size: 15px;
}
.time {
  margin-top: 5px;
  margin-bottom: 10px;
  font-size: 5px;
  color: rgba(0, 0, 0, 0.5);
}
.likes {
  position: absolute;
  right: 10px;
  font-size: 15px;
}
.zan {
  width: 20px;
  height: 20px;
  overflow: hidden;
  position: relative;
}
.likes p {
  height: 20px;
  line-height: 20px;
}
</style>