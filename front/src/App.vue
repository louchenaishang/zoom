<template>
  <div id="app">
    <transition name="fade">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>


  export default {
    name: 'app',
    data: function () {
      return {}
    },
    components: {},
    mounted(){

    },
    computed: {},
    methods: {}
  }
</script>

<style>
  @import "../node_modules/font-awesome/css/font-awesome.min.css";
  @import "../node_modules/flex.css/dist/flex.css";

  body {
    /*background-color: #324057;*/
    margin: 0px;
    padding: 0px;
    /*background: url(assets/bg1.jpg) center !important;
        background-size: cover;*/
    background: #1F2D3D;
    font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
    /*font-weight: 400;*/
    font-size: 14px;
    -webkit-font-smoothing: antialiased;
  }

  #app {
    position: absolute;
    top: 0px;
    bottom: 0px;
    width: 100%;
  }

  .el-submenu [class^=fa] {
    vertical-align: baseline;
    margin-right: 10px;
  }

  .el-menu-item [class^=fa] {
    vertical-align: baseline;
    margin-right: 10px;
  }

  .toolbar .el-form-item {
    margin-bottom: 10px;
  }

  .toolbar {
    background: #fff;
    padding: 10px 10px 0px 10px;
  }
</style>
