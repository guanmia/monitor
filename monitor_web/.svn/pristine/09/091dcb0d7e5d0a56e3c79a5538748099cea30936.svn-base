<template>
  <div class="chart">
    <button class="recalc" @click="recalc">重新获取数据</button>
    <div class="selects">
      <select v-model="minter">
        <option v-for="option in moptions" v-bind:value="option.id">
            {{ option.regionName }}
        </option>
      </select>
      <select v-model="ginter[2]">
        <option v-for="option in goptions" v-bind:value="option.gateway_id">
            {{ option.gateway_id }}
        </option>
      </select>
    </div>
    <lines height='300px' width='100%' :info='linecpu' :para='ginter'></lines>
    <lines height='300px' width='100%' :info='linememory' :para='ginter'></lines>
    <lines height='300px' width='100%' :info='linein' :para='ginter'></lines>
    <lines height='300px' width='100%' :info='lineout' :para='ginter'></lines>
    <disk height='300px' width='100%' :info='disk' :para='ginter'></disk>
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
.selects {
  position: absolute;
  z-index: 999;
  right: 20px;
  top: 20px;
}
.selects select {
  background-color: #323c48;
  color: #fff;
  outline: none;
  border-color: #2a333d;
  min-width: 70px;
  height: 30px;
  line-height: 30px;
  padding-left: 4px;
  border-radius: 5px;
  font-size: 14px;
}
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
  left: 20px;
  border: none;
  width: auto;
  padding: 0 10px;
  cursor: pointer;
}
</style>

<script>
import { choice, topo, gate, recalcg } from "@/api/chart";
import Lines from "@/components/Charts/lines";
import Disk from "@/components/Charts/disk";

export default {
  name: "Chart",
  components: { Lines, Disk },
  data() {
    return {
      linecpu: ["cpu", "CPU使用率", "CPU使用率/100%"],
      linememory: ["memory", "内存使用量", "内存使用量/MB"],
      lineout: ["out", "网络输出", "网络输出/Mbps"],
      linein: ["in", "网络输入", "网络输入/Mbps"],
      disk: ["disk", "磁盘信息", "容量/GB"],
      minter: "",
      ginter: [],
      moptions: "",
      goptions: "",
      fs: function(lj, cs, gt) {
        return gate(lj, cs, gt);
      }
    };
  },
  mounted() {
    choice().then(result => {
      this.moptions = result.data.results;
      this.minter = result.data.results[0].id;
    });
  },
  watch: {
    minter: function() {
      topo(this.minter).then(result => {
        this.goptions = result.data;

        this.ginter.splice(0, this.ginter.length);
        this.ginter.push(this.minter);
        this.ginter.push(this.fs);
        this.ginter.push(result.data[0].gateway_id);
      });
    }
  },
  methods: {
    recalc() {
      recalcg(this.ginter[0]).then(result => {
        if (result.error_code == 0) {
          this.inter.push("sign");
        }
      });
    }
  }
};
</script>


