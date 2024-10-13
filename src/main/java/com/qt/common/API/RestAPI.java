/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common.API;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.Setting;
import com.qt.common.mylogger.MyLogger;
import com.qt.view.component.ShowMessagePanel;
import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Administrator
 */
public class RestAPI {

    public static final String AUTHORIZATION_KEY = "authorization";
    private JwtUtil jwtUtil;
    private Component component;
    private ShowMessagePanel showMessagePanel;
    private final MyLogger logger;

    public RestAPI() {
        this.jwtUtil = new JwtUtil();
        this.logger = new MyLogger();
        String dir = Setting.getInstance().getLogDir();
        this.logger.setFile(new File(dir, "API"));
        this.logger.setDailyLog(true);
        this.logger.setSaveMemory(true);
    }

    public MyLogger getLogger() {
        return logger;
    }
    
    public void setTextComponent(ShowMessagePanel messagePanel) {
        this.showMessagePanel = messagePanel;
    }

    public RestAPI(Component component) {
        this();
        this.component = component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Response uploadFile(String url, RequestParam param, JsonBodyAPI bodyAPI, FileInfo... FileInfos) {
        return uploadFile(createUrl(param, url), bodyAPI, FileInfos);
    }

    public Response uploadFile(String url, JsonBodyAPI bodyAPI, FileInfo... fileInfos) {
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        for (FileInfo fileInfo : fileInfos) {
            Object data = fileInfo.getFile();
            if (data == null) {
                this.logger.addLog("UPLOAD", String.format("%s - %s data == null", fileInfo.getPartName(), fileInfo.getName()));
                continue;
            }
            this.logger.addLog("UPLOAD", String.format("%s - %s", fileInfo.getPartName(), fileInfo.getName()));
            switch (fileInfo.getType()) {
                case FILE -> {
                    String name = fileInfo.getName();
                    FileBody body;
                    if (name == null) {
                        body = new FileBody((File) data, ContentType.MULTIPART_FORM_DATA);
                    } else {
                        body = new FileBody((File) data, ContentType.MULTIPART_FORM_DATA, name);
                    }
                    entityBuilder.addPart(fileInfo.getPartName(), body);
                }
                case BYTE ->
                    entityBuilder.addBinaryBody(fileInfo.getPartName(), (byte[]) data,
                            ContentType.MULTIPART_FORM_DATA, fileInfo.getName());
                case INPUT_STREAM ->
                    entityBuilder.addBinaryBody(fileInfo.getPartName(), (InputStream) data,
                            ContentType.MULTIPART_FORM_DATA, fileInfo.getName());
                default ->
                    throw new AssertionError("invalid file type");
            }
        }
        if (bodyAPI != null) {
            for (String key : bodyAPI.getKeySet()) {
                Object value = bodyAPI.getString(key);
                if (value == null) {
                    continue;
                }
                entityBuilder.addPart(key, new StringBody(
                        String.valueOf(value), ContentType.MULTIPART_FORM_DATA));
            }
        }
        httpPost.setEntity(entityBuilder.build());
        return execute(httpPost);
    }

    public Response sendPost(String url, RequestParam param, JsonBodyAPI body, boolean saveLog) {
        return sendPost(createUrl(param, url), body, saveLog);
    }

    public Response sendPost(String url, JsonBodyAPI body) {
        return sendPost(url, body.toJSONString(), true);
    }

    public synchronized Response sendPost(String url, JsonBodyAPI body, boolean saveLog) {
        return sendPost(url, body.toJSONString(), saveLog);
    }

    public synchronized Response sendPost(String url, String body, boolean saveLog) {
        if (saveLog) {
            this.logger.addLog("POST", String.format("%s: %s", url, body));
        }
        HttpPost request = new HttpPost(url);
        return executeHaveStringBody(request, body, saveLog);
    }

    public Response sendPut(String url, RequestParam param, JsonBodyAPI body) {
        return sendPut(createUrl(param, url), body);
    }

    public Response sendPut(String url, JsonBodyAPI body) {
        if (body == null) {
            return sendPut(url, "");
        }
        return sendPut(url, body.toJSONString());
    }

    public Response sendPut(String url, String body) {
        this.logger.addLog("PUT", String.format("%s: %s", url, body));
        HttpPut request = new HttpPut(url);
        return executeHaveStringBody(request, body);
    }

    public Response sendGet(String url) {
        this.logger.addLog("GET", url);
        HttpGet request = new HttpGet(url);
        return execute(request);
    }

    public Response sendGet(String url, RequestParam param) {
        return sendGet(createUrl(param, url));
    }

    public Response sendDelete(String url) {
        this.logger.addLog("DELETE", url);
        HttpDelete request = new HttpDelete(url);
        return execute(request);
    }

    public Response sendDelete(String url, RequestParam param) {
        return sendDelete(createUrl(param, url));
    }

    public JwtUtil getJwtUtil() {
        return jwtUtil;
    }

    public void setJwtToken(String stringJWT) {
        this.jwtUtil.setJWT(stringJWT);
        checkJwt();
    }

    private void checkJwt() {
        if (!this.jwtUtil.isTokenValid()) {
            this.jwtUtil.logout();
        }
    }

    private Response executeHaveStringBody(HttpEntityEnclosingRequestBase request, String entity) {
        return executeHaveStringBody(request, entity, true);
    }

    private Response executeHaveStringBody(HttpEntityEnclosingRequestBase request, String entity, boolean saveLog) {
        try {
            request.setEntity(new StringEntity(entity));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        request.setHeader("Content-Type", "application/json;charset=UTF-8");
        return execute(request, saveLog);
    }

    public Response downloadFile(String apiUrl, RequestParam param, CreatePath createPath) {
        return RestAPI.this.downloadFile(createUrl(param, apiUrl), createPath);
    }

    public synchronized Response downloadFile(String apiUrl, CreatePath createPath) {
        Response response = null;
        try {
            if (component != null) {
                this.component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            }
            HttpGet httpGet = new HttpGet(apiUrl);
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                checkJwt();
                httpGet.addHeader("Accept", "*/*");
                httpGet.addHeader(AUTHORIZATION_KEY, jwtUtil.getJWT());
                try (CloseableHttpResponse httpRp = httpClient.execute(httpGet)) {
                    Header header = httpRp.getFirstHeader("Content-Disposition");
                    JSONObject attachment = new JSONObject();
                    if (header != null) {
                        for (HeaderElement element : header.getElements()) {
                            String name = element.getName();
                            if (name != null && name.equalsIgnoreCase("attachment")) {
                                for (NameValuePair parameter : element.getParameters()) {
                                    attachment.put(parameter.getName(), parameter.getValue());
                                }
                            }
                        }
                    }
                    File file = createPath.createFile(attachment);
                    if (file != null) {
                        file.getParentFile().mkdirs();
                        try (InputStream inputStream = httpRp.getEntity().getContent(); FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, bytesRead);
                            }
                        }
                        response = new Response(httpRp.getStatusLine().getStatusCode(),
                                JsonBodyAPI.builder()
                                        .put(Response.STATUS, true)
                                        .put(Response.MESSAGE, "")
                                        .put(Response.DATA, attachment.toJSONString())
                                        .toString(), showMessagePanel);
                        return response;
                    }
                    Response response1 = new Response(200, JsonBodyAPI.builder()
                            .put(Response.STATUS, false)
                            .put(Response.MESSAGE, "Cancel").toJSONString(),
                            showMessagePanel);
                    return response1;
                }
            }
        } catch (Exception e) {
            response = new Response(-1, JsonBodyAPI.builder()
                    .put(Response.STATUS, false)
                    .put(Response.MESSAGE, e.getLocalizedMessage()).toJSONString(),
                    showMessagePanel);
            return response;
        } finally {
            if (component != null) {
                this.component.setCursor(Cursor.getDefaultCursor());
            }
            if (response == null) {
                this.logger.addLog("RESPONSE", "null");
            } else {
                this.logger.addLog("RESPONSE", String.format("%s - %s", response.getCode(), response.getResponse()));
            }
        }
    }

    private synchronized Response execute(HttpUriRequest request) {
        return execute(request, true);
    }

    private synchronized Response execute(HttpUriRequest request, boolean saveLog) {
        if (component != null) {
            this.component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        }
        Response response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            checkJwt();
            request.addHeader("Accept", "*/*");
            request.addHeader(AUTHORIZATION_KEY, jwtUtil.getJWT());
            try (CloseableHttpResponse httpR = httpClient.execute(request)) {
                String body = EntityUtils.toString(httpR.getEntity());
                int statusCode = httpR.getStatusLine().getStatusCode();
                response = new Response(statusCode, body, showMessagePanel);
                return response;
            }
        } catch (Exception e) {
            response = new Response(-1, JsonBodyAPI.builder()
                    .put(Response.STATUS, false)
                    .put(Response.MESSAGE, e.getLocalizedMessage()).toJSONString(),
                    showMessagePanel);
            return response;
        } finally {
            if (component != null) {
                this.component.setCursor(Cursor.getDefaultCursor());
            }
            if (saveLog) {
                if (response == null) {
                    this.logger.addLog("RESPONSE", "null");
                } else {
                    this.logger.addLog("RESPONSE", String.format("%s - %s", response.getCode(), response.getResponse()));
                }
            }
        }
    }

    private String createUrl(RequestParam param, String url) {
        if (param != null) {
            url = String.format("%s%s", url, param);
        }
        return url;
    }

    public String extractUsername() {
        if (this.jwtUtil == null) {
            return null;
        }
        return this.jwtUtil.extractUsername();
    }

    public String extractUserRole() {
        if (this.jwtUtil == null) {
            return null;
        }
        return this.jwtUtil.extractUserRole();
    }

    public boolean isTokenValid() {
        if (this.jwtUtil == null) {
            return false;
        }
        return this.jwtUtil.isTokenValid();
    }

    public void logout() {
        this.jwtUtil.logout();
    }

    public interface CreatePath {

        File createFile(JSONObject jsono);

        default File autoCreateFileByAttachment(JSONObject attachment) {
            String name = attachment.getString("filename");
            String dir = attachment.getString("filepath");
            return new File(dir, name);
        }
    }

}
