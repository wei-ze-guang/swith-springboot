package com.chat.service.minioservice;

import me.doudan.doc.annotation.ServiceLayer;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author goudan
 */
public interface MinioService {

    /**
     *存储文件
     * @param bucketName 桶名字
     * @param file  文件
     * @return 返回存储完的名字
     */
    @ServiceLayer(value = "把文件发送到minio",module = "上传文件到服务器")
    String minioUploadFile(String bucketName,  MultipartFile file);

    /**
     * 删除文件
     * @param bucketName //桶名字
     * @param fileName  文件名
     * @return 删除的文件名
     */
    @ServiceLayer("删除minio文件")
    String minioDeleteFile(String bucketName, String fileName);

}
