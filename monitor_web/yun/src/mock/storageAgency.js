import Mock from 'mockjs';
import { param2Obj } from '@/utils';

const List = [];
const count = 100;

for(let i=0;i<count;i++){
  List.push(Mock.mock({
    id: '@increment', //递增1
    region_center_id: '@increment', 
    regionName: '@region', //模拟区域
    storageAgencyIp: '@ip', //模拟ip
    'connectClientNum|1-100': 100, //1-100的数字
    'businessNum|1-100': 100,
    'capacityUserd|1-100': 100,
    'capacityUsable|1-924': 100,
    capacityTotal: 1024,
    'status|1': [1,2,3], //随机选择数组中的其中一项
  }));
}

export default {
  //获取存储代理列表，查询
  getStorageAgencyList: config => {
    const { searchkeys,page,limit } = JSON.parse(config.body);

    //查询
    let mockList = List.filter(item => {
      if(searchkeys.search && item.regionName.indexOf(searchkeys.search) < 0) return false;
      return true;
    });

    const pageList = mockList.filter((item,index) => index < limit * page && index >= limit * (page-1)); //分页

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
  }
};