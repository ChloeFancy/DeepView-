### 板块划分

* 实时股价：
    * echarts：http://echarts.baidu.com/examples/editor.html?c=candlestick-sh-2015  
    * api：http://118.25.108.103:8080/deepview/chart/realTimePrice?secuCode=000001.XSHE
    * 返回数据：{证券名，绘制数据[[日期，开盘价，收盘价，最低价，最高价]，...]}
    
* 股价异动：
    * echarts：表格（以后可能会增加http://echarts.baidu.com/examples/editor.html?c=area-simple ）
    * api：http://118.25.108.103:8080/deepview/chart/abnormal?secuCode=601238.XSHG
    * 返回数据：
    ```python 
    print(suceName) 
    print(stat_list)
    # 二维数组，每列含义如下：
    # 日期  异动类型  当日总成交额
    print(value_list)
    # 三维数组，每个元素对应上面一个日期，每个元素是二维数组，其中每列含义如下：
    # 买卖方向  排名  营业部名称  买入金额  买入金额占比  卖出金额  卖出金额占比  买卖净额
    ```

* 走势预测：http://echarts.baidu.com/examples/editor.html?c=candlestick-sh-2015

* 相关性分析(n支股票相关性)：
    * echarts：http://echarts.baidu.com/examples/editor.html?c=heatmap-cartesian
    * api：http://118.25.108.103:8080/deepview/chart/correlation?secuCode=000001.XSHG&secuCode=000002.XSHE&secuCode=000004.XSHG&secuCode=000005.XSHE&startDate=2017-1-1&endDate=2018-1-1
    * 返回数据：{secuNames[证券名1，证券名2, ...]，绘制数据[n行n列]}

* 回归分析(两支股票之间)：
    * http://echarts.baidu.com/examples/editor.html?c=scatter-linear-regression
    * api：http://118.25.108.103:8080/deepview/chart/regression?secuCode1=000001.XSHG&secuCode2=000002.XSHE&startDate=2017-1-1&endDate=2018-1-1
    * 返回数据：{证券名1，证券名2，图例参数[样本数，alpha，beta，x^2]，绘制数据[[日期，股价1，股价2]，...]}

* 价差分析(两支股票比较)：
    * echarts：http://echarts.baidu.com/examples/editor.html?c=line-stack
    * api：http://118.25.108.103:8080/deepview/chart/priceCompare?secuCode1=000001.XSHE&secuCode2=000002.XSHE&startDate=2017-1-1&endDate=2018-1-1
    * 返回数据：{证券名1，证券名2，绘制数据[[日期，股价1，股价2，差值]，...]}

* 季节图表(显示一支股票同一天不同年份的股价)：
    * echarts：http://echarts.baidu.com/examples/editor.html?c=line-stack
    * api：http://118.25.108.103:8080/deepview/chart/season?secuCode=000001.XSHG&yearNum=3
    * 返回值：{证券名，绘制数据[[今年1月1号股价，去年..., ...]，[今年1月2号股价，去年..., ...]，...]}

* 历史波动率：
    * echarts：http://echarts.baidu.com/examples/editor.html?c=line-stack
    * api：http://118.25.108.103:8080/deepview/chart/history?secuCode=000001.XSHE&monthNum=6
    * 返回值：{证券名，图例[hv5, hv15, hv30]，绘制数据[[日期，hv5波动率，hv15波动率，hv30波动率]，...}

* 历史回看：
    * echarts：http://echarts.baidu.com/examples/editor.html?c=pie-custom
    * api：http://118.25.108.103:8080/deepview/chart/historyReview?secuCode=601238.XSHG
    * 返回数据：  
    ```python
    print(name)
    print(month_ratio_list)
    # 每个月的收益，三维数组
    # [[['2013-01', 0.0993], ['2014-01', -0.0911], ...], [['2013-02', -0.0497], ...], ...]
    #  -------------------这是每年1月的------------------  --------这是每年2月的-------

    print(month_stat)
    # 1-12月每月的涨、跌、平均收益，二维数组
    # [[2, 4, -0.0259], ...]
    #  ----这是1月的---

    print(weekday_stat)
    # 周1-周5每天的涨、跌、平均收益，二维数组，和month_stat类似
    ```
* 股票估值：
   * echarts：http://echarts.baidu.com/examples/editor.html?c=line-stack
   * api：http://118.25.108.103:8080/deepview/chart/estimate?secuCode=601238.XSHG
   * 返回数据：
   ```
   {
      chartData[[日期,行业平均市盈率,个股市盈率],...]
   }
   ```

* 自选股：
   * api：http://118.25.108.103:8080/deepview/user/myStocks?userID=1

* 市场情绪：
   * echarts：http://echarts.baidu.com/examples/editor.html?c=candlestick-sh-2015
   * api：http://118.25.108.103:8080/deepview/chart/marketEmotion?monthNum=30
   * 返回数据：  
   ```
   {  
      chartData1[['date（日期）'，'close（收盘价）', 'ma20（二十天均值）'],...]，  
      chartData2[['date（日期）'，'zscore（市场情绪值）', 'upper（上界）', 'lower（下界）'],...]  
   }  
   ```

* 证券表行业表更新：
   * api：http://118.25.108.103:8080/deepview/security/update （post：name: admin, password: admin）

* 普通用户登录：
   * api：http://118.25.108.103:8080/deepview/user/login （post：name: 1, password: 1）
   * 返回数据：id

* 管理员登录：
   * api：http://118.25.108.103:8080/deepview/admin/login （post：name: admin, password: admin）
   * 返回数据：id
   
### 环境配置
* jqdatasdk安装：https://www.joinquant.com/post/12479
