import datetime
import pandas as pd
from sys import *
from jqdatasdk import *

auth('18800209419','Zh123456')
# stock_list = ['000001.XSHE', '000002.XSHE']  # java传来的股票列表
stock_list= argv[1:]
name_list = []
for stock in stock_list:
    name_list.append(get_security_info(stock).display_name)
today = datetime.datetime.today()
# 交易期间
if datetime.date(today.year, today.month, today.day) in get_all_trade_days() and 9 <= today.hour < 15 and today.minute >= 30:
    freq = '1m'  # 频率设为“分钟”
# 非交易期间
else:
    freq = '1d'  # 频率设为“天”
q = query(valuation.turnover_ratio).filter(valuation.code.in_(stock_list))
turnover_df = get_fundamentals(q, today).applymap(lambda x : '%.2f%%' % x)
turnover_df = turnover_df.T
turnover_df.columns = stock_list
panel = get_price(stock_list, end_date=today, count=1, frequency=freq, \
                  fields=['close', 'open', 'high', 'low', 'pre_close', 'volume', 'money'])
pre_close_df = panel.pre_close
close_df = panel.close
pre_close_df.index = [0]
close_df.index = [0]
ratio_df = (close_df / pre_close_df - 1).applymap(lambda x : "%.2f%%" % (x * 100))
volume_df = panel.volume.applymap(lambda x : "%.2f万" % (x / 1000000.0))
money_df = panel.money.applymap(lambda x : "%.2f亿" % (x / 100000000.0))
price_df = pd.concat([close_df, ratio_df, panel.open, panel.high, panel.low, pre_close_df, volume_df, money_df, turnover_df])
price_df.index = ['close', 'ratio', 'open', 'high', 'low', 'pre_close', 'volume', 'money', 'turnover']
price_df = price_df.T
price_df.insert(0, 'code', stock_list)
price_df.insert(1, 'name', name_list)
print(price_df.values.tolist())
# 股票代码 股票名称 当前价 涨跌幅 开盘 最高 最低 昨收 成交量 成交额 换手率