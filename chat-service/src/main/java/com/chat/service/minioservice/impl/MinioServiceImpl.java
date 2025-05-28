package com.chat.service.minioservice.impl;

import com.chat.common.utils.Result;
import com.chat.service.minioservice.MinioService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    private MinioClient minioClient;



    /**
     * 存储文件
     *
     * @param bucketName 桶名字
     * @param file  文件
     * @return 返回存储完的名字
     */
    @Override
    public Result minioUploadFile(String bucketName, MultipartFile file){
        String fileName= UUID.randomUUID() + "_" + file.getOriginalFilename();

        try{
            // 先检查存储桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return Result.OK(fileName);
        } catch (Exception e) {
            log.error("文件上传到minio失败");
            return Result.FAIL(false);
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName 桶名字
     * @param fileName   文件名
     * @return 删除的文件名
     */
    @Override
    public Result minioDeleteFile(String bucketName, String fileName) {
        try{
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
            return Result.OK(fileName);
        }catch (Exception e){
            log.error("文件删除失败，文件名:{}",fileName);
            return Result.FAIL(false);
        }
    }

    /**
     * 生成预签名下载 URL
     *
     * @param objectName 对象名称
     * @return 预签名 URL
     */
    @Override
    public Result generatePresignedDownloadUrl(String objectName) throws MinioException {
        try {
            // 创建一个参数 Map，用于在请求时指定额外的查询参数
            Map<String, String> reqParams = new HashMap<>();
            // 计算过期时间
            reqParams.put("response-content-type", "application/octet-stream");
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket("first-chat")  //TODO  这里需要优化
                            .object(objectName)
                            .expiry(60 , TimeUnit.HOURS)
                            .extraQueryParams(reqParams)
                            .build());
            log.info("生成预签名下载 URL 成功：{}", url);
            return Result.OK(url);
        } catch (Exception e) {
            throw new MinioException("生成预签名下载 URL 失败"+ e);
        }
    }
}
