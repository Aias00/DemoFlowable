package com.aias.demo.flowable.service;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class FlowableService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 部署新的流程定义文件
     *
     * @param fileName
     * @return
     */
    public String addNewProcessDefinition(String fileName) {
        try {
            File file = new File(fileName);
            // 获取文件输入流
            InputStream fileInputSream = new FileInputStream(file);
            // 获取流程部署builder
            DeploymentBuilder deployment = repositoryService.createDeployment();
            // TODO 部署文件,除了bpmn20.xml/bpmn两种格式，还支持zip包等格式部署
            deployment.addInputStream(fileName, fileInputSream);
            // 部署
            Deployment deploy = deployment.deploy();
            // 调用api
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery().deploymentId(deploy.getId())
                    .singleResult();
            return processDefinition.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有已经部署的流程定义的列表
     *
     * @return
     */
    public List<ProcessDefinition> listDefinition() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

    /**
     * 根据id查询流程定义
     *
     * @param processDefinitionId
     * @return
     */
    public ProcessDefinition queryProcessDefinitionById(String processDefinitionId) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    /**
     * 根据流程定义发起流程实例
     *
     * @param processDefinitionId
     * @return
     */
    public ProcessInstance startNewProcessInstance(String processDefinitionId) {
        return startNewProcessInstance(processDefinitionId, null);
    }

    /**
     * 根据流程定义发起流程实例，参数以map:key-value形式传递
     *
     * @param processDefinitionId
     * @param variables
     * @return
     */
    public ProcessInstance startNewProcessInstance(String processDefinitionId, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceById(processDefinitionId, variables);
    }


    /**
     * 根据流程定义id查询所有流程实例列表
     *
     * @return
     */
    public List<ProcessInstance> listInstanceByProcessDefinitionId(String processDefinitionId) {
        return runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).list();
    }

    /**
     * 根据流程实例id查询流程实例信息
     *
     * @param processInstanceId
     * @return
     */
    public ProcessInstance queryProcessInstance(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 获取用户组列表
     *
     * @return
     */
    public List<Group> listGroup() {
        return identityService.createGroupQuery().list();
    }

    /**
     * 添加用户组
     *
     * @param groupName
     */
    public Group addGroup(String groupId, String groupName) {
        Group group = identityService.newGroup(groupId);
        group.setName(groupName);
        identityService.saveGroup(group);
        return group;
    }

    /**
     * 查询用户组
     *
     * @param groupId
     * @return
     */
    public Group queryGroup(String groupId) {
        return identityService.createGroupQuery().groupId(groupId).singleResult();
    }

    /**
     * 根据流程实例id查询未完成的任务列表
     *
     * @param processInstanceId
     * @return
     */
    public List<Task> listTaskByProcessInstanceId(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }

    /**
     * 根据id查询任务信息
     *
     * @param taskId
     * @return
     */
    public Task queryTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    /**
     * 设置任务为完成
     *
     * @param taskId
     */
    public void completeTask(String taskId) {
        completeTask(taskId, null);
    }

    /**
     * 完成任务，带参数
     *
     * @param taskId
     * @param variables
     */
    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    /**
     * 根据流程定义id查询已完成的流程实例列表
     *
     * @param processDefinitionId
     * @return
     */
    public List<HistoricProcessInstance> listHistoricProcessInstance(String processDefinitionId) {
        return historyService.createHistoricProcessInstanceQuery().processDefinitionId(processDefinitionId).list();
    }

    /**
     * 根据流程实例id查询已完成的流程实例信息
     *
     * @param processInstanceId
     * @return
     */
    public HistoricProcessInstance queryHistoricProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

}
