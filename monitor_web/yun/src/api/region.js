import request from '@/utils/request'; //axios默认配置

//获取区域列表，查询
export function getRegionList(data) {

  //处理searchkeys
  let searchkeys = JSON.stringify(data.searchkeys);
  data = {
    page: data.page,
    limit: data.limit,
    searchkeys
    };

  return request({
    url: '/admin/region/list',
    method: 'post',
    data
  })
}

//添加区域
export function createRegion(data){
  return request({
    url: '/admin/region/create',
    method: 'post',
    data
  });
}

//获取某个区域信息
export function getRegionInfo(data){
  return request({
    url: '/admin/region/getinfo',
    method: 'get',
    params: data
  });
}

//更新区域
export function updateRegion(data){
  console.log(data);
  return request({
    url: '/admin/region/update',
    method: 'post',
    data
  });
}

//删除用户
export function deleteRegion(data){
  return request({
    url: '/admin/region/delete',
    method: 'post',
    data
  });
}

//获取全部省市区域
export function getLocation(){
  return request({
    url: '/admin/locations',
    method: 'post'
  });
}