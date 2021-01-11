package org.mule.extension.aws.polly.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.IOException;
import java.io.InputStream;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.PollyException;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.Voice;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class AwspollyOperations {

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String retrieveInfo(@Config AwspollyConfiguration configuration, @Connection AwspollyConnection connection) {
		return "Using Configuration [" + configuration.getConfigId() + "] with Connection id [" + connection.getId()
				+ "]";
	}

	/**
	 * Example of a simple operation that receives a string parameter and returns a
	 * new string message that will be set on the payload.
	 */
	@MediaType(value = ANY, strict = false)
	public InputStream textToSpeach(@Config AwspollyConfiguration configuration, @Connection AwspollyConnection connection,
			String text, @OfValues(LanguageValueProvider.class) String language, @OfValues(VoiceValueProvider.class) String voiceName) {
	
		Voice voice = connection.getPollyClient().describeVoices().voices()
				.stream()
				.filter(e -> e.name().equals(voiceName))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
		
		return generateVoice(connection.getPollyClient(), voice, text);
	}

	private InputStream generateVoice(PollyClient polly, Voice voice, String text) {

		try {
			return synthesize(polly, text, voice, OutputFormat.MP3);

		} catch (PollyException | IOException e) {
			System.err.println(e.getMessage());
		}

		return null;
	}

	private static InputStream synthesize(PollyClient polly, String text, Voice voice, OutputFormat format)
			throws IOException {

		return polly.synthesizeSpeech(SynthesizeSpeechRequest.builder()
				.text(text)
				.voiceId(voice.id())
				.outputFormat(format)
				.build());
	}

}
