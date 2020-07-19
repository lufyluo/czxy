package com.czxy.manage.service;

import com.czxy.manage.model.vo.site.TopicInfo;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public   class CollectionTest {

    @Test
    public void removeAllTest() {
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

    @Test
    public void filterTest() {
        List<TopicInfo> arr1 = new ArrayList<>();
        TopicInfo topicInfo = new TopicInfo();
        topicInfo.setTopic("lufy");
        arr1.add(topicInfo);
        List<Integer> collect = arr1
                .stream()
                .filter(n -> n.getId() != null)
                .map(n -> n.getId())
                .collect(Collectors.toList());
        System.out.println(arr1.size());
    }

    @Test
    public void hashSetTest() {
        List<Integer> arr1 = new ArrayList<>();
        Integer integer = new Integer(1);
        Integer integer1 = new Integer(1);
        System.out.println(integer.equals(integer1));
        arr1.add(integer);
        arr1.add(integer);
        HashSet hashSet = new HashSet(arr1);

    }
    @Test
    public void doubleTest() {
        int a =17123;
        int b = 13;
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        System.out.println(decimalFormat.format((double)a/b));
    }
}
