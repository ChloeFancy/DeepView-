from datetime import datetime
from jqdatasdk import *
from sys import *

auth('18800209419','Zh123456')
# code = '000001.XSHE'
code=argv[1]
name = get_security_info(code).display_name
start = get_security_info(code).start_date
end   = datetime.today()
billboard_df = get_billboard_list(code, start, end)
billboard_df['day'] = [v.strftime('%Y-%m-%d') for v in billboard_df.day]
del billboard_df['id']; del billboard_df['code']; del billboard_df['total_value']; del billboard_df['abnormal_code']
#billboard_df.sort_index(by=['day'], ascending=False, inplace=True)
#print(billboard_df)
stat_list = []
value_list = []
group_list = sorted(list(billboard_df.groupby('day')), key=lambda x:x[0], reverse=True)
for date, value_df in group_list:
    stat_list.append([date, value_df.abnormal_name.values[0], value_df.amount.values[0]])
    del value_df['abnormal_name']; del value_df['day']; del value_df['amount']
    trans_df = value_df.T
    trans_df[trans_df.isnull()] = '-'
    value_df = trans_df.T
    value_df.sort_index(by=['direction', 'rank'], inplace=True)
    value_list.append(value_df.values.tolist())

print(name)
print(stat_list)
# 二维数组，每列含义如下：
# 日期  异动类型  当日总成交额
print(value_list)
# 三维数组，每个元素对应上面一个日期，每个元素是二维数组，其中每列含义如下：
# 买卖方向  排名  营业部名称  买入金额  买入金额占比  卖出金额  卖出金额占比  买卖净额