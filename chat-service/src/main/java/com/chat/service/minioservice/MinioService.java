package com.chat.service.minioservice;

import com.chat.common.utils.Result;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import me.doudan.doc.annotation.ServiceLayer;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

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
    Result minioUploadFile(String bucketName, MultipartFile file);

    /**
     * 删除文件
     * @param bucketName //桶名字
     * @param fileName  文件名
     * @return 删除的文件名
     */
    @ServiceLayer("删除minio文件")
    Result minioDeleteFile(String bucketName, String fileName);


    /**
     * 生成预签名下载 URL
     * @param objectName 对象名称
     * @return 预签名 URL
     * @throws MinioException 异常
     */
    public Result generatePresignedDownloadUrl( String objectName) throws MinioException;

}
