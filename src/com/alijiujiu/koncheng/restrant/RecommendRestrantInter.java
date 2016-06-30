package com.alijiujiu.koncheng.restrant;

import java.util.List;
import java.util.Map;

import org.junit.runners.Parameterized.Parameters;
/*
 * 推荐餐厅过程需要用到的方法
 * 
 */
public interface RecommendRestrantInter {

	// 按用户ID分组
	public abstract Map<String, List<FondRestrant>> groupByUserId(List<FondRestrant> list);

	// 计算余弦相似度
	public abstract Map<String, Double> getSimilarity(Map<String, List<FondRestrant>> userIdmap,
			List<FondRestrant> myFondRestrant);

	// 获取喜欢的餐厅列表
	public abstract Map<String, Double> getRecommendRestrantMap(Map<String, Double> similarityMap,
			Map<String, List<FondRestrant>> userIdmap);

	// 获得推荐
	public abstract String recommendRestrant(Map<String, Double> recommendRestrantMap);
}
