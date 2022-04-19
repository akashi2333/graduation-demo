package fymxy.ckmp_server.controller;

import fymxy.ckmp_server.entity.Team;
import fymxy.ckmp_server.service.TeamService;
import org.slf4j.Logger;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Project;
import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.entity.UserProject;
import fymxy.ckmp_server.service.ProjectService;
import fymxy.ckmp_server.service.UserProjectService;
import fymxy.ckmp_server.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.LoggerFactory;
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
@Api(tags = "用户操作相关类")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserProjectService userProjectService;
    @Autowired
    TeamService teamService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "登录操作")
    @ApiOperationSupport(ignoreParameters = {"user.id","user.eMail"})
    @ApiResponses({
            @ApiResponse(code = 400,message = "登录失败"),
            @ApiResponse(code = 200,message = "登录成功 返回用戶id")
    })

    @GetMapping("/login")
    private Respone login(User user){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",user.getUsername());
        wrapper.eq("password",user.getPassword());
        User one = userService.getOne(wrapper);
        if (one == null){
            return new Respone(400,"登录失败",null);
        }
        else {
            //返回用戶id
            return new Respone(200,"登录成功",one.getId());
        }
    }

    @ApiOperation(value = "修改操作 需要email，错误的显示了false")
    @ApiResponses({
            @ApiResponse(code = 400,message = "修改失败"),
            @ApiResponse(code = 200,message = "修改成功")
    })
    @PostMapping("/change")
    private Respone changeDetails(@RequestBody User user){
        //如果加入Spring Security此处需要对密码进行加密

        //不准改成别人的名字
        User one = userService.getOne(new QueryWrapper<User>().eq("name", user.getUsername()));
        if (one!=null && !one.getId().equals(user.getId())){
            logger.warn(String.valueOf(user));
            return new Respone(400,"修改失败",null);
        }
        userService.updateById(user);
        return new Respone(200,"修改成功",null);
    }

    @ApiOperation(value = "注册操作")
    @ApiOperationSupport(ignoreParameters = {"user.id","user.eMail"})
    @ApiResponses({
            @ApiResponse(code = 400,message = "注册失败"),
            @ApiResponse(code = 200,message = "注册成功")
    })
    @PostMapping("/register")
    private Respone register(@RequestBody User user){
        //不准改成别人的名字
        User one = userService.getOne(new QueryWrapper<User>().eq("name", user.getUsername()));
        if (one!=null && !one.getId().equals(user.getId())){
            return new Respone(400,"注册失败",null);
        }
        userService.save(user);
        return new Respone(200,"注册成功",null);
    }

    @ApiOperation(value = "获得加入的群 返回群list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查找成功")
    })
    @GetMapping("/getProjects")
    public Respone getProjects(@RequestParam int uid){
        //成员列表
        List<Project> members = new ArrayList<>();
        for (UserProject joinProject : userProjectService.list(new QueryWrapper<UserProject>().eq("uid", uid))) {
            members.add(projectService.getById(joinProject.getPid()));
        }
        return new Respone(200,"查找成功",members);
    }

    @ApiOperation(value = "获得加入的团队 返回团队list")
    @ApiOperationSupport(ignoreParameters = {"userProject.pid"})
    @ApiResponses({
            @ApiResponse(code = 200,message = "查找成功")
    })
    @GetMapping("/getTeams")
    public Respone getTeams(@RequestParam int uid){
        //成员列表
        List<Team> members = new ArrayList<>();
        members.addAll(teamService.list(new QueryWrapper<Team>().eq("uid", uid)));
        return new Respone(200,"查找成功",members);
    }
}
