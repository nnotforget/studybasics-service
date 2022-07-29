package com.study.basice.repository;

import com.study.basice.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JPA Dao  层
 *
 * @author zhaoyk
 * @date 2022/5/17
 **/
public interface DictionaryRepository extends JpaRepository<DictionaryEntity, Long> {

    /**
     * @param words
     * @return
     * @throws Exception
     */
    @Query(value = "select * from basice_dictionary where 1=1 and it_enabled =1 and words = ?1 limit 1", nativeQuery = true)
    DictionaryEntity queryDictionaryByWords(String words) throws Exception;

    @Query(value = "select radicals from basice_dictionary where 1=1 and it_enabled =1 and radicals <> '' GROUP BY radicals limit ?1,?2 ", nativeQuery = true)
    List<String> queryRadicalsData(int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT radicals) from basice_dictionary where 1=1 and it_enabled =1 and radicals <> ''")
    int queryRadicalsCount() throws Exception;

    @Query(value = "select words from basice_dictionary where 1=1 and it_enabled =1 and radicals = ?1 limit ?2,?3", nativeQuery = true)
    List<String> queryWordsByRadicalsData(String radicals, int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_dictionary where 1=1 and it_enabled =1 and radicals =?1")
    int queryWordsByRadicalsCount(String radicals) throws Exception;

    @Query(value = "select pinyin4j from basice_dictionary where 1=1 and it_enabled =1 and pinyin4j is not null and pinyin4j like CONCAT(?1,'%') GROUP BY pinyin4j limit ?2,?3", nativeQuery = true)
    List<String> queryPinYinsData(String pinyin, int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT pinyin4j) from basice_dictionary where 1=1 and it_enabled =1 and pinyin4j is not null and pinyin4j like CONCAT(?1,'%')")
    int queryPinYinsCount(String pinyin) throws Exception;

    @Query(value = "select words from basice_dictionary where 1=1 and it_enabled =1 and pinyin4j = ?1 limit ?2,?3", nativeQuery = true)
    List<String> queryWordsByPinYinData(String radicals, int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_dictionary where 1=1 and it_enabled =1 and pinyin4j =?1")
    int queryWordsByPinYinCount(String radicals) throws Exception;

    @Query(value = "select words from basice_dictionary where 1=1 and it_enabled =1 and (?1 is null or radicals = ?1) and (?2 is null or strokes = ?2) limit ?3,?4", nativeQuery = true)
    List<String> queryWordsByRadicalsAndStrokesData(String radicals, String strokes, int pageNum, int pageSize) throws Exception;

    @Query(nativeQuery = true, value = "select count(DISTINCT words) from basice_dictionary where 1=1 and it_enabled =1 and (?1 is null or radicals = ?1) and (?2 is null or strokes = ?2)")
    int queryWordsByRadicalsAndStrokesCount(String radicals, String strokes) throws Exception;

}
