package com.efe.ms.translationservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.common.vo.BusinessResult;
import com.efe.ms.translationservice.service.GoogleTranslationService;
import com.efe.ms.translationservice.vo.TranslationVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * google翻译控制器
 * 
 * @author Tianlong Liu 2020年8月10日 上午11:18:44
 */
@RestController
@RequestMapping("/google")
@Api(tags = "Google翻译API")
public class GoogleTranslationController {

	@Autowired
	private GoogleTranslationService googleTranslationService;

	@PostMapping
	@ApiOperation(value = "翻译")
	public BusinessResult<?> translate(@RequestBody TranslationVO vo) throws Exception {
		return BusinessResult.success(googleTranslationService.translate(vo));
	}

	@GetMapping("/langs")
	@ApiOperation(value = "获取支持翻译的语言列表")
	public BusinessResult<?> getSupportedLanguages() {
		return BusinessResult.success(googleTranslationService.getSupportedLanguages());
	}

}
