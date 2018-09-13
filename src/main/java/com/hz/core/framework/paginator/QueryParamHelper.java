package com.hz.core.framework.paginator;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import org.apache.commons.lang.math.NumberUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询条件统一封装处理类
 *
 *
 */
public class QueryParamHelper {

	/**
	 * 返回下载时的PageBounds（不分页）
	 *
	 * @return
	 */
	public static PageBounds getDownloadPageBounds() {

		PageBounds pageBounds = new PageBounds(1, 65535);
		pageBounds.setAsyncTotalCount(false);
		pageBounds.setContainsTotalCount(false);

		return pageBounds;
	}

	public static PageBounds getPageBounds(Map<String, String> paramMap) {

		String paramPage = paramMap.get("page");
		String paramPageSize = paramMap.get("pageSize");
		
		int page = NumberUtils.toInt(paramPage, 1); // 页号
		int pageSize = NumberUtils.toInt(paramPageSize, 2); // 每页数据条数

		// String sortString = "age.asc,gender.desc";//如果你想排序的话逗号分隔可以排序多列
		PageBounds pageBounds = new PageBounds(page, pageSize);
		pageBounds.setAsyncTotalCount(false);
		pageBounds.setContainsTotalCount(true);

		return pageBounds;
	}
	
	public static PageBounds getPageBoundsForMobile(Map<String, Object> paramMap) {

		String paramPage = String.valueOf(paramMap.get("PageIndex"));
		String paramPageSize = String.valueOf(paramMap.get("PageSize"));
		
		int page = NumberUtils.toInt(paramPage, 1); // 页号
		int pageSize = NumberUtils.toInt(paramPageSize, 2); // 每页数据条数

		// String sortString = "age.asc,gender.desc";//如果你想排序的话逗号分隔可以排序多列
		PageBounds pageBounds = new PageBounds(page, pageSize);
		pageBounds.setAsyncTotalCount(false);
		pageBounds.setContainsTotalCount(true);

		return pageBounds;
	}

	//public static Map<String, Object> getPagedMap(List<Map<String, Object>> detailList) {
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getPagedMap(List detailList) {

		PageList value = (PageList) detailList;

		Map<String, Object> map = new HashMap<String, Object>();
		Paginator paginator = value.getPaginator();
		map.put("totalCount", paginator.getTotalCount());
		map.put("totalPages", paginator.getTotalPages());
		map.put("page", paginator.getPage());
		map.put("pageSize", 2);
		map.put("pageIndex", PageIndex.getPageIndex(5, paginator.getPage(), paginator.getTotalPages()));
		map.put("limit", paginator.getLimit());
		map.put("voList", value);
		map.put("startRow", paginator.getStartRow());
		map.put("endRow", paginator.getEndRow());
		map.put("offset", paginator.getOffset());
		map.put("slider", paginator.getSlider());
		map.put("prePage", paginator.getPrePage());
		map.put("nextPage", paginator.getNextPage());
		map.put("firstPage", paginator.isFirstPage());
		map.put("hasNextPage", paginator.isHasNextPage());
		map.put("hasPrePage", paginator.isHasPrePage());
		map.put("lastPage", paginator.isLastPage());
		return map;
	}
}
