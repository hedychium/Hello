package com.peijie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class UploadController {

    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, MultipartFile file){
        try{
            String uploadDir = request.getSession().getServletContext().getRealPath("/")+"upload/";
            File dir = new File(uploadDir);
            if(!dir.exists()){
                dir.mkdir();
            }
            String filename = file.getOriginalFilename();
            File serverFile = new File(uploadDir+filename);
            file.transferTo(serverFile);
        }catch (Exception e){
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }

    @RequestMapping("/uploads")
    public String uploads(HttpServletRequest request, MultipartFile[] file){
        String uploadDir = request.getSession().getServletContext().getRealPath("/")+"upload/";
        File dir = new File(uploadDir);
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            for (MultipartFile file1 : file){
                String filename = file1.getOriginalFilename();
                File serverFile = new File(uploadDir+filename);
                file1.transferTo(serverFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";

    }
}
