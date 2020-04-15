package com.czxy.manage.infrastructure.util.maplain;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-10-25 上午10:06
 */
public class MaplainUtil {
    public static <T extends Ancestors> List<T> toHierarchy(List<T> plains) {
        List<Long> rootParentIds = new ArrayList<>();
        for (int i = 0; i < plains.size(); i++) {
            if (plains.get(i).getParentId() == null || plains.get(i).getParentId() == 0) {
                rootParentIds.add(plains.get(i).getId());
                continue;
            }
            T current = plains.get(i);
            Optional<T> parentOptional = plains.stream().filter(n -> n.getId().equals(current.getParentId())).findFirst();
            if (parentOptional.isPresent()) {
                if (parentOptional.get().getChildren() == null) {
                    parentOptional.get().setChildren(new ArrayList<>());
                }
                parentOptional.get().getChildren().add(current);
            } else {
                rootParentIds.add(plains.get(i).getId());
            }
        }

        return plains.stream().filter(n -> rootParentIds.contains(n.getId())).collect(Collectors.toList());
    }

    /**
     * 扁平化数据获取末端节点
     */
    @Deprecated
    public static <T extends Ancestors> List<T> tips(@NotNull List<T> plains) {
        if (plains.size() == 0) {
            return null;
        }
        List<Long> parentIds = plains.stream().map(T::getParentId).collect(Collectors.toList());
        return plains.stream().filter(n -> !parentIds.contains(n.getId())).collect(Collectors.toList());
    }
}
