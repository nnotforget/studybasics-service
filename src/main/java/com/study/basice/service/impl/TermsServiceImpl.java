package com.study.basice.service.impl;

import com.study.basice.entity.TermsEntity;
import com.study.basice.entity.dto.TermsDTO;
import com.study.basice.repository.TermsRepository;
import com.study.basice.service.TermsService;
import com.study.basice.utils.PageUtils;
import com.study.basice.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/20
 **/
@Service
public class TermsServiceImpl implements TermsService {

    @Autowired
    private TermsRepository repository;

    @Override
    public void saveWords(List<TermsEntity> entitys) {
        repository.saveAll(entitys);
    }

    @Override
    public void saveWords(TermsEntity entity) {
        repository.save(entity);
    }

    @Override
    public TermsDTO queryTermsByWords(String words) throws Exception {
        TermsEntity termsEntity = repository.queryTermsByWords(words);
        if (StringUtils.isNotNull(termsEntity)) {
            TermsDTO termsDTO = new TermsDTO();
            BeanUtils.copyProperties(termsEntity, termsDTO);
            return termsDTO;
        }
        return null;
    }

    @Override
    public PageUtils<TermsDTO> queryTermsList(PageUtils<String> pageUtils) throws Exception {
        PageUtils termsPageUtils = new PageUtils();
        List<TermsDTO> termsDTOList = new ArrayList<>();
        List<TermsEntity> termsEntities = repository.queryTermsData(pageUtils.getPageIndex(), pageUtils.getPageSize());
        if (StringUtils.isNotNull(termsEntities) && termsEntities.size() > 0) {
            for (TermsEntity termsEntity : termsEntities) {
                TermsDTO termsDTO = new TermsDTO();
                BeanUtils.copyProperties(termsEntity, termsDTO);
                termsDTOList.add(termsDTO);
            }
        }
        termsPageUtils.setData(termsDTOList);
        termsPageUtils.setTotalCount(repository.queryTermsCount());
        return termsPageUtils;
    }
}
