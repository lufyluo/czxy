package com.czxy.manage.service;

import com.czxy.manage.dao.CustomerContactsMapper;
import com.czxy.manage.dao.CustomerMapper;
import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.PlanMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.CustomerContactsEntity;
import com.czxy.manage.model.entity.CustomerEntity;
import com.czxy.manage.model.entity.OrgEntity;
import com.czxy.manage.model.entity.PlanEntity;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsCreateInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsUpdateInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerContactsService {
    @Resource
    private CustomerContactsMapper customerContactsMapper;
    @Autowired
    private OrgService orgService;
    @Resource
    private OrgMapper orgMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private PlanMapper planMapper;

    public PageInfo<CustomerContactsInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<CustomerContactsEntity> customerEntityList = customerContactsMapper.query(pageParam);
        PageInfo<CustomerContactsInfo> pageInfo = page.toPageInfo();
        pageInfo.setList(PojoMapper.INSTANCE.toCustomerContactsInfos(customerEntityList));
        return pageInfo;
    }

    @Transactional
    public Boolean update(CustomerContactsUpdateInfo customerInfo) {
        updateOrg(customerInfo);
        updateContactor(customerInfo);
        updatePlan(customerInfo);
        return true;
    }

    private void updatePlan(CustomerContactsUpdateInfo customerInfo) {
        PlanEntity planEntity = PojoMapper.INSTANCE.toPlanEntity(customerInfo);
        planEntity.setFiles(getFiles(customerInfo.getFileIds()));
        planMapper.update(planEntity);
    }

    private void updateContactor(CustomerContactsUpdateInfo customerInfo) {
        CustomerEntity customerEntity = PojoMapper.INSTANCE.toCustomerEntity(customerInfo);
        customerMapper.updateAll(customerEntity);
    }

    private void updateOrg(CustomerContactsUpdateInfo customerInfo) {
        OrgEntity orgEntity = new OrgEntity();
        orgEntity.setId(customerInfo.getOrgId());
        orgEntity.setName(customerInfo.getOrgName());
        orgEntity.setProvinceId(customerInfo.getProvinceId());
        orgEntity.setCityId(customerInfo.getCityId());
        orgEntity.setCountyId(customerInfo.getCountyId());
        orgMapper.updateAll(orgEntity);
    }

    @Transactional
    public Boolean add(CustomerContactsCreateInfo customerInfo) {
        OrgEntity orgEntitytemp = orgMapper.queryByNames(customerInfo.getOrgName());
        if(orgEntitytemp!=null){
            throw new ManageException(ResponseStatus.DATAEXIST,"客户已存在");
        }
        Integer orgId = insertOrg(customerInfo);
        customerInfo.setOrgId(orgId);
        insertContactor(customerInfo);
        insertPlan(customerInfo);
        return true;
    }

    public Boolean delete(List<Integer> ids) {
        planMapper.delete(ids);
        return true;
    }

    private void insertPlan(CustomerContactsCreateInfo customerContactsCreateInfo) {
        PlanEntity planEntity = PojoMapper.INSTANCE.toPlanEntity(customerContactsCreateInfo);
        planEntity.setFiles(getFiles(customerContactsCreateInfo.getFileIds()));
        planMapper.insert(planEntity);
    }

    private void insertContactor(CustomerContactsCreateInfo customerContactsCreateInfo) {
        Integer count = customerMapper.countContactor(customerContactsCreateInfo.getOrgId());
        CustomerEntity customerEntity = PojoMapper.INSTANCE.toCustomerEntity(customerContactsCreateInfo);
        if(count>0){
            customerMapper.updateAll(customerEntity);
            return;
        }
        customerMapper.insert(customerEntity);
    }

    private Integer insertOrg(CustomerContactsCreateInfo customerContactsCreateInfo) {
        OrgEntity orgEntity = getOrgEntity(customerContactsCreateInfo);
        return orgService.insertIfAbsentOrg(orgEntity);
    }

    private OrgEntity getOrgEntity(CustomerContactsCreateInfo customerContactsCreateInfo) {
        OrgEntity orgEntity = new OrgEntity();
        orgEntity.setId(customerContactsCreateInfo.getOrgId());
        orgEntity.setName(customerContactsCreateInfo.getOrgName());
        orgEntity.setProvinceId(customerContactsCreateInfo.getProvinceId());
        orgEntity.setCityId(customerContactsCreateInfo.getCityId());
        orgEntity.setCountyId(customerContactsCreateInfo.getCountyId());
        return orgEntity;
    }

    private String getFiles(List<Integer> fileIds) {
        if (fileIds != null && fileIds.size() > 0) {
            return fileIds.stream().map(n -> n.toString()).collect(Collectors.joining(","));
        }
        return null;
    }
}
