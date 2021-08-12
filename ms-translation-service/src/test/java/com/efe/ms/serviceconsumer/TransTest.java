package com.efe.ms.serviceconsumer;

import org.junit.jupiter.api.Test;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import com.google.cloud.translate.v3.TranslationServiceClient;

public class TransTest {

	@Test
	public void test01() {
		String text = "hello world!";
		System.out.println(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
		System.out.println(System.getenv("JAVA_HOME"));
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		com.google.cloud.translate.Translation translation = translate.translate(text,
				TranslateOption.sourceLanguage("en"), TranslateOption.targetLanguage("ru"));
		String re = translation.getTranslatedText();
		System.out.println(re);
	}

	@Test
	public void test02() throws Exception {
		String text = "hello world!";
		TranslationServiceClient client = TranslationServiceClient.create();
		LocationName parent = LocationName.of("liquid-folio-285803", "global");
		TranslateTextRequest request = TranslateTextRequest.newBuilder().setParent(parent.toString())
				.setMimeType("text/plain")
//		              .setTargetLanguageCode("us")
				.addContents(text).build();

		TranslateTextResponse response = client.translateText(request);

		// Display the translation for each input text provided
		for (Translation translation : response.getTranslationsList()) {
			System.out.printf("Translated text: %s\n", translation.getTranslatedText());
		}
	}

}
