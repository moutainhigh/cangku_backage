package cn.enn.wise.ssop.api.cms.facade;

import cn.enn.wise.ssop.api.cms.dto.request.PartnerQueryParam;
import cn.enn.wise.ssop.api.cms.dto.request.PartnerSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.PartnerAddRespDTO;
import cn.enn.wise.ssop.api.cms.dto.response.PartnerDTO;
import cn.enn.wise.ssop.api.cms.dto.response.SimplePartnerDTO;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 合作伙伴接口
 * @author lishuiquan
 * @since 2019-11-18
 */
@Api(value = "合作伙伴", tags = {"合作伙伴"})
@FeignClient(value = "cms-service")
public interface PartnerFacade {



}
