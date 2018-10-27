import request from '@/utils/request'; //axios默认配置
import qs from 'qs';

//获取客户端列表，查询
export function getClientList(data) {

  //处理searchkeys
  let searchkeys = JSON.stringify(data.searchkeys);
  data = {
    page: data.page,
    limit: data.limit,
    searchkeys
  };

  return request({
    url: '/admin/client/list',
    method: 'post',
    data
  })
}

//添加客户端
export function createClient(data) {
  return request({
    url: '/admin/client/create',
    method: 'post',
    data
  });
}

//批量添加客户端
export function createBatchClient(data) {

  let formData = new FormData();
  formData.append('region_center_id', data.region_center_id);
  formData.append('file', data.file);
  console.log(formData.get("file"));

  // let config = {
  //   headers: {
  //     'Content-Type': 'multipart/form-data'
  //   }
  // }

  return request({
    url: '/admin/client/createBatch',
    method: 'post',
    data: formData
  });
}

//获取某个客户端信息
export function getClientInfo(data) {
  //console.log(data);
  return request({
    url: '/admin/client/getinfo',
    method: 'get',
    params: data
  });
}

//更新客户端
export function updateClient(data) {
  return request({
    url: '/admin/client/update',
    method: 'post',
    data
  });
}

//批量修改客户端
export function updateBatchClient(data) {
  //处理searchkeys
  let clientids = JSON.stringify(data.clientids);
  // data = {
  //   region_center_id: data.region_center_id,
  //   clientids
  //   };
  data.clientids = clientids;

  return request({
    url: '/admin/client/updateBatch',
    method: 'post',
    data
  });
}

//删除客户端
export function deleteClient(data) {
  return request({
    url: '/admin/client/delete',
    method: 'post',
    data
  });
}

//获取客户端配置记录列表
export function getSite(data) {
  return request({
    url: '/admin/client/site',
    method: 'get',
    params: data
  });
}

//获取客户端状态
export function getStatus() {
  return request({
    url: '/admin/client/status',
    method: 'get'
  });
}