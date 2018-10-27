import Mock from 'mockjs';
import { param2Obj } from '@/utils';

const List = [];
const count = 100;

for(let i=0;i<count;i++){
  List.push(Mock.mock({
    id: '@increment', //递增1
    username: '@name', //模拟名称
    password: '@first',
    email: '@email', //模拟email
    phoneNumber: /^1[34578]\d{9}$/, //手机号码正则
    'role|1': [["admin"],["user"]], //随机选择数组中的其中一项
    introduction: '@cparagraph()' //模拟文本
  }));
}

export default {
  //获取用户列表，查询
  getUserList: config => {
    //const { username,page,limit } = param2Obj(config.url);
    const { searchkeys,page,limit } = JSON.parse(config.body);

    //查询
    let mockList = List.filter(item => {
      if(searchkeys && item.username.indexOf(searchkeys) < 0) return false;
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
  },
  //添加用户
  createUser: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //获取某个用户信息
  getUserInfo: config => {
    console.log(config);
    const { id } = param2Obj(config.url);
    return {
      results: {
        error_code: 0,
        data: List[id]
      }
    }
  },
  //更新用户
  updateUser: config => {
    //console.log(config);
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //重置用户密码
  resetUserPwd: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  },
  //删除用户
  deleteUser: config => {
    return {
      results: {
        error_code: 0,
        data: {}
      }
    }
  }
};