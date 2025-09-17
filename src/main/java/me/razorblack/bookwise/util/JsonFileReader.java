package me.razorblack.bookwise.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * Utility class for reading JSON files from the resource directory.
 */
@Slf4j
public class JsonFileReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads a JSON file from the resource directory and returns it as a JsonNode.
     * @param resourcePath the path to the JSON file in the resource directory
     * @return the JSON content as a JsonNode, or an empty array node if an error occurs
     */
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
}
