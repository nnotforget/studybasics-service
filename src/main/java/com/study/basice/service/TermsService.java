package com.study.basice.service;

import com.study.basice.entity.TermsEntity;
import com.study.basice.entity.dto.TermsDTO;
import com.study.basice.utils.PageUtils;

import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/20
 **/
public interface TermsService {

    void saveWords(List<TermsEntity> entitys);

    void saveWords(TermsEntity entity);

    TermsDTO queryTermsByWords(String words) throws Exception;

    PageUtils<TermsDTO> queryTermsList(PageUtils<String> pageUtils) throws Exception;
}
