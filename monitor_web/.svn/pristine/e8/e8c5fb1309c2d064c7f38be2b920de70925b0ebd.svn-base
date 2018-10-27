<template>
    <div class="chart">
    <button class="recalc" @click="recalc">重新获取数据</button>
    <select v-model="inter[0]" class="selects">
        <option v-for="option in options" v-bind:value="option.id">
            {{ option.regionName }}
        </option>
    </select>
    <lines height='300px' width='100%' :info='linecpu' :para='inter'></lines>
    <lines height='300px' width='100%' :info='linememory' :para='inter'></lines>
    <lines height='300px' width='100%' :info='linein' :para='inter'></lines>
    <lines height='300px' width='100%' :info='lineout' :para='inter'></lines>
    <disk height='300px' width='100%' :info='disk' :para='inter'></disk>
    <count height='400px' width='100%' :info='nodes' :para='inter' @city="fileNum" style="float:left"></count>
    <count height='400px' width='100%' :info='files' :para='cityname' style="float:right"></count>
  </div>
</template>

<style lang="scss" scoped>
.el-tabs__content {
  overflow: visible;
}
.chart {
  padding: 0 !important;
  background-color: #404a59 !important;
  position: relative;
}
.selects,
.recalc {
  position: absolute;
  z-index: 999;
  right: 20px;
  top: 20px;
  background-color: #323c48;
  color: #fff;
  outline: none;
  border-color: #2a333d;
  min-width: 60px;
  height: 30px;
  line-height: 30px;
  padding-left: 4px;
  border-radius: 5px;
  font-size: 14px;
}
.recalc {
  left: 20px;
  border: none;
  width: auto;
  padding: 0 10px;
  cursor: pointer;
}
</style>

<script>
import { choice, meta, recalcm } from "@/api/chart";
import Lines from "@/components/Charts/lines";
import Disk from "@/components/Charts/disk";
import Count from "@/components/Charts/count";

export default {
  name: "Chart",
  components: { Lines, Disk, Count },
  data() {
    return {
      linecpu: ["cpu", "CPU使用率", "CPU使用率/100%"],
      linememory: ["memory", "内存使用量", "内存使用量/MB"],
      lineout: ["out", "网络输出", "网络输出/Mbps"],
      linein: ["in", "网络输入", "网络输入/Mbps"],
      disk: ["disk", "磁盘信息", "容量/GB"],
      nodes: ["nodes", "各城市节点数", "节点城市", "节点统计"],
      files: ["files", "节点文件统计", "节点名", "文件统计"],
      cityname: "",
      inter: [],
      options: "",
      fs: function(lj, cs) {
        return meta(lj, cs);
      }
    };
  },
  mounted() {
    choice().then(result => {
      this.options = result.data.results;
      this.inter.push(result.data.results[0].id);
      this.inter.push(this.fs);
    });
  },
  methods: {
    fileNum(name) {
      this.cityname = name;
    },
    recalc() {
      recalcm(this.inter[0]).then(result => {});
      this.inter.push("sign");
    }
  }
};
</script>


