package com.bjtu.movie.controller;

import com.bjtu.movie.service.OssGetService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/user/{uid}")
public class OssGetController {

    @GetMapping("/files")
    public void getFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            String contentType = determineContentType(fileName);
            InputStream fileStream = OssGetService.getFile(fileName);

            if (fileStream != null) {
                response.setContentType(contentType);
                StreamUtils.copy(fileStream, response.getOutputStream());
                response.flushBuffer();
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.getWriter().write("文件未找到");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private String determineContentType(String fileName) {
        // 根据文件名后缀判断文件类型，示例实现，你可以根据需求扩展
        if (fileName.toLowerCase().endsWith(".mp4")) {
            return "video/mp4";
        } else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream"; // 默认返回通用的二进制流类型
        }
    }
}

