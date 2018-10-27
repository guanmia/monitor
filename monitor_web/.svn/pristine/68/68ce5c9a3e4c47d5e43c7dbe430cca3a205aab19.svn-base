<template>
    <div :ref="info[0]" style="height:300px"></div>
</template>

<style lang="scss">

</style>

<script>
export default {
  props: ["info", "para"],
  data() {
    return {
      origin: "",
      once: true,
      length: ""
    };
  },
  mounted() {},
  watch: {
    para: function() {
      this.para[1](this.para[0], this.length, this.para[2]).then(result => {
        this.origin = result.data[this.info[0]][0];
        if (this.once) {
          this.getCharts();
          this.once = false;
        } else {
          this.change();
        }
      });
    }
  },
  methods: {
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
      let tuli = ["磁盘使用量"];

      let time = [];
      let hm = new Date().getTime();
      for (let i = 0; i < 100; i++) {
        let bl = new Date();
        bl.setTime(hm);
        time.push(bl.toLocaleTimeString("chinese", { hour12: false }));
        hm -= 300000;
      }
      time.reverse();

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
            type: "line",
            label: {
              show: true,
              backgroundColor: color[6]
            },
            lineStyle: {
              type: "dashed"
            }
          }
        },
        grid: {
          right: "3%",
          top: "30%",
          left: "4%",
          bottom: "16%"
        },
        legend: {
          data: tuli,
          top: "15%",
          textStyle: {
            color: color[1]
          }
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              color: color[1]
            }
          },
          data: time
        },
        yAxis: [
          {
            type: "value",
            scale: true,
            axisLine: {
              lineStyle: {
                color: color[1]
              }
            },
            name: this.info[2],
            splitLine: false
          }
        ],
        dataZoom: [
          {
            type: "inside",
            start: 0,
            end: 100,
            minValueSpan: 10
          }
        ],
        series: [
          {
            name: tuli,
            type: "line",
            smooth: true,
            symbol: "none",
            lineStyle: {
              normal: {
                color: color[5],
                width: 3
              }
            },
            itemStyle: {
              normal: {
                color: color[5]
              }
            },
            data: this.origin
          }
        ]
      };

      myChart.setOption(option, true);

      this.change = function() {
        myChart.setOption({
          series: [
            {
              data: self.origin
            }
          ]
        });
      };

      setInterval(function() {
        self.length = self.origin.length - 1;

        self.para[1](self.para[0], self.length, self.para[2]).then(result => {
          time.splice(0, result.data[self.info[0]][0].length);
          time.push(
            new Date().toLocaleTimeString("chinese", { hour12: false })
          );
          self.origin.splice(0, result.data[self.info[0]][0].length);
          self.origin = self.origin.concat(result.data[self.info[0]][0]);
          myChart.setOption({
            xAxis: {
              data: time
            },
            series: [
              {
                name: tuli[2],
                data: self.origin
              }
            ]
          });
        });
      }, 300000);
    }
  }
};
</script>


