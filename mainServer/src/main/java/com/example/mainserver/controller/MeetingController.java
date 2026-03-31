package com.example.mainserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.mainserver.service.TranscriptionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MeetingController {
    private final TranscriptionService transcriptionService;
    
    @PostMapping("/upload")
    public String handleFiledUpload(@RequestParam("file") MultipartFile file, Model model) {
        String transcript = transcriptionService.uploadAndTranscribe(file);

        model.addAttribute("transcript", transcript);
        return "result";
    }
    
}
