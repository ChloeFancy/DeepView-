import pandas as pd
from datetime import datetime
from jqdatasdk import *
from sys import *

auth('18800209419','Zh123456')

today = datetime.today()
IndMain_df = get_industries(name='zjw')  # 证监会行业
IndMain_df.start_date = [v.strftime('%Y-%m-%d') for v in IndMain_df.start_date]
IndMain_df.insert(0, 'code', IndMain_df.index)
SecuMain_df = get_all_securities()
del SecuMain_df['type']
SecuMain_df.columns = ['SecuAbbr', 'ChiSpelling', 'ListedDate', 'EndDate']
SecuMain_df.ListedDate = [v.strftime('%Y-%m-%d') for v in SecuMain_df.ListedDate]
SecuMain_df.EndDate = [v.strftime('%Y-%m-%d') for v in SecuMain_df.EndDate]
SecuMain_df.insert(0, 'SecuCode', SecuMain_df.index)

Ind_df = pd.DataFrame()
c = 1
for code in IndMain_df.code:
    code_list = get_industry_stocks(code, today)
    temp = pd.DataFrame()
    temp['SecuCode'] = code_list
    temp['IndID'] = [c for i in range(len(temp))]
    c += 1
    Ind_df = pd.concat([Ind_df, temp], axis=0)
SecuMain_df = pd.merge(SecuMain_df, Ind_df, how='inner', on='SecuCode')
SecuMain_df.IndID = list(map(int, SecuMain_df.IndID))  # 强转int
SecuMain_df.sort_values(by='SecuCode', inplace=True)
SecuMain_df.reset_index(drop=True, inplace=True)
# 注意每次更新时行业表的ID从1开始自增！！
print(IndMain_df.values.tolist())
print(SecuMain_df.values.tolist())