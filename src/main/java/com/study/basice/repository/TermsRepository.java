package com.study.basice.repository;

import com.study.basice.entity.TermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/20
 **/
public interface TermsRepository extends JpaRepository<TermsEntity, Long> {

    @Query(value = "select * from basice_terms where 1=1 and it_enabled =1 limit ?1,?2", nativeQuery = true)
    List<TermsEntity> queryTermsData(int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_terms where 1=1 and it_enabled =1")
    int queryTermsCount() throws Exception;


    @Query(value = "select * from basice_terms where 1=1 and it_enabled =1 and words = ?1 limit 1", nativeQuery = true)
    TermsEntity queryTermsByWords(String words) throws Exception;

}
