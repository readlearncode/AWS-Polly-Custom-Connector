package org.mule.extension.aws.polly.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class (as it's name implies) provides connection instances and the
 * functionality to disconnect and validate those connections.
 * <p>
 * All connection related parameters (values required in order to create a
 * connection) must be declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares
 * that connections resolved by this provider will be pooled and reused. There
 * are other implementations like {@link CachedConnectionProvider} which lazily
 * creates and caches connections or simply {@link ConnectionProvider} if you
 * want a new connection each time something requires one.
 */
public class AwspollyConnectionProvider implements PoolingConnectionProvider<AwspollyConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(AwspollyConnectionProvider.class);

	/**
	 * The AWS access key
	 */
	@DisplayName("AWS Access Key")
	@Parameter
	@Password
	private String accessKey;

	/**
	 * The AWS secret key
	 */
	@DisplayName("AWS Secret Key")
	@Parameter
	@Password
	private String accessSecret;

	/**
	 * The AWS region
	 */
	@Parameter
	@DisplayName("AWS Region")
	@Optional(defaultValue = "us-east-1")
	@OfValues(RegionValueProvider.class)
	private String awsRegion;

	@Override
	public AwspollyConnection connect() throws ConnectionException {
		return new AwspollyConnection(accessKey, accessSecret, awsRegion);
	}

	@Override
	public void disconnect(AwspollyConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error("Error while disconnecting [" + connection.getId() + "]: " + e.getMessage(), e);
		}
	}

	@Override
	public ConnectionValidationResult validate(AwspollyConnection connection) {
		return connection.isValid() ? ConnectionValidationResult.success()
				: ConnectionValidationResult.failure("Connection is not valid", new InvalidConnectionException());
	}
}
