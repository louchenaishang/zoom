<template>
  <el-form class="form" ref="form" label-position="top"
           :model="form"
           :rules="formRules">
    <el-form-item label="店铺名称" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="店铺地址" prop="address">
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
          address: '',
        },
        formRules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
          ],
          address: [
            {required: true, message: '请输入名称', trigger: 'blur'},
          ],
        },
        btnLoading: false
      }
    },
    methods: {
      getDetail(){
        let {id, type} = this.$route.query
        if(type==='edit'){
          Api.getStore({id: id}).then((res) => {
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
                Api.addStore(para).then((res) => {
                  this.btnLoading = false
                  this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  this.$router.push({path: '/admin/store/list'})
                }).catch((error) => {
                  this.btnLoading = false
                })
              }else{
                Api.editStore(para).then((res) => {
                  this.btnLoading = false
                  this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  this.$router.push({path: '/admin/store/list'})
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
