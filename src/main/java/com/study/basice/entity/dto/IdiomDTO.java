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
 * @date 2022/5/23
 **/
@ApiModel(value = "IdiomDTO", description = "")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdiomDTO {

    @ApiModelProperty(value = "成语", name = "words")
    private String words;

    @ApiModelProperty(value = "拼音", name = "pinyin")
    private String pinyin;

    @ApiModelProperty(value = "拼音缩写", name = "abbreviation")
    private String abbreviation;

    @ApiModelProperty(value = "基本释义", name = "explanation")
    private String explanation;

    @ApiModelProperty(value = "出处", name = "derivation")
    private String derivation;

    @ApiModelProperty(value = "例句", name = "example")
    private String example;

    @ApiModelProperty(value = "近义词", name = "nearWords")
    private String nearWords;

    @ApiModelProperty(value = "反义词", name = "antisenseWords")
    private String antisenseWords;

}
