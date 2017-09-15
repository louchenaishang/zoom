import NotFound from '@/views/404View.vue'
import HomeView from '@/views/HomeView.vue'
import WelcomeView from '@/views/WelcomeView.vue'
import LoginView from '@/views/LoginView.vue'
import UsersListView from '@/views/nav1/UsersListView.vue'
import SystemSettingView from '@/views/nav1/SystemSettingView.vue'


//import EchartsView from '@/views/charts/EchartsView.vue'  //这玩意巨大 5m
import Form from '@/views/example/Form.vue'

let routes = [
  {
    path: '/login',
    component: LoginView,
    name: '',
    hidden: true
  },
  {
    path: '/404',
    component: NotFound,
    name: '',
    hidden: true
  },
  {
    path: '/',
    component: HomeView,
    name: '',
    iconCls: 'fa fa-home',
    leaf: true,//只有一个节点
    children: [
      {path: '/welcome', component: WelcomeView, name: 'welcome'}
    ]
  },
  {
    path: '/',
    component: HomeView,
    name: '系统配置',
    iconCls: 'fa fa-gears',
    children: [
      {path: '/system/setting', component: SystemSettingView, name: '系统设置', iconCls: 'fa fa-gear'},
      {path: '/usersList', component: UsersListView, name: '操作人员', iconCls: 'fa fa-user'},
      //{path: '/form', component: Form, name: '表单', iconCls: 'fa fa-user'},
    ]
  },
  {
    path: '*',
    hidden: true,
    redirect: {path: '/404'}
  }
]

export default routes

