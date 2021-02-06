package com.czxy.manage.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTest {
    @Test
    public void localDateTest(){
        LocalDate now = LocalDate.now();
        String nowDate = now.toString();
        System.out.println(nowDate);
        String[] split = nowDate.split("-");
        String birthDay = split[1] + split[2];
        System.out.println(birthDay);
    }
}
