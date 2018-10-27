

//处理excel格式函数
let formatJson = (filterVal, jsonData) => {
  return jsonData.map(v =>
    filterVal.map(j => {
      if (j === "timestamp") {
        return parseTime(v[j]);
      } else {
        return v[j];
      }
    })
  );
}

//定义全局函数
exports.install = function (Vue) {

  //路由的i18n
  Vue.prototype.generateTitle = function (title) {
    return this.$t('route.' + title);
  }

  //权限判断
  Vue.prototype.checkRole = function () {
    let roleChecked = this.$store.getters.roles.some(role => role === 'admin');
    return roleChecked;
  }

  //导出excel
  Vue.prototype.handleDownload = function (fieldArray, list, title) {
    this.$store.dispatch('changeDownloadLoading', true);
    import("@/vendor/Export2Excel").then(excel => {
      let arr = [];
      for (let x in fieldArray) {
        arr.push(x);
      }
      const tHeader = arr;
      const filterVal = arr;
      const data = formatJson(filterVal, list);
      excel.export_json_to_excel(tHeader, data, title);
      this.$store.dispatch('changeDownloadLoading', false);
    });
  }

  //日期格式化
  Vue.prototype.timeFormats = function (time) {
    let getFullYear = time.getFullYear(),
      getMonth = time.getMonth() + 1 < 10 ? '0' + (time.getMonth() + 1) : time.getMonth() + 1,
      getDate = time.getDate() < 10 ? '0' + time.getDate() : time.getDate(),
      getHours = time.getHours() < 10 ? '0' + time.getHours() : time.getHours(),
      getMinutes = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes(),
      getSeconds = time.getSeconds() < 10 ? '0' + time.getSeconds() : time.getSeconds();
    return `${getFullYear}-${getMonth}-${getDate} ${getHours}:${getMinutes}:${getSeconds}`;
  }

}