<template>
  <div id="app">
    <h1>{{ restData }}</h1>
    <h2>{{ countInVuex }}</h2>
    <h2>{{ totalPrice | currency }}</h2>
    <h1 @click="increment">{{ msg }}</h1>
    <br/>
    <button @click="addToCart({id:'001',name:'sku1',price:10})">增加Sku1($10)到购物车</button>
    <button @click="addToCart({id:'002',name:'sku2',price:20})">增加Sku2($20)到购物车</button>
    <button @click="getData1">getData1</button>
    <button @click="getData2">getData2</button>
    <br/>
    <h1 v-for="item in list">{{ item.name }}</h1>
    <hello message1="计数器+1,async"></hello>
    <br/>
    <router-link to="/a">Go to PageA</router-link>
    <router-link to="/b">Go to PageB</router-link>
    <br/>
    <router-view></router-view>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import Hello from './components/Hello.vue'


  export default {
    name: 'app',
    data: function () {
      return {
        msg: '计数器+1,sync',
        list: [{name: 'eric'}, {name: 'miumiu'}],
        restData: {}
      }
    },
    components: {
      Hello
    },
    mounted(){
      this.getData2()
    },
    computed: {
      ...mapGetters([
        'countInVuex',
        'totalPrice'
      ]),
      countInAppVueComputed (){
        return 0
      },
      uuid(){
        return "uuid"
      }
    },
    methods: {
      ...mapActions([
        'increment',
        'decrement',
        'addToCart'
      ]),
      getData1(){
        this.$http.get('http://jsonplaceholder.typicode.com/users').then(response => {
          // success callback
          console.log(response.data)
          return response.data
        }, response => {
          // error callback
          console.log('error callback')
        });
      },
      getData2(){
        this.$http.get('http://120.26.218.22:8092/op_rest/userManage/loadCountsOfUserType', {
          id: 1,
          name: 'test'
        }).then(response => {
          // success callback
          this.restData = response.data
        }, response => {
          // error callback
          console.log('error callback')

        });
      }
    }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
