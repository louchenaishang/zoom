<template>
  <el-form class="form" ref="form" label-position="top"
           :model="form"
           :rules="formRules">
    <el-tabs>
      <el-tab-pane>
        <span slot="label"><i class="fa fa-desktop"></i> 基本设置</span>
        <el-form-item label="网站名称" prop="siteName">
          <el-input v-model="form.siteName"></el-input>
        </el-form-item>
        <el-form-item label="网站地址" prop="siteUrl">
          <el-input v-model="form.siteUrl"></el-input>
        </el-form-item>
      </el-tab-pane>
      <el-tab-pane>
        <span slot="label"><i class="fa fa-shield"></i> 安全设置</span>
        <el-form-item label="最大登录失败尝试次数" prop="maxFailedLoginAttempts">
          <el-input v-model.number="form.maxFailedLoginAttempts"></el-input>
        </el-form-item>
        <el-form-item label="密码锁定时间" prop="passwordLockTime">
          <el-input v-model.number="form.passwordLockTime"></el-input>
        </el-form-item>
      </el-tab-pane>
    </el-tabs>
    <el-form-item>
      <el-button type="primary" @click="submitForm('form')" :loading="btnLoading">保存</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import util from '@/common/util'
  import Api from '@/api/api'

  export default {
    data() {
      var checkSiteUrl = (rule, value, callback) => {
        let regExp = /^(http:\/\/|https:\/\/).*$/
        if (value === '') {
          callback(new Error('请输入网站地址'));
        }else if(regExp.test(value) === false){
          callback(new Error('网站地址必须是http://或者https://开头'));
        }else {
          callback();
        }
      };

      return {
        form: {
          siteName: '',
          siteUrl: '',
          maxFailedLoginAttempts: '',
          passwordLockTime: ''
        },
        formRules: {
          siteName: [
            {required: true, message: '请输入网站名称', trigger: 'blur'},
            {min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur'}
          ],
          siteUrl: [
            {validator: checkSiteUrl, trigger: 'blur'}
          ],
          maxFailedLoginAttempts: [
            {required: true, message: '请输最大登录失败尝试次数'},
            {type: 'number', message: '必须为数字值'}
          ],
          passwordLockTime: [
            {required: true, message: '请输密码锁定时间'},
            {type: 'number', message: '必须为数字值'}
          ]
        },
        btnLoading: false
      }
    },
    methods: {
      getSystemSetting(){
        Api.getSystemSetting({}).then((res) => {
            if(res.data!=''){
              this.form = res.data
            }
        })
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.btnLoading = true
              let para = this.form
              Api.saveSystemSetting(para).then((res) => {
                this.btnLoading = false
                this.$notify({
                  title: '成功',
                  message: '提交成功',
                  type: 'success'
                })
              }).catch((error) => {
                this.btnLoading = false
              })
            })
          }
        });
      },
    },
    mounted() {
      this.getSystemSetting()
    }
  }
</script>

<style>
  .form {
    width: 100%;
    min-width: 600px;
  }
</style>
