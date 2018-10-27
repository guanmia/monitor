import Mock from 'mockjs';
import { param2Obj } from '@/utils';

let List = [];
const count = 100;

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    id: '@increment', //递增1
    'region_center_id|1-10': 1,
    site_id: '@increment',
    regionName: "",
    clientName: '@city', //模拟城市
    location_id: i,
    addTime: "@datetime", //模拟日期时间
    newConfigTime: "@datetime",
    status: {'transfer_num|1-100': 1,'location_num|1-100': 1},
  }));
}

let region = ['西北','华北','东北','华南','华东','华西','华中','西南','东南','西东'];
List.forEach(item => {
  item.regionName = region[item.region_center_id-1];
});

export default {
  //获取客户端列表，查询
  getClientList: config => {
    //console.log(config);
    const { searchkeys, page, limit } = JSON.parse(config.body);
    //查询
    let mockList = List.filter(item => {
      
      if (searchkeys.search && item.clientName.indexOf(searchkeys.search) < 0) return false;
      if (searchkeys.region_center_id && item.region_center_id !== searchkeys.region_center_id) return false;
      //if (clientName && item.clientName.indexOf(clientName) < 0) return false;
      return true;
    });

    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1)); //分页

    let lists = {
      error_code: 0,
      data: {
        total: mockList.length,
        results: pageList
      }
    }
    //console.log(lists);

    return {
      results: lists
    }
  },
  //添加客户端
  createClient: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //添加客户端
  createBatchClient: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //获取某个客户端信息
  getClientInfo: config => {
    console.log(config);
    const { id } = param2Obj(config.url);
    let data = List.filter((item, index) => item.id === Number(id));
    return {
      results: {
        error_code: 0,
        data: data[0]
      }
    }
  },
  //更新客户端
  updateClient: config => {
    //console.log(config);
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //批量修改客户端
  updateBatchClient: config => {
    //console.log(config);
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //删除区域
  deleteClient: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
};