package com.quick.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author ：dongzongzu
 * @date ：Created in 2021/03/25
 * @description：图片工具类
 * @version: 1.0
 */
public class FileUtils {

    /**
     * multipartFile转File
     **/
    public static File multipartFile2File(MultipartFile multipartFile) {
        File file = null;
        if (multipartFile != null) {
            try {
                file = File.createTempFile("temp", ".png");
                multipartFile.transferTo(file);
                System.gc();
                file.deleteOnExit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}

