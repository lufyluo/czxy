package com.czxy.manage.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.czxy.manage.dao.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    @Resource
    private FileMapper fileMapper;


        /**
         * 上传文件至阿里云 oss
         *
         * @param
         * @return
         * @throws Exception
         */
        public Boolean upload(MultipartFile file) throws Exception {

            String endpoint = "";
            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = "";
            String accessKeySecret = "";
            //创建的bucket名
            String bucketName = "";
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀名
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //使用uuid作为文件名防止重复
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = uuid+"."+substring;
            final File tempFile = File.createTempFile(uuid, substring);
            //MultipartFile  转换为 File
            file.transferTo(tempFile);
            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            try {
                //容器不存在，就创建
                if(! ossClient.doesBucketExist(bucketName)){
                    ossClient.createBucket(bucketName);
                    CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                    createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                    ossClient.createBucket(createBucketRequest);
                }
                String fileHost = "upload";
                //上传文件
                //File file1 = multipartFileToFile(file);
                //创建文件路径
                String fileUrl= fileHost+"/"+fileName;
                PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, tempFile));
                //设置权限 这里是公开读
                ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
                if(null != result){
                    log.info("==========>OSS文件上传成功,OSS地址："+fileUrl);
                }
            }catch (OSSException oe){
                log.error(oe.getMessage());
            }catch (ClientException ce){
                log.error(ce.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭
                ossClient.shutdown();
            }
            //删除临时生成的文件
            deleteFile(tempFile);
            return true;
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
