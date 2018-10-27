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
        this.origin = result.data[this.info[0]];
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
      let tuli = ["前天数据", "昨天数据", "当天数据"];

      let day = 86400000;
      let initial = 0;
      let interval = 300000;

      let time = [];
      while (initial < day) {
        time.push(format(initial));
        initial += interval;
      }

      function format(mss) {
        let hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = (mss % (1000 * 60)) / 1000;
        return hours + ":" + minutes + ":" + seconds;
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
            min: 0,
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
            name: tuli[0],
            type: "line",
            smooth: true,
            symbol: "none",
            lineStyle: {
              normal: {
                color: color[4],
                width: 3
              }
            },
            itemStyle: {
              normal: {
                color: color[4]
              }
            },
            data: self.origin[0]
          },
          {
            name: tuli[1],
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
            data: self.origin[1]
          },
          {
            name: tuli[2],
            type: "line",
            smooth: true,
            symbol: "none",
            lineStyle: {
              normal: {
                color: color[3],
                width: 3
              }
            },
            itemStyle: {
              normal: {
                color: color[3]
              }
            },
            data: self.origin[2]
          }
        ]
      };

      myChart.setOption(option, true);

      this.change = function() {
        myChart.setOption({
          series: [
            {
              name: tuli[0],
              data: self.origin[0]
            },
            {
              name: tuli[1],
              data: self.origin[1]
            },
            {
              name: tuli[2],
              data: self.origin[2]
            }
          ]
        });
      };

      setInterval(function() {
        self.length = self.origin[2].length;
        if (self.para[0].length == 0) {
          self.para[0] = self.length;
        }
        self.para[1](self.para[0], self.length, self.para[2]).then(result => {
          self.origin[2] = self.origin[2].concat(result.data[self.info[0]][0]);
          myChart.setOption({
            series: [
              {
                name: tuli[2],
                data: self.origin[2]
              }
            ]
          });
        });
      }, 300000);
    }
  }
};
</script>


