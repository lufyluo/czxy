package com.czxy.manage.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionTest {
    @Test
    public void removeAllTest(){
        List<String> arr1 = new ArrayList<>();
        arr1.add("a");
        arr1.add("b");
        arr1.add("c");
        List<String> arr2 = new ArrayList<>();
        arr2.add("a");
        arr2.add("b");
        arr1.removeAll(arr2);
        System.out.println(arr1.size());
    }
}
