package com.study.basice.controller;

import com.alibaba.fastjson.JSONArray;
import com.study.basice.entity.DictionaryEntity;
import com.study.basice.entity.IdiomEntity;
import com.study.basice.entity.TermsEntity;
import com.study.basice.entity.dto.IdiomDTO;
import com.study.basice.service.DictionaryService;
import com.study.basice.service.IdiomService;
import com.study.basice.service.TermsService;
import com.study.basice.utils.ChineseCharacterUtil;
import com.study.basice.utils.JSONUtils;
import com.study.basice.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据初始化
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/

@ApiIgnore
@RestController
@RequestMapping("v1/initialization")
public class InitializationController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private IdiomService idiomService;

    @Autowired
    private TermsService termsService;

    /**
     * 新华字典
     * 读取json文件并保存数据信息
     */
    @GetMapping("readjson")
    public void readJsonTxt() {
        String s = JSONUtils.readJsonFile("D:\\workspace\\chinese-xinhua\\data\\word.json");
        JSONArray jsonArray = JSONArray.parseArray(s);
        List<DictionaryEntity> dictionaryList = jsonArray.toJavaList(DictionaryEntity.class);
        if (StringUtils.isNotEmpty(dictionaryList)) {
            for (DictionaryEntity dictionary : dictionaryList) {
                dictionary.setEnabled(1);
                dictionary.setPostDate(new Date());
            }
        }
        dictionaryService.saveDicoionary(dictionaryList);
    }

    @GetMapping("readWords")
    public void readWords() {
        String s = JSONUtils.readJsonFile("D:\\workspace\\chinese-xinhua\\data\\ci.json");
        JSONArray jsonArray = JSONArray.parseArray(s);
        List<TermsEntity> entities = jsonArray.toJavaList(TermsEntity.class);
        if (StringUtils.isNotEmpty(entities)) {
            for (TermsEntity termsEntity : entities) {
                termsEntity.setPostDate(new Date());
            }
        }
        termsService.saveWords(entities);
    }

    /**
     * 读取成语
     */
    @GetMapping("readidiom")
    public void readIdiomJson() {
        String s = JSONUtils.readJsonFile("D:\\workspace\\chinese-xinhua\\data\\chengyu.json");
        JSONArray jsonArray = JSONArray.parseArray(s);
        List<IdiomDTO> idiomEntities = jsonArray.toJavaList(IdiomDTO.class);

        List<IdiomEntity> idiomEntityList = new ArrayList<>();
        if (StringUtils.isNotEmpty(idiomEntities)) {
            for (IdiomDTO dto : idiomEntities) {
                try {
                    IdiomEntity newIdiomEntity = null; //idiomService.queryIdiomByWords(dto.getName());
                    if (StringUtils.isNull(newIdiomEntity)) {
                        newIdiomEntity = new IdiomEntity();
                        newIdiomEntity.setPostDate(new Date());
                        //   newIdiomEntity.setWords(dto.getName());
                        newIdiomEntity.setPinyin(dto.getPinyin());
                        //  newIdiomEntity.setExplanation(dto.getJbsy());
                        //   newIdiomEntity.setExample(dto.getLiju());
                        //   newIdiomEntity.setDerivation(dto.getChuchu());
                        //  newIdiomEntity.setAntisenseWords(dto.getFyc());
                        // newIdiomEntity.setNearWords(dto.getJyc());
                        idiomEntityList.add(newIdiomEntity);
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            idiomService.saveIdioms(idiomEntityList);
        }
    }

    /**
     * 删除重复的 成语字典
     */
    @GetMapping("delRepeatIdiom")
    public void delRepeatIdiom() {
        try {
            List<IdiomEntity> idiomEntities = idiomService.findRepeatIdiom();
            if (StringUtils.isEmpty(idiomEntities)) {
                return;
            }
            for (IdiomEntity idiomEntity : idiomEntities) {
                IdiomEntity newIdiomEntity = null; //idiomService.queryIdiomByWords(idiomEntity.getWords());
                idiomService.delIdiom(newIdiomEntity);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    @GetMapping("updateDictionary")
    public void updateDictionary() {

        List<DictionaryEntity> dictionaryEntityList = dictionaryService.queryDicoionarys();
        if (StringUtils.isEmpty(dictionaryEntityList)) {
            return;
        }
        List<DictionaryEntity> newdictionarys = new ArrayList<>();
        for (DictionaryEntity dictionary : dictionaryEntityList) {
            try {
                String word = dictionary.getWords();
                String pinyin4j = ChineseCharacterUtil.toPinyin(word, ChineseCharacterUtil.PinyinType.ALL);
                dictionary.setPinyin4j(pinyin4j);
                dictionary.setUpdateDate(new Date());
                newdictionarys.add(dictionary);
            } catch (Exception ee) {
                ee.printStackTrace();
                continue;
            }
        }
        dictionaryService.saveDicoionary(newdictionarys);

    }


}
