import i18n from '@/lang';

let rules = {
  
  //区域id的验证
  idRule(rule, value, callback) {
    let valReg = /^\d+$/;
    if (!valReg.test(value)) {
      callback(new Error("区域id只能为数字"));
      return;
    }
    callback();
  },

  //域名ip验证
  domainIpRule(rule, value, callback) {
    let valReg = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
    let valRegIp = /((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))/;
    if (!valReg.test(value) && !valRegIp.test(value)) {
      callback(new Error("输入ip地址或者域名有误"));
      return;
    }
    callback();

  },

  //ip验证
  ipRule(rule, value, callback) {
    let valReg = /((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))/;
    if (!valReg.test(value)) {
      callback(new Error(i18n.t('region.ipRule')));
    } else {
      callback();
    }
  },

  //端口号验证
  portRule(rule, value, callback) {
    let valReg = /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
    if (!valReg.test(value)) {
      callback(new Error(i18n.t('region.portRule')));
    } else {
      callback();
    }
  },

  //用户名验证
  usernameRule(rule, value, callback) {
    let valReg = /^[\u4e00-\u9fa5\w-@.]{1,18}$/;
    if (!valReg.test(value)) {
      callback(new Error(i18n.t('user.usernameRule')));
    } else {
      callback();
    }
  },

  //密码验证
  passwordRule(rule, value, callback) {
    let valReg = /^[\u4e00-\u9fa5\w-@.]{6,18}$/;
    if (!valReg.test(value)) {
      callback(new Error(i18n.t('user.passwordRule')));
    } else {
      callback();
    }
  },

  //电子邮箱验证
  emailRule(rule, value, callback) {
    let valReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    if (!valReg.test(value)) {
      callback(new Error(i18n.t('user.emailRule')));
    } else {
      callback();
    }
  },

  //手机号码验证
  phoneRule(rule, value, callback) {
    let valReg = /^1[34578]\d{9}$/;
    if (!valReg.test(value)) {
      callback(new Error(i18n.t('user.phoneRule')));
    } else {
      callback();
    }
  }

};

export default rules;