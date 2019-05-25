package com.bootdo.activiti.service.impl;

import com.bootdo.activiti.dao.FlowAuthDao;
import com.bootdo.activiti.domain.FlowAuthDO;
import com.bootdo.activiti.service.FlowAuthService;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class FlowAuthServiceImpl implements FlowAuthService {
    @Autowired
    private FlowAuthDao flowAuthDao;

    @Override
    public FlowAuthDO get(Integer id) {
        return flowAuthDao.get(id);
    }

    @Override
    public List<FlowAuthDO> list(Map<String, Object> map) {
        return flowAuthDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return flowAuthDao.count(map);
    }

    @Override
    public int save(FlowAuthDO flowAuth, UserDO userDO) {
        Date date = new Date();
        flowAuth.setCreateDate(date);
        flowAuth.setCreateBy(userDO.getName());
        flowAuth.setUpdateDate(date);
        flowAuth.setUpdateBy(userDO.getName());
        flowAuth.setCreateId(userDO.getUserId());
        flowAuth.setUpdateId(userDO.getUserId());
        return flowAuthDao.save(flowAuth);
    }

    @Override
    public int update(FlowAuthDO flowAuth) {
        return flowAuthDao.update(flowAuth);
    }

    @Override
    public int remove(Integer id) {
        return flowAuthDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return flowAuthDao.batchRemove(ids);
    }

}
