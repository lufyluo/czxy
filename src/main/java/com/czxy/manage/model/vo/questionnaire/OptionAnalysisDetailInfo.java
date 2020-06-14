package com.czxy.manage.model.vo.questionnaire;

import lombok.Data;

import java.util.List;

@Data
public class OptionAnalysisDetailInfo extends OptionDetailInfo{
    private int count;
    private String percent;
    private List<String> answers;
}
