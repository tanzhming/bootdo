package com.bootdo.activiti.controller;

import com.alibaba.fastjson.JSON;
import com.bootdo.activiti.service.ActTaskService;
import com.bootdo.activiti.vo.ProcessVO;
import com.bootdo.activiti.vo.TaskVO;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**

 */
@RequestMapping("activiti/task")
@RestController
public class TaskController {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    FormService formService;
    @Autowired
    TaskService taskService;
    @Autowired
    ActTaskService actTaskService;
    @Resource
    IdentityService identityService;
    @Resource
    ProcessEngine processEngine;
    @Resource
    private DeptService deptService;
    @Resource
    private HistoryService historyService;

    @GetMapping("goto")
    public ModelAndView gotoTask(){
        return new ModelAndView("act/task/gotoTask");
    }

    @GetMapping("/gotoList")
    PageUtils list(int offset, int limit) {
        UserDO user = ShiroUtils.getUser();
       /* User u = identityService.newUser("123");
        u.setFirstName("sdfsfsfsdf");
        identityService.saveUser(u);*/
//        identityService.setAuthenticatedUserId("1");
//        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
//                .listPage(offset, limit);
//        ProcessDefinition processDefinition1 = repositoryService.createProcessDefinitionQuery().deploymentId("182501").singleResult();
//        repositoryService.addCandidateStarterUser(processDefinition1.getId(), user.getUserId()+"");
        // 查询发起人为当前用户的流程
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().startableByUser(user.getUserId()+"").listPage(offset, limit);
        int count = (int) repositoryService.createProcessDefinitionQuery().count();
        List<Object> list = new ArrayList<>();
        for(ProcessDefinition processDefinition: processDefinitions){
            list.add(new ProcessVO(processDefinition));
        }

        PageUtils pageUtils = new PageUtils(list, count);
        return pageUtils;
    }

    @GetMapping("/form/{procDefId}")
    public void startForm(@PathVariable("procDefId") String procDefId  ,HttpServletResponse response) throws IOException {
        String formKey = actTaskService.getFormKey(procDefId, null);
        response.sendRedirect(formKey);
    }

    @GetMapping("/form/{procDefId}/{taskId}")
    public void form(@PathVariable("procDefId") String procDefId,@PathVariable("taskId") String taskId ,HttpServletResponse response) throws IOException {
        // 获取流程XML上的表单KEY

        String formKey = actTaskService.getFormKey(procDefId, taskId);


        response.sendRedirect(formKey+"/"+taskId);
    }

    @GetMapping("/todo")
    ModelAndView todo(){
        return new ModelAndView("act/task/todoTask");
    }

    @GetMapping("/todoList")
    @ResponseBody
    List<TaskVO> todoList(){
        String username = ShiroUtils.getUser().getUsername();
//        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
        // 查询当前用户的待办任务
        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
        DeptDO deptDO = deptService.get(ShiroUtils.getUser().getDeptId());
        String deptName = deptDO.getName();
        // 查询单签角色待办任务
        List<Task> task2 = taskService.createTaskQuery().taskCandidateGroup(deptName).list();
        task2.removeAll(tasks);
        tasks.addAll(task2);
        List<TaskVO> taskVOS =  new ArrayList<>();
        for(Task task : tasks){
            TaskVO taskVO = new TaskVO(task);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }


    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/trace/photo/{procDefId}/{execId}")
    public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
        InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 流程跟踪(查询我发起的的流程信息)
     */
    @GetMapping("/trackTask")
    @ResponseBody
    List<TaskVO> trackTask(){
        String username = ShiroUtils.getUser().getUsername();
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().startedBy(username).list();
        System.out.println(JSON.toJSONString(list));
        List<TaskVO> taskVOS =  new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance : list) {
            TaskVO taskVO = new TaskVO(historicProcessInstance);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }

}
