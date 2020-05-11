package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.constants.GoodsConstants;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 项目时段运营信息请求类
 *
 * @author caiyt
 * @since 2019-05-28
 */
@Data
@ApiModel(value = "项目时段运营信息请求类")
public class ProjectPeriodReqParam implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "所属项目ID")
    private Long projectId;

    @ApiModelProperty(value = "运营时段[08:00-09:00]")
    private String title;

    private byte orderby;

    @ApiModelProperty(value = "状态 1-启用 2-禁用")
    private Byte status;

    private String startTime;

    private String endTime;

    public ProjectPeriodReqParam() {
        super();
    }

    public ProjectPeriodReqParam(Long id, Long projectId, String title, Byte status) {
        this();
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.status = status;
    }

    /**
     * 校验保存时段运营信息参数
     *
     * @param projectPeriodReqParamList
     */
    public static void validateSaveProjectPeriodParam(long projectId, List<ProjectPeriodReqParam> projectPeriodReqParamList) {
        if (projectPeriodReqParamList == null) {
            projectPeriodReqParamList = new ArrayList<>();
            return;
        }
        for (ProjectPeriodReqParam reqParam : projectPeriodReqParamList) {
            if (reqParam.getProjectId() == null) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "所属项目ID不能为空");
            }
            if (!reqParam.getProjectId().equals(projectId)) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "运营时段信息的项目ID不一致");
            }
            if (reqParam.getStatus() == null) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "运营时段的状态[status]不能为空！");
            }
            if (StringUtils.isEmpty(reqParam.getTitle())) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "运营时段[title]不能为空！");
            }
            if (reqParam.getTitle().indexOf("-") < 0 || reqParam.getTitle().indexOf(":") < 0) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "运营时段[title]不合法，格式样例为[08:00-09:00]");
            }
        }
        // 将请求参数按照运营时段ASC排序
        projectPeriodReqParamList.sort((ProjectPeriodReqParam o1, ProjectPeriodReqParam o2) -> {
            Calendar cal1 = getCalByTitle(o1.getTitle(), 0);
            Calendar cal2 = getCalByTitle(o2.getTitle(), 0);
            return cal1.getTime().compareTo(cal2.getTime());
        });
        // 进行时段重叠的校验，校验逻辑为当前时段的开始时间和前一时段的结束时间相比较
        // 遍历时，当遇到删除状态的记录时会跳过不处理，下次遇到非删除状态的记录时，需要知道前一个非删除状态的记录和当前记录下标的差值，也就是prePeriodIdxDifference
        int prePeriodIdxDifference = 1;
        for (int i = 0; i < projectPeriodReqParamList.size(); ++i) {
            ProjectPeriodReqParam projectPeriodReqParam = projectPeriodReqParamList.get(i);
            // 时间重叠只校验启动的时间段
            if (projectPeriodReqParam.getStatus().equals(GoodsConstants.GoodsExtendStatus.DELETED.value())) {
                // 遇到删除状态的记录 prePeriodIdxDifference 执行 +1 操作
                ++prePeriodIdxDifference;
                continue;
            }
            // 排序序号
            projectPeriodReqParam.setOrderby((byte) (i + 1));
            // 起始时间/结束时间
            String[] timeSpanArr = projectPeriodReqParam.getTitle().split("-");
            projectPeriodReqParam.setStartTime(timeSpanArr[0]);
            projectPeriodReqParam.setEndTime(timeSpanArr[1]);
            // 时间重叠只校验启动的时间段
            if (projectPeriodReqParam.getStatus().equals(GoodsConstants.GoodsExtendStatus.DISABLE.value())) {
                // 遇到禁用状态的记录 prePeriodIdxDifference 执行 +1 操作
                ++prePeriodIdxDifference;
                continue;
            }
            if (i == prePeriodIdxDifference - 1) {
                continue;
            }
            // 取前一个非删除状态的记录
            ProjectPeriodReqParam projectPeriodReqParamPre = projectPeriodReqParamList.get(i - prePeriodIdxDifference);
            // 重置为 1
            prePeriodIdxDifference = 1;
            Calendar calCurr = getCalByTitle(projectPeriodReqParam.getTitle(), 0);
            Calendar calPre = getCalByTitle(projectPeriodReqParamPre.getTitle(), 1);
            if (calCurr.getTime().before(calPre.getTime())) {
                throw new BusinessException(GeneConstant.BUSINESS_ERROR, "运营时段上有重叠的部分："
                        + projectPeriodReqParamPre.getTitle() + " 和 " + projectPeriodReqParam.getTitle());
            }
        }
    }

    /**
     * 根据时间段获取当天的时间
     *
     * @param title 时间段，如 08:00-09:00
     * @param flag  开始时间与结束时间标识：0-开始时间 1-结束时间
     * @return
     */
    private static Calendar getCalByTitle(String title, int flag) {
        String[] timeSpanArr = title.split("-");
        String[] time = timeSpanArr[flag].split(":");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, Integer.parseInt(time[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}