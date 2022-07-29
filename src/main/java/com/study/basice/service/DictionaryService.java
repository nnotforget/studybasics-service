package com.study.basice.service;

import com.study.basice.entity.DictionaryEntity;
import com.study.basice.entity.dto.DictionaryDTO;
import com.study.basice.utils.PageUtils;

import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/
public interface DictionaryService {

    List<DictionaryEntity> queryDicoionarys();

    void saveDicoionary(List<DictionaryEntity> entity);

    DictionaryDTO queryDictionaryByWords(String words) throws Exception;

    PageUtils<String> queryRadicals(PageUtils<String> pageUtils) throws Exception;

    PageUtils<String> queryWordsByRadicals(String radicals, PageUtils<String> pageUtils) throws Exception;

    PageUtils<String> queryWordsByRadicalsAndStrokes(String radicals, String strokes, PageUtils<String> pageUtils) throws Exception;

    PageUtils<String> queryPinYins(String firstPinYin, PageUtils<String> pageUtils) throws Exception;

    PageUtils<String> queryWordsByPinYin(String pinyin, PageUtils<String> pageUtils) throws Exception;

    PageUtils<String> queryWordsByGrade(String grade, PageUtils<String> pageUtils) throws Exception;
}
