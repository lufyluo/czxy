package com.czxy.manage.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
    @Test
    public void subListTest(){
        List<String> arr = new ArrayList<String>();
        arr.add("a");
        arr.add("b");
        arr.add("c");
        List<String> strings = arr.subList(0, arr.size());
        strings.forEach(n->{
            if(n.equals("b")){
                return;
            }
            System.out.println(n);
        });
        System.out.println("===================");
        strings.stream().forEach(n->{
            if(n.equals("a")){
                return;
            }
            System.out.println(n);
        });
        System.out.println(strings.size());
    }
}
