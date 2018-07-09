from datetime import datetime
import pandas as pd
import numpy as np
from sys import *
from jqdatasdk import *

auth('18800209419','Zh123456')
code = '000001.XSHG'
# dur_month = 120  # 月份数
dur_month=int(argv[1])
today = datetime.today()
zscore_window = 60  # zscore窗口
ma_window = 20      # ma窗口
count = dur_month * 21 + ma_window + zscore_window - 2  # 数据总数
price_df = get_price(code, end_date=today, count=count, fields='close')
price_df['ma'] = price_df['close'].rolling(ma_window).mean()
price_df = price_df.round(2)
price_df.dropna(inplace=True)
price_df['sub'] = price_df.close - price_df.ma  # 作差

zscore_list = [np.nan for i in range(zscore_window-1)]
for i in range(zscore_window-1, len(price_df)):
    range_data = price_df.values[i+1-zscore_window:i+1, 2]  # 一共zscore_window个数据
    mean = range_data.mean(); std = range_data.std()  # 计算mean和std
    zscore = round((price_df['sub'][i] - mean) / std, 2)
    zscore_list.append(zscore)
price_df['zscore'] = zscore_list
price_df['upper'] = 2
price_df['lower'] = -2
price_df.insert(0, 'date', [v.strftime('%Y-%m-%d') for v in price_df.index])
price_df.dropna(inplace=True)
print(price_df[['date', 'close', 'ma']].values.tolist())
# 日期 收盘价 ma20
print(price_df[['date', 'zscore', 'upper', 'lower']].values.tolist())
# 日期 市场情绪值 上界 下界