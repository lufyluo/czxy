package com.czxy.manage.dao;

import com.czxy.manage.model.entity.StockEntity;
import com.czxy.manage.model.vo.stock.StockOutInfo;
import com.czxy.manage.model.vo.stock.StockTotalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface StockMapper {

    List<StockEntity> page(@Param("param")String param,@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    Integer insert(StockOutInfo stockOutInfo);

    Integer selectTotal(StockTotalInfo stockTotalInfo);

    Integer query(@Param("goodsName") String goodsName);
}
