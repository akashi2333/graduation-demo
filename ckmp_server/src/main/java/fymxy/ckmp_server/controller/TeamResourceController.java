package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.TeamResource;
import fymxy.ckmp_server.service.TeamResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-04-04
 */
@RestController
@RequestMapping("/team-resource")
@Api(tags = "团队资源的增查")
public class TeamResourceController {
    @Autowired
    TeamResourceService teamResourceService;

    @Value("${file.basedir}")
    String basePath;


    @ApiOperation(value = "上传文件操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "上传成功")
    })
    @ApiOperationSupport(ignoreParameters = {"teamResource.resourceName","teamResource.timestamp"})
    @PostMapping("/add")
    public Respone uploadFile(@RequestBody TeamResource teamResource, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        teamResource.setResourceName(originalFilename);
        file.transferTo(new File(basePath+originalFilename));
        teamResource.setTimestamp(new Date());
        teamResourceService.save(teamResource);

        return new Respone(200,"上传成功",null);
    }

    @ApiOperation(value = "下载文件操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "下载成功")
    })
    @ApiOperationSupport(ignoreParameters = {"teamResource.timestamp"})
    @GetMapping("/download")
    public Respone downloadFile(@RequestBody TeamResource teamResource , HttpServletResponse response) throws IOException {
        TeamResource one = teamResourceService.getOne(new QueryWrapper<TeamResource>()
                .eq("uid", teamResource.getUid())
                .eq("tid", teamResource.getTid())
                .eq("resource_name", teamResource.getResourceName())
        );

        File file = new File(one.getResourceName());
        OutputStream outputStream=null;
        InputStream inputStream=null;
        BufferedInputStream bufferedInputStream=null;
        byte[] bytes=new byte[1024];
        // 获取输出流
        response.setHeader("Content-Disposition", "attachment;filename=" +  new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        response.setContentType("application/force-download");
        inputStream=new  FileInputStream(file);
        bufferedInputStream=new BufferedInputStream(inputStream);
        outputStream = response.getOutputStream();
        int i=bufferedInputStream.read(bytes);
        while (i!=-1){
            outputStream.write(bytes,0,i);
            i=bufferedInputStream.read(bytes);
        }
        inputStream.close();
        outputStream.close();
        bufferedInputStream.close();
        return new Respone(200,"下载成功",null);
    }

    @ApiOperation(value = "获取文件列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取成功")
    })
    @ApiOperationSupport(ignoreParameters = {"teamResource.resourceName","teamResource.uid","teamResource.timestamp"})
    @PostMapping("/getFileList")
    public Respone getFileList(@RequestBody TeamResource teamResource){
        List<TeamResource> list = teamResourceService.list(new QueryWrapper<TeamResource>().eq("tid", teamResource.getTid()));
        return new Respone(200,"获取成功",list);
    }
}
