package com.bjtu.movie.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.InputStream;

import static com.bjtu.movie.service.OssPushService.uploadFile;

public class OssGetService {

    private static final String ENDPOINT = "";
    private static final String BUCKET_NAME = "";
    private static final String ACCESS_KEY_ID = "";
    private static final String ACCESS_KEY_SECRET = "";

    public static InputStream getFile(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            // 根据文件名后缀设置GetObjectRequest
            String fileType = determineFileType(objectName);
            GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET_NAME, fileType + "/" + objectName);

            return ossClient.getObject(getObjectRequest).getObjectContent();
        } catch (OSSException | ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    private static String determineFileType(String fileName) {
        // 根据文件名后缀判断文件类型，示例实现，你可以根据需求扩展
        if (fileName.toLowerCase().endsWith(".mp4")) {
            return "video";
        } else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".png")) {
            return "images";
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            return "documents";
        } else {
            return "others"; // 或者根据你的具体情况返回默认类型
        }
    }

//    public static void main(String[] args) {
//        String objectName = "images/exampleimage1.jpg";
//        String filePath = "D:/Users/NYLM/Desktop/ab65b53de994d063d532ad34d75f3f1.jpg";
//
//        String url = uploadFile(objectName, filePath);
//        if (url != null) {
//            // 将URL传递给前端
//            System.out.println("URL to be sent to frontend: " + url);
//        }
//    }
}