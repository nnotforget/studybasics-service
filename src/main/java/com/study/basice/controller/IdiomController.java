package com.study.basice.controller;

import com.study.basice.constant.MessageConstants;
import com.study.basice.entity.RestResult;
import com.study.basice.entity.dto.IdiomDTO;
import com.study.basice.service.IdiomService;
import com.study.basice.utils.CommonTools;
import com.study.basice.utils.PageUtils;
import com.study.basice.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 成语 控制层
 *
 * @author zhaoyk
 * @date 2022/7/6
 **/
@Slf4j
@RestController
@RequestMapping("v1/idiom")
@Api(tags = "成语相关")
public class IdiomController {

    @Autowired
    private IdiomService service;

    @ApiOperation("根据成语查询详细信息")
    @GetMapping("/queryIdiomByWordsData")
    public RestResult<IdiomDTO> getIdiomByWords(@ApiParam(name = "words", value = "成语词典") @RequestParam("words") String words) {
        log.info("### 根据成语查询详细信息:{}", words);
        RestResult<IdiomDTO> restResult = new RestResult<>();
        try {
            // 判断汉字是否为空
            if (StringUtils.isEmpty(words)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入成语信息.");
                return restResult;
            }

            //判断是否是特殊字符
            if (CommonTools.isSpecialChar(words)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入正确的成语信息.");
                return restResult;
            }

            IdiomDTO idiom = service.queryIdiomByWords(words);
            if (StringUtils.isNull(idiom)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("暂无[" + words + "]的数据信息.");
                return restResult;
            }
            // TODO:后期这里添加 redis缓存

            restResult.setData(idiom);
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

    @ApiOperation("根据成语首字查询成语信息")
    @GetMapping("/queryIdiomByFirstWordsList")
    public RestResult<Map<String, Object>> queryIdiomByFirstWords(@ApiParam(name = "words", value = "成语首字") @RequestParam("words") String words,
                                                                  @ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                                  @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 根据成语首字查询成语信息:{}-{}-{}", words, pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            // 判断部首是否为空
            if (StringUtils.isEmpty(words)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入汉字信息.");
                return restResult;
            }

            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryIdiomByFirstWords(words, pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(3);
            map.put("words", words);
            map.put("wordsList", pageUtils.getData());
            map.put("wordsCount", pageUtils.getTotalCount());
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

    @ApiOperation("根据成语拼音缩写查询成语信息")
    @GetMapping("/queryIdiomByAbbreviationList")
    public RestResult<Map<String, Object>> queryIdiomByAbbreviationList(@ApiParam(name = "abbreviation", value = "拼音缩写") @RequestParam("abbreviation") String abbreviation,
                                                                        @ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                                        @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 根据成语拼音缩写查询成语信息:{}-{}-{}", abbreviation, pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            // 判断部首是否为空
            if (StringUtils.isEmpty(abbreviation)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入拼音缩写信息.");
                return restResult;
            }

            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryIdiomByAbbreviation(abbreviation, pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(3);
            map.put("abbreviation", abbreviation);
            map.put("wordsList", pageUtils.getData());
            map.put("wordsCount", pageUtils.getTotalCount());
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

    @ApiOperation("获取成语列表信息")
    @GetMapping("/queryIdiomList")
    public RestResult<Map<String, Object>> queryIdiomList(@ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                          @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 获取成语列表信息:{}-{}", pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            PageUtils<IdiomDTO> pageUtilsIdioms = service.queryIdiomsList(pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("wordsList", pageUtilsIdioms.getData());
            map.put("wordsCount", pageUtilsIdioms.getTotalCount());
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
