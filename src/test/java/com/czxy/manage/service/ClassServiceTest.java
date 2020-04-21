package com.czxy.manage.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassServiceTest {
    @Test
    public void stringListContains(){
        List<String> ids = Arrays.asList("1","2");
        Assert.isTrue(ids.contains("1"),"不对");
    }

}