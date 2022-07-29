package com.study.basice.repository;

import com.study.basice.entity.IdiomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/
public interface IdiomRepository extends JpaRepository<IdiomEntity, Long> {

    @Query(value = "SELECT * FROM basice_idiom where 1=1 and it_enabled =1 GROUP BY words HAVING count(words) >1", nativeQuery = true)
    List<IdiomEntity> findRepeat() throws Exception;

    /**
     * 查询 成语词典 列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Query(value = "select * from basice_idiom where 1=1 and it_enabled =1 limit ?1,?2", nativeQuery = true)
    List<IdiomEntity> queryIdiomData(int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_idiom where 1=1 and it_enabled =1")
    int queryIdiomCount() throws Exception;

    /**
     * 获取成语详细信息
     *
     * @param words
     * @return
     * @throws Exception
     */
    @Query(value = "select * from basice_idiom where 1=1 and it_enabled =1 and words = ?1 limit 1", nativeQuery = true)
    IdiomEntity queryIdiomByWords(String words) throws Exception;

    /**
     * 根据首字母查询成语 列表信息
     *
     * @param words
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Query(value = "select words from basice_idiom where 1=1 and it_enabled =1 and words is not null and words like CONCAT(?1,'%') limit ?2,?3", nativeQuery = true)
    List<String> queryIdiomByWords(String words, int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_idiom where 1=1 and it_enabled =1 and words is not null and words like CONCAT(?1,'%')")
    int queryIdiomByWordsCount(String words) throws Exception;


    /**
     * 根据 拼音缩写 查询成语 列表信息
     *
     * @param abbreviation
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Query(value = "select words from basice_idiom where 1=1 and it_enabled =1 and abbreviation is not null and abbreviation =?1 limit ?2,?3", nativeQuery = true)
    List<String> queryIdiomByAbbreviation(String abbreviation, int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_idiom where 1=1 and it_enabled =1 and abbreviation is not null and abbreviation= ?1")
    int queryIdiomByAbbreviationCount(String abbreviation) throws Exception;
}
