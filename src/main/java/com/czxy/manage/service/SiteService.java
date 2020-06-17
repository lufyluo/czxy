package com.czxy.manage.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.czxy.manage.dao.FileMapper;
import com.czxy.manage.dao.SiteMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.FileEntity;
import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.files.FileInfo;
import com.czxy.manage.model.vo.site.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {
    @Resource
    private SiteMapper siteMapper;
    @Resource
    private TypeService typeService;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    FileMapper fileMapper;

    @Transactional
    public Boolean add(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
        siteMapper.insert(siteEntity);
        List<TopicInfo> topics = siteAddInfo.getTopics();
        Integer id = siteEntity.getId();
        for (TopicInfo t : topics) {
            t.setSiteId(id);
        }
        siteMapper.insertTopic(topics);
        return true;
    }

    public Boolean delete(List<Integer> siteIds) {
        siteMapper.delete(siteIds);
        siteMapper.deleteTopic(siteIds);
        return true;
    }

    public PageInfo<SiteInfo> page(SitePageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<SiteEntity> siteEntities = siteMapper.query(pageParam);

        for (int i = 0; i < siteEntities.size(); i++) {
            SiteEntity siteEntity = siteEntities.get(i);
            String types = siteEntity.getTypes();
            if(StringUtils.isEmpty(types)){
                continue;
            }
            String[] split = types.split(",");
            List<String> typeNames = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                Integer m = Integer.parseInt(split[j]);
                String typeName = typeMapper.query(m);
                typeNames.add(typeName);
            }
            String typeName = String.join(",", typeNames);
            siteEntity.setTypeName(typeName);
        }
        PageInfo<SiteInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toSiteInfo(siteEntities));
        result.getList().forEach(n -> {
            if (StringUtils.isEmpty(n.getPath())) {
                n.setAddress(n.getAddr());
            } else {
                n.setAddress(n.getPath().replace(",", "") + n.getAddr());
            }

        });
        return result;
    }

    @Transactional
    public Boolean update(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
        siteMapper.update(siteEntity);
        List<TopicInfo> topics = siteAddInfo.getTopics();
        Integer id = siteEntity.getId();
        List<Integer> integers = siteMapper.queryTopicId(id);
        List<TopicInfo> collect = topics.stream().filter(n -> n.getId() != null).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            List<Integer> listids = collect.stream().map(n -> n.getId()).collect(Collectors.toList());
            integers.removeAll(listids);
            if (!integers.isEmpty()) {
                siteMapper.deleteTopicById(integers);
            }
        }
        if (collect.isEmpty()) {
            siteMapper.clear(id);
        }
        if (!topics.isEmpty()) {
            for (TopicInfo topicInfo : topics) {
                if (topicInfo.getId() != null) {
                    siteMapper.updateTopic(topicInfo);
                }
                if (topicInfo.getId() == null) {
                    siteMapper.insertTopics(topicInfo);
                }
            }
        }
        return true;
    }

    private SiteEntity prepare(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = PojoMapper.INSTANCE.toSiteEntity(siteAddInfo);
        if (siteAddInfo.getTypes() != null && siteAddInfo.getTypes().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteAddInfo.getTypes());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTypes(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        return siteEntity;
    }

    public Boolean batchImport(MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setStartRows(0);
        try {
            List<SiteAddInfo> siteInfos = ExcelImportUtil.importExcel(file.getInputStream(), SiteAddInfo.class, params);
            if (siteInfos == null || siteInfos.size() == 0) {
                throw new ManageException(ResponseStatus.ARGUMENTNOTVALID, "数据为空！");
            }
            List<SiteEntity> siteEntities = PojoMapper.INSTANCE.toSiteEntities(siteInfos);
            siteMapper.batchInsert(siteEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public SiteDetailInfo get(Integer id) {
        SiteEntity siteEntity = siteMapper.queryById(id);
        if (siteEntity == null) {
            throw new ManageException(ResponseStatus.DATANOTEXIST);
        }
        SiteDetailInfo siteInfo = PojoMapper.INSTANCE.toSiteDetailInfo(siteEntity);
        fillTypes(siteEntity, siteInfo);
        fillTopics(siteInfo);
        fillPics(siteEntity,siteInfo);
        fillAttachFiles(siteEntity,siteInfo);
        return siteInfo;
    }

    private void fillAttachFiles(SiteEntity siteEntity,SiteDetailInfo siteInfo) {
        String attachFiles = siteEntity.getAttachFiles();
        if (!StringUtils.isEmpty(attachFiles)) {
            List<Integer> fileIds = Arrays.asList(attachFiles.split(",")).stream().map(n -> Integer.parseInt(n)).collect(Collectors.toList());
            List<FileEntity> fileInfos = fileMapper.query(fileIds);
            siteInfo.setAttachFiles(PojoMapper.INSTANCE.tiFileInfos(fileInfos));
        }
    }

    private void fillPics(SiteEntity siteEntity,SiteDetailInfo siteInfo) {
        String pics = siteEntity.getPics();
        if (!StringUtils.isEmpty(pics)) {
            List<Integer> fileIds = Arrays.asList(pics.split(",")).stream().map(n -> Integer.parseInt(n)).collect(Collectors.toList());
            List<FileEntity> fileInfos = fileMapper.query(fileIds);
            siteInfo.setPics(PojoMapper.INSTANCE.tiFileInfos(fileInfos));
        }
    }

    private void fillTopics(SiteDetailInfo siteInfo) {
        List<TopicInfo> topicInfos = siteMapper.queryTopicsBySiteId(siteInfo.getId());
        siteInfo.setTopics(topicInfos);
    }

    private void fillTypes(SiteEntity siteEntity, SiteDetailInfo siteInfo) {
        String types = siteEntity.getTypes();
        if (!StringUtils.isEmpty(types)) {
            List<Integer> typeIds = Arrays.asList(types.split(",")).stream().map(n -> Integer.parseInt(n)).collect(Collectors.toList());
            List<TypeEntity> typeEntities = typeMapper.queryAll(typeIds);
            siteInfo.setTypes(PojoMapper.INSTANCE.toTypeInfos(typeEntities));
        }
    }
}
