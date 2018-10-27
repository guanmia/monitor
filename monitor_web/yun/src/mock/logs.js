import Mock from 'mockjs';
import { param2Obj } from '@/utils';

const List = [];
const count = 100;

for(let i=0;i<count;i++){
  List.push(Mock.mock({
    id: '@increment', //递增1
    username: '@name', //模拟名称
    'content|1': ['登录','添加了深圳区域','修改了北京区域','添加了user用户','修改了个人密码','删除了user用户'],
    time: '@datetime', //模拟日期时间
  }));
}

export default {
  //获取日志列表，查询
  getLogsList: config => {
    const { searchkeys,page,limit } = JSON.parse(config.body);
    console.log(searchkeys);
    
    //将日期时间转化为毫秒数，进行比较
    let startTime,endTime;
    if(searchkeys.starttime && searchkeys.endtime){
      startTime = new Date(searchkeys.starttime).getTime();
      endTime = new Date(searchkeys.endtime).getTime();
    }

    //查询
    let mockList = List.filter(item => {
      if(searchkeys.search && item.username.indexOf(searchkeys.search) < 0) return false;
      if(startTime && endTime && (startTime > new Date(item.time).getTime() || endTime < new Date(item.time).getTime())) return false;
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