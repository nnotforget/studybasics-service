package com.study.basice.controller;

import com.study.basice.constant.MessageConstants;
import com.study.basice.entity.RestResult;
import com.study.basice.entity.dto.TermsDTO;
import com.study.basice.service.TermsService;
import com.study.basice.utils.CommonTools;
import com.study.basice.utils.PageUtils;
import com.study.basice.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 词 控制层
 *
 * @author zhaoyk
 * @date 2022/7/7
 **/
@Slf4j
@RestController
@RequestMapping("v1/terms")
@Api(tags = "词语相关")
public class TermsController {

    @Autowired
    private TermsService service;


    @ApiOperation("根据词语查询详细信息")
    @GetMapping("/queryTermsByWordsData")
    public RestResult<TermsDTO> getTermsByWords(@ApiParam(name = "words", value = "词语") @RequestParam("words") String words) {
        log.info("### 根据词语查询详细信息:{}", words);
        RestResult<TermsDTO> restResult = new RestResult<>();
        try {
            // 判断汉字是否为空
            if (StringUtils.isEmpty(words)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入词语信息.");
                return restResult;
            }

            //判断是否是特殊字符
            if (CommonTools.isSpecialChar(words)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入正确的词语信息.");
                return restResult;
            }

            TermsDTO terms = service.queryTermsByWords(words);
            if (StringUtils.isNull(terms)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("暂无[" + words + "]的数据信息.");
                return restResult;
            }
            // TODO:后期这里添加 redis缓存

            restResult.setData(terms);
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg("查询成功");
            return restResult;
        } catch (Exception ce) {
            restResult.setData(null);
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg(ce.getMessage());
            return restResult;
        }
    }

    @ApiOperation("获取词语列表信息")
    @GetMapping("/queryTermsList")
    public RestResult<Map<String, Object>> queryTermsList(@ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                          @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 获取词语列表信息:{}-{}", pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            PageUtils<TermsDTO> pageUtilsTerms = service.queryTermsList(pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("wordsList", pageUtilsTerms.getData());
            map.put("wordsCount", pageUtilsTerms.getTotalCount());
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg("查询成功");
            restResult.setData(map);
        } catch (Exception ex) {
            restResult.setData(null);
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg(ex.getMessage());
            return restResult;
        }
        return restResult;
    }

}
