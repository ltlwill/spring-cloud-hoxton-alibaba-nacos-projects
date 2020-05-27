package com.efe.ms.crawlerservice.model.ali1688;

import com.efe.ms.crawlerservice.model.common.SerializationEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryImage extends SerializationEntity {

	private String previewUrl;
	private String originalUrl;
	private String smallUrl;
	private String detailUrl;
}
