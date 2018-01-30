package com.engagetech.engage.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class EngageCache {     
    
    private Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());

    // Private constructor prevents instantiation from other classes
    private EngageCache() {
    }

    /**
     * FOGCacheHolder is loaded on the first execution of
     * FOGCacheHolder.getInstance() or the first access to KnowledgeBaseCacheHolder.INSTANCE,
     * not before.
     */
    private static class EngageCacheHolder {

        private static final EngageCache INSTANCE = new EngageCache();
    }

    public static EngageCache getInstance() {
        return EngageCacheHolder.INSTANCE;
    }
    
    public void put(String cacheKey, Object value) {
        cache.put(cacheKey, value);
    }

    public Object get(String cacheKey) {
        return cache.get(cacheKey);
    }
    
    public void clear(String cacheKey) {
        cache.put(cacheKey, null);
    }

    public void clear() {
        cache.clear();
    }

}
