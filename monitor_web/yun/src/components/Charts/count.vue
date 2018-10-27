<template>
    <div :ref="info[0]" style="height: 400px; width: 50%;"></div>
</template>

<style lang="scss">

</style>

<script>
import { maps, files } from "@/api/chart";
import { getLocation } from "@/api/region";
export default {
  props: ["info", "para"],
  data() {
    return {
      origin: "",
      once: true,
      cityinfo: []
    };
  },
  mounted() {
    getLocation().then(result => {
      let self = this;
      result.data.forEach(function(citylist) {
        citylist.children.forEach(function(cityinfo) {
          self.cityinfo.push({
            cityname: cityinfo.location,
            cityid: cityinfo.location_id
          });
        });
      });
    });
  },
  watch: {
    para: function() {
      let self = this;
      if (this.info[0] == "nodes") {
        maps(this.para[0]).then(result => {
          this.origin = this.arrangement(result.data.client_city);
          this.cityinfo.forEach(function(val) {
            if (val.cityname.indexOf(self.origin[0][0]) > -1) {
              self.file(parseInt(val.cityid));
            }
          });
          if (this.once) {
            this.getCharts();
            this.once = false;
          } else {
            this.change();
          }
        });
      } else {
        files(this.para).then(result => {
          if (result.data.length > 0) {
            this.origin = this.arranged(result.data);
            if (this.once) {
              this.getCharts();
              this.once = false;
            } else {
              this.change();
            }
          } else {
            this.$message.error({
              message: "未获取到该城市节点文件数据！",
              center: true
            });
          }
        });
      }
    }
  },
  methods: {
    arrangement(data) {
      let nodes = [[], []];
      data.forEach(val => {
        nodes[0].push(val.city_name);
        nodes[1].push(val.value);
      });
      return nodes;
    },
    arranged(data) {
      let nodes = [[], []];
      data.forEach(val => {
        nodes[0].push(val.node_name);
        nodes[1].push(val.num);
      });
      return nodes;
    },
    getCharts() {
      let self = this;
      let dom = this.$refs[this.info[0]];
      let myChart = this.$echarts.init(dom);

      let color = [
        "#404a59",
        "#ccc",
        "#fff",
        "#ffe052",
        "#2dffd8",
        "#ff61ae",
        "#2a333d",
        "#76a3fe",
        "#006ebd",
        "#2cc08e",
        "#ffb171",
        "#ff687b"
      ];
      let jbc = [];

      if (this.info[0] == "nodes") {
        jbc = [{ offset: 0, color: color[8] }, { offset: 1, color: color[9] }];
      } else {
        jbc = [
          { offset: 0, color: color[10] },
          { offset: 1, color: color[11] }
        ];
      }
      let option = {
        title: {
          text: this.info[1],
          left: "center",
          top: "5%",
          textStyle: {
            color: color[2]
          }
        },
        backgroundColor: color[0],
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow"
          }
        },
        grid: {
          right: "4%",
          top: "16%",
          left: "8%",
          bottom: "12%"
        },
        xAxis: {
          type: "value",
          minInterval: 1,
          axisLine: {
            lineStyle: {
              color: color[1]
            }
          }
        },
        yAxis: {
          type: "category",
          name: this.info[2],
          axisLine: {
            lineStyle: {
              color: color[1]
            }
          },
          data: this.origin[0]
        },
        color: ["#00a65a"],
        series: [
          {
            name: this.info[3],
            type: "bar",
            data: this.origin[1],
            itemStyle: {
              normal: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 1, 0, jbc)
              }
            },
            label: {
              normal: {
                show: true,
                formatter: "{b}: {c}"
              }
            }
          }
        ]
      };

      myChart.setOption(option, true);

      this.change = function() {
        myChart.setOption({
          yAxis: {
            data: this.origin[0]
          },
          series: [
            {
              data: this.origin[1]
            }
          ]
        });
      };

      if (this.info[0] == "nodes") {
        myChart.on("click", function(para) {
          self.cityinfo.forEach(function(val) {
            if (val.cityname.indexOf(para.name) > -1) {
              self.file(parseInt(val.cityid));
            }
          });
        });
      }
    },
    file(name) {
      let x = { region_id: this.para[0], location_id: name };
      this.$emit("city", x);
    }
  }
};
</script>


