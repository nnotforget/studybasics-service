package com.study.basice.service.impl;

import com.study.basice.entity.IdiomEntity;
import com.study.basice.entity.dto.IdiomDTO;
import com.study.basice.repository.IdiomRepository;
import com.study.basice.service.IdiomService;
import com.study.basice.utils.PageUtils;
import com.study.basice.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 成语
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/
@Service
public class IdiomServiceImpl implements IdiomService {

    @Autowired
    private IdiomRepository repository;

    @Override
    public void saveIdioms(List<IdiomEntity> entitys) {
        repository.saveAll(entitys);
    }

    @Override
    public void saveIdiom(IdiomEntity entity) {
        repository.save(entity);
    }

    @Override
    public List<IdiomEntity> findRepeatIdiom() throws Exception {
        return repository.findRepeat();
    }

    /**
     * 查询 成语 详细信息
     *
     * @param words
     * @return
     * @throws Exception
     */
    @Override
    public IdiomDTO queryIdiomByWords(String words) throws Exception {
        IdiomEntity idiomEntity = repository.queryIdiomByWords(words);
        if (StringUtils.isNotNull(idiomEntity)) {
            IdiomDTO idiomDTO = new IdiomDTO();
            BeanUtils.copyProperties(idiomEntity, idiomDTO);
            return idiomDTO;
        }
        return null;
    }

    /**
     * 根据 成语首字 查询
     *
     * @param words
     * @param pageUtils
     * @return
     * @throws Exception
     */
    @Override
    public PageUtils<String> queryIdiomByFirstWords(String words, PageUtils<String> pageUtils) throws Exception {
        pageUtils.setData(repository.queryIdiomByWords(words, pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(repository.queryIdiomByWordsCount(words));
        return pageUtils;
    }

    /**
     * 根据 成语拼音缩写 查询
     *
     * @param abbreviation
     * @param pageUtils
     * @return
     * @throws Exception
     */
    @Override
    public PageUtils<String> queryIdiomByAbbreviation(String abbreviation, PageUtils<String> pageUtils) throws Exception {
        pageUtils.setData(repository.queryIdiomByAbbreviation(abbreviation, pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(repository.queryIdiomByAbbreviationCount(abbreviation));
        return pageUtils;
    }


    @Override
    public void delIdiom(IdiomEntity entity) {
        repository.delete(entity);
    }

    /**
     * 查询 成语 列表
     *
     * @param pageUtils
     * @return
     * @throws Exception
     */
    @Override
    public PageUtils<IdiomDTO> queryIdiomsList(PageUtils<String> pageUtils) throws Exception {
        PageUtils idiomPageUtils = new PageUtils();
        List<IdiomDTO> idiomDTOList = new ArrayList<>();
        List<IdiomEntity> idiomEntities = repository.queryIdiomData(pageUtils.getPageIndex(), pageUtils.getPageSize());
        if (StringUtils.isNotNull(idiomEntities) && idiomEntities.size() > 0) {
            for (IdiomEntity idiomEntity : idiomEntities) {
                IdiomDTO idiomDTO = new IdiomDTO();
                BeanUtils.copyProperties(idiomEntity, idiomDTO);
                idiomDTOList.add(idiomDTO);
            }
        }
        idiomPageUtils.setData(idiomDTOList);
        idiomPageUtils.setTotalCount(repository.queryIdiomCount());
        return idiomPageUtils;
    }

}
