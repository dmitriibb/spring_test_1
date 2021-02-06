package com.dmbb.test_app_1.controller;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/json-parse")
public class JsonParseController {

    @PutMapping("/count-different-fields")
    public Map<String, Integer> countDifferentFields(@RequestBody Map<String, Object> body) {
        Map<String, Set<Object>> res = new HashMap<>();

        body.entrySet().forEach(entry -> processObject(entry.getValue(), res, entry.getKey()));

        Map<String, Integer> response = new HashMap<>();
        res.entrySet().forEach(entry ->response.put(entry.getKey(), entry.getValue().size()));

        return response;
    }

    @PutMapping("/group-different-fields")
    public Map<String, Set<Object>> groupDifferentFields(@RequestBody Map<String, Object> body) {
        Map<String, Set<Object>> res = new HashMap<>();

        body.entrySet().forEach(entry -> processObject(entry.getValue(), res, entry.getKey()));

        return res;
    }

    private void processMap(Map<String, Object> map, Map<String, Set<Object>> res, String fieldName) {
        map.entrySet().forEach(entry -> processObject(entry.getValue(), res, fieldName + "." + entry.getKey()));
    }

    private void processObject(Object currentField, Map<String, Set<Object>> res, String fieldName) {
        if (currentField instanceof Map) {
            processMap((Map<String, Object>) currentField, res, fieldName);
        } else if (currentField instanceof List) {
            List<Object> list = (List<Object>) currentField;
            list.forEach(obj -> processObject(obj, res, fieldName));
        } else {
            Set<Object> set = res.get(fieldName);
            if (set == null) {
                set = new HashSet<>();
                res.put(fieldName, set);
            }
            set.add(currentField);
        }
    }


}
