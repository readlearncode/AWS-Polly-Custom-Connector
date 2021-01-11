package org.mule.extension.aws.polly.internal;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class VoiceProfile {
	
	/**
	 * The AWS region
	 */
	@Parameter
	@DisplayName("AWS Region")
	@Optional(defaultValue = "us-east-1")
	@OfValues(RegionValueProvider.class)
	private String awsRegion;

	/**
	 * The voice
	 */
	@Parameter
	@DisplayName("Voice")
	@Optional(defaultValue = "Joanna")
	private String voiceName;

	/**
	 * The engine
	 */
	@Parameter
	@DisplayName("Engine")
	@Optional(defaultValue = "standard")
	@OfValues(EngineValueProvider.class)
	private String engine;

	public String getAwsRegion() {
		return awsRegion;
	}

	public void setAwsRegion(String awsRegion) {
		this.awsRegion = awsRegion;
	}

	public String getVoiceName() {
		return voiceName;
	}

	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((awsRegion == null) ? 0 : awsRegion.hashCode());
		result = prime * result + ((engine == null) ? 0 : engine.hashCode());
		result = prime * result + ((voiceName == null) ? 0 : voiceName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VoiceProfile other = (VoiceProfile) obj;
		if (awsRegion == null) {
			if (other.awsRegion != null)
				return false;
		} else if (!awsRegion.equals(other.awsRegion))
			return false;
		if (engine == null) {
			if (other.engine != null)
				return false;
		} else if (!engine.equals(other.engine))
			return false;
		if (voiceName == null) {
			if (other.voiceName != null)
				return false;
		} else if (!voiceName.equals(other.voiceName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VoiceProfile [awsRegion=" + awsRegion + ", voiceName=" + voiceName + ", engine=" + engine + "]";
	}
	
	

}
