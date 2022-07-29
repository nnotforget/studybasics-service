package com.study.basice.service;

import com.study.basice.entity.IdiomEntity;
import com.study.basice.entity.dto.IdiomDTO;
import com.study.basice.utils.PageUtils;

import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/
public interface IdiomService {
    void saveIdioms(List<IdiomEntity> entitys);

    void saveIdiom(IdiomEntity entity);

    List<IdiomEntity> findRepeatIdiom() throws Exception;

    IdiomDTO queryIdiomByWords(String word) throws Exception;

    PageUtils<String> queryIdiomByFirstWords(String words, PageUtils<String> pageUtils) throws Exception;

    void delIdiom(IdiomEntity entity);

    PageUtils<IdiomDTO> queryIdiomsList(PageUtils<String> pageUtils) throws Exception;

    PageUtils<String> queryIdiomByAbbreviation(String abbreviation, PageUtils<String> pageUtils) throws Exception;
}
