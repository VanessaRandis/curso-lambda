package com.lambda.createUrl;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final ObjectMapper objectMapper = new ObjectMapper(); // é uma classe que vai ser importada de uma biblioteca Jacson

    private final S3Client s3Client = S3Client.builder().build();
    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) { // renomear stringObjectMap para input
        String body = input.get("body").toString(); // aqui vai ser o campo da url ou timestamp
        //para caso não cosga transformar nãoq uebre o sistema
        Map<String, String> bodyMap;
        try{
            bodyMap = objectMapper.readValue(body, Map.class);

        } catch (Exception exception) {
            throw new RuntimeException("Erro parsin KSON body:" + exception.getMessage(), exception);
        }

        String originalUrl = bodyMap.get("originalUrl");
        String expirationTime = bodyMap.get("expirationTime");
        long expirationTimeInSeconds = Long.parseLong(expirationTime);// aqui faz um casting de converter um string
                                                                            // em valor númerico de long

        String shortUrlCode = UUID.randomUUID().toString().substring(0,8);

        UrlData urlData = new UrlData(originalUrl, expirationTimeInSeconds);

        try{
            String urlDataJson = objectMapper.writeValueAsString(urlData);

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("url-shortener-umc")
                    .key(shortUrlCode + ".json") // aqui passa o nome do arquivo
                    .build();

            s3Client.putObject(request, RequestBody.fromString(urlDataJson)); // aqui passa o conteúdo do arquivo

        } catch (Exception exception) {
            throw new RuntimeException("Erros saving data to S3: " + exception.getMessage(), exception);
        }

        Map<String, String> response = new HashMap<>();
        response.put("code", shortUrlCode);

        return response;
    }
}