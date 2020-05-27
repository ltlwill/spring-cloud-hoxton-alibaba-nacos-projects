package com.efe.ms.crawlerservice.webmagic.pipeline.ali1688;

import java.util.Map;

import com.efe.ms.common.util.SpringBeanUtil;
import com.efe.ms.crawlerservice.constant.Constants;
import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.service.ali1688.Ali1688ProductService;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * ali 1688数据抓取持久化
 * @author Tianlong Liu
 * @2020年4月16日 下午2:54:15
 */
public class Ali1688ProductsPipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		Map<String,Object> res = resultItems.getAll();
		Ali1688Product product = (Ali1688Product)res.get(Constants.PRODUCT_FIELD_NAME);
		if(product != null) {
			SpringBeanUtil.getBean(Ali1688ProductService.class).add(product);
		}
	}

}
