package com.study.basice.repository;

import com.study.basice.entity.DictionaryGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/7/6
 **/
public interface DictionaryGradeRepository extends JpaRepository<DictionaryGradeEntity, Long> {

    @Query(value = "select words from basice_dictionary_grade where 1=1 and it_enabled =1 and grade_type = ?1 limit ?2,?3", nativeQuery = true)
    List<String> queryWordsByGradeData(String gradeType, int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_dictionary_grade where 1=1 and it_enabled =1 and grade_type =?1")
    int queryWordsByGradeCount(String gradeType) throws Exception;
}
