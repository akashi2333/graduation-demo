package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Project;
import fymxy.ckmp_server.entity.ProjectTask;
import fymxy.ckmp_server.entity.ProjectTaskMember;
import fymxy.ckmp_server.entity.TeamJoin;
import fymxy.ckmp_server.service.ProjectService;
import fymxy.ckmp_server.service.ProjectTaskMemberService;
import fymxy.ckmp_server.service.ProjectTaskService;
import fymxy.ckmp_server.vo.AddTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/project-task")
@Api(tags = "任务的增删查改")
public class ProjectTaskController {
    @Autowired
    ProjectTaskService projectTaskService;
    @Autowired
    ProjectTaskMemberService projectTaskMemberService;
    @Autowired
    ProjectService projectService;

    @ApiOperation(value = "新建task操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "创建成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody AddTask addTask){
        ProjectTask projectTask = addTask.getProjectTask();
        int[] members = addTask.getMembers();
        projectTaskService.save(projectTask);
        ProjectTask task = projectTaskService.getOne(new QueryWrapper<ProjectTask>()
                .eq("pid", projectTask.getPid())
                .eq("detail", projectTask.getDetail()));
        Project project = projectService.getById(task.getPid());
        projectTaskMemberService.save(new ProjectTaskMember(task.getTaskId(),project.getUid()));
        for (int uid : members) {
            projectTaskMemberService.save(new ProjectTaskMember(task.getTaskId(),uid));
        }
        return new Respone(200,"创建成功",task);
    }

    @ApiOperation(value = "删除task")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"state","end","begin","detail","pid"})
    @DeleteMapping("/delete")
    private Respone delete(ProjectTask projectTask){
        projectTaskService.remove(new QueryWrapper<ProjectTask>()
                .eq("tid",projectTask.getTaskId()));
        projectTaskMemberService.remove(new QueryWrapper<ProjectTaskMember>()
                .eq("tid",projectTask.getTaskId()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "通过pid查找task")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"state","end","begin","detail","taskId"})
    @GetMapping("/getByPid")
    private Respone getByPid(ProjectTask projectTask){
        return new Respone(200,"处理成功",
                projectTaskService.list(new QueryWrapper<ProjectTask>()
                        .eq("pid",projectTask.getPid())));
    }

    @ApiOperation(value = "通过taskId查找task")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"state","end","begin","detail","pid"})
    @GetMapping("/getByTaskId")
    private Respone getByTaskId(ProjectTask projectTask){
        return new Respone(200,"处理成功",
                projectTaskService.list(new QueryWrapper<ProjectTask>()
                        .eq("tid",projectTask.getTaskId())));
    }

    @ApiOperation(value = "修改task")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @PutMapping("/update")
    private Respone update(ProjectTask projectTask){
        projectTaskService.update(projectTask,new UpdateWrapper<ProjectTask>()
                .eq("tid",projectTask.getTaskId()));
        return new Respone(200,"处理成功",null);
    }
}
