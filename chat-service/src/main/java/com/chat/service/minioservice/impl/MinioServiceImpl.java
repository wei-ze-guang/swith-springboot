package com.chat.service.minioservice.impl;

import com.chat.common.utils.Result;
import com.chat.service.minioservice.MinioService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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
}
