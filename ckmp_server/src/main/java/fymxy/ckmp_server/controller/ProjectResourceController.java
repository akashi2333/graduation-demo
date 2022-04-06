package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.ProjectResource;
import fymxy.ckmp_server.service.ProjectResourceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
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
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/project-resource")
@Validated
@Api(tags = "文件上传和下载")
public class ProjectResourceController {
    @Autowired
    ProjectResourceService projectResourceService;

    @Value("${file.basedir}")
    String basePath;


    @ApiOperation(value = "项目上传文件操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "上传成功")
    })
    @ApiOperationSupport(ignoreParameters = {"projectResource.resourceName","projectResource.timestamp"})
    @PostMapping("/add")
    public Respone uploadFile(@RequestBody ProjectResource projectResource, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        projectResource.setResourceName(originalFilename);
        projectResource.setTimestamp(new Date());

        file.transferTo(new File(basePath+originalFilename));

        projectResourceService.save(projectResource);

        return new Respone(200,"上传成功",null);
    }

    @ApiOperation(value = "下载文件操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "下载成功")
    })
    @ApiOperationSupport(ignoreParameters = {"projectResource.timestamp"})
    @GetMapping("/download")
    public Respone downloadFile(ProjectResource projectResource , HttpServletResponse response) throws IOException {
        ProjectResource one = projectResourceService.getOne(new QueryWrapper<ProjectResource>()
                .eq("uid", projectResource.getUid())
                .eq("pid", projectResource.getPid())
                .eq("resource_name", projectResource.getResourceName())
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

    @ApiOperation(value = "根据pid获取文件列表")
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取成功")
    })
    @ApiOperationSupport(ignoreParameters = {"projectResource.resourceName","projectResource.uid","projectResource.timestamp"})
    @PostMapping("/getFileList")
    public Respone getFileList(@RequestBody ProjectResource projectResource){
        List<ProjectResource> list = projectResourceService.list(new QueryWrapper<ProjectResource>().eq("pid", projectResource.getPid()));
        return new Respone(200,"获取成功",list);
    }
}
