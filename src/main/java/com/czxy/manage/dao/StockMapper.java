package com.czxy.manage.dao;

import com.czxy.manage.model.entity.StockEntity;
import com.czxy.manage.model.vo.stock.StockOutInfo;
import com.czxy.manage.model.vo.stock.StockTotalInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface StockMapper {

    List<StockEntity> page(String param, Date beginTime, Date endTime);

    Integer insert(StockOutInfo stockOutInfo);

    Integer selectTotal(StockTotalInfo stockTotalInfo);
}
