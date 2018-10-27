import Vue from 'vue';
import SvgIcon from '@/components/SvgIcon'; //svg组件

import generateIconsView from '@/views/svg-icons/generateIconsView.js';

//定义全局组件
Vue.component('svg-icon',SvgIcon);

//需要或者返回所有匹配的模块
const requireAll = requireContext => requireContext.keys().map(requireContext);
//引入所有的svg
const req = require.context('./svg',false,/\.svg$/);
const iconMap = requireAll(req);

generateIconsView.generate(iconMap);
