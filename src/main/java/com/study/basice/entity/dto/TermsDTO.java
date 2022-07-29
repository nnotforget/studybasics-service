package com.study.basice.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/7/7
 **/
@ApiModel(value = "TermsDTO", description = "")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TermsDTO {

    @ApiModelProperty(value = "成语", name = "words")
    private String words;

    @ApiModelProperty(value = "解释", name = "explanation")
    private String explanation;
}
