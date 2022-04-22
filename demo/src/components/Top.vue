<template>
  <div class="nav">
    <el-menu :default-active="activeIndex"
             class="el-menu-demo"
             mode="horizontal"
             @select="handleSelect"
             text-color="black"
             active-text-color="#409EFF">
      <div class="nav-left">
        <el-menu-item class="demo-icon">
          <img src="../assets/demo-icon.png"
               alt="">
        </el-menu-item>
        <el-menu-item :index="nav.name"
                      v-for="nav in navs"
                      :key="nav.name"
                      @click="goToNav(nav.name, nav.path)">{{nav.name}}</el-menu-item>
      </div>
      <div class="nav-right"
           v-show="!loginState">
        <el-menu-item @click="goToNav(item.name, item.path)"
                      v-for="item in loginMsg"
                      :key="item.name"
                      :index="item.name">{{item.name}}</el-menu-item>
      </div>
      <div class="nav-right"
           v-show="loginState">
        <el-menu-item><i class="el-icon-info"
             style="font-size: 30px; color: #409EFF"></i></el-menu-item>
        <el-menu-item @click="quit()">退出</el-menu-item>
      </div>
    </el-menu>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  name: "Top",
  data () {
    return {
      activeIndex: '首页',
      navs: [
        { name: "首页", path: "/" },
        { name: "全部团队", path: "/Team" },
        { name: "消息中心", path: "/Info" },
        { name: "个人空间", path: "/My" }
      ],
      loginMsg: [
        { name: "注册", path: "/register" },
        { name: "登录", path: "/login" }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'loginState'
    ])
  },
  methods: {
    handleSelect (key, keyPath) {
      console.log(key, keyPath);
    },
    goToNav (name, path) {
      if (!this.loginState && path === '/My') {
        this.$message.error("请先登录")
      } else if (!this.loginState && path === '/Info') {
        this.$message.error("请先登录")
      } else {
        this.$router.push({ path: path })
      }
    },
    quit () {
      this.$store.commit('setLoginState', false)
      this.$router.push({ path: '/register' })
    }
  },
}
</script>

<style scoped>
.nav {
  position: fixed;
  width: 100%;
  z-index: 1000;
  top: 0;
  left: 0;
}
.el-menu-demo {
  border: 0;
  padding: 0 30px;
  align-items: center;
  display: flex;
  justify-content: space-between;
  font-size: 15px;
}
.el-menu-demo::before,
.el-menu-demo::after {
  content: none;
}
.nav-left {
  display: flex;
}
.nav-right {
  display: flex;
}
.demo-icon {
  height: 50px;
  padding-right: 20px;
}
.demo-icon img {
  width: 100%;
  height: inherit;
}
</style>