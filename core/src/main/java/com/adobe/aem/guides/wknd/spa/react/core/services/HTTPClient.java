package com.adobe.aem.guides.wknd.spa.react.core.services;

import javax.json.Json;
import java.io.IOException;

public interface HTTPClient {
    String sendPostRequest (String endPoint, String requestParam) throws IOException;
}
