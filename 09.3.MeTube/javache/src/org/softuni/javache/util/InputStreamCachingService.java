package org.softuni.javache.util;

import org.softuni.javache.io.Reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamCachingService {
    private static final String CONTENT_LOADING_FAILURE_EXCEPTION_MESSAGE = "Failed loading content.";

    private String content;

    public InputStreamCachingService() { }

    public InputStream getOrCacheInputStream(InputStream inputStream) throws IOException {
        if(content == null) {
            int counter = 0;

            while(counter++ < 5000) {
                content = new Reader().readAllLines(inputStream);

                if(content != null && content.length() > 0) break;
            }

            if(content == null) {
                throw new IllegalArgumentException(CONTENT_LOADING_FAILURE_EXCEPTION_MESSAGE);
            }
        }

        return new ByteArrayInputStream(content.getBytes());
    }

    public void evictCache() {
        this.content = null;
    }
}
