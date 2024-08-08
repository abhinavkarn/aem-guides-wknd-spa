package com.adobe.aem.guides.wknd.spa.react.core.services.impl;

import com.adobe.aem.guides.wknd.spa.react.core.services.HTTPClient;
import org.osgi.service.component.annotations.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Component(service = HTTPClient.class)
public class HTTPClientImpl implements HTTPClient {

    @Override
    public String sendPostRequest(String endPoint, String requestParam) throws IOException {

        HttpURLConnection con = getHttpURLConnection(endPoint, requestParam);
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    private static HttpURLConnection getHttpURLConnection(String endPoint, String requestParam) throws IOException {
        URL url = new URL(endPoint);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/text");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = requestParam.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return con;
    }
}
