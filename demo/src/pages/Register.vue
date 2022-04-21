<template>
  <div style="margin-top: 65px"
       class="register">
    <div class="logo"> <img src="../assets/demo-icon.png"
           alt=""
           class="icon"></div>
    <div class="left">
      <div class="left-top">
        <span>注册</span>
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
        <el-form-item label="确认密码"
                      prop="checkPassword">
          <el-input type="password"
                    v-model="ruleForm.checkPassword"
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
import { register } from '../api/Index'
export default {
  name: "Register",
  data () {
    var checkUserName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('用户名不能为空'));
      }
      callback();
    };

    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        password: '',
        checkPassword: '',
        userName: '',
        email: ''
      },
      rules: {
        userName: [
          { required: true, validator: checkUserName, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        var _this = this
        if (valid) {
          register({
            username: _this.ruleForm.userName,
            email: _this.ruleForm.email,
            password: _this.ruleForm.password
          }).then(res => {
            if (res.code === 200) {
              _this.$message('注册成功')
              setTimeout(function () {
                _this.$router.push({ path: '/' })
              }, 2000)
            } else {
              _this.$message(res.msg)
            }
          })
        } else {
          console.log('error!!')
          return false;
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
.register {
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