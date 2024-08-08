package com.adobe.aem.guides.wknd.spa.react.core.services;

import java.io.IOException;

public interface GeminiAPIService {
    String sendDetailsToAPI (String base64Image ) throws IOException;
}
