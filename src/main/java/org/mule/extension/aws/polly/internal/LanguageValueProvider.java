package org.mule.extension.aws.polly.internal;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

public class LanguageValueProvider implements ValueProvider {
	
	@Connection
	private AwspollyConnection connection;
	
    @Override
    public Set<Value> resolve() {   	   	
    	Set<String> langs = connection.getPollyClient().describeVoices().voices().stream().map(e -> e.languageCodeAsString()).collect(Collectors.toSet());    	
    	return ValueBuilder.getValuesFor(new ArrayList<>(langs));      	
    }
    
}
