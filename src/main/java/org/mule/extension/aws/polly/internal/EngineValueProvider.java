package org.mule.extension.aws.polly.internal;

import java.util.Set;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import software.amazon.awssdk.services.polly.model.Engine;

public class EngineValueProvider implements ValueProvider {
	
    @Override
    public Set<Value> resolve() {
    	return ValueBuilder.getValuesFor(Engine.knownValues().stream().map(e -> e.name().toLowerCase()));  
    }
    
}
