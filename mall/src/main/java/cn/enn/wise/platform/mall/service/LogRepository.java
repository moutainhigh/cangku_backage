package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.config.logging.LogModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * es 交互类
 *
 * @author caiyt
 */
public interface LogRepository extends ElasticsearchRepository<LogModel, String> {
    List<LogModel> findByMethodEquals(String method);
}