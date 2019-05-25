package com.bootdo.activiti.dao;


import java.util.List;
import java.util.Map;

import com.bootdo.activiti.domain.FlowAuthDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程发起人授权
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-05-22 14:46:23
 */
@Mapper
public interface FlowAuthDao {

	FlowAuthDO get(Integer id);
	
	List<FlowAuthDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FlowAuthDO flowAuth);
	
	int update(FlowAuthDO flowAuth);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
