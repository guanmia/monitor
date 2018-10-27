<template>
    <div class="chart">
      <lines height='300px' width='100%' :info='linecpu' :para='inter'></lines>
      <lines height='300px' width='100%' :info='linememory' :para='inter'></lines>
      <lines height='300px' width='100%' :info='linein' :para='inter'></lines>
      <lines height='300px' width='100%' :info='lineout' :para='inter'></lines>
    </div>
</template>

<style lang="scss">
.el-tabs__content {
  overflow: visible;
}
.chart {
  padding: 0 !important;
  background-color: #404a59 !important;
  position: relative;
}
</style>

<script>
import { moni } from "@/api/chart";
import Lines from "@/components/Charts/lines";

export default {
  name: "Chart",
  components: { Lines },
  data() {
    return {
      linecpu: ["cpu", "CPU使用率", "CPU使用率/100%"],
      linememory: ["memory", "内存使用量", "内存使用量/MB"],
      lineout: ["out", "网络输出", "网络输出/Mbps"],
      linein: ["in", "网络输入", "网络输入/Mbps"],
      inter: [],
      fs: function(cs) {
        return moni(cs);
      }
    };
  },
  mounted() {
    this.inter.push("");
    this.inter.push(this.fs);
  },
  methods: {}
};
</script>


