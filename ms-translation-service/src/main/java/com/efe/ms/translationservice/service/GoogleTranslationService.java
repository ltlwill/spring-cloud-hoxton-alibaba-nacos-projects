package com.efe.ms.translationservice.service;

import java.util.List;

import com.efe.ms.translationservice.vo.TranslationVO;
import com.google.cloud.translate.Language;

/**
 * google翻译业务接口
 * 
 * @author Tianlong Liu
 * 2020年8月10日 上午11:13:39
 */
public interface GoogleTranslationService {

	/**
	 * 开始翻译
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	String translate(TranslationVO vo) throws Exception;
	
	/**
	 * 获取支持的语言列表
	 * @return
	 */
	List<Language> getSupportedLanguages();
}
