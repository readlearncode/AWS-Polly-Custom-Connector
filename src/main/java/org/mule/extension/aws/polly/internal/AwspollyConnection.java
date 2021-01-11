package org.mule.extension.aws.polly.internal;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;

/**
 * This class represents an extension connection just as example (there is no
 * real connection with anything here c:).
 */
public final class AwspollyConnection {

	private final String accessKey;
	private final String accessSecret;
	private final String awsRegion;
	private PollyClient polly;

	public AwspollyConnection(String accessKey, String accessSecret, String awsRegion) {
		this.accessKey = accessKey;
		this.accessSecret = accessSecret;
		this.awsRegion = awsRegion;
		createConenctionToPolly();
	}

	private void createConenctionToPolly() {

		AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, accessSecret);
		StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

		Region region = Region.regions().stream().filter(e -> e.id().equals(awsRegion)).findFirst()
				.orElseThrow(IllegalArgumentException::new);

		polly = PollyClient.builder().credentialsProvider(staticCredentialsProvider).region(region).build();

	}

	public String getId() {
		return accessKey + accessSecret;
	}

	public void invalidate() {
		polly.close();
	}

	public PollyClient getPollyClient() {
		return polly;
	}

	public boolean isValid() {
		return true;
	}
}
