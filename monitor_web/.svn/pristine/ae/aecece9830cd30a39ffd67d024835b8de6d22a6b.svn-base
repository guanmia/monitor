import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV); //process.env.NODE_ENV：development：开发环境，production：线上环境

Vue.use(Router)

export const constantRouterMap = [
  { path: '/login', component: _import('Login'), hidden: true },
  { path: '/findbackpwd', component: _import('FindBackPwd'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '/home', component: _import('Home'), redirect: '/home/chart', meta: { title: 'home', menu_name: '首页', ismenu: true, icon: "fa fa-home" },
    children: [
      { path: 'chart', component: _import('Chart'), meta: { title: 'home' } },
      { path: '', redirect: 'chart' }
    ]
  },
  { path: '/status', component: _import('Home'),redirect: '/status/metadata',  meta: {title: 'status',menu_name:'状态监控',ismenu:true,icon:"el-icon-view"},
      children: [         
          { path: 'metadata', component:_import('charts/metadata') , meta: {title: 'metadata',menu_name:'metadata监控',ismenu:true,icon:"fa fa-cloud-upload"}},
          { path: 'gateway', component:_import('charts/gateway') , meta: {title: 'gateway',menu_name:'gateway监控',ismenu:true,icon:"fa fa-code-fork"}},
          { path: 'monitor', component:_import('charts/monitor') , meta: {title: 'monitor',menu_name:'本机监控',ismenu:true,icon:"fa fa-dashboard"}}                 
          ]
    },
  {
    path: '/center', component: _import('Home'), redirect: '/center/region', meta: { title: 'regionHome', menu_name: '区域中心管理', ismenu: true, icon: "fa fa-database" },
    children: [
      { path: 'region', component: _import('Region'), meta: { title: 'region', menu_name: '区域中心列表', ismenu: true, icon: "fa fa-map-marker" } },
      { path: 'client', component: _import('Client'), meta: { title: 'client', menu_name: '客户端列表', ismenu: true, icon: "fa fa-desktop" } },
      { path: 'storage-agency', component: _import('StorageAgency'), name: 'storage-agency', meta: { title: 'storageagency', menu_name: '存储代理列表', ismenu: true, icon: "el-icon-tickets" } },
    ]
  },
  {
    path: '/user', component: _import('Home'), redirect: '/user/changepwd', meta: { title: 'user', menu_name: '用户设定' },
    children: [
      { path: 'changepwd', component: _import('ChangePwd'), meta: { title: 'changepwd', menu_name: '', menu_name: '修改密码' } }
    ]
  },
  { path: '', redirect: '/home/chart' }


];

export default new Router({
  //mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }), //滚动行为
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/system', component: _import('Home'), redirect: '/system/changepwd', meta: { title: 'systemHome', role: ['admin'], menu_name: '系统管理', ismenu: true, icon: "fa fa-gears" },
    children: [
      { path: 'smtp', component: _import('Smtp'), meta: { title: 'smtp', role: ['admin'], menu_name: '系统设置', ismenu: true, icon: "fa fa-gear" } },
      { path: 'logs', component: _import('Logs'), meta: { title: 'logs', role: ['admin'], menu_name: '操作日志', ismenu: true, icon: "fa fa-building" } },
      { path: 'users', component: _import('User'), meta: { title: 'users', role: ['admin'], menu_name: '用户管理', ismenu: true, icon: "fa fa-user-circle-o" } }
    ]
  },
  { path: '*', redirect: '/login', hidden: true }
]