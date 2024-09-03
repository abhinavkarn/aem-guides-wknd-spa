package com.adobe.aem.guides.wknd.spa.react.core.workflows;

import com.adobe.aem.guides.wknd.spa.react.core.services.GeminiAPIService;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.commons.io.FileUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.*;
import java.net.*;
import org.apache.commons.io.IOUtils;
import java.util.Base64;
import java.nio.file.Files;
import org.json.*;

@Component(service = WorkflowProcess.class,
        property = {"process.label = Update MetaData Using Gemini API" })
public class UpdateMetadataGeminiAPI implements WorkflowProcess {

    private static final String TYPE_JCR_PATH = "JCR_PATH";

    @Reference
    private GeminiAPIService geminiAPIService;

    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        WorkflowData workflowData = item.getWorkflowData();
        String responseData = "";
        String title = "";
        String desc = "";
        String keywords = "";

        if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
            String path = workflowData.getPayload().toString();
            String b64Image = null;
            try {
                b64Image = fileToBase64(path, session);
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
            try {
                responseData = geminiAPIService.sendDetailsToAPI(b64Image);
                JSONObject jsonObject = new JSONObject(responseData);
                String metadataValues = (String) jsonObject.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).get("text");
                String[] metaArray = metadataValues.split("\n\n");
                title = metaArray[1].split(":")[1].replaceAll("\\**","");
                desc = metaArray[2].split(":")[1].replaceAll("\\**","");
                keywords = metaArray[3].split(":")[1].replaceAll("\\**","");

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                Session jcrSession = session.adaptTo(Session.class);
                assert jcrSession != null;
                Node node = (Node) jcrSession.getItem(path +"/jcr:content/metadata");
                if (node != null) {
                    node.setProperty("dc:title", title);
                    node.setProperty("dc:description", desc);
                    node.setProperty("dam:search_promote", keywords);
                    jcrSession.save();
                }
            } catch (RepositoryException e) {
                throw new WorkflowException(e.getMessage(), e);
            }
        }
    }

    private String fileToBase64 (String imagePath, WorkflowSession session) throws IOException, URISyntaxException {
        URL domain = new URL("http://localhost:4502");
        URL fileUrl = new URL(domain, imagePath);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "admin".toCharArray());
            }
        });

        InputStream inputStream = fileUrl.openStream();
        OutputStream outputStream = FileUtils.openOutputStream(new File("download.jpg"));
        IOUtils.copy(inputStream, outputStream);

        String encodedFile = null;
        try {
            byte[] imageBytes = Files.readAllBytes(new File("download.jpg").toPath());
            encodedFile = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e){
            e.printStackTrace();
        }

        return encodedFile;
    }
}
