package com.hz.app.evaluate.dao;

import java.util.List;
import java.util.Map;


public interface EveluateDao {
	/**
	 * 投诉建议列表 S0044
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> doComplaintProposalList(Map<String, String> map);
	
	/**
	 * 投诉建议 S0028
	 * @param map
	 * @return
	 */
	public int doComplaintProposal(Map<String, String> map);
	/**
	 * 可维修项目  S0068
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getRepairableProject(Map<String, String> map);
	
	
	
}
