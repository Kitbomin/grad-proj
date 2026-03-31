package com.example.mainserver.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class TranscriptionService {
    private final WebClient webClient;

    public TranscriptionService(@Value("${ai.server.url}") String aiServerUrl) {
        this.webClient = WebClient.create(aiServerUrl);
    }

    @Value("${ai.server.url}")
    private String aiServerUrl;

    public String uploadAndTranscribe(MultipartFile file) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("file", file.getResource()).filename(file.getOriginalFilename());


        return webClient.post()
            .uri("/transcribe")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData("file", file.getResource()))
            .retrieve()
            .bodyToMono(Map.class)
            .map(result -> result.get("text").toString())
            .block();
    }

    
}