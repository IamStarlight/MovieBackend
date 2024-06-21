package com.bjtu.movie.controller;

import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.OssGetService;
import com.bjtu.movie.utils.OssUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

@RestController
@RequestMapping("/movie/{mid}")
public class OssController {

    @SneakyThrows
    @PostMapping("/file")
    public ResponseEntity<Result> uploadFile(@RequestParam("file") MultipartFile file) {
        // 将MultipartFile转换为File
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);

        // 根据文件类型设置存储路径和对象名
        String fileType = getFileType(file.getContentType());
        String objectName = fileType + "/" + file.getOriginalFilename();

        // 上传文件并获取URL
        String url = OssUtil.uploadFile(objectName, convFile.getAbsolutePath());

        return new ResponseEntity<>(Result.success(url), HttpStatus.CREATED);


//        try {
//            System.out.println("Received file: " + file.getOriginalFilename());
//
//            // 将MultipartFile转换为File
//            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
//            file.transferTo(convFile);
//            System.out.println("File converted to: " + convFile.getAbsolutePath());
//
//            // 根据文件类型设置存储路径和对象名
//            String fileType = getFileType(file.getContentType());
//            String objectName = fileType + "/" + file.getOriginalFilename();
//
//            // 上传文件并获取URL
//            String url = OssUtil.uploadFile(objectName, convFile.getAbsolutePath());
//
//            if (url != null) {
//                System.out.println("Upload successful. File URL: " + url);
//                return ResponseEntity.ok(url);
//            } else {
//                System.out.println("Upload failed.");
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
//        }
    }

    private String getFileType(String contentType) {
        // 根据contentType判断文件类型，示例实现，你可以根据需求扩展
        if (contentType.startsWith("image")) {
            return "images";
        } else if (contentType.startsWith("video")) {
            return "video";
        } else {
            return "documents";
        }
    }

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

