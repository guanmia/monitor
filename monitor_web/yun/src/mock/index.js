import Mock from 'mockjs'
import loginAPI from './login'
import userAPI from './user';
import regionAPI from './region';
import clientAPI from './client';
import systemAPI from './system';
import logsAPI from './logs';
import storageAgencyAPI from './storageAgency';
import chartAPI from './chart';

// Mock.setup({
//   timeout: '350-600'
// })

// 登录相关 
Mock.mock(/\/oauth\/token/, 'post', loginAPI.loginByUsername)
Mock.mock(/\/admin\/user\/logout/, 'post', loginAPI.logout)
Mock.mock(/\/admin\/user\/info\.*/, 'get', loginAPI.getUserInfo)

//用户管理
Mock.mock(/\/admin\/user\/list/,'post',userAPI.getUserList); 
Mock.mock(/\/admin\/user\/create/,'post',userAPI.createUser);
Mock.mock(/\/admin\/user\/getinfo/,'get',userAPI.getUserInfo);
Mock.mock(/\/admin\/user\/update/,'post',userAPI.updateUser);
Mock.mock(/\/admin\/user\/resetpwd/,'post',userAPI.resetUserPwd);
Mock.mock(/\/admin\/user\/delete/,'post',userAPI.deleteUser);
Mock.mock(/\/admin\/user\/forgotpasswordstep1/,'post',userAPI.sendEmail);
Mock.mock(/\/admin\/user\/forgotpasswordstep2/,'post',userAPI.newUserPwd);
Mock.mock(/\/admin\/user\/changepwd/,'post',userAPI.changePwd);

//区域管理
Mock.mock(/\/admin\/region\/list/,'post',regionAPI.getRegionList);
Mock.mock(/\/admin\/region\/create/,'post',regionAPI.createRegion);
Mock.mock(/\/admin\/region\/getinfo/,'get',regionAPI.getRegionInfo);
Mock.mock(/\/admin\/region\/update/,'post',regionAPI.updateRegion);
Mock.mock(/\/admin\/region\/delete/,'post',regionAPI.deleteRegion);

//客户端管理
Mock.mock(/\/admin\/client\/list/,'post',clientAPI.getClientList);
Mock.mock(/\/admin\/client\/create/,'post',clientAPI.createClient);
Mock.mock(/\/admin\/client\/createBatch/,'post',clientAPI.createBatchClient);
Mock.mock(/\/admin\/client\/getinfo/,'get',clientAPI.getClientInfo);
Mock.mock(/\/admin\/client\/update/,'post',clientAPI.updateClient);
Mock.mock(/\/admin\/client\/updateBatch/,'post',clientAPI.updateBatchClient);
Mock.mock(/\/admin\/client\/delete/,'post',clientAPI.deleteClient);

//邮件服务器设置
Mock.mock(/\/admin\/system\/smtp/,'post',systemAPI.smtpSetting);

//操作日志
Mock.mock(/\/admin\/logs\/list/,'post',logsAPI.getLogsList);

//存储代理管理
Mock.mock(/\/admin\/region\/\d+\/storageagency\/list/,'post',storageAgencyAPI.getStorageAgencyList);

//公共
Mock.mock(/\/admin\/locations/,'get',regionAPI.getLocation);

//图表相关
Mock.mock(/\/choice/, 'get', chartAPI.choice)
Mock.mock(/\/lines/, 'get', chartAPI.lines)
Mock.mock(/\/files/, 'get', chartAPI.files)
Mock.mock(/\/maps/, 'get', chartAPI.maps)


export default Mock;
