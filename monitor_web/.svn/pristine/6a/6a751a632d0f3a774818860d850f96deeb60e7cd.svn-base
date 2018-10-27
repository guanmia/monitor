// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import store from './store';
import './permission' // permission control
//import './mock' // simulation data

//引入axios
import axios from 'axios';
Vue.prototype.$http = axios;

//引入element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//引入i18n
import i18n from './lang';
import './icons';
Vue.use(ElementUI,{
    size: 'medium', //建立element的默认尺寸
    i18n: (key,value) => i18n.t(key,value)
});

//全局过滤
// import * as filters from './filters';
// Object.keys(filters).forEach(key => {
//     Vue.filter(key,filters[key]);
// });

//引入echarts
import echarts from 'echarts';
Vue.prototype.$echarts = echarts;
Vue.use(echarts);

//引入font-awesome
import 'font-awesome/css/font-awesome.min.css';

//设置全局css
import '@/styles/index.scss';

//设置全局函数
import base from './base';
Vue.use(base);

//设置全局指令
import waves from "@/directive/waves"; //水波纹指令
Vue.use(waves);

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    i18n,
    template: '<App/>',
    components: { App }
});
