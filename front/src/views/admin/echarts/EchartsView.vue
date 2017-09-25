<template>
  <section class="chart">
    <el-row>
      <el-col :span="12">
        <div id="chartPie" style="width:100%; height:400px;"></div>
      </el-col>
    </el-row>
  </section>
</template>

<script>
  import echarts from 'echarts'
  import Api from '@/api/api'

  export default {
    data() {
      return {
        chartPie: null
      }
    },
    mounted: function () {
      Api.getStoreTop10({}).then((res) => {
        console.log(res)
        if(res.data!=''){
          let datas = res.data

          let legend = {}
          legend.data = []
          let series = {}
          series.data = []
          for (let d of datas) {
            legend.data.push(d.name)

            let d1 = {}
            d1.value = d.amount
            d1.name = d.name
            series.data.push(d1)
          }

          this.chartPie = echarts.init(document.getElementById('chartPie'));
          this.chartPie.setOption({
            title: {
              text: '门店收入',
              subtext: 'top10',
              x: 'center'
            },
            tooltip: {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient: 'vertical',
              left: 'left',
              data: legend.data
            },
            series: [
              {
                name: '',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: series.data,
                itemStyle: {
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          });
        }
      })
    }
  }
</script>

<style scoped>
  .chart {
    width: 100%;
    float: left;
  }

  /*.chart div {
      height: 400px;
      float: left;
  }*/

  .el-col {
    padding: 30px 20px;
  }
</style>
