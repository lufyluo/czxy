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
    private FileMapper fileMapper;

    @Transactional
    public Boolean add(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
        siteMapper.insert(siteEntity);

        return true;
    }

    public Boolean delete(List<Integer> siteIds) {
        Boolean delete = siteMapper.delete(siteIds);
        return delete;
    }

    public PageInfo<SiteInfo> page(SitePageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<SiteEntity> siteEntities = siteMapper.query(pageParam);
        PageInfo<SiteInfo> result = page.toPageInfo();
        List<SiteInfo> siteInfos = PojoMapper.INSTANCE.toSiteInfo(siteEntities);
        for (int i = 0; i < siteInfos.size(); i++) {
            SiteInfo siteInfo = siteInfos.get(i);
            fillTypes(siteInfo);
            fillFiles(siteInfo);
        }
        result.setList(siteInfos);
        result.getList().forEach(n->{
            if(StringUtils.isEmpty(n.getPath())){
                n.setAddress(n.getAddr());
            }else{
                n.setAddress(n.getPath().replace(",","")+n.getAddr());
            }

        });
        return result;
    }

    private void fillFiles(SiteInfo siteInfo) {
        siteInfo.setPictureFileInfos(getFiles(siteInfo.getPics()));
        siteInfo.setAttachFileInfos(getFiles(siteInfo.getAttachFiles()));

    }
    private List<FileInfo> getFiles(String fileIdStrings){
        if(StringUtils.isEmpty(fileIdStrings)){
            return null;
        }
        List<Integer> fileIds = Arrays.asList(fileIdStrings.split(","))
                .stream().map(n->Integer.parseInt(n)).collect(Collectors.toList());
        List<FileEntity> entities = fileMapper.query(fileIds);
        return PojoMapper.INSTANCE.toFileInfos(entities);
    }

    private void fillTypes(SiteInfo siteInfo) {
        String types = siteInfo.getTypes();
        if(types==null)
        {
            return;
        }
        String[] split = types.split(",");
        List<String> typeNames= new ArrayList<>();
        for (int j = 0; j < split.length; j++) {
            Integer m = Integer.parseInt(split[j]);
            String typeName = typeMapper.query(m);
            typeNames.add(typeName);
        }
        String typeName = String.join(",", typeNames);
        siteInfo.setTypeName(typeName);
    }

    public Boolean update(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
        siteMapper.update(siteEntity);
        return true;
    }

    private SiteEntity prepare(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = PojoMapper.INSTANCE.toSiteEntity(siteAddInfo);
        if (siteAddInfo.getTypes() != null && siteAddInfo.getTypes().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteAddInfo.getTypes());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTypes(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        if (siteAddInfo.getTopic() != null) {
            TypeEntity typeEntity = PojoMapper.INSTANCE.toTypeEntity(siteAddInfo.getTopic());
            typeService.batchInsertIfObsent(Arrays.asList(typeEntity));
            siteEntity.setTopicId(typeEntity.getId());
        }
        return siteEntity;
    }

    public Boolean batchImport(MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setStartRows(0);
        try {
            List<SiteAddInfo> siteInfos = ExcelImportUtil.importExcel(file.getInputStream(), SiteAddInfo.class, params);
            if(siteInfos == null || siteInfos.size()==0){
                throw new ManageException(ResponseStatus.ARGUMENTNOTVALID,"数据为空！");
            }
            List<SiteEntity> siteEntities = PojoMapper.INSTANCE.toSiteEntities(siteInfos);
            siteMapper.batchInsert(siteEntities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
