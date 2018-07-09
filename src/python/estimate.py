from jqdatasdk import *
from sys import *

auth('18800209419','Zh123456')


from datetime import datetime

code = '601238.XSHG'  # 股票代码
ind_code = 'C36'  # 行业代码
# 以上两个都是java传过来的
today = datetime.today()
stock_list = get_industry_stocks(ind_code)
q = query(valuation.pe_ratio, valuation.circulating_market_cap, valuation.circulating_cap).filter(valuation.code.in_(stock_list))
panel = get_fundamentals_continuously(q, today, 10000//len(stock_list))
pe_df = panel.xs('pe_ratio', axis=0)
single_pe_series = pe_df[code].round(2)
mc_df = panel.xs('circulating_market_cap', axis=0)
cap_df = panel.xs('circulating_cap', axis=0)
price_df = mc_df * 10000 / cap_df  # 算股价
earning_series = (price_df / pe_df).T.sum()  # 算净利润和
mc_series = mc_df.T.sum()  # 算市值和
avg_pe_df = (mc_series / (earning_series * 10)).to_frame('avg_pe').round(2)  # 算平均市盈率
avg_pe_df['single_pe'] = single_pe_series
avg_pe_df.insert(0, 'date', avg_pe_df.index)
print(avg_pe_df.values.tolist())