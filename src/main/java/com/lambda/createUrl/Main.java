package com.lambda.createUrl;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final ObjectMapper objectMapper = new ObjectMapper(); // é uma classe que vai ser importada de uma biblioteca Jacson
    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) { // renomear stringObjectMap para input
        String body = input.get("body").toString(); // aqui vai ser o campo da url ou timestamp
        //para caso não cosga transformar nãoq uebre o sistema
        Map<String, String> bodyMap;
        try{cre
            bodyMap = objectMapper.readValue(body, Map.class);

        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Erro parsin KSON body:" + exception.getMessage(), exception);
        }

        String originalUrl = bodyMap.get("originalUrl");
        String expirationTime = bodyMap.get("expirationTime");

        String shortUrlCode = UUID.randomUUID().toString().substring(0,8);
        Map<String, String> response = new HashMap<>();
        response.put("code", shortUrlCode);

        return response;
    }
}