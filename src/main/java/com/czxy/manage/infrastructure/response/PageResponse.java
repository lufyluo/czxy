package com.czxy.manage.infrastructure.response;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-28 下午1:34
 */
@Data
@NoArgsConstructor
public class PageResponse<T> extends BaseResponse<List<T>> implements Serializable {
    private int pageIndex;
    private int pageCount;
    private int pageSize;
    private Long totalCount;

    private PageResponse(ResponseStatus status, PageInfo<T> result) {
        this.status = status;
        this.code = status.getCode();
        this.msg = status.getDesc();
        this.pageIndex = result.getPageNum();
        this.pageCount = result.getPages();
        this.pageSize = result.getPageSize();
        this.totalCount = result.getTotal();
        this.data = result.getList();
    }

    public static PageResponse success(PageInfo pageList) {
        return new PageResponse(ResponseStatus.SUCCESS, pageList);
    }
}
