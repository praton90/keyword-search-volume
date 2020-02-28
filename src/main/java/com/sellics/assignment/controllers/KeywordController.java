package com.sellics.assignment.controllers;

import com.sellics.assignment.dto.KeywordDetailDTO;
import com.sellics.assignment.services.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search-volume")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/{keyword}")
    public KeywordDetailDTO getKeyword(@PathVariable("keyword") String keyword) {
        KeywordDetailDTO result = keywordService.getKeywordSearchVolume(keyword);

        return result;
    }
}
