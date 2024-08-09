package com.adobe.aem.guides.wknd.spa.react.core.services.impl;

import com.adobe.aem.guides.wknd.spa.react.core.services.GeminiAPIService;
import com.adobe.aem.guides.wknd.spa.react.core.services.HTTPClient;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.component.annotations.Activate;

import java.io.IOException;

@Component(service = GeminiAPIService.class, immediate = true)
@Designate(ocd = GeminiConfig.class)
public class GeminiAPIServiceImpl implements GeminiAPIService {

    @Reference
    private HTTPClient httpClient;

    private String GEMINI_URI = "";

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

    @Activate
    public final void activate (final GeminiConfig configuration) {
        GEMINI_URI = configuration.geminiUrl().concat(configuration.geminiKey());
    }

    @Override
    public String sendDetailsToAPI (String base64Image ) throws IOException {
        String updatedRequest = requestStructure.replace("{{base64val}}", base64Image);
        return httpClient.sendPostRequest(GEMINI_URI, updatedRequest);
    }
}
