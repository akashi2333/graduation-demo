<template>
  <div class="team-detail"
       style="margin-top: 65px">
    <div class="intruducation">
      <div class="image">
        <img :src="team.pic"
             alt=""
             class="img">
      </div>
      <div class="left">
        <p class="title">{{team.name}}</p>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="team-time">创建时间：{{team.time}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="team-manager">管理员：{{team.manager}}</p>
        </div>
        <div class="item">
          <i class="el-icon-caret-right"
             style="color:#409EFF; font-size:25px"></i>
          <p class="team-text">介绍：{{team.intruducation}}</p>
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
                <li v-for="member in members"
                    :key="member.id"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="member-name">{{member.name}}</div>
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small">删除</el-button>
                  </div>
                </li>
              </ul>
            </div>
            <div class="team-member">
              <div class="team-member-top">申请人员</div>
              <ul class="list">
                <li v-for="member in members"
                    :key="member.id"
                    class="list-item">
                  <div class="list-left">
                    <i class="el-icon-caret-right"
                       style="color:#409EFF; font-size:25px"></i>
                    <div class="member-name">{{member.name}}</div>
                  </div>
                  <div class="list-right">
                    <el-button type="primary"
                               size="small">同意</el-button>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="项目管理">
          <div class="project">
            <div class="project-manage">
              <div class="project-manage-top">团队项目</div>
              <ul class="list">
                <li v-for="project in projects"
                    :key="project.id"
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
                               size="small">编辑</el-button>
                    <el-button type="primary"
                               size="small">删除</el-button>
                    <el-button type="primary"
                               size="small">归档</el-button>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="团队资源管理">
          <div class="resource">
            <div class="resource-manage">
              <div class="resource-manage-top">团队资源</div>
              <ul class="list">
                <li v-for="resource in resources"
                    :key="resource.id"
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
                               size="small">编辑</el-button>
                    <el-button type="primary"
                               size="small">删除</el-button>
                  </div>
                </li>
              </ul>
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

export default {
  name: 'TeamDetail',
  data () {
    return {
      id: 0,
      team: {},
      members: {},
      projects: {},
      resources: {},
    }
  },
  computed: {
    ...mapGetters([
      'tempTeamList'
    ])
  },
  mounted () {
    this.id = this.$route.params.id;
    this.team = this.tempTeamList;
    this.members = this.tempTeamList.members;
    this.projects = this.tempTeamList.projects;
    this.resources = this.tempTeamList.resources;
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

.member {
  display: flex;
}
.team-member {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 50%;
}
.project-manage,
.resource-manage {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
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
</style>