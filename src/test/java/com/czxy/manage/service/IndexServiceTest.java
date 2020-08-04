package com.czxy.manage.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IndexServiceTest {
    @Test
    public void dateFillTest(){
        IndexService indexService = new IndexService();
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.setTimeInMillis(1588751103000L);//2020-05-06 15:45:03
        end.setTimeInMillis(1596699903000L);//2020-08-06 15:45:03
        List<String> strings = indexService.dateFill(begin.getTime(), end.getTime());
        Assert.isTrue(strings.size() == 4,"数量错误");
    }
}
