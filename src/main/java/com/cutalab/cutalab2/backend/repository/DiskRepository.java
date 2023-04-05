package com.cutalab.cutalab2.backend.repository;

import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskEntity;
import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskGenreEntity;
import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskStyleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DiskRepository extends JpaRepository<DiskEntity, Integer> {

    @Query(value = "select * from disks d where title like concat('%', :title, '%') and author like concat('%', :author, '%') and user_id = :userId order by author limit :offset, 10", nativeQuery = true)
    List<DiskEntity> search(Integer offset, String title, String author, Integer userId);

    @Query(value = "select distinct \n" +
            "\tdisk.id,\n" +
            "\tdisk.author,\n" +
            "\tdisk.cover,\n" +
            "\tdisk.label,\n" +
            "\tdisk.note,\n" +
            "\tdisk.openable,\n" +
            "\tdisk.presumed_value,\n" +
            "\tdisk.reprint,\n" +
            "\tdisk.title,\n" +
            "\tdisk.user_id,\n" +
            "\tdisk.year,\n" +
            "\tdisk.cover_status,\n" +
            "\tdisk.disk_status\n" +
            "from disks as disk\n" +
            "\tleft join disk_genre_list as dgl on dgl.disk_id = disk.id\n" +
            "where disk.title like concat('%', :title,'%') and disk.author like concat('%', :author,'%') and dgl.genre_id = :diskGenreEntity and disk.user_id = :userId order by author limit :offset, 10" +
            "\n", nativeQuery = true)
    List<DiskEntity> search(Integer offset, String title, String author, DiskGenreEntity diskGenreEntity, Integer userId);

    @Query(value = "select distinct \n" +
            "\tdisk.id,\n" +
            "\tdisk.author,\n" +
            "\tdisk.cover,\n" +
            "\tdisk.label,\n" +
            "\tdisk.note,\n" +
            "\tdisk.openable,\n" +
            "\tdisk.presumed_value,\n" +
            "\tdisk.reprint,\n" +
            "\tdisk.title,\n" +
            "\tdisk.user_id,\n" +
            "\tdisk.year,\n" +
            "\tdisk.cover_status,\n" +
            "\tdisk.disk_status\n" +
            "from disks as disk\n" +
            "\tleft join disk_style_list as dsl on dsl.disk_id = disk.id\n" +
            "where disk.title like concat('%', :title, '%') and disk.author like concat('%', :author, '%') and dsl.style_id = :diskStyleEntity and disk.user_id = :userId order by author limit :offset, 10" +
            "\n", nativeQuery = true)
    List<DiskEntity> search(Integer offset, String title, String author, DiskStyleEntity diskStyleEntity, Integer userId);

    @Query(value = "select distinct \n" +
            "\tdisk.id,\n" +
            "\tdisk.author,\n" +
            "\tdisk.cover,\n" +
            "\tdisk.label,\n" +
            "\tdisk.note,\n" +
            "\tdisk.openable,\n" +
            "\tdisk.presumed_value,\n" +
            "\tdisk.reprint,\n" +
            "\tdisk.title,\n" +
            "\tdisk.user_id,\n" +
            "\tdisk.year,\n" +
            "\tdisk.cover_status,\n" +
            "\tdisk.disk_status\n" +
            "from disks as disk\n" +
            "\tleft join disk_genre_list as dgl on dgl.disk_id = disk.id\n" +
            "\tleft join disk_style_list as dsl on dsl.disk_id = disk.id\n" +
            "where disk.title like concat('%', :title,'%') and disk.author like concat('%', :author, '%') and dgl.genre_id = :diskGenreEntity and dsl.style_id = :diskStyleEntity and disk.user_id = :userId order by author limit :offset, 10" +
            "\n", nativeQuery = true)
    List<DiskEntity> search(Integer offset, String title, String author, DiskGenreEntity diskGenreEntity, DiskStyleEntity diskStyleEntity, Integer userId);

    @Query(value = "select sum(presumed_value) from disks d where user_id = :userId", nativeQuery = true)
    BigDecimal totalValue(Integer userId);

    @Query(value = "select count(id) from disks d where user_id = :userId", nativeQuery = true)
    Integer count(Integer userId);

    @Query(value = "select count(id) from disks disk where disk.title like concat('%', :title,'%') and disk.author like concat('%', :author, '%') and disk.user_id = :userId", nativeQuery = true)
    Integer searchCount(String title, String author, Integer userId);

    @Query(value = "select count(disk.id) " +
        "from disks as disk\n" +
        "\tleft join disk_genre_list as dgl on dgl.disk_id = disk.id\n" +
        "where disk.title like concat('%', :title,'%') and disk.author like concat('%', :author,'%') and dgl.genre_id = :diskGenreEntity and disk.user_id = :userId" +
        "\n", nativeQuery = true)
    Integer searchCount(String title, String author, DiskGenreEntity diskGenreEntity, Integer userId);

    @Query(value = "select count(disk.id) " +
        "from disks as disk\n" +
        "\tleft join disk_style_list as dsl on dsl.disk_id = disk.id\n" +
        "where disk.title like concat('%', :title, '%') and disk.author like concat('%', :author, '%') and dsl.style_id = :diskStyleEntity and disk.user_id = :userId" +
        "\n", nativeQuery = true)
    Integer searchCount(String title, String author, DiskStyleEntity diskStyleEntity, Integer userId);

    @Query(value = "select count(disk.id) " +
        "from disks as disk\n" +
        "\tleft join disk_genre_list as dgl on dgl.disk_id = disk.id\n" +
        "\tleft join disk_style_list as dsl on dsl.disk_id = disk.id\n" +
        "where disk.title like concat('%', :title,'%') and disk.author like concat('%', :author, '%') and dgl.genre_id = :diskGenreEntity and dsl.style_id = :diskStyleEntity and disk.user_id = :userId" +
        "\n", nativeQuery = true)
    Integer searchCount(String title, String author, DiskGenreEntity diskGenreEntity, DiskStyleEntity diskStyleEntity, Integer userId);

}