<template>
  <div class="team"
       style="margin-top: 65px">
    <div class="title">
      <div class="search">
        <input type="text"
               placeholder="搜索团队"
               v-model="keywaords">
        <div class="search-btn"
             @click="search()">
          <i class="el-icon-search"
             style="font-size: 20px"></i>
        </div>
      </div>
    </div>
    <div class="team-list">
      <ul class="content">
        <li class="content-item"
            v-for="team in teamList"
            :key="team.tid">
          <img :src="team.pic"
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
  </div>
</template>

<script>
import { joinTeam } from '../api/Index'
import { mapGetters } from 'vuex'
export default {
  name: "Team",
  data () {
    return {
      keywaords: '',
      teamList: [{ tid: "1", resources: [{ rid: "1", name: "Meiko", time: "2022.2.17" }, { rid: "2", name: "Scout", time: "2022.2.17" }, { rid: "3", name: "Jiejie", time: "2022.2.17" }, { rid: "4", name: "Flandre", time: "2022.2.17" }, { rid: "5", name: "Viper", time: "2022.2.17" }], projects: [{ pid: "1", name: "Meiko", time: "2022.2.17" }, { pid: "2", name: "Scout", time: "2022.2.17" }, { pid: "3", name: "Jiejie", time: "2022.2.17" }, { pid: "4", name: "Flandre", time: "2022.2.17" }, { pid: "5", name: "Viper", time: "2022.2.17" }], name: "Meiko", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪" },
      { tid: "2", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Scout", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "3", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Flandre", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "4", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Viper", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "5", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Jiejie", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "6", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Xiaoxiang", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "7", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Junjia", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "8", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Hope", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "9", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Clearlove", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "10", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Deft", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "11", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Pawn", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" },
      { tid: "12", projects: [{ pid: "1", name: "Meiko" }, { pid: "2", name: "Scout" }, { pid: "3", name: "Jiejie" }, { pid: "4", name: "Flandre" }, { pid: "5", name: "Viper" }], name: "Alori", pic: require("../assets/5.jpg"), time: "2022.2.17", manager: "Meiko", members: [{ uid: "1", name: "Meiko" }, { uid: "2", name: "Scout" }, { uid: "3", name: "Jiejie" }, { uid: "4", name: "Flandre" }, { uid: "5", name: "Viper" }], intruducation: "EDG战队" }]
    }
  },
  computed: {
    ...mapGetters([
      'userId'
    ])
  },
  mounted () {
    this.getAllTeams()
  },
  methods: {
    //获取全部团队
    // getAllTeams () {
    //   getTeams().then(res => {
    //       this.teamList = res
    //     }).catch(err => {
    //       console.log(err)
    //     })
    // },
    goTeam (item) {
      this.$store.commit('setTempTeamList', item);
      this.$router.push({ path: `TeamDetail/${item.tid}` });
    },
    join (team) {
      joinTeam({
        tid: team.tid,
        uid: this.userId
      }).then(res => {
        if (res.code === 1) {
          this.notify('申请加入成功', 'success')
        } else {
          this.notify(res.msg, 'error')
        }
      })
    },
    search () {
      this.$store.commit('setSearchWord', this.keywaords)
      this.$router.push({ path: '/Search', query: { keywords: this.keywaords } })
    }
  }
}
</script>

<style scoped>
.team {
  margin: 10px 30px 0 30px;
  background-color: white;
}
.title {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 1%;
  height: 40px;
  background-color: #409eff;
  font-size: 15px;
  color: white;
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