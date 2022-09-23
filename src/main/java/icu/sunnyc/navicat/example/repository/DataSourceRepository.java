package icu.sunnyc.navicat.example.repository;

import icu.sunnyc.navicat.example.entity.po.DataSourcePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 14:22
 */
@Repository
public interface DataSourceRepository extends JpaRepository<DataSourcePO, Long> {
}
