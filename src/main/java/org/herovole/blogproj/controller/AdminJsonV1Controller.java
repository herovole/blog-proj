package org.herovole.blogproj.controller;

import com.google.gson.Gson;
import org.herovole.blogproj.domain.tag.CountryTagUnit;
import org.herovole.blogproj.domain.tag.TagUnit;
import org.herovole.blogproj.entrypoint.property.LocalProperty;
import org.herovole.blogproj.infra.jpa.entity.ATag;
import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.herovole.blogproj.infra.jpa.repository.ATagRepository;
import org.herovole.blogproj.infra.jpa.repository.MCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@EnableConfigurationProperties(LocalProperty.class)
public class AdminJsonV1Controller {

    private static final String K_COUNTRY_SELECT_BOX_INPUT = "input";
    @Autowired
    private MCountryRepository mCountryRepository;

    @Autowired
    private ATagRepository aTagRepository;

    @PostMapping("/countries")
    public List<String> countrySelectBox(@RequestBody Map<String, String> request) {
        System.out.println("endpoint : select");
        System.out.println("b/api/countries");
        List<MCountry> candidates = mCountryRepository.findForwardMatchCandidates(request.get(K_COUNTRY_SELECT_BOX_INPUT));
        return candidates.stream().map(MCountry::toDomainObj).sorted().map(CountryTagUnit::toJsonString).toList();
    }

    @PostMapping("/tags")
    public List<String> tags(@RequestBody Map<String, String> request) {
        System.out.println("endpoint : select");
        System.out.println("b/api/tags");
        List<ATag> candidates = aTagRepository.findAllTags();
        return candidates.stream().map(ATag::toDomainObj).sorted().map(TagUnit::toJsonString).toList();
    }

    @PostMapping("/article")
    public String article(
            @RequestBody Map<String, String> request,
            ) {
        System.out.println("endpoint : upsert");
        System.out.println("b/api/article");

        String jsonString = request.get("input");

        HashMap<String, String> map = new Gson().fromJson(jsonString, HashMap.class);

        for (Map.Entry<String, String> e : map.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
        return "";
    }

    @PostMapping("/images")
    public ResponseEntity<String> images(@RequestPart("image") MultipartFile file) {
        System.out.println("endpoint : post");
        System.out.println("/api/v1/images");
        if (!file.isEmpty()) {
            System.out.println("Received file: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize() + " bytes");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Data received successfully");
    }

}
