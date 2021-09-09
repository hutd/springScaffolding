package com.quick.controller;

import com.quick.config.MinioConfiguration;
import com.quick.enums.ExceptionEnum;
import com.quick.exception.CamelliaException;
import com.quick.utils.MinIoUploadUtils;
import com.quick.vo.IResultEntity;
import com.quick.vo.ResultEntity;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;

@Api("minio")
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Resource
    private MinioConfiguration minioConfiguration;

    public MinioClient initMinioClient() throws InvalidPortException, InvalidEndpointException {
        return MinIoUploadUtils.minioClient(minioConfiguration.getUrl() + ":" + minioConfiguration.getPort(), minioConfiguration.getAccessKey(), minioConfiguration.getSecretKey());
    }

    @RequestMapping("/upload")
    @ApiOperation("minio上传文件")
    public IResultEntity<String> upload(@RequestParam("file") MultipartFile file) throws InvalidPortException, InvalidEndpointException, IOException {
        //图片名称
        String extensionName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        //上传文件
        String url = MinIoUploadUtils.uploadFile(initMinioClient(), minioConfiguration.getBucketName(),
                Calendar.getInstance().getTimeInMillis() + "." + extensionName, file.getInputStream(),
                "image/jpeg", file.getSize());
        String imgUrl = StringUtils.substringAfterLast(url, minioConfiguration.getBucketName());

        return ResultEntity.ok(minioConfiguration.getBaceUrl() + minioConfiguration.getBucketName() + imgUrl);
    }

    @RequestMapping("/removeFile")
    @ApiOperation("minio删除文件")
    public IResultEntity<String> removeFile(@RequestParam("filename") String filename) throws InvalidPortException, InvalidEndpointException {
        return ResultEntity.ok(MinIoUploadUtils.removeFile(initMinioClient(), minioConfiguration.getBucketName(), filename));
    }

    @GetMapping("/downloadFile")
    @ApiOperation("minio下载文件")
    public void downloadFile(@RequestParam("filename") String filename, HttpServletResponse httpResponse) {
        try {
            InputStream object = initMinioClient().getObject(minioConfiguration.getBucketName(), filename);
            byte buf[] = new byte[1024];
            int length = 0;
            httpResponse.reset();

            httpResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            httpResponse.setContentType("application/octet-stream");
            httpResponse.setCharacterEncoding("utf-8");
            OutputStream outputStream = httpResponse.getOutputStream();
            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            outputStream.close();
        } catch (Exception ex) {
            throw new CamelliaException(ExceptionEnum.DOWN_FAILED);
        }
    }

}