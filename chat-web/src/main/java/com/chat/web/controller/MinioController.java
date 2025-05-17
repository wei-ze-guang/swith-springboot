package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.utils.Result;
import com.chat.service.minioservice.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/minio")
@Tag(name = "MinioController",description = "用来获取minio的预签名的下载和上传的url")
@Slf4j
public class MinioController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    @Operation(description = "上传文件到minio，方法是POST请求，参数是file")
    @ControllerLayer(value = "上传文件",module = ModuleConstant.MODULE_UPLOAD_FILE_TO_MINIO)
    public Result uploadMinioFile(@RequestParam("file") MultipartFile file) {
        //TODO 这个桶名字还需要处理
        return minioService.minioUploadFile("first-chat",file);

    }

    @GetMapping("/download/{fileName}")
    @Operation(description = "下载文件，方法是GET请求，参数是fileName")
    public Result downloadFile(@PathVariable String fileName) {
        return Result.OK("");

    }


}