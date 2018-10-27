import axios from 'axios'
import { Message } from 'element-ui'
import { getToken, checkTokenExpiresin } from '@/utils/auth'
import i18n from '@/lang';
import qs from 'qs';
import { param } from "@/utils";

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url  
  timeout: 5000, // request timeout
  //withCredentials: true
})

const usemock = false;

// request interceptor
service.interceptors.request.use(config => {

  if ((config.data == undefined || config.data.grant_type == undefined) && config.url.indexOf('forgotpassword') < 0)
    if (getToken()) {
      config.headers['Authorization'] = 'Bearer ' + getToken(); // 让每个请求携带accessToken
    }
  if (config.url.indexOf('token') >= 0) {// token的api不包括/api/v1/路径
    config.baseURL = '';
  }

  if (!usemock) {
    config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
    
    if (config.data !== undefined) {
      
      if (config.url.indexOf("/createBatch") > -1) {
        //console.log(config.url);
        config.headers['Content-Type'] = 'multipart/form-data';
      }else{
        config.data = qs.stringify(config.data);
      }
      //console.log(config.headers);

    }



  }



  return config
}, error => {
  // Do something with request error
  console.log(error) // for debug
  Promise.reject(error)
})

// respone interceptor
service.interceptors.response.use(
  response => {

    if (!response.data.access_token) {
      //console.log(response.data);
      if (checkError(response))
        return Promise.reject('error');

    }
    //console.log(response.data);

    return response.data;

  },
  /**
  * 下面的注释为通过response自定义code来标示请求状态，当code返回如下情况为权限有问题，登出并返回到登录页
  * 如通过xmlhttprequest 状态码标识 逻辑可写在下面error中
  */
  //  const res = response.data;
  //     if (res.code !== 20000) {
  //       Message({
  //         message: res.message,
  //         type: 'error',
  //         duration: 5 * 1000
  //       });
  //       // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
  //       if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
  //         MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
  //           confirmButtonText: '重新登录',
  //           cancelButtonText: '取消',
  //           type: 'warning'
  //         }).then(() => {
  //           store.dispatch('FedLogOut').then(() => {
  //             location.reload();// 为了重新实例化vue-router对象 避免bug
  //           });
  //         })
  //       }
  //       return Promise.reject('error');
  //     } else {
  //       return response.data;
  //     }
  error => {
    checkError(error.request)
    //checkError(error.response)           
    return Promise.reject(error)
  })


function checkError(response) {
  let code = response.status != 404 && response.data && response.data.error_code >= 0 ? response.data.error_code : response.status;

  if (response.status === 401 || response.status === 400)
    code = response.status;

  if (response.responseText && response.responseText.indexOf('invalid_grant') >= 0) {
    code = 7;
  }

  if (code > 0) {
    Message({
      message: i18n.t("error_message." + code),
      type: 'error',
      duration: 5 * 1000,
      center: true
    });

    if (code === 2 || code === 3 || code === 401 || code === 7) {
      store.dispatch('FedLogOut').then(() => {
        if (location.href.indexOf('login') < 0)
          location.reload();// 为了重新实例化vue-router对象 避免bug
      });
    }

    return true;
  }

  return false;
}

export default service
