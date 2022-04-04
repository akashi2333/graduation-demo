<template>
  <div class="home"
       style="margin-top: 65px">
    <div class="img">
      <el-carousel height="300px"
                   style="width:100%">
        <el-carousel-item v-for="item in imgs"
                          :key="item.url">
          <img class="image"
               :src="item.url" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="bottom">
      <div class="notice">
        <div class="notice-top">公告栏</div>
        <ul class="list">
          <li class="list-item"
              v-for="notice in notices"
              :key="notice.name">
            <div class="notice-left">
              <i class="el-icon-caret-right"
                 style="color:#409EFF; font-size:25px"></i>
              <div class="notice-name">{{notice.name}}</div>
            </div>
            <div class="notice-time">{{notice.time}}</div>
            <div class="notice-publisher">{{notice.publisher}}</div>
          </li>
        </ul>
      </div>
      <div class="resource">
        <div class="resource-top">常用资源</div>
        <ul class="list">
          <li class="list-item"
              v-for="resource in resources"
              :key="resource.name">
            <div class="resource-left">
              <i class="el-icon-caret-right"
                 style="color:#409EFF; font-size:25px"></i>
              <div class="resource-name">{{resource.name}}</div>
            </div>
          </li>
        </ul>
      </div>
      <div class="team">
        <div class="team-top">优秀团队</div>
        <ul class="list">
          <li class="list-item"
              v-for="team in teams"
              :key="team.name">
            <div class="team-left">
              <i class="el-icon-caret-right"
                 style="color:#409EFF; font-size:25px"></i>
              <div class="team-name">{{team.name}}</div>
            </div>
          </li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {
      imgs: [
        { url: require("../assets/1.jpg") },
        { url: require("../assets/2.jpg") },
        { url: require("../assets/3.jpg") },
        { url: require("../assets/4.jpg") }
      ],
      teams: [
        { name: "Meiko" },
        { name: "Scout" },
        { name: "Viper" },
        { name: "Jiejie" },
        { name: "Flandre" },
        { name: "Xiaoxiang" },
        { name: "Junjia" },
        { name: "Hope" },
        { name: "Clearlove" },
        { name: "Deft" }
      ],
      notices: [
        { name: "EDG2020年LPL春季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2020年LPL夏季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2021年LPL春季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2021年LPL夏季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2022年LPL春季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2022年LPL夏季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2023年LPL春季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2023年LPL夏季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2024年LPL春季赛合集", time: "2020-4-5", publisher: "akashi" },
        { name: "EDG2024年LPL夏季赛合集", time: "2020-4-5", publisher: "akashi" },
      ],
      resources: [
        { name: "EDG-Meiko精彩操作合集" },
        { name: "EDG-Scout精彩操作合集" },
        { name: "EDG-Flandre精彩操作合集" },
        { name: "EDG-Jiejie精彩操作合集" },
        { name: "EDG-Viper精彩操作合集" },
        { name: "EDG-Xiaoxiang精彩操作合集" },
        { name: "EDG-Junjia精彩操作合集" },
        { name: "EDG-Hope精彩操作合集" },
        { name: "EDG-Clearlove精彩操作合集" },
        { name: "EDG-Deft精彩操作合集" }
      ]
    };
  },
  methods: {

  },
  mounted () {
  }
}
</script>

<style scoped>
.img {
  margin: 0 30px;
  margin-top: 10px;
  background-color: white;
  display: flex;
}
.image {
  width: 100%;
  height: inherit;
}

.bottom {
  margin: 10px 30px;
  display: flex;
}
.notice,
.resource,
.team {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 50%;
  background-color: white;
}
.notice-top,
.resource-top,
.team-top {
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
.notice-left,
.resource-left,
.team-left {
  display: flex;
  justify-content: center;
  align-items: center;
}
.resource,
.team {
  margin-left: 10px;
  width: 25%;
}
</style>