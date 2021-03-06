package com.czxy.manage.service;

import com.czxy.manage.dao.StockMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.StockEntity;
import com.czxy.manage.model.vo.StockPageParam;
import com.czxy.manage.model.vo.stock.StockOutInfo;
import com.czxy.manage.model.vo.stock.StockInfo;
import com.czxy.manage.model.vo.stock.StockTotalInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class StockService {
    @Resource
    private StockMapper stockMapper;

    public PageInfo<StockInfo> page(StockPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<StockEntity> stockEntityList = stockMapper.page(pageParam.getParam(), pageParam.getBeginTime(), pageParam.getEndTime());
        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList(PojoMapper.INSTANCE.toStockInfos(stockEntityList));
        return pageInfo;
    }
    @Transactional
    public Boolean out(StockOutInfo stockOutInfo) {
        Integer total = stockMapper.query(stockOutInfo.getGoodsName());
        if (total>=stockOutInfo.getSpend()){
            Integer newTotal=total-stockOutInfo.getSpend();
            stockOutInfo.setTotal(newTotal);
            stockMapper.insert(stockOutInfo);
        }else {
            throw new ManageException(ResponseStatus.FAILURE,"库存不足");
        }
        return true;
    }
    @Transactional
    public Boolean in(StockOutInfo stockOutInfo) {
        Integer total = stockMapper.query(stockOutInfo.getGoodsName());
        if (total==null){
            Integer newTotal=stockOutInfo.getSpend();
            stockOutInfo.setTotal(newTotal);
            stockMapper.insert(stockOutInfo);
        }else {
            Integer newTotal=total+stockOutInfo.getSpend();
            stockOutInfo.setTotal(newTotal);
            stockMapper.insert(stockOutInfo);
        }
        return true;
    }
    public Integer total(StockTotalInfo stockTotalInfo) {
        Integer total = stockMapper.selectTotal(stockTotalInfo);
        return total;
    }
}
