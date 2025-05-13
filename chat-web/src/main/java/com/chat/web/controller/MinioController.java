package com.chat.web.controller;

import com.chat.common.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/minio")
@Tag(name = "MinioController",description = "用来获取minio的预签名的下载和上传的url")
@Slf4j
public class MinioController {


    @PostMapping("/upload")
    @Operation(description = "上传文件到minio，方法是POST请求，参数是file")
    @ControllerLayer(value = "上传文件",module = "上传文件")
    public Result uploadMinioFile(@RequestParam("file") MultipartFile file) {
        return Result.OK("");

    }

    @GetMapping("/download/{fileName}")
    @Operation(description = "下载文件，方法是GET请求，参数是fileName")
    public Result downloadFile(@PathVariable String fileName) {

        return Result.OK("");

    }


}