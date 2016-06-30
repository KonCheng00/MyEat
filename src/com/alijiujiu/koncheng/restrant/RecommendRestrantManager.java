package com.alijiujiu.koncheng.restrant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class RecommendRestrantManager implements RecommendRestrantInter {

	private int MAX_ORI_MAN = 30;
	private int MAX_REC_RESTRANT = 10;
	private Context context;

	public RecommendRestrantManager(Context context) {
		this.context = context;
	}

	@Override
	public Map<String, List<FondRestrant>> groupByUserId(List<FondRestrant> list) {
		// 按 用户id 分组
		Map<String, List<FondRestrant>> map = new HashMap<String, List<FondRestrant>>();
		if (list == null || list.size() == 0) {
			
		} else {
			for (Iterator<FondRestrant> it = list.iterator(); it.hasNext();) {
				FondRestrant restrant = (FondRestrant) it.next();

				// 存在对应的List直接添加
				if (map.containsKey(restrant.getUserId())) {
					List<FondRestrant> list_ = map.get(restrant.getUserId());
					list_.add(restrant);
				} else { // 不存在对应的List新建后添加
					List<FondRestrant> newList = new ArrayList<FondRestrant>();
					newList.add(restrant);
					map.put(restrant.getUserId(), newList);
				}
			}
		}
		return map;

	}

	@Override
	public Map<String, Double> getSimilarity(Map<String, List<FondRestrant>> userIdmap,
			List<FondRestrant> myFondRestrant) {

		if (userIdmap == null || userIdmap.size() == 0 || myFondRestrant == null || myFondRestrant.size() == 0) {
			Toast.makeText(context, "推荐失败了", Toast.LENGTH_LONG).show();
			return null;
		} else {
			// 去掉自己喜欢的餐厅列表
			if (userIdmap.containsKey(myFondRestrant.get(0).getUserId()))
				userIdmap.remove(myFondRestrant.get(0).getUserId());

			Map<String, Double> map = new HashMap<String, Double>();
			for (Map.Entry<String, List<FondRestrant>> entry : userIdmap.entrySet()) {
				List<FondRestrant> list = entry.getValue();

				// 寻找喜欢相同餐厅的数量
				int commen = 0;
				for (FondRestrant restrant : myFondRestrant) {
					for (FondRestrant restrant_ : list) {
						if (restrant.getRestrantId() != null && restrant_.getRestrantId() != null)
							if (restrant.getRestrantId().equals(restrant_.getRestrantId())) {
								commen++;
								break;
							}
					}
				}
				// 计算相似度
				double similarity = (commen * 1.0) / (Math.sqrt(list.size() * myFondRestrant.size() * 1.0));
				map.put(entry.getKey(), similarity);
			}

			// 按相似度从大到小排序
			Map<String, Double> sortedMap = sortMapByValue(map);

			// 取前N名
			if (sortedMap.size() <= MAX_ORI_MAN) {

				for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
					Log.i("getSimilarity", entry.getKey() + " : " + entry.getValue());
				}

				return sortedMap;
			} else {
				int count = 0;
				Map<String, Double> finalMap = new HashMap<String, Double>();
				for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
					finalMap.put(entry.getKey(), entry.getValue());
					count++;
					if (count >= MAX_ORI_MAN)
						break;
				}
				return finalMap;
			}
		}

	}

	@Override
	public Map<String, Double> getRecommendRestrantMap(Map<String, Double> similarityMap,
			Map<String, List<FondRestrant>> userIdmap) {
		// TODO Auto-generated method stub

		// 两个参数map的key都是userId
		if (userIdmap == null || userIdmap.size() == 0 || similarityMap == null || similarityMap.size() == 0) {
			Toast.makeText(context, "推荐失败了", Toast.LENGTH_LONG).show();
			return null;
		} else {
			Map<String, List<FondRestrant>> map = new HashMap<String, List<FondRestrant>>();
			List<FondRestrant> list = new ArrayList<FondRestrant>();
			Map<String, Double> interestDegreeMap = new HashMap<String, Double>();
			// 找到这些人喜欢的餐厅列表 注：这里没有去除目标用户已经喜欢过的餐厅
			for (Map.Entry<String, Double> entry : similarityMap.entrySet()) {
				map.put(entry.getKey(), userIdmap.get(entry.getKey()));
			}
			// 把餐厅都放到List里
			for (Map.Entry<String, List<FondRestrant>> entry : map.entrySet()) {
				List<FondRestrant> list1 = entry.getValue();
				for (FondRestrant restrant : list1) {
					if (!list.contains(restrant)) {
						list.add(restrant);
					}
				}
			}
			// 计算感兴趣度
			for (FondRestrant restrant : list) {
				double interestDegree = 0.0;
				for (Map.Entry<String, List<FondRestrant>> entry : map.entrySet()) {
					List<FondRestrant> list1 = entry.getValue();
					for (FondRestrant restrant1 : list1) {
						if (restrant1.getRestrantId().equals(restrant.getRestrantId())) {
							double similarity = 0.0;
							similarity = similarityMap.get(entry.getKey());
							interestDegree += similarity;
							break;
						}
					}
				}
				if (!interestDegreeMap.containsKey(restrant.getRestrantId()))
					interestDegreeMap.put(restrant.getRestrantId(), interestDegree);
			}

			// 感兴趣度排序
			Map<String, Double> sortedMap = sortMapByValue(interestDegreeMap);
			// 取前N名
			if (sortedMap.size() <= MAX_REC_RESTRANT) {

				for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
					Log.i("getRecommendRestrantMap", entry.getKey() + " : " + entry.getValue());
				}

				return sortedMap;
			} else {
				int count = 0;
				Map<String, Double> finalMap = new HashMap<String, Double>();
				for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
					finalMap.put(entry.getKey(), entry.getValue());
					count++;
					if (count >= MAX_REC_RESTRANT)
						break;
				}

				/*
				 * for (Map.Entry<String, Double> entry : finalMap.entrySet()) {
				 * Log.i("getRecommendRestrantMap", entry.getKey() + " : " +
				 * entry.getValue()); }
				 */

				return finalMap;
			}
		}
	}

	@Override
	public String recommendRestrant(Map<String, Double> recommendRestrantMap) {
		// TODO Auto-generated method stub

		if (recommendRestrantMap == null || recommendRestrantMap.size() == 0) {
			Toast.makeText(context, "推荐失败了", Toast.LENGTH_LONG).show();
			return null;
		} else {
			String recommendRestrantId = null;
			int recommend = new Random().nextInt(recommendRestrantMap.size());
			int count = 0;
			for (Map.Entry<String, Double> entry : recommendRestrantMap.entrySet()) {
				if (count == recommend) {
					recommendRestrantId = entry.getKey();
					break;
				}
				count++;
			}
			return recommendRestrantId;
		}
	}

	private Map<String, Double> sortMapByValue(Map<String, Double> oriMap) {
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(oriMap.entrySet());
			Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
				public int compare(Entry<String, Double> entry1, Entry<String, Double> entry2) {
					double value1 = 0.0, value2 = 0.0;
					try {
						value1 = entry1.getValue();
						value2 = entry2.getValue();
					} catch (NumberFormatException e) {
						value1 = 0.0;
						value2 = 0.0;
					}
					if (value2 > value1)
						return 1;
					else if (value2 == value1)
						return 0;
					else
						return -1;

				}
			});
			Iterator<Map.Entry<String, Double>> iter = entryList.iterator();
			Map.Entry<String, Double> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}

	public int getMAX_ORI_MAN() {
		return MAX_ORI_MAN;
	}

	public void setMAX_ORI_MAN(int mAX_ORI_MAN) {
		MAX_ORI_MAN = mAX_ORI_MAN;
	}

	public int getMAX_REC_RESTRANT() {
		return MAX_REC_RESTRANT;
	}

	public void setMAX_REC_RESTRANT(int mAX_REC_RESTRANT) {
		MAX_REC_RESTRANT = mAX_REC_RESTRANT;
	}
}
