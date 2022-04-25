<template>
  <div style="margin-top: 65px"
       class="login">
    <div class="logo"> <img src="../assets/demo-icon.png"
           alt=""
           class="icon"></div>
    <div class="left">
      <div class="left-top">
        <span>登录</span>
      </div>
      <el-form :model="ruleForm"
               status-icon
               :rules="rules"
               ref="ruleForm"
               label-width="100px"
               class="demo-ruleForm">
        <el-form-item label="用户名"
                      prop="userName">
          <el-input v-model="ruleForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="邮箱"
                      prop="email">
          <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>
        <el-form-item label="密码"
                      prop="password">
          <el-input type="password"
                    v-model="ruleForm.password"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"
                     @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { login } from '../api/Index'
import { mapGetters } from 'vuex'
export default {
  name: "Login",
  data () {
    return {
      ruleForm: {
        password: '',
        userName: '',
        email: ''
      },
      rules: {
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      }
    };
  },
  computed: {
    ...mapGetters([
      'userId',
      'loginState'
    ])
  },
  methods: {
    submitForm () {
      var _this = this
      login({
        username: _this.ruleForm.userName,
        email: _this.ruleForm.email,
        password: _this.ruleForm.password
      }).then(res => {
        if (res.code === 200) {
          _this.$message('登录成功')
          _this.$store.commit('setUserId', res.data)
          _this.$store.commit('setUserEmail', _this.ruleForm.email)
          _this.$store.commit('setUserName', _this.ruleForm.userName)
          _this.$store.commit('setLoginState', true)
          setTimeout(function () {
            _this.$router.push({ path: '/' })
          }, 2000)
        } else {
          _this.$message(res.msg)
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.login {
  margin: 0 30px;
  display: flex;
}
.logo {
  border-radius: 10px;
  background-color: white;
  height: 400px;
  width: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
}
.icon {
  height: 300px;
  width: inherit;
}
.left {
  background-color: #fff;
  border-radius: 10px;
  width: 50%;
  padding: 20px;
}
.left-top {
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: 600;
}
</style>