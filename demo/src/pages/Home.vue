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
      <div class="intruducation"
           style="margin-left:10px">
        <div class="title">介绍</div>
        <div class="content">
          <div class="teamwork">
            <img src="../assets/teamwork.svg"
                 alt=""
                 class="teamwork-img">
            <p class="text">团队协同：在线多人协同办公，工作内容自动保存、实时同步，随时随地高效协作。</p>
          </div>
          <div class="teamwork">
            <img src="../assets/team-management.svg"
                 alt=""
                 class="teamwork-img">
            <p class="text">团队管理：构建团队公共空间知识库，发布或引导员工查看或导出团队文件：如团队活动、团队项目、学习资料等，促进团队重要信息传达，并获得及时反馈。
            </p>
          </div>
          <div class="teamwork">
            <img src="../assets/rd-document.svg"
                 alt=""
                 class="teamwork-img">
            <p class="text">研发文档：进行需求文档、测试用例文档、技术文档等研发文档管理，文档可直接与具体开发或测试工作项双向关联，实时同步最近更新，减少沟通成本，提升研发效率。
            </p>
          </div>
        </div>
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
.teamwork {
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 15px;
}
.teamwork-img {
  width: 30%;
  height: inherit;
}
.text {
  font-size: 15px;
  line-height: 1.5;
}
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

.content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 3%;
  width: 94%;
  height: 100%;
}
.bottom {
  margin: 10px 30px;
  display: flex;
}
.notice,
.intruducation {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 50%;
  background-color: white;
}
.notice-top,
.title {
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
.notice-left {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>