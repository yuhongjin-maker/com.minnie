package com.minnie.reg.controller;

import com.minnie.reg.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")

@Slf4j
//文件上传下载

public class CommonController {
    @Value("${minnie.path}")
    private String basePath;
    //文件上传
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        //临时文件需要转存
        log.info(file.toString());

        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖

        String form= file.getOriginalFilename();
        String suffix = form.substring(form.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString();

        //创建一个目录对象
        File dir= new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }

        try{
            file.transferTo(new File(basePath+fileName+suffix));


        }catch (IOException e){
            e.printStackTrace();

        }

        return R.success(basePath+fileName+suffix);

    }

    @GetMapping("/download")

    public void download(HttpServletResponse response,String name)  {

        try{
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = (new FileInputStream(new File(basePath+name)));

            //输出流，通过输出流将文件写回浏览器，在浏览器展示图件了

            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len=0;
            byte[] bytes = new byte[1024];
            while (fileInputStream.read(bytes) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();

        }catch (Exception e){
            e.printStackTrace();

        }


    }
}
