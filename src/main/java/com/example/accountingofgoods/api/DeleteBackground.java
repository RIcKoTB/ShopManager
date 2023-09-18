//package com.example.accountingofgoods.api;
//
//import javafx.embed.swing.SwingFXUtils;
//import javafx.scene.image.Image;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//
//public class DeleteBackground {
//    public Image deleteBackground(Image image) throws IOException {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        HttpPost request = new HttpPost("https://api.remove.bg/v1.0/removebg");
//        request.addHeader("X-Api-Key", "jZXbrRg92hhtBPVXPxUDq9h2");
//
//        // Convert Image to byte array
//        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "png", outputStream);
//        byte[] imageBytes = outputStream.toByteArray();
//
//        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
//        entityBuilder.addBinaryBody("image_file", imageBytes, ContentType.DEFAULT_BINARY, "file.png");
//        entityBuilder.addTextBody("size", "auto");
//
//        request.setEntity(entityBuilder.build());
//
//        CloseableHttpResponse response = httpClient.execute(request);
//        HttpEntity responseEntity = response.getEntity();
//
//        // Convert response to Image
//        Image processedImage = null;
//        if (responseEntity != null) {
//            InputStream inputStream = responseEntity.getContent();
//            processedImage = new Image(inputStream);
//            inputStream.close();
//        }
//
//        EntityUtils.consume(responseEntity);
//
//        response.close();
//        httpClient.close();
//
//        return processedImage;
//    }
//
//
//}
