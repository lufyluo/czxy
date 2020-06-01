package com.czxy.manage.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.czxy.manage.dao.FileMapper;
import com.czxy.manage.infrastructure.config.AliyunOssConfig;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.FileEntity;
import com.czxy.manage.model.vo.files.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    @Resource
    private FileMapper fileMapper;
    @Autowired
    private AliyunOssConfig aliyunOssConfig;

    /**
     * 上传文件至阿里云 oss
     *
     * @param
     * @return
     * @throws Exception
     */
    public FileInfo upload(MultipartFile file) {

        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //使用uuid作为文件名防止重复
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + "." + substring;
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(aliyunOssConfig.getEndPoint(), aliyunOssConfig.getAccessKey(), aliyunOssConfig.getAccessKeySecret());
        try {
            //容器不存在，就创建
//            if (!ossClient.doesBucketExist(aliyunOssConfig.getBucket())) {
//                ossClient.createBucket(aliyunOssConfig.getBucket());
//                CreateBucketRequest createBucketRequest = new CreateBucketRequest(aliyunOssConfig.getBucket());
//                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
//                ossClient.createBucket(createBucketRequest);
//            }
            //上传文件
            //File file1 = multipartFileToFile(file);
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(aliyunOssConfig.getBucket(), fileName, file.getInputStream()));
            //设置权限 这里是公开读
            ossClient.setBucketAcl(aliyunOssConfig.getBucket(), CannedAccessControlList.PublicRead);
            if (null != result) {
                log.info("==========>OSS文件上传成功,OSS地址：" + fileName);
                FileEntity fileEntity = new FileEntity();
                fileEntity.setExtension(substring);
                String url = "https://" + aliyunOssConfig.getBucket() + "." + aliyunOssConfig.getEndPoint() + "/" + fileName;
                fileEntity.setUrl(url);
                fileEntity.setSize(file.getSize());
                fileEntity.setName(file.getOriginalFilename());
                fileMapper.insert(fileEntity);
                return PojoMapper.INSTANCE.tiFileInfo(fileEntity);
            }
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * 删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
