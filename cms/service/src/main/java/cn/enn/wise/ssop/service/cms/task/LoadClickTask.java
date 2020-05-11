package cn.enn.wise.ssop.service.cms.task;

import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.consts.TaskKey;
import cn.enn.wise.ssop.service.cms.model.Advertise;
import cn.enn.wise.ssop.service.cms.service.AdvertiseService;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@EnableScheduling
public class LoadClickTask {

	private RedisUtil redisBean;
	private AdvertiseService advertiseService;

	@Autowired
	public void setRedisBean(RedisUtil redisBean) {
		this.redisBean = redisBean;
	}

	@Autowired
	public void setKnowledgeService(AdvertiseService advertiseService) {
		this.advertiseService = advertiseService;
	}


	/**
	 * 将点击/加载量每10分钟轮询一次存入数据库中；
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void billDownload() {
		String taskName = "存入点击/加载量定时任务";
		log.info("【{}】定时任务启动。", taskName);

		try {
			/*
			 * 获取redis标记
			 * 若存在则表示其它线程正在执行该任务，退出本线程
			 * 若不存在，按过期时间设置redis标记，继续后续处理逻辑
			 */
			Object redisValue = redisBean.get(TaskKey.ADVERTISE_LOAD_CLICK_TASK);
			if (redisValue != null) {
				log.info("【{}】定时任务退出，其它线程正在执行该任务", taskName);
				return;
			}
			redisBean.valueSetIfAbsent(TaskKey.ADVERTISE_LOAD_CLICK_TASK,
					TaskKey.ADVERTISE_LOAD_CLICK_TASK, TaskKey.ADVERTISE_LOAD_CLICK_TASK_TIME);
			//业务部分
			String click = RedisKey.ServerName+String.format(RedisKey.ADVERTISE_CLICK_LOAD, CmsEnum.ADVERTISE_CLICK.getValue());
			String load = RedisKey.ServerName+String.format(RedisKey.ADVERTISE_CLICK_LOAD, CmsEnum.ADVERTISE_LOAD.getValue());
			updateLoadClickNumber(click, CmsEnum.ADVERTISE_CLICK.getValue());
			updateLoadClickNumber(load, CmsEnum.ADVERTISE_LOAD.getValue());
			// 任务执行结束，清除redis标记
			redisBean.del(TaskKey.ADVERTISE_LOAD_CLICK_TASK);
			log.info("【{}】定时任务执行结束。", taskName);
		} catch (Exception e) {
			// 执行异常，也需清除redis标记
			log.info("【{}】定时任务执行错误: {}, exception={}", taskName, e.getMessage(), e);
			redisBean.del(TaskKey.ADVERTISE_LOAD_CLICK_TASK);
		} finally {
			/*
			 * 清除redis标记的动作不能放在finally执行
			 * 因其它线程正在执行该任务时的return不需要清理标记
			 */
		}
	}

	void updateLoadClickNumber (String key, byte type) {
		Object[] toArray = redisBean.getRedisTemplate().opsForZSet().rangeWithScores(key, 0, -1).toArray();
		for (int i = 0 ; i < toArray.length ; i++) {
			Object id = ((DefaultTypedTuple) toArray[i]).getValue();
			Double number = ((DefaultTypedTuple) toArray[i]).getScore();
			if (type == 1) {
				UpdateWrapper<Advertise> updateWrapper = new UpdateWrapper<>();
				updateWrapper.set("click_number", number);
				updateWrapper.eq("id", id);
				advertiseService.update(updateWrapper);
			} else if (type == 2) {
				UpdateWrapper<Advertise> updateWrapper = new UpdateWrapper<>();
				updateWrapper.set("load_number", number);
				updateWrapper.eq("id", id);
				advertiseService.update(updateWrapper);
			}

		}
	}

}
