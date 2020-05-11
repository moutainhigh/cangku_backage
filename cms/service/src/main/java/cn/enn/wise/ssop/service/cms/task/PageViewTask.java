package cn.enn.wise.ssop.service.cms.task;

import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.consts.TaskKey;
import cn.enn.wise.ssop.service.cms.mapper.KnowledgeMapper;
import cn.enn.wise.ssop.service.cms.mapper.StrategyMapper;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@EnableScheduling
public class PageViewTask {

	private RedisUtil redisBean;
	private KnowledgeMapper knowledgeMapper;
	private StrategyMapper strategyMapper;

	@Autowired
	public void setRedisBean(RedisUtil redisBean) {
		this.redisBean = redisBean;
	}

	@Autowired
	public void setKnowledgeService(KnowledgeMapper knowledgeMapper) {
		this.knowledgeMapper = knowledgeMapper;
	}

	@Autowired
	public void setStrategyService(StrategyMapper strategyMapper) {
		this.strategyMapper = strategyMapper;
	}

	/**
	 * 将浏览量每10分钟轮询一次存入数据库中；
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void billDownload() {
		String taskName = "存入浏览量定时任务";
		log.info("【{}】定时任务启动。", taskName);

		try {
			/*
			 * 获取redis标记
			 * 若存在则表示其它线程正在执行该任务，退出本线程
			 * 若不存在，按过期时间设置redis标记，继续后续处理逻辑
			 */
			Object redisValue = redisBean.get(TaskKey.PAGE_VIEW_TASK);
			if (redisValue != null) {
				log.info("【{}】定时任务退出，其它线程正在执行该任务", taskName);
				return;
			}
			redisBean.valueSetIfAbsent(TaskKey.PAGE_VIEW_TASK, TaskKey.PAGE_VIEW_TASK, TaskKey.PAGE_VIEW_TASK_TIME);
			//业务部分
			String strategy = RedisKey.ServerName + String.format(RedisKey.PAGE_VIEW, CmsEnum.CMS_TYPE_STRATEGY.getValue());//攻略
			String knowledge = RedisKey.ServerName + String.format(RedisKey.PAGE_VIEW, CmsEnum.CMS_TYPE_KNOWLEDGE.getValue());//知识
			updateViewNumber(strategy, CmsEnum.CMS_TYPE_STRATEGY.getValue());
			updateViewNumber(knowledge, CmsEnum.CMS_TYPE_KNOWLEDGE.getValue());

			// 任务执行结束，清除redis标记
			redisBean.del(TaskKey.PAGE_VIEW_TASK);
			log.info("【{}】定时任务执行结束。", taskName);
		} catch (Exception e) {
			// 执行异常，也需清除redis标记
			log.info("【{}】定时任务执行错误: {}, exception={}", taskName, e.getMessage(), e);
			redisBean.del(TaskKey.PAGE_VIEW_TASK);
		} finally {
			/*
			 * 清除redis标记的动作不能放在finally执行
			 * 因其它线程正在执行该任务时的return不需要清理标记
			 */
		}
	}

	void updateViewNumber (String key, byte type) {
		Object[] toArray = redisBean.getRedisTemplate().opsForZSet().rangeWithScores(key, 0, -1).toArray();
		for (int i = 0 ; i < toArray.length ; i++) {
			Object value = ((DefaultTypedTuple) toArray[i]).getValue();
			Double score = ((DefaultTypedTuple) toArray[i]).getScore();
			if (type == 2) {
				knowledgeMapper.updateViewNumber(value, score);
			} else if (type == 3) {
				strategyMapper.updateViewNumber(value, score);
			}
		}
	}

}
