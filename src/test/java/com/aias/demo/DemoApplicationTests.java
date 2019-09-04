package com.aias.demo;

import com.aias.demo.flowable.service.FlowableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private FlowableService flowableService;

    @Test
    public void testAddNew() {
        String id = flowableService.addNewProcessDefinition("D:\\临时文件\\工作流\\流程\\leavevBill 1.bpmn");
        System.out.println("部署的流程定义id：" + id);
    }

    @Test
    public void testList() {
        System.out.println("流程定义列表：" + Arrays.toString(flowableService.listDefinition().toArray()));
    }

    @Test
    public void testQuery() {
        System.out.println(flowableService.queryProcessDefinitionById("myProcess1111:2:67031f04-cba9-11e9-aab7-005056c00008"));
    }

    @Test
    public void testStart() {
//        String id = flowableService.addNewProcessDefinition("D:\\临时文件\\工作流\\流程\\leavevBill 1.bpmn");
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("leaderGroup", "1");
//        System.out.println(flowableService.startNewProcessInstance(id, map));

        String id = "Expense:2:f6f5b179-cd2d-11e9-a612-005056c00008";
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", "zhangsan");
        map.put("money", "2000");
        System.out.println(flowableService.startNewProcessInstance(id, map));
    }

    @Test
    public void testInstanceList() {
        System.out.println("流程实例列表：" + Arrays.toString(flowableService.listInstanceByProcessDefinitionId("leaveBill:3:4b5f37ca-cbae-11e9-8068-005056c00008").toArray()));
    }

    @Test
    public void testGroup() {
//        System.out.println("创建的用户组：" + flowableService.addGroup("1", "test group"));
        System.out.println("用户组列表：" + Arrays.toString(flowableService.listGroup().toArray()));
    }

    @Test
    public void testUser() {
        flowableService.addUserToGroup("zhangsan", "张三", "1");
        System.out.println("用户下用户列表：" + Arrays.toString(flowableService.listGroupUser("1").toArray()));
    }


    @Test
    public void taskList() {
        System.out.println("当前待办任务列表：" + Arrays.toString(flowableService.listTaskByProcessInstanceId("4b6aa97b-cbae-11e9-8068-005056c00008").toArray()));
    }

    @Test
    public void testHistoryList() {
        System.out.println("已完成的流程实例列表：" + Arrays.toString(flowableService.listHistoricProcessInstance("leaveBill:3:4b5f37ca-cbae-11e9-8068-005056c00008").toArray()));
    }

    @Test
    public void testComplete() {
//        flowableService.completeTask(flowableService.listTaskByProcessInstanceId("4b6aa97b-cbae-11e9-8068-005056c00008").get(0).getId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        flowableService.completeTask(flowableService.listUserTask("Expense:2:f6f5b179-cd2d-11e9-a612-005056c00008", "zhangsan").get(0).getId(), map);
        System.out.println("当前待办任务列表：" + Arrays.toString(flowableService.listUserTask("Expense:2:f6f5b179-cd2d-11e9-a612-005056c00008", "zhangsan").toArray()));
    }


    @Test
    public void testUserTaskList() {
        System.out.println("当前待办任务列表：" + flowableService.listUserTask("Expense:2:f6f5b179-cd2d-11e9-a612-005056c00008", "zhangsan"));
    }


    @Test
    public void testQueryProcessInstanceDiagram() {

        try {
            byte[] result = flowableService.queryProcessInstanceDiagram("bf4dfe96-cd2e-11e9-b1bd-005056c00008");

            FileOutputStream fileOutputStream = new FileOutputStream("D://test.png");
            fileOutputStream.write(result);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
