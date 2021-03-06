package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.ProjectJoin;
import fymxy.ckmp_server.entity.Team;
import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.entity.UserProject;
import fymxy.ckmp_server.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/project-join")
@Api(tags = "project申请 进进进 的增删查")
public class ProjectJoinController {
    @Autowired
    ProjectJoinService projectJoinService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserProjectService userProjectService;
    @Autowired
    TeamService teamService;

    @ApiOperation(value = "申请加群操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "申请成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody ProjectJoin projectJoin){
        ArrayList<Integer> joinedTeamId = new ArrayList<>();
        for (Team joinedTeam : teamService.list(new QueryWrapper<Team>()
                .eq("uid", projectJoin.getUid()))) {
            joinedTeamId.add(joinedTeam.getTid());
        }

        if (!joinedTeamId.contains(
                projectService.getById(projectJoin.getPid()).getTid())){
            return new Respone(200,"未加入团队",null);
        }


        if (userProjectService.list(new QueryWrapper<UserProject>()
                .eq("uid",projectJoin.getUid())
                .eq("pid",projectJoin.getPid())).size()!=0){
            return new Respone(200,"已加入",null);
        }
        if (projectJoinService.list(new QueryWrapper<ProjectJoin>()
                .eq("uid",projectJoin.getUid())
                .eq("pid",projectJoin.getPid())).size()!=0){
            return new Respone(200,"已申请",null);
        }
        projectJoinService.save(projectJoin);
        return new Respone(200,"申请成功",null);
    }

    @ApiOperation(value = "删除对应申请加群操作的记录（即处理了此请求）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @DeleteMapping("/delete")
    private Respone delete( ProjectJoin projectJoin){
        projectJoinService.remove(new QueryWrapper<ProjectJoin>().eq("uid",projectJoin.getUid()).eq("pid",projectJoin.getPid()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "根据pid查询想进群人员信息，返回user的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"uid"})
    @GetMapping("/getByProjectId")
    private Respone getByProjectId( ProjectJoin projectJoin){
        ArrayList<User> res = new ArrayList<>();
        for (ProjectJoin exit : projectJoinService.list(new QueryWrapper<ProjectJoin>()
                .eq("pid", projectJoin.getPid()))) {
            res.add(userService.getById(exit.getUid()));
        }

        return new Respone(200,"处理成功",res);
    }

    @ApiOperation(value = "根据uid查询想进的群，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"pid"})
    @GetMapping("/getByUserId")
    private Respone getByUserId( ProjectJoin projectJoin){
        ArrayList<Integer> res = new ArrayList<>();
        for (ProjectJoin exit : projectJoinService.list(new QueryWrapper<ProjectJoin>()
                .eq("uid", projectJoin.getUid()))) {
            res.add(exit.getPid());
        }
        return new Respone(200,"处理成功",res);
    }
}
