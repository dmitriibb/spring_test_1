package com.dmbb.test_app_1.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class MyUtils {

    private static MyUtils instance = new MyUtils();

    public static <T> T readJson(String fileName, Class<T> clazz) {
        try (InputStream file = instance.getClass().getClassLoader().getResourceAsStream(fileName);
             InputStreamReader reader = new InputStreamReader(file)) {
            return new Gson().fromJson(reader, clazz);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
