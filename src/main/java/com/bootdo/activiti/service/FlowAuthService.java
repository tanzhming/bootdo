package com.bootdo.activiti.service;


import com.bootdo.activiti.domain.FlowAuthDO;
import com.bootdo.system.domain.UserDO;

import java.util.List;
import java.util.Map;

/**
 * 流程发起人授权
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-05-22 14:33:57
 */
public interface FlowAuthService {
	
	FlowAuthDO get(Integer id);
	
	List<FlowAuthDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FlowAuthDO flowAuth, UserDO userDO);
	
	int update(FlowAuthDO flowAuth);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
