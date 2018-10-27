import request from '@/utils/request'; //axios默认配置

//邮件服务器设置
export function getSmtp(data){
  return request({
    url: '/admin/system/smtpdata',
    method: 'get',
    params: data
  });
}

//邮件服务器设置
export function smtpSetting(data){
  return request({
    url: '/admin/system/smtp',
    method: 'post',
    data
  });
}