package org.mule.extension.aws.polly.internal;

import java.util.Set;
import java.util.stream.Collectors;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import software.amazon.awssdk.regions.Region;

public class RegionValueProvider implements ValueProvider {
	
    @Override
    public Set<Value> resolve() {
    	return ValueBuilder.getValuesFor(Region.regions().stream().map(e -> e.id()).collect(Collectors.toList()));  
    }
    
}
