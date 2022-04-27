package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.ProjectTask;
import fymxy.ckmp_server.entity.ProjectTaskMember;
import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.service.ProjectTaskMemberService;
import fymxy.ckmp_server.service.ProjectTaskService;
import fymxy.ckmp_server.service.UserService;
import fymxy.ckmp_server.vo.AddTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/project-task-member")
@Api(tags = "任务与人员的查增删")
public class ProjectTaskMemberController {
    @Autowired
    ProjectTaskMemberService projectTaskMemberService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectTaskService projectTaskService;


    @ApiOperation(value = "通过taskId查人员,返回人员列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"uid"})
    @GetMapping("/getByTaskId")
    private Respone getByTaskId(ProjectTaskMember projectTaskMember){
        ArrayList<User> res = new ArrayList<>();
        for (ProjectTaskMember taskMember : projectTaskMemberService.list(new QueryWrapper<ProjectTaskMember>()
                .eq("tid", projectTaskMember.getTaskId()))) {
            res.add(userService.getById(taskMember.getUid()));
        }
        return new Respone(200,"处理成功",res);
    }

    @ApiOperation(value = "插入人员")
    @ApiResponses({
            @ApiResponse(code = 200,message = "插入成功")
    })
    @PostMapping("/add")
    private Respone add(ProjectTaskMember projectTaskMember) {
        projectTaskMemberService.save(projectTaskMember);
        return new Respone(200,"插入成功",null);
    }

    @ApiOperation(value = "删除人员")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @DeleteMapping("/delete")
    private Respone delete(ProjectTaskMember projectTaskMember){
        projectTaskMemberService.remove(new QueryWrapper<ProjectTaskMember>()
                .eq("tid",projectTaskMember.getTaskId())
                .eq("uid",projectTaskMember.getUid()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "通过uid查任务,返回任务列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"taskId"})
    @GetMapping("/getTaskByUid")
    private Respone getTaskByUid(ProjectTaskMember projectTaskMember){
        ArrayList<ProjectTask> res = new ArrayList<>();
        for (ProjectTaskMember taskMember : projectTaskMemberService.list(new QueryWrapper<ProjectTaskMember>()
                .eq("uid", projectTaskMember.getUid()))) {
            res.add(projectTaskService.getById(taskMember.getTaskId()));
        }
        return new Respone(200,"处理成功",res);
    }

}
