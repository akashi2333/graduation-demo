package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Project;
import fymxy.ckmp_server.entity.UserProject;
import fymxy.ckmp_server.service.ProjectService;
import fymxy.ckmp_server.service.UserProjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/project")
@Api(tags = "project 的增改查")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    UserProjectService userProjectService;

    @ApiOperation(value = "新建project操作")
    @ApiOperationSupport(ignoreParameters = {"project.pid","project.timestamp"})
    @ApiResponses({
            @ApiResponse(code = 200,message = "创建成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody Project project){
        project.setTimestamp(new Date());
        projectService.save(project);
        //插入到群-成员关系表
        userProjectService.save(new UserProject(project.getUid(),project.getPid()));
        return new Respone(200,"创建成功",null);
    }

    @ApiOperation(value = "修改project操作,前端需做核实拥有者身份")
    @ApiResponses({
            @ApiResponse(code = 200,message = "创建成功")
    })
    @PutMapping("/update")
    private Respone update(@RequestBody Project project){
        // TODO: 2022/4/5 根据时间是否为创建还是最后修改决定是否要求修改
        if (projectService.update(new UpdateWrapper<>(project))){
            return new Respone(200,"修改成功",null);
        }else {
            return new Respone(400,"修改失败",null);
        }
    }

    @ApiOperation(value = "根据pid查询project操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "创建成功")
    })
    @ApiOperationSupport(ignoreParameters = {"project.name","project.uid","project.state","project.timestamp"})
    @GetMapping("/getSingleProjectDetail")
    private Respone getSingleProjectDetail(Project project){
        Project res = projectService.getById(project.getPid());
        return new Respone(200,"查询成功",res);
    }
}
