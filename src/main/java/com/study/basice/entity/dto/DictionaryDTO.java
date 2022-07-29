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
 * @date 2022/5/25
 **/
@ApiModel(value = "DictionaryDTO", description = "")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryDTO {

    @ApiModelProperty(value = "汉字", name = "words")
    private String words;

    @ApiModelProperty(value = "繁体字", name = "oldword")
    private String oldword;

    @ApiModelProperty(value = "笔画数", name = "strokes")
    private int strokes;

    @ApiModelProperty(value = "拼音", name = "pinyin")
    private String pinyin;

    @ApiModelProperty(value = "部首", name = "radicals")
    private String radicals;

    @ApiModelProperty(value = "解释", name = "explanation")
    private String explanation;

    @ApiModelProperty(value = "更多解释", name = "more")
    private String more;
}
