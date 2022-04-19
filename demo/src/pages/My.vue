<template>
  <div style="margin-top: 65px"
       class="my">
    <div class="title">
      <div class="info">
        <i class="el-icon-info"
           style="margin-right:10px;font-size:80px"></i>
        <div class="intruducation">
          <p style="font-weight: bold">akashi</p>
          <p style="font-size: 15px;margin-top:10px">Email：3391857675@qq.com</p>
        </div>
      </div>
    </div>
    <div class="bottom">
      <el-tabs :stretch="true">
        <el-tab-pane label="我的团队">
          <div class="myTeam">
            <div class="team"
                 style="margin-right: 5px;">
              <ul class="list">
                <li v-for="myTeam in myTeams.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="myTeam.tid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="team-name">{{myTeam.name}}</div>
                  </div>
                  <div class="list-center">
                    {{myTeam.time}}
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="goTeam(myTeam)">查看</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="quitTeam(myTeam)">退出</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="myTeams.length">
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的项目">
          <div class="resource">
            <div class="resource-manage">
              <ul class="list">
                <li v-for="myProject in myProjects.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    :key="myProject.pid"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="resource-name">{{myProject.name}}</div>
                  </div>
                  <div class="list-center">
                    {{myProject.time}}
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small"
                               @click="goProject(myProject)">查看</el-button>
                    <el-button type="primary"
                               size="small"
                               @click="quitProject(myProject)">退出</el-button>
                  </div>
                </li>
              </ul>
              <el-pagination class="bottom-bottom"
                             background
                             @current-change="handleCurrentChange"
                             layout="total, prev, pager, next"
                             :current-page="currentPage"
                             :page-size="pageSize"
                             :total="myProjects.length">
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
import { deleteProjectFromList, deleteTeamFromList, getMyTeams, getMyProjects, quitMyProject, quitMyTeam } from '../api/Index';
export default {
  name: "My",
  data () {
    return {
      pageSize: 5,
      currentPage: 1,
      myTeams: [{
        tempMembers: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }],
        tid: 1,
        resources: [{
          rid: 1, name: "Meiko", time: "2022.2.17", isowner: "Scout", intruducation: "gg",
          comments: [{ cid: 1, publisher: "Scout", up: 10, time: "2022.4.11", content: "优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯" },
          { cid: 2, publisher: "Scout", up: 10, time: "2022.4.11", content: "优点单纯缺点单纯" }]
        },
        { rid: 2, name: "Scout", time: "2022.2.17" }, { rid: 3, name: "Jiejie", time: "2022.2.17" }, { rid: 4, name: "Flandre", time: "2022.2.17" }, { rid: 5, name: "Viper", time: "2022.2.17" }],
        projects: [{
          pid: 1,
          pic: require("../assets/5.jpg"),
          tempMembers: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }],
          name: "Meiko",
          isowner: "Meiko",
          time: "2022.2.17",
          intruducation: "wgg",
          resources: [{
            rid: 6, name: "Meikovo", time: "2022.2.17", isowner: "Scout", intruducation: "gg",
            comments: [{ cid: 1, publisher: "Scout", up: 10, time: "2022.4.11", content: "优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯" },
            { cid: 2, publisher: "Scout", up: 10, time: "2022.4.11", content: "优点单纯缺点单纯" }]
          },
          { rid: 2, name: "Scout", time: "2022.2.17" }, { rid: 3, name: "Jiejie", time: "2022.2.17" }, { rid: 4, name: "Flandre", time: "2022.2.17" }, { rid: 5, name: "Viper", time: "2022.2.17" }],
          members: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }, { uid: 3, name: "Jiejie" }, { uid: 4, name: "Flandre" }, { uid: 5, name: "Viper" }]
        },
        { pid: 2, name: "Scout", time: "2022.2.17" },
        { pid: 3, name: "Jiejie", time: "2022.2.17" },
        { pid: 4, name: "Flandre", time: "2022.2.17" },
        { pid: 5, name: "Viper", time: "2022.2.17" }],
        name: "Meiko",
        pic: require("../assets/5.jpg"),
        time: "2022.2.17",
        isowner: "Meiko",
        members: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }, { uid: 3, name: "Jiejie" }, { uid: 4, name: "Flandre" }, { uid: 5, name: "Viper" }],
        intruducation: "EDG战队呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪"
      },
      { tid: 2, projects: [{ pid: 1, name: "Meiko" }, { pid: 2, name: "Scout" }, { pid: 3, name: "Jiejie" }, { pid: 4, name: "Flandre" }, { pid: 5, name: "Viper" }], name: "Scout", pic: require("../assets/5.jpg"), time: "2022.2.17", isowner: "Meiko", members: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }, { uid: 3, name: "Jiejie" }, { uid: 4, name: "Flandre" }, { uid: 5, name: "Viper" }], intruducation: "EDG战队" },
      { tid: 3, projects: [{ pid: 1, name: "Meiko" }, { pid: 2, name: "Scout" }, { pid: 3, name: "Jiejie" }, { pid: 4, name: "Flandre" }, { pid: 5, name: "Viper" }], name: "Flandre", pic: require("../assets/5.jpg"), time: "2022.2.17", isowner: "Meiko", members: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }, { uid: 3, name: "Jiejie" }, { uid: 4, name: "Flandre" }, { uid: 5, name: "Viper" }], intruducation: "EDG战队" },],
      myProjects: [{
        pid: 1,
        pic: require("../assets/5.jpg"),
        tempMembers: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }],
        name: "Meiko",
        isowner: "Meiko",
        time: "2022.2.17",
        intruducation: "wgg",
        resources: [{
          rid: 6, name: "Meikovo", time: "2022.2.17", isowner: "Scout", intruducation: "gg",
          comments: [{ cid: 1, publisher: "Scout", up: 10, time: "2022.4.11", content: "优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯优点单纯缺点单纯" },
          { cid: 2, publisher: "Scout", up: 10, time: "2022.4.11", content: "优点单纯缺点单纯" }]
        },
        { rid: 2, name: "Scout", time: "2022.2.17" }, { rid: 3, name: "Jiejie", time: "2022.2.17" }, { rid: 4, name: "Flandre", time: "2022.2.17" }, { rid: 5, name: "Viper", time: "2022.2.17" }],
        members: [{ uid: 1, name: "Meiko" }, { uid: 2, name: "Scout" }, { uid: 3, name: "Jiejie" }, { uid: 4, name: "Flandre" }, { uid: 5, name: "Viper" }]
      },
      { pid: 2, name: "Scout", time: "2022.2.17" },]
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'userName',
      'tempTeamList'
    ])
  },
  mounted () {
    // this.myTeams = this.getMyTeams(this.userId)
    // this.myProjects = this.getMyProjects(this.userId)
  },
  methods: {
    handleCurrentChange (val) {
      this.currentPage = val
    },
    // getMyTeams (uid) {
    //   getMyTeams({
    //     uid: uid
    //   }).then(res => {
    //     if (res.code === 200) {
    //       this.myTeams = res.data
    //     }
    //   })
    // },
    // getMyProjects (uid) {
    //   getMyProjects({
    //     uid: uid
    //   }).then(res => {
    //     if (res.code === 200) {
    //       this.myProjects = res.data
    //     }
    //   })
    // },
    goTeam (team) {
      this.$store.commit('setTempTeamList', team);
      this.$router.push({ path: `/TeamDetail/${team.tid}` });
    },
    quitTeam (team) {
      var _this = this
      deleteTeamFromList({
        tid: team.tid,
        uid: _this.userId
      }).then(res => {
        if (res === 200) {
          quitMyTeam({
            tid: team.tid,
            uid: _this.userId
          }).then(res => {
            if (res.code === 200) {
              _this.getMyTeams(_this.userId)
              _this.$message('已退出团队')
            } else {
              _this.$message('退出失败')
            }
          })
        }
      })
    },
    goProject (project) {
      this.$store.commit('setTempProjectList', project)
      this.$router.push({ path: `/ProjectDetail/${project.pid}` });
    },
    quitProject (project) {
      var _this = this
      deleteProjectFromList({
        pid: project.pid,
        uid: _this.userId
      }).then(res => {
        if (res === 200) {
          quitMyProject({
            pid: project.pid,
            uid: _this.userId
          }).then(res => {
            if (res.code === 200) {
              _this.getMyProjects(_this.userId)
              _this.$message('已退出项目')
            } else {
              _this.$message('退出失败')
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.my {
  margin: 0 30px;
}
.title {
  position: relative;
  background-image: url('../assets/7.jpg');
  background-size: 100%;
  background-repeat: no-repeat;
  height: 220px;
  width: 100%;
}
.info {
  display: flex;
  align-items: center;
  position: absolute;
  left: 10px;
  bottom: 10px;
  font-size: 30px;
  color: white;
}
.intruducation {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.team,
.resource-manage {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  height: 330px;
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