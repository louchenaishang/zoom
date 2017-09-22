<template>
  <el-form class="form" ref="form" label-position="top"
           :model="form"
           :rules="formRules">
    <el-form-item label="订单号" prop="sn">
      <el-input v-model="form.sn"></el-input>
    </el-form-item>
    <el-form-item label="订单金额" prop="amount">
      <el-input v-model.number="form.amount"></el-input>
    </el-form-item>
    <el-form-item label="店铺" prop="store">
      <el-input v-model="form.store"></el-input>
    </el-form-item>
    <el-form-item label="会员" prop="member">
      <el-input v-model="form.member"></el-input>
    </el-form-item>
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

      return {
        form: {
          id: '',
          sn: '',
          amount: '',
          store: '',
          member: '',
        },
        formRules: {

        },
        btnLoading: false
      }
    },
    methods: {
      getDetail(){
        let {id, type} = this.$route.query
        if(type==='edit'){
          Api.getOrder({id: id}).then((res) => {
            if(res.data!=''){
              this.form = res.data
            }
          })
        }
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.btnLoading = true
              let para = this.form
              if(para.id===''){
                Api.addOrder(para).then((res) => {
                  this.btnLoading = false
                  this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  this.$router.push({path: '/admin/sku/list'})
                }).catch((error) => {
                  this.btnLoading = false
                })
              }else{
                Api.editOrder(para).then((res) => {
                  this.btnLoading = false
                  this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  this.$router.push({path: '/admin/sku/list'})
                }).catch((error) => {
                  this.btnLoading = false
                })
              }
            })
          }
        });
      },
    },
    mounted() {
      this.getDetail()
    }
  }
</script>

<style>
  .form {
    margin-top: 10px;
    width: 100%;
    min-width: 600px;
  }
</style>
