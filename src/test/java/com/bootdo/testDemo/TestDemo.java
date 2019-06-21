package com.bootdo.testDemo;

import com.alibaba.fastjson.JSON;
import com.bootdo.activiti.vo.ProcessVO;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {
    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    private UserService userService;
    @Autowired
    RepositoryService repositoryService;
    @Resource
    IdentityService identityService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("a", "b");
        System.out.println(redisTemplate.opsForValue().get("a"));
        System.out.println("success");
    }

    @Test
    public void test2() {
        UserDO user = userService.get(1L);
        int offset = 0,limit = 10;
       /* User u = identityService.newUser("123");
        u.setFirstName("sdfsfsfsdf");
        identityService.saveUser(u);*/
        identityService.setAuthenticatedUserId("1");
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().startableByUser(user.getUserId()+"")
                .listPage(offset, limit);
//        ProcessDefinition processDefinition1 = repositoryService.createProcessDefinitionQuery().deploymentId("157501").singleResult();
//        repositoryService.addCandidateStarterUser(processDefinition1.getId(), "1");
//        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().startableByUser(user.getUserId()+"").listPage(offset, limit);
        int count = (int) repositoryService.createProcessDefinitionQuery().count();
        List<Object> list = new ArrayList<>();
        for(ProcessDefinition processDefinition: processDefinitions){
            list.add(new ProcessVO(processDefinition));
        }
        PageUtils pageUtils = new PageUtils(list, count);
    }

    /**
     * 流程授权测试
     */
    @Test
    public void test3(){
        ProcessDefinition processDefinition1 = repositoryService.createProcessDefinitionQuery().deploymentId("217501").singleResult();
        repositoryService.addCandidateStarterUser(processDefinition1.getId(), "139");
//        repositoryService.addCandidateStarterGroup(processDefinition1.getId(), "1");
        System.out.println("success");
    }

    /**
     * 获取流程信息
     */
    @Test
    public void test4(){
//        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId("217501").singleResult();
        List<Task> admin = taskService.createTaskQuery().taskCandidateUser("admin").list();

        // 查询由某人发起流程
//        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().startedBy("tanzhiming").list();
//        System.out.println(JSON.toJSONString(list));

        // 查询某个用户的已办任务
        List<HistoricTaskInstance> hisTask = historyService.createHistoricTaskInstanceQuery().taskAssignee("tanzhiming").list();
        System.out.println(JSON.toJSONString(hisTask));
    }

    @Test
    public void test5(){
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup("研發一部").list();
        System.out.println(list.size());
        List<String> deptList = new ArrayList<>();
        deptList.add("研發一部");
        List<Task> list1 = taskService.createTaskQuery().taskCandidateGroupIn(deptList).list();
        System.out.println(list1.size());
        List<Task> list2 = taskService.createTaskQuery().taskCandidateOrAssigned("jruoning").list();
        System.out.println(list2);
    }
}
