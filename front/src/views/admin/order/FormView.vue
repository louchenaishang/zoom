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
      <el-input v-model="form.store.name"></el-input>
    </el-form-item>
    <el-form-item label="会员" prop="member">
      <el-input v-model="form.member.name"></el-input>
    </el-form-item>
    <el-form-item>

      <el-table :data="form.orderItems" highlight-current-row :stripe="true" style="width: 100%;">
        <el-table-column width="90" type="index">
        </el-table-column>
        <el-table-column prop="sku.name" label="SKU" sortable>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" sortable>
        </el-table-column>
        <el-table-column prop="amount" label="金额" sortable>
        </el-table-column>
      </el-table>

      <el-button type="default" @click="back()" :loading="btnLoading">返回</el-button>
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
          store: {},
          member: {},
          orderItems: [],
        },
        formRules: {

        },
        btnLoading: false
      }
    },
    methods: {
      getDetail(){
        let {id, type} = this.$route.query
        if(type==='edit'||type==='view'){
          Api.getOrder({id: id}).then((res) => {
            if(res.data!=''){
              this.form = res.data
            }
          })
        }
      },
      back(){
        this.$router.push({path: '/admin/order/list'})
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
