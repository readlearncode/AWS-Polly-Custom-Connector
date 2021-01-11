package org.mule.extension.aws.polly.internal;

import java.util.Set;
import java.util.stream.Collectors;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

public class VoiceValueProvider implements ValueProvider {
	
	@Connection
	private AwspollyConnection connection;
	
	@Parameter
	private String language;
	
    @Override
    public Set<Value> resolve() {    	   	   	   			
    	return ValueBuilder.getValuesFor(connection.getPollyClient().describeVoices().voices().stream()
    			.filter(e -> e.languageCodeAsString().equalsIgnoreCase(language))
    			.collect(Collectors.toList())
    			.stream()
    			.map(e -> e.name()));     	
    }
    
}
