package com.example.valetkey.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Resolves the StorageProvider to use at runtime. Spring injects every bean
 * that implements StorageProvider (currently only AWSS3Service) into the constructor automatically — no wiring needed
 * when a new provider is added, it just has to implement the interface.
 */
@Component
public class StorageProviderRegistry {

    private final Map<String, StorageProvider> providersByName;

    @Value("${storage.primary-provider:S3}")
    private String primaryProviderName;

    public StorageProviderRegistry(List<StorageProvider> providers) {
        this.providersByName = providers.stream()
                .collect(Collectors.toMap(StorageProvider::getProviderName, p -> p));
    }

    /** The provider new uploads should go to, per storage.primary-provider. */
    public StorageProvider getPrimary() {
        return get(primaryProviderName);
    }

    /** Resolve a specific provider by name, e.g. the one a given Resource was stored on. */
    public StorageProvider get(String providerName) {
        StorageProvider provider = providersByName.get(providerName);
        if (provider == null) {
            throw new RuntimeException("Unknown storage provider: " + providerName +
                    ". Available: " + providersByName.keySet());
        }
        return provider;
    }
}
