package com.efe.ms.translationservice.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.efe.ms.translationservice.vo.TranslationVO;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

/**
 * 谷歌翻译业务接口实现类
 * 
 * @author Tianlong Liu
 * 2020年8月10日 上午11:14:23
 */
@Service
public class GoogleTranslationServiceImpl implements GoogleTranslationService {

	@Override
	public String translate(TranslationVO vo) throws Exception {
		if(vo == null || StringUtils.isBlank(vo.getContent())) {
			return null;
		}
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		Translation translation = translate.translate(vo.getContent(),
				TranslateOption.sourceLanguage(vo.getSourceLang()),
				TranslateOption.targetLanguage(vo.getTargetLang()));
		return translation.getTranslatedText();
	}

	@Override
	public List<Language> getSupportedLanguages() {
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		return translate.listSupportedLanguages();
	}

}
