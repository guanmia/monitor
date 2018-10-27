import request from '@/utils/request'; //axios默认配置
import { encrypt } from '@/utils/encrypt';

//获取用户列表，查询
export function getUserList(data) {

  //处理searchkeys
  let searchkeys = JSON.stringify(data.searchkeys);
  data = {
    page: data.page,
    limit: data.limit,
    searchkeys
    };

  return request({
    url: '/admin/user/list',
    method: 'post',
    data
  })
}

//添加用户
export function createUser(data){
  data.password = encrypt(data.password);
  return request({
    url: '/admin/user/create',
    method: 'post',
    data
  });
}

//获取某个用户信息
export function getUserInfo(data){
  return request({
    url: '/admin/user/getinfo',
    method: 'get',
    params: data
  });
}

//更新用户
export function updateUser(data){
  return request({
    url: '/admin/user/update',
    method: 'post',
    data
  });
}

//重置用户密码
export function resetUserPwd(data){
  data.password = encrypt(data.password);
  return request({
    url: '/admin/user/resetpwd',
    method: 'post',
    data
  });
}

//删除用户
export function deleteUser(data){
  return request({
    url: '/admin/user/delete',
    method: 'post',
    data
  });
}

//发送短信验证码
export function sendEmail(data){
  return request({
    url: '/admin/user/forgotpasswordstep1',
    method: 'post',
    data
  });
}

//设置新的密码
export function newUserPwd(data){
  data.password = encrypt(data.password);
  return request({
    url: '/admin/user/forgotpasswordstep2',
    method: 'post',
    data
  });
}

//修改面
export function changePwd(data){
  return request({
    url: '/admin/user/changepwd',
    method: 'post',
    data
  });
}
