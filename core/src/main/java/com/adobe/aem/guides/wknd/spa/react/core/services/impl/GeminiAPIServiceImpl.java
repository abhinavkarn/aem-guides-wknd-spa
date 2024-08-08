package com.adobe.aem.guides.wknd.spa.react.core.services.impl;

import com.adobe.aem.guides.wknd.spa.react.core.services.GeminiAPIService;
import com.adobe.aem.guides.wknd.spa.react.core.services.HTTPClient;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;

@Component(service = GeminiAPIService.class)
public class GeminiAPIServiceImpl implements GeminiAPIService {

    @Reference
    private HTTPClient httpClient;

    final String GEMINI_URI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyDiLLehJXY7hQ-25vJuibkZ9TzFsIjMNRg";

    String requestStructure = "{\n" +
            "  \"contents\":[\n" +
            "    {\n" +
            "      \"parts\":[\n" +
            "        {\"text\": \"Provide MetaData like title, description and keywords for this image :\"},\n" +
            "        {\n" +
            "          \"inline_data\": {\n" +
            "            \"mime_type\":\"image/jpeg\",\n" +
            "            \"data\": \"{{base64val}}\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Override
    public String sendDetailsToAPI (String base64Image ) throws IOException {
        String updatedRequest = requestStructure.replace("{{base64val}}", base64Image);
        return httpClient.sendPostRequest(GEMINI_URI, updatedRequest);
    }
}
