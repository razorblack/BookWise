package me.razorblack.bookwise.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public class JsonFileReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode readJson(String resourcePath) {
        try (InputStream inputStream = JsonFileReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                log.error("JSON file not found: {}", resourcePath);
                return objectMapper.createArrayNode(); // empty array
            }
            return objectMapper.readTree(inputStream);
        } catch (Exception e) {
            log.error("Error reading JSON from {}", resourcePath, e);
            return objectMapper.createArrayNode();
        }
    }

    public static void main(String[] args) {
        System.out.println(readJson("data/books.json"));
    }
}
