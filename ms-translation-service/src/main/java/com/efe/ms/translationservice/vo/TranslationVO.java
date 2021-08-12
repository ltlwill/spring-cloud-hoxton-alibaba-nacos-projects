package com.efe.ms.translationservice.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "翻译接口参数")
public class TranslationVO implements Serializable{
	
	@ApiModelProperty(value = "要翻译的内容",required = true,example = "dress")
	@NotBlank
	private String content;
	@ApiModelProperty(value = "源语言",required = true,example = "en")
	@NotBlank
	private String sourceLang = "en";
	@ApiModelProperty(value = "目标语言",required = true,example = "zh-CN")
	@NotBlank
	private String targetLang = "zh-CN";

}
