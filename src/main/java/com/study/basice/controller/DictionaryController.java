package com.study.basice.controller;

import com.study.basice.constant.GradeEnum;
import com.study.basice.constant.MessageConstants;
import com.study.basice.entity.RestResult;
import com.study.basice.entity.dto.DictionaryDTO;
import com.study.basice.service.DictionaryService;
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
 * 新华字典 控制层
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/
@Slf4j
@RestController
@RequestMapping("v1/dictionary")
@Api(tags = "新华字典相关")
public class DictionaryController {

    @Autowired
    private DictionaryService service;


    @ApiOperation("根据汉字查询详细信息")
    @GetMapping("/queryInEnergyData")
    public RestResult<DictionaryDTO> getDictiomaryByWords(@ApiParam(name = "words", value = "汉字") @RequestParam("words") String words) {
        log.info("### 根据汉字查询详细信息:{}", words);
        RestResult<DictionaryDTO> restResult = new RestResult<>();
        try {
            // 判断汉字是否为空
            if (StringUtils.isEmpty(words)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入汉字信息.");
                return restResult;
            }

            //判断是否是特殊字符
            if (CommonTools.isSpecialChar(words)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入正确的汉字信息.");
                return restResult;
            }

            DictionaryDTO dictionary = service.queryDictionaryByWords(words);
            if (StringUtils.isNull(dictionary)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("暂无[" + words + "]的数据信息.");
                return restResult;
            }
            // TODO:后期这里添加 redis缓存

            restResult.setData(dictionary);
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


    @ApiOperation("获取所有的部首列表信息")
    @GetMapping("/queryRadicalsData")
    public RestResult<Map<String, Object>> queryAllRadicals(@ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                            @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 获取所有的部首列表信息:{}-{}", pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryRadicals(pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("radicalsList", pageUtils.getData());
            map.put("radicalsCount", pageUtils.getTotalCount());
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

    @ApiOperation("根据部首查询汉字信息")
    @GetMapping("/queryWordsByRadicalsData")
    public RestResult<Map<String, Object>> queryWordsByRadicals(@ApiParam(name = "radicals", value = "部首") @RequestParam("radicals") String radicals,
                                                                @ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                                @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 根据部首查询汉字信息:{}-{}-{}", radicals, pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            // 判断部首是否为空
            if (StringUtils.isEmpty(radicals)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入部首信息.");
                return restResult;
            }

            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryWordsByRadicals(radicals, pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(3);
            map.put("radicals", radicals);
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

    @ApiOperation("根据字母查询拼音列表信息")
    @GetMapping("/queryPinYinData")
    public RestResult<Map<String, Object>> queryAllPinYin(
            @ApiParam(name = "firstPinYin", value = "首字母") @RequestParam("firstPinYin") String firstPinYin,
            @ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 根据字母查询拼音列表信息:{}-{}-{}", firstPinYin, pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryPinYins(firstPinYin, pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("pinyinList", pageUtils.getData());
            map.put("pinyinCount", pageUtils.getTotalCount());
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


    @ApiOperation("根据拼音查询汉字信息")
    @GetMapping("/queryWordsByPinYinData")
    public RestResult<Map<String, Object>> queryWordsByPinYin(@ApiParam(name = "pinyin", value = "拼音") @RequestParam("pinyin") String pinyin,
                                                              @ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                              @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 根据拼音查询汉字信息:{}-{}-{}", pinyin, pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            // 判断部首是否为空
            if (StringUtils.isEmpty(pinyin)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入部首信息.");
                return restResult;
            }
            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryWordsByPinYin(pinyin, pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(3);
            map.put("pinyin", pinyin);
            map.put("pinyinList", pageUtils.getData());
            map.put("pinyinCount", pageUtils.getTotalCount());
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg("查询成功");
            restResult.setData(map);
        } catch (Exception ee) {
            restResult.setData(null);
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg(ee.getMessage());
            return restResult;
        }
        return restResult;
    }

    @ApiOperation("根据部首笔画查询汉字信息")
    @GetMapping("/queryWordsByRadicalsAndStrokesData")
    public RestResult<Map<String, Object>> queryWordsByRadicalsAndStrokes(@ApiParam(name = "radicals", value = "部首") @RequestParam("radicals") String radicals,
                                                                          @ApiParam(name = "strokes", value = "笔画") @RequestParam("strokes") String strokes,
                                                                          @ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                                          @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 根据部首笔画查询汉字信息:{}-{}-{}-{}", radicals, strokes, pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            // 判断部首\笔画是否为空
            if (StringUtils.isEmpty(radicals) && StringUtils.isEmpty(strokes)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入部首或者笔画信息.");
                return restResult;
            }

            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryWordsByRadicalsAndStrokes(radicals, strokes, pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(3);
            map.put("radicals", radicals);
            map.put("strokes", strokes);
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

    @ApiOperation("根据年级查询生字信息")
    @GetMapping("/queryWordsByGradeData")
    public RestResult<Map<String, Object>> queryWordsByGradeData(@ApiParam(name = "grade", value = "年级") @RequestParam("grade") String grade,
                                                                 @ApiParam(name = "period", value = "1上/2下学期") @RequestParam("period") String period,
                                                                 @ApiParam(name = "pageNum", value = "当前页码") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                                 @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        log.info("### 根据年级查询生字信息:{}-{}-{}-{}", grade, period, pageNum, pageSize);
        RestResult<Map<String, Object>> restResult = new RestResult<>();
        PageUtils<String> pageUtils = new PageUtils<>();
        try {
            // 判断年级、学期是否为空
            if (StringUtils.isEmpty(grade) || StringUtils.isEmpty(period)) {
                restResult.setCode(MessageConstants.SUCCESS_CODE);
                restResult.setMsg("请录入年级、学期信息.");
                return restResult;
            }
            pageNum = pageNum * pageSize - pageSize;
            pageUtils.setPageIndex(pageNum);
            pageUtils.setPageSize(pageSize);
            pageUtils = service.queryWordsByGrade(grade + "-" + period, pageUtils);
            Map<String, Object> map = new LinkedHashMap<>(3);
            String periodName = null;
            switch (period) {
                case "1":
                    periodName = GradeEnum.PERIOD_UPPER.getDesc();
                    break;
                case "2":
                    periodName = GradeEnum.PERIOD_LOWER.getDesc();
                    break;
            }
            map.put("grade", grade + "年级-" + periodName);
            map.put("pinyinList", pageUtils.getData());
            map.put("pinyinCount", pageUtils.getTotalCount());
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg("查询成功");
            restResult.setData(map);

        } catch (Exception ee) {
            restResult.setData(null);
            restResult.setCode(MessageConstants.SUCCESS_CODE);
            restResult.setMsg(ee.getMessage());
            return restResult;
        }
        return restResult;
    }

}
