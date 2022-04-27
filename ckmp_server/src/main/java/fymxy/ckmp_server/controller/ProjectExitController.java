package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.ProjectExit;
import fymxy.ckmp_server.entity.ProjectJoin;
import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.service.ProjectExitService;
import fymxy.ckmp_server.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/project-exit")
@Api(tags = "project申请 退退退 的增删查")
public class ProjectExitController {
    @Autowired
    ProjectExitService projectExitService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "申请退项目操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "申请成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody ProjectExit projectExit){
        if (projectExitService.list(new QueryWrapper<ProjectExit>()
                .eq("uid",projectExit.getUid())
                .eq("pid",projectExit.getPid())).size()!=0){
            return new Respone(200,"已申请",null);
        }
        projectExitService.save(projectExit);
        return new Respone(200,"申请成功",null);
    }

    @ApiOperation(value = "删除对应申请退项目操作的记录（即处理了此请求）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @DeleteMapping("/delete")
    private Respone delete( ProjectExit projectExit){
        projectExitService.remove(new QueryWrapper<ProjectExit>().eq("uid",projectExit.getUid()).eq("pid",projectExit.getPid()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "根据pid查询退项目人员id，返回user的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"uid"})
    @GetMapping("/getByProjectId")
    private Respone getByProjectId( ProjectExit projectExit){
        ArrayList<User> res = new ArrayList<>();
        for (ProjectExit exit : projectExitService.list(new QueryWrapper<ProjectExit>()
                .eq("pid", projectExit.getPid()))) {
            res.add(userService.getById(exit.getUid()));
        }
        return new Respone(200,"处理成功",res);
    }

    @ApiOperation(value = "根据uid查询退的项目，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"pid"})
    @GetMapping("/getByUserId")
    private Respone getByUserId(ProjectExit projectExit){
        ArrayList<Integer> res = new ArrayList<>();
        for (ProjectExit exit : projectExitService.list(new QueryWrapper<ProjectExit>()
                .eq("uid", projectExit.getUid()))) {
            res.add(exit.getPid());
        }
        return new Respone(200,"处理成功",res);
    }
}
