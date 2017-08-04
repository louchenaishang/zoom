<template>
  <el-form ref="form" label-width="80px" class="form">
    <el-form-item label="用户名">
      <template v-if="principal && principal.principal&& principal.principal.username">
        {{ principal.principal.username }}
      </template>
    </el-form-item>
    <el-form-item label="角色">
      <template v-for="(item,index) in roles">
        {{ item.authority }}
        <template v-if="index<principal.authorities.length-1">
          ,
        </template>
      </template>
    </el-form-item>
    <el-form-item label="TOKEN">
        {{ token }}
    </el-form-item>
  </el-form>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import Api from '../api/api'


  export default {
    data() {
      return {
        name: 'welcome',
        title: 'welcome',
        token: ''
      };
    },
    computed: {
      ...mapGetters([
        'principal',
        'username',
        'roles',
        'isAdmin'
      ])
    },
    methods: {
      ...mapActions([
        'addToPincipal'
      ]),
      getPrincipal() {
        Api.getPrincipal({}).then(res => {
          this.addToPincipal(res.data)
        })
      }
    },
    mounted() {
      this.getPrincipal()
      this.token = JSON.parse(sessionStorage.getItem('token'))
    }
  }
</script>

<style scoped>
  .form{
    margin:20px;
    width:90%;
    min-width:600px;
    word-wrap:break-word;
  }
</style>
