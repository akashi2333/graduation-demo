<template>
  <div class="search"
       style="margin-top: 65px">
    <el-card class="box-card"
             v-show="isExsited">
      <div slot="header"
           class="clearfix">
        <span>搜索结果</span>
      </div>
      <div class="text item">
        <div class="image"><img :src="'data:image/png;base64,'+team.img"
               alt=""></div>
        <div class="team-name">{{team.name}}</div>
        <div>{{team.timestamp}}</div>
        <i class="el-icon-circle-plus"
           style="font-size: 30px; color: #409EFF"
           @click="join(team)"></i>
      </div>
    </el-card>
    <el-card class="box-card"
             v-show="!isExsited">
      <div class="no-exsited">
        <span>未找到你要搜索的团队</span>
        <i class="el-icon-document-delete icon"></i>
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { joinTeam, searchTeamById } from '../api/Index'
export default {
  name: 'Search',
  data () {
    return {
      team: {},
      isExsited: false
    }
  },
  computed: {
    ...mapGetters([
      'searchWord',
      'userId',
      'loginState'
    ])
  },
  mounted () {
    this.searchTeam(this.searchWord)
  },
  methods: {
    searchTeam (teamId) {
      searchTeamById({ tid: teamId }).then(res => {
        if (res.code === 200) {
          console.log(res.data)
          this.team = res.data[0]
          this.isExsited = true
        } else {
          this.isExsited = false
        }
      })
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
    }
  }
}
</script>

<style scoped>
.box-card {
  margin: 0 30px;
  height: 300px;
}
.item {
  display: flex;
  font-size: 15px;
  align-items: center;
  justify-content: space-between;
}
.image {
  width: 20%;
  height: 200px;
}
.image img {
  width: 100%;
  height: inherit;
}
.no-exsited {
  display: flex;
  flex-direction: column;
  color: gray;
  font-size: 20px;
  text-align: center;
}
.icon {
  margin-top: 20px;
  font-size: 200px;
}
</style>