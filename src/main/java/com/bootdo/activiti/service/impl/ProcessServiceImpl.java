package com.bootdo.activiti.service.impl;

import com.bootdo.activiti.domain.FlowAuthDO;
import com.bootdo.activiti.service.FlowAuthService;
import com.bootdo.activiti.service.ProcessService;
import com.bootdo.common.exception.BDException;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**

 */
@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Resource
    private FlowAuthService flowAuthService;
    @Resource
    private UserService userService;


    @Override
    public Model convertToModel(String procDefId) throws Exception {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        org.activiti.engine.repository.Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getCategory());//.getDeploymentId());
        modelData.setDeploymentId(processDefinition.getDeploymentId());
        modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count() + 1)));

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return modelData;
    }

    @Override
    public InputStream resourceRead(String id, String resType) throws Exception {


        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();


        String resourceName = "";
        if (resType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }

        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        return resourceAsStream;
    }

    @Override
    public int flowAuth(Long deploymentId,String authUserIds, UserDO user) {
        int count = 0;
        if (deploymentId == null) {
            throw new BDException("参数异常,授权流程不能为空");
        }
        if (StringUtils.isBlank(authUserIds)){
            throw new BDException("参数异常,授权用户不能为空");
        }
        String[] userIds = authUserIds.split(",");
        for (String userId : userIds) {
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId + "").singleResult();
            FlowAuthDO flowAuthDO = new FlowAuthDO();
            flowAuthDO.setFlowId(deploymentId);
            flowAuthDO.setFlowName(deployment.getName());
            flowAuthDO.setUserId(Long.valueOf(userId));
            UserDO userDO = userService.get(Long.valueOf(userId));
            flowAuthDO.setUserName(userDO.getName());
            int num = flowAuthService.save(flowAuthDO, user);
            count += num;
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId+"").singleResult();
            repositoryService.addCandidateStarterUser(processDefinition.getId(), userId);
        }
        return count;
    }
}
