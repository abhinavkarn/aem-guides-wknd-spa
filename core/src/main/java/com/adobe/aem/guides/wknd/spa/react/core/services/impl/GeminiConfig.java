package com.adobe.aem.guides.wknd.spa.react.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Gemini Config",
        description = "Gemini API Configuration"
)

public @interface GeminiConfig {

    @AttributeDefinition(
            name = "API URL",
            description = "Gemini API URL"
    )
    String geminiUrl() default "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=";

    @AttributeDefinition(
            name = "API Key",
            description = "Gemini API Key"
    )
    String geminiKey() default "AIzaSyDiLLehJXY7hQ-25vJuibkZ9TzFsIjMNRg";
}