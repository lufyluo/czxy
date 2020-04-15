package com.czxy.manage.infrastructure.util.maplain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-10-25 上午9:59
 */
@Getter
@Setter
public class Ancestors<T extends Ancestors> {
    private Long id;
    private Long parentId;
    private List<T> children;
}
