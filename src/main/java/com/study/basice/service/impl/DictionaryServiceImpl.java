package com.study.basice.service.impl;

import com.study.basice.entity.DictionaryEntity;
import com.study.basice.entity.dto.DictionaryDTO;
import com.study.basice.repository.DictionaryGradeRepository;
import com.study.basice.repository.DictionaryRepository;
import com.study.basice.service.DictionaryService;
import com.study.basice.utils.PageUtils;
import com.study.basice.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryRepository repository;

    @Autowired
    private DictionaryGradeRepository dictionaryGradeRepository;

    @Override
    public List<DictionaryEntity> queryDicoionarys() {
        return repository.findAll();
    }

    /**
     * @param entity
     */
    @Override
    public void saveDicoionary(List<DictionaryEntity> entity) {
        repository.saveAll(entity);
    }

    @Override
    public DictionaryDTO queryDictionaryByWords(String words) throws Exception {
        DictionaryEntity dictionaryEntity = repository.queryDictionaryByWords(words);
        if (StringUtils.isNotNull(dictionaryEntity)) {
            DictionaryDTO dictionaryDTO = new DictionaryDTO();
            BeanUtils.copyProperties(dictionaryEntity, dictionaryDTO);
            return dictionaryDTO;
        }
        return null;
    }

    @Override
    public PageUtils<String> queryRadicals(PageUtils<String> pageUtils) throws Exception {
        pageUtils.setData(repository.queryRadicalsData(pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(repository.queryRadicalsCount());
        return pageUtils;
    }

    @Override
    public PageUtils<String> queryWordsByRadicals(String radicals, PageUtils<String> pageUtils) throws Exception {
        pageUtils.setData(repository.queryWordsByRadicalsData(radicals, pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(repository.queryWordsByRadicalsCount(radicals));
        return pageUtils;
    }

    @Override
    public PageUtils<String> queryWordsByRadicalsAndStrokes(String radicals, String strokes, PageUtils<String> pageUtils) throws Exception {
        String newRadicals = (radicals == null || radicals == "") ? null : "" + radicals + "";
        String newStrokes = (strokes == null || strokes == "") ? null : "" + strokes + "";
        pageUtils.setData(repository.queryWordsByRadicalsAndStrokesData(newRadicals, newStrokes, pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(repository.queryWordsByRadicalsAndStrokesCount(newRadicals, newStrokes));
        return pageUtils;
    }

    @Override
    public PageUtils<String> queryPinYins(String firstPinYin, PageUtils<String> pageUtils) throws Exception {
        pageUtils.setData(repository.queryPinYinsData(firstPinYin, pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(repository.queryPinYinsCount(firstPinYin));
        return pageUtils;
    }

    @Override
    public PageUtils<String> queryWordsByPinYin(String pinyin, PageUtils<String> pageUtils) throws Exception {
        pageUtils.setData(repository.queryWordsByPinYinData(pinyin, pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(repository.queryWordsByPinYinCount(pinyin));
        return pageUtils;
    }

    @Override
    public PageUtils<String> queryWordsByGrade(String grade, PageUtils<String> pageUtils) throws Exception {
        pageUtils.setData(dictionaryGradeRepository.queryWordsByGradeData(grade, pageUtils.getPageIndex(), pageUtils.getPageSize()));
        pageUtils.setTotalCount(dictionaryGradeRepository.queryWordsByGradeCount(grade));
        return pageUtils;
    }

}
