package com.mirdar.taxi1;

public class DealConflict {
	
	//再这里对custRequest进行冲突检查，然后进行推荐，一旦推荐taxi,马上更新对应taxi的profit值
	//当存在冲突的情况，即当多个custRequest使用GetTaxi得到相同的taxi时，利用冲突处理
	//选择custRequest中profit最大的推荐给该taxi,然后针对每个custRequest的去除该taxi,再选择最大Balance的
	
}
