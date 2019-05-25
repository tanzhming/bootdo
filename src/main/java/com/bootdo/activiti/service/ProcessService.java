package com.bootdo.activiti.service;

import com.bootdo.system.domain.UserDO;
import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**

 */
@Service
public interface ProcessService {
    Model convertToModel(String procDefId) throws Exception;

    InputStream resourceRead(String id, String resType) throws Exception;

    /**
     * 流程授权用户 ,设置流程发发起人可见
     * @param deploymentId 流程部署id
     * @param authUserIds 授权用户id数组
     * @param user 当前处理用户
     */
    int flowAuth(Long deploymentId,String authUserIds, UserDO user);
}
