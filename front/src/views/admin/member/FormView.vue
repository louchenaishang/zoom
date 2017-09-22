<template>
  <el-form class="form" ref="form" label-position="top"
           :model="form"
           :rules="formRules">
    <el-form-item label="会员名称" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="累计消费" prop="amount">
      <el-input v-model.number="form.price"></el-input>
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
          name: '',
          amount: '',
        },
        formRules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
          ],
          amount: [
            {required: true, message: '请输入价格'},
            {type: 'number', message: '必须为数字值'}
          ]
        },
        btnLoading: false
      }
    },
    methods: {
      getDetail(){
        let {id, type} = this.$route.query
        if(type==='edit'){
          Api.getMember({id: id}).then((res) => {
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
                Api.addMember(para).then((res) => {
                  this.btnLoading = false
                  this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  this.$router.push({path: '/admin/member/list'})
                }).catch((error) => {
                  this.btnLoading = false
                })
              }else{
                Api.editMember(para).then((res) => {
                  this.btnLoading = false
                  this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  this.$router.push({path: '/admin/member/list'})
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
