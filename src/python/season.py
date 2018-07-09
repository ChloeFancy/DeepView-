import pandas as pd
from jqdatasdk import *
from datetime import datetime
from sys import *

auth('18800209419','Zh123456')
# code = '000001.XSHE'
# dur_year = 5  # 年份数
code=argv[1]
dur_year=int(argv[2])
name = get_security_info(code).display_name
today = datetime.today()
date_list = [datetime.strftime(x,'%m-%d') for x in list(pd.date_range(start='2016-1-1', end='2016-12-31'))]
price_df = pd.DataFrame(date_list, columns=['date'])
for i in range(dur_year):
    start = '%d-1-1' % (today.year - i)
    end = today if i == 0 else '%d-12-31' % (today.year - i)
    price_temp = get_price(code, start, end, fields='close')
    price_temp.columns = [today.year - i]
    price_temp['date'] = [datetime.strftime(x,'%m-%d') for x in price_temp.index.tolist()]
    price_df = pd.merge(price_df, price_temp, how='left', on='date')
price_df.fillna(method='ffill', inplace=True)
trans_df = price_df.T
trans_df[trans_df.isnull()] = '-'
price_df = trans_df.T
print(name)
print(price_df.values.tolist())