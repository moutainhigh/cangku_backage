package cn.enn.wise.ssop.service.cms.util;


import cn.enn.wise.ssop.service.cms.entity.oldcms.LatAndLonEntity;

import java.util.List;

/**
 * 根据经纬度分析景区 ClassName: AnalyseScence date: 2017年11月14日 下午2:26:47
 * 
 * @author whz
 * @since JDK 1.8
 */
public class ConfirmScence {
	/**
	 * 判断当前点是否落在闭合区域内
	 * @author whz
	 * @param latlon
	 * @param latlons
	 * @return
	 * @since JDK 1.8
	 */
	public static boolean isPolygonContainsPoint(LatAndLonEntity latlon, List<LatAndLonEntity> latlons) {
		// 闭合区域点不得小于3个
		if (GeneUtil.isNullOrEmpty(latlons) || latlons.size() < 3)
			return false;
		int count = 0;
		for (int i = 0; i < latlons.size(); i++) {
			LatAndLonEntity p1 = latlons.get(i);
			LatAndLonEntity p2 = latlons.get((i + 1) % latlons.size());
			// 取多边形任意一个边,做点latlon的水平向右射线,求解与当前边的交点个数
			// p1p2是水平线段,要么没有交点,要么有无限个交点
			if (p1.getLon() == p2.getLon())
				continue;
			// point 在p1p2 底部 --> 无交点
			if (latlon.getLon() < Math.min(p1.getLon(), p2.getLon()))
				continue;
			// point 在p1p2 顶部 --> 无交点
			if (latlon.getLon() > Math.max(p1.getLon(), p2.getLon()))
				continue;
			// 求解 point点水平线与当前p1p2边的交点的 X 坐标
			double x = (latlon.getLon() - p1.getLon()) * (p2.getLat() - p1.getLat()) / (p2.getLon() - p1.getLon())
					+ p1.getLat();
			if (x > latlon.getLat()) // 当x=point.x时,说明point在p1p2线段上
				count++; // 只统计单边交点
		}
		// 单边交点为偶数，点在多边形之外
		return (count % 2 == 1);
	}
}
