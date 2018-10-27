import Mock from 'mockjs';
import { param2Obj } from '@/utils';

let List = [], locationList = [];
const count = 10;

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    id: i+1, //递增1
    regionName: ['西北','华北','东北','华南','华东','华西','华中','西南','东南','西东'][i], //模拟区域
    //regionAddress: '@county',
    location_id: i,
    dbIp: '@ip', //模拟ip
    dbPort: '@ip',
    msIp: '@ip',
    msPort: '@ip',
    'clientNum|1-10000': 100, //1-10000的数字
    'storageAgencyNum|1-10000': 100,
    'status|1': [1, 2, 3], //随机选择数组中的其中一项
    comments: '@cparagraph()' //模拟文本
  }));

  locationList.push(Mock.mock({ //模拟省市区
    location_id: i,
    location: "@province",
    level: 1,
    children: [
      {
        location_id: 1,
        location: "@city",
        level: 2,
        children: [
          {
            location_id: 1,
            location: "@county",
            street: "翠竹街道",
            level: 3
          }
        ]
      },
    ]
  }));
}



export default {
  //获取区域列表，查询
  getRegionList: config => {
    //console.log(config);
    const { searchkeys, page, limit } = JSON.parse(config.body);
    //console.log(searchkeys);

    //查询
    let mockList = List.filter(item => {
      if (searchkeys.search && item.regionName.indexOf(searchkeys.search) < 0) return false;
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
  //添加区域
  createRegion: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //获取某个区域信息
  getRegionInfo: config => {
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
  //更新区域
  updateRegion: config => {
    //console.log(config);
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //删除区域
  deleteRegion: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //获取全部省市区域
  getLocation: config => {
    return {
      results: {
        error_code: 0,
        data: locationList
      }
    }
  }
};