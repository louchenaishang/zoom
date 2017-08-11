<template>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px"
           class="demo-ruleForm card-box loginform">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item prop="captcha">
      <el-col :span="18">
        <el-input type="text" v-model="ruleForm2.captcha" auto-complete="off" placeholder="图形验证码" :maxlength="4"></el-input>
      </el-col>
      <el-col :span="6">
        <captcha :captchaId="ruleForm2.captchaId" scene="login" @click.native.prevent="refreshCaptchaId" class="captcha"></captcha>
      </el-col>
    </el-form-item>
    <el-checkbox v-model="checked" checked style="margin:0px 0px 35px 0px;">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录
      </el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>
  import Api from '../api/api'
  import Captcha from '../components/Captcha'
  import uuid from 'uuid'

  export default {
    components: {
      Captcha
    },
    data() {
      return {
        logining: false,
        ruleForm2: {
          account: '18017233379',
          checkPass: '123456',
          captchaId: '',
          captcha: '',
        },
        rules2: {
          account: [
            {required: true, message: '请输入账号', trigger: 'blur'},
            //{ validator: validaePass }
          ],
          checkPass: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            //{ validator: validaePass2 }
          ]
        },
        checked: true
      };
    },
    methods: {
      refreshCaptchaId(){
        this.ruleForm2.captchaId = '';
        this.ruleForm2.captchaId = uuid.v1().replace(new RegExp('-', "gm"), '');
      },
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            _this.logining = true;
            var loginParams = {
              username: this.ruleForm2.account,
              password: this.ruleForm2.checkPass,
              captchaId: this.ruleForm2.captchaId,
              captcha: this.ruleForm2.captcha
            };
            Api.requestLogin(loginParams).then(res => {
              _this.logining = false;
              _this.$notify.success({
                title: '成功',
                message: '登陆成功'
              });
              let {token} = res.data;
              sessionStorage.setItem('token', JSON.stringify(token));
              sessionStorage.setItem('user', JSON.stringify(token));
              _this.$router.push({path: '/welcome'});
            }).catch(function (error) {
              _this.logining = false;
              _this.refreshCaptchaId();
            });
          } else {
            _this.$notify.error({
              title: '错误',
              message: '填把信息补充完整'
            });
            return false;
          }
        });
      }
    },
    mounted(){
      this.refreshCaptchaId();
    }
  }
</script>

<style scoped>
  .card-box {
    padding: 20px;
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin-bottom: 20px;
    background-color: #F9FAFC;
    margin: 180px auto;
    width: 400px;
    border: 2px solid #8492A6;
  }

  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .loginform {
    width: 350px;
    padding: 35px 35px 15px 35px;
  }

  .captcha{
    float: right;
  }
</style>
