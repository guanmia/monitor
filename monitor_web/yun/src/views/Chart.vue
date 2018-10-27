<template>
    <div class="chart">
        <el-dialog title="当前区域拓扑图" :visible.sync="visible" :closeOnClickModal="false">
            <topology height='400px' width='100%' :info='inter'></topology>
        </el-dialog>
        <div class="show-temp sp">
            <table>
                <tr><th colspan="2">总体统计信息</th></tr>
                <tr v-for="(value, key) in info"><td>{{ key }}</td><td>{{ value }}</td></tr>
            </table>
        </div>
        <select v-model="inter[0]" class="selects">
            <option v-for="option in options" v-bind:value="option.id">
                {{ option.regionName }}
            </option>
        </select>
        <div ref="myChart" class="myChart"></div>
    </div>
</template>

<style lang="scss" scoped>
.chart{ height:calc(100% - 60px);padding:0!important;position: relative;background-color: #404a59 !important;
    .myChart{ width:100%; min-height:100%;}
    .show-temp { text-align: center; width: 100%; border: none;
    }
}
.selects{
    position: absolute;z-index: 999;right: 20px;top: 20px;background-color:#323c48;color:#fff;outline: none;border-color: #2a333d;min-width: 70px;height: 30px;line-height: 30px;padding-left: 4px;border-radius: 5px;
}
</style>
<style lang="scss">
.chart{
    .sp{position: absolute;z-index: 999;bottom: 20px;right: 20px;width: auto !important;padding: 6px;border-radius: 6px;background-color: rgba(50, 50, 50, 0.7)}
    th,td{ height: 34px; line-height: 34px; padding: 0 14px; background-color: #222427;text-align: center;color: #fff;}
    th{ border-bottom: 2px solid #00707f; font-size: 14px;}
    td { border-bottom: 1px solid #36393e; font-size: 12px;}
}
</style>


<script>
import { maps, choice, stat } from '@/api/chart';
import 'echarts/map/js/china';
import Topology from '@/components/Charts/topology'
export default {
    name: 'chart',
    components: { Topology },
    data () {
        return {
            info:"",
            visible: false,
            inter: [],
            options: "",
            metadataName:'',
            mnsj:'',
            city:[],
            myMap:{},
            once: true
        }
    },
    mounted(){
        choice().then(result => {
            this.options = result.data.results;
            this.inter.push(result.data.results[0].id);
            this.inter.push(result.data.results[0].location);
        });
        stat().then(result => {
             this.info = {"区域数量":result.data.metadata_num,"客户端数量":result.data.client_num,"存储代理数量":result.data.gateway_num,"当前文件数量":result.data.business_num};
        });
    },
    watch:{
        inter:function(){
            maps(this.inter[0]).then(result => {
                let self = this;
                result.data.client_city.forEach(function(val,index){   
                    if(val.children){
                        let node = "";
                        val.children.forEach(function(data,i){
                            node += "<tr><td>"+data.name+"</td><td>"+data.value+"</td></tr>"
                        });
                        let dom = "<table class='show-temp'><tr><th>网点名</th><th>连接数</th></tr>"+node+"</table>";
                        self.myMap[val.city_name] = dom;
                    }                         
                });
                self.city = [result.data.city_name,this.arrangement(result.data)];
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
        arrangement(data){
            let citydata = [];
            data.client_city.forEach(function(val,index){             
                citydata.push(
                   [{"name":data.city_name},{"name":val.city_name,"value":val.value}]
                )
            });
            return citydata;
        },
        getCharts(){
            let chart = this.$refs.myChart;
            let myChart = this.$echarts.init(chart);
            let self = this;
            let show = "";
            
            // if(this.city[1].length == 0){
            //     show = {
            //         normal: {
            //             show: true,
            //             position: 'right',
            //             formatter: '{b}'
            //         }
            //     }
            // }else{
            //     show = ""
            // }

            let geoCoordMap={"上海":[121.4648,31.2891],"东莞":[113.8953,22.901],"东营":[118.7073,37.5513],"中山":[113.4229,22.478],"临汾":[111.4783,36.1615],"临沂":[118.3118,35.2936],"丹东":[124.541,40.4242],"丽水":[119.5642,28.1854],"乌鲁木齐":[87.9236,43.5883],"佛山":[112.8955,23.1097],"保定":[115.0488,39.0948],"兰州":[103.5901,36.3043],"包头":[110.3467,41.4899],"北京":[116.4551,40.2539],"北海":[109.314,21.6211],"南京":[118.8062,31.9208],"南宁":[108.479,23.1152],"南昌":[116.0046,28.6633],"南通":[121.1023,32.1625],"厦门":[118.1689,24.6478],"台州":[121.1353,28.6688],"合肥":[117.29,32.0581],"呼和浩特":[111.4124,40.4901],"咸阳":[108.4131,34.8706],"哈尔滨":[127.9688,45.368],"唐山":[118.4766,39.6826],"嘉兴":[120.9155,30.6354],"大同":[113.7854,39.8035],"大连":[122.2229,39.4409],"天津":[117.4219,39.4189],"太原":[112.3352,37.9413],"威海":[121.9482,37.1393],"宁波":[121.5967,29.6466],"宝鸡":[107.1826,34.3433],"宿迁":[118.5535,33.7775],"常州":[119.4543,31.5582],"广州":[113.5107,23.2196],"廊坊":[116.521,39.0509],"延安":[109.1052,36.4252],"张家口":[115.1477,40.8527],"徐州":[117.5208,34.3268],"德州":[116.6858,37.2107],"惠州":[114.6204,23.1647],"成都":[103.9526,30.7617],"扬州":[119.4653,32.8162],"承德":[117.5757,41.4075],"拉萨":[91.1865,30.1465],"无锡":[120.3442,31.5527],"日照":[119.2786,35.5023],"昆明":[102.9199,25.4663],"杭州":[119.5313,29.8773],"枣庄":[117.323,34.8926],"柳州":[109.3799,24.9774],"株洲":[113.5327,27.0319],"武汉":[114.3896,30.6628],"汕头":[117.1692,23.3405],"江门":[112.6318,22.1484],"沈阳":[123.1238,42.1216],"沧州":[116.8286,38.2104],"河源":[114.917,23.9722],"泉州":[118.3228,25.1147],"泰安":[117.0264,36.0516],"泰州":[120.0586,32.5525],"济南":[117.1582,36.8701],"济宁":[116.8286,35.3375],"海口":[110.3893,19.8516],"淄博":[118.0371,36.6064],"淮安":[118.927,33.4039],"深圳":[114.5435,22.5439],"清远":[112.9175,24.3292],"温州":[120.498,27.8119],"渭南":[109.7864,35.0299],"湖州":[119.8608,30.7782],"湘潭":[112.5439,27.7075],"滨州":[117.8174,37.4963],"潍坊":[119.0918,36.524],"烟台":[120.7397,37.5128],"玉溪":[101.9312,23.8898],"珠海":[113.7305,22.1155],"盐城":[120.2234,33.5577],"盘锦":[121.9482,41.0449],"石家庄":[114.4995,38.1006],"福州":[119.4543,25.9222],"秦皇岛":[119.2126,40.0232],"绍兴":[120.564,29.7565],"聊城":[115.9167,36.4032],"肇庆":[112.1265,23.5822],"舟山":[122.2559,30.2234],"苏州":[120.6519,31.3989],"莱芜":[117.6526,36.2714],"菏泽":[115.6201,35.2057],"营口":[122.4316,40.4297],"葫芦岛":[120.1575,40.578],"衡水":[115.8838,37.7161],"衢州":[118.6853,28.8666],"西宁":[101.4038,36.8207],"西安":[109.1162,34.2004],"贵阳":[106.6992,26.7682],"连云港":[119.1248,34.552],"邢台":[114.8071,37.2821],"邯郸":[114.4775,36.535],"郑州":[113.4668,34.6234],"鄂尔多斯":[108.9734,39.2487],"重庆":[107.7539,30.1904],"金华":[120.0037,29.1028],"铜川":[109.0393,35.1947],"银川":[106.3586,38.1775],"镇江":[119.4763,31.9702],"长春":[125.8154,44.2584],"长沙":[113.0823,28.2568],"长治":[112.8625,36.4746],"阳泉":[113.4778,38.0951],"青岛":[120.4651,36.3373],"韶关":[113.7964,24.7028]};

            let convertData = function (data) {
                let res = [];
                for (let i = 0; i < data.length; i++) {
                    let dataItem = data[i];
                    let fromCoord = geoCoordMap[dataItem[0].name];
                    let toCoord = geoCoordMap[dataItem[1].name];
                    if (fromCoord && toCoord) {
                        res.push({
                            fromName: dataItem[0].name,
                            toName: dataItem[1].name,
                            coords: [toCoord, fromCoord]
                        });
                    }
                }
                return res;
            };

            let color = ['#fbe52d', '#a6c84c', '#50cfe2'];

            let series = [];
            function serdata(city) {      
                series.push(
                    {
                        name: city[0],
                        type: 'lines',
                        zlevel: 1, 
                        effect: { 
                            show: true,
                            period: 6, 
                            trailLength: 0.4, 
                            color: '#fff', 
                            symbolSize: 3 
                        },
                        silent: true,
                        lineStyle: { 
                            normal: {
                                color: color[1],
                                width: 0,
                                curveness: 0.2 
                            }
                        },
                        data: convertData(city[1])
                    },
                    {
                        name: city[0],
                        type: 'lines',
                        zlevel: 2,
                        symbol: 'none',
                        symbolSize: 10,
                        effect: {
                            show: true,
                            period: 6,
                            trailLength: 0,
                            symbol: 'arrow', 
                            symbolSize: 8
                        },
                        silent:true,
                        lineStyle: {
                            normal: {
                                color: color[1],
                                width: 1,
                                opacity: 0.6,
                                curveness: 0.2
                            }
                        },
                        data: convertData(city[1])
                    },
                    {
                        name: city[0],
                        type: 'effectScatter', 
                        coordinateSystem: 'geo',
                        zlevel: 2,
                        rippleEffect: { 
                            brushType: 'stroke'
                        },
                        label: {
                            normal: {
                                show: true,
                                position: 'right',
                                formatter: '{b}'
                            }
                        },
                        symbolSize: (val) => {
                            return (val[2]/2+20)/2;
                            //return val[2] / 8;
                        },
                        itemStyle: { 
                            normal: {
                                color: color[1]
                            }
                        },
                        data: city[1].map((dataItem) =>{        
                            let obj = {
                                name: dataItem[1].name,
                                value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value])
                            };
                            return obj;
                        })
                    },
                    {
                        name: city[0],
                        type: 'effectScatter',
                        coordinateSystem: 'geo',
                        zlevel: 2,
                        label: {
                            normal: {
                                show: true,
                                position: 'right',
                                formatter: '{b}'
                            }
                        },
                        symbol:'pin',
                        symbolSize: 20,
                        itemStyle: {
                            normal: {
                                color: color[0]
                            }
                        },
                        data: [{
                            name: city[0],
                            value: geoCoordMap[city[0]]
                        }]
                    }
                );
            };
            serdata(this.city);

            let option = {
                backgroundColor: '#404a59',
                title : {
                    text: '全国区域中心分布图',
                    //subtext: '',
                    top: '30px',
                    left: 'center',
                    textStyle : {
                        color: '#fff'
                    }
                },
                tooltip : {
                    trigger: 'item',
                    formatter: function(params){ 
                        return self.myMap[params.name];
                    }
                },
                geo: {
                    map: 'china',
                    label: { 
                        emphasis: {
                            show: false
                        }
                    },
                    roam: false, 
                    itemStyle: {
                        normal: {
                            areaColor: '#323c48',
                            borderColor: '#404a59'
                        },
                        emphasis: {
                            areaColor: '#2a333d'
                        }
                    }
                },
                series: series
            };

            myChart.setOption(option);

            this.change = function() {
                // if(this.city[1].length == 0){
                //     show = {
                //         normal: {
                //             show: true,
                //             position: 'right',
                //             formatter: '{b}'
                //         }
                //     }
                // }else{
                //     show = ""
                // }
                series.splice(0,series.length);
                serdata(this.city);
                myChart.setOption({
                    series: series
                });
            };

            myChart.on('click',function(data){
                if(data.name==data.seriesName){
                    self.visible = true;
                    self.inter.pop();
                    self.inter.push(data.name);
                }
            })

        }
    }
}
</script>


