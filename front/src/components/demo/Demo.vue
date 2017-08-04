<template>
  <div>
    <div class="block">
      <a href="http://element.eleme.io/#/zh-CN/component/button" target="_blank">see docs</a>
    </div>

    <div class="block">
      <el-button>默认按钮</el-button>
      <el-button type="primary">主要按钮</el-button>
      <el-button type="text">文字按钮</el-button>
    </div>

    <div class="block">
      <el-button :plain="true" :disabled="true">主要按钮</el-button>
      <el-button type="primary" :disabled="true">主要按钮</el-button>
      <el-button type="text" :disabled="true">文字按钮</el-button>
    </div>

    <div class="block">
      <span class="demonstration">默认显示颜色</span>

      <el-button type="success">成功按钮</el-button>
      <el-button type="warning">警告按钮</el-button>
      <el-button type="danger">危险按钮</el-button>
      <el-button type="info">信息按钮</el-button>

    </div>

    <div class="block">
      <span class="demonstration">hover 显示颜色</span>

      <el-button :plain="true" type="success">成功按钮</el-button>
      <el-button :plain="true" type="warning">警告按钮</el-button>
      <el-button :plain="true" type="danger">危险按钮</el-button>
      <el-button :plain="true" type="info">信息按钮</el-button>

    </div>

    <div class="block">
      <el-button type="primary" icon="edit"></el-button>
      <el-button type="primary" icon="share"></el-button>
      <el-button type="primary" icon="delete"></el-button>
      <el-button type="primary" icon="search">搜索</el-button>
      <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
    </div>

    <div class="block">
      <el-button-group>
        <el-button type="primary" icon="arrow-left">上一页</el-button>
        <el-button type="primary">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>
      </el-button-group>
      <el-button-group>
        <el-button type="primary" icon="edit"></el-button>
        <el-button type="primary" icon="share"></el-button>
        <el-button type="primary" icon="delete"></el-button>
      </el-button-group>
    </div>

    <div class="block">
      <el-button type="primary" :loading="true">加载中</el-button>
      <el-button type="primary" size="large">大型按钮</el-button>
      <el-button type="primary">正常按钮</el-button>
      <el-button type="primary" size="small">小型按钮</el-button>
      <el-button type="primary" size="mini">超小按钮</el-button>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'demo',
    props: {},
    computed: {},
    data () {
      return {}
    },
    methods: {}
  }
</script>

<style>

</style>
