import NotFound from '@/views/404View.vue'
import LoginView from '@/views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'
import WelcomeView from '@/views/WelcomeView.vue'
import SystemSettingView from '@/views/admin/system/SystemSettingView.vue'
import UserListView from '@/views/admin/user/UserListView.vue'
import SkuListView from '@/views/admin/sku/ListView.vue'
import SkuFormView from '@/views/admin/sku/FormView.vue'
import StoreListView from '@/views/admin/store/ListView.vue'
import StoreFormView from '@/views/admin/store/FormView.vue'
import OrderListView from '@/views/admin/order/ListView.vue'
import OrderFormView from '@/views/admin/order/FormView.vue'
import MemberListView from '@/views/admin/member/ListView.vue'
import MemberFormView from '@/views/admin/member/FormView.vue'
import EchartsView from '@/views/admin/echarts/EchartsView.vue'
import Form from '@/views/example/Form.vue'

let routes = [
  {
    path: '*',
    redirect: {path: '/404'},
    hidden: true
  },
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
      {path: '/admin/system/setting', component: SystemSettingView, name: '系统设置', iconCls: 'fa fa-gear'},
      {path: '/admin/user/list', component: UserListView, name: '后台操作人员', iconCls: 'fa fa-user'},
      //{path: '/form', component: Form, name: '表单', iconCls: 'fa fa-user'},
    ]
  },
  {
    path: '/',
    component: HomeView,
    name: '运营设置',
    iconCls: 'fa fa-building',
    children: [
      {path: '/admin/sku/list', component: SkuListView, name: 'SKU列表', iconCls: 'fa fa-circle-o'},
      {path: '/admin/sku/form', component: SkuFormView, name: 'SKU编辑', iconCls: 'fa fa-circle-o', hidden: true},
      {path: '/admin/store/list', component: StoreListView, name: '店铺列表', iconCls: 'fa fa-circle-o'},
      {path: '/admin/store/form', component: StoreFormView, name: '店铺编辑', iconCls: 'fa fa-circle-o', hidden: true},
      {path: '/admin/order/list', component: OrderListView, name: '订单列表', iconCls: 'fa fa-circle-o'},
      {path: '/admin/order/form', component: OrderFormView, name: '订单编辑', iconCls: 'fa fa-circle-o', hidden: true},
      {path: '/admin/member/list', component: MemberListView, name: '会员列表', iconCls: 'fa fa-circle-o'},
      {path: '/admin/member/form', component: MemberFormView, name: '会员', iconCls: 'fa fa-circle-o', hidden: true},
      {path: '/admin/echarts', component: EchartsView, name: '图表', iconCls: 'fa fa-circle-o'},
    ]
  }
]

export default routes

