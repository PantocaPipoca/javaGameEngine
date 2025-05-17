package Game.Loaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * A simple JSON parser that can parse JSON strings and files.
 * It supports parsing objects, arrays, strings, numbers, booleans, and null values.
 * This is a simplified version for basic JSON parsing.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (13/05/2025)
 * @see <a href="https://www.codeproject.com/Articles/5262223/How-to-Build-a-Recursive-Descent-Parser?pageflow=FixedWidtht.com">JSON Specification</a>
 */
public class JsonParser {
    private final String src;
    private int pos;

    /**
     * Constructs a new JsonParser for the given JSON string.
     * @param json The JSON string to parse.
     */
    public JsonParser(String json) {
        this.src = json.trim();
        this.pos = 0;
    }

    /**
     * Parses a JSON file and returns the parsed object.
     * @param path The path to the JSON file.
     * @return The parsed JSON object, which can be a Map, List, String, Number, Boolean, or null.
     * @throws RuntimeException If the file cannot be read or the JSON is invalid.
     */
    public static Object parseFile(String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return new JsonParser(content).parseValue();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON: " + e.getMessage());
        }
    }

    /**
     * Returns the current character in the JSON string.
     * @return The current character.
     */
    private char cur() {
        return src.charAt(pos);
    }

    /**
     * Skips whitespace characters in the JSON string.
     */
    private void skip() {
        while (pos < src.length() && Character.isWhitespace(cur())) pos++;
    }

    /**
     * Parses a JSON value from the current position.
     * @return The parsed JSON value, which can be a Map (for objects), List (for arrays), String, Number, Boolean, or null.
     * @throws RuntimeException If the JSON is invalid or an unexpected character is encountered.
     */
    public Object parseValue() {
        skip();
        if (cur() == '{') return parseObject();
        if (cur() == '[') return parseArray();
        if (cur() == '"') return parseString();
        if (Character.isDigit(cur()) || cur() == '-') return parseNumber();
        if (src.startsWith("true", pos)) { pos += 4; return true; }
        if (src.startsWith("false", pos)) { pos += 5; return false; }
        if (src.startsWith("null", pos)) { pos += 4; return null; }
        throw new RuntimeException("Unexpected JSON at " + pos);
    }

    /**
     * Parses a JSON object from the current position.
     * @return A Map representing the JSON object.
     * @throws RuntimeException If the JSON object is malformed.
     */
    private Map<String, Object> parseObject() {
        Map<String, Object> m = new HashMap<>();
        pos++; // skip '{'
        skip();
        while (cur() != '}') {
            String key = parseString();
            skip();
            pos++; // skip ':'
            skip();
            Object val = parseValue();
            m.put(key, val);
            skip();
            if (cur() == ',') {
                pos++;
                skip();
            }
        }
        pos++; // skip '}'
        return m;
    }

    /**
     * Parses a JSON array from the current position.
     * @return A List representing the JSON array.
     * @throws RuntimeException If the JSON array is malformed.
     */
    private List<Object> parseArray() {
        List<Object> list = new ArrayList<>();
        pos++; // skip '['
        skip();
        while (cur() != ']') {
            list.add(parseValue());
            skip();
            if (cur() == ',') {
                pos++;
                skip();
            }
        }
        pos++; // skip ']'
        return list;
    }

    /**
     * Parses a JSON string from the current position.
     * @return The parsed string.
     * @throws RuntimeException If the JSON string is malformed.
     */
    private String parseString() {
        StringBuilder sb = new StringBuilder();
        pos++; // skip '"'
        while (cur() != '"') {
            if (cur() == '\\') pos++;
            sb.append(cur());
            pos++;
        }
        pos++; // skip '"'
        return sb.toString();
    }

    /**
     * Parses a JSON number from the current position.
     * @return The parsed number, which can be an Integer or Double.
     * @throws RuntimeException If the JSON number is malformed.
     */
    private Number parseNumber() {
        int start = pos;
        if (cur() == '-') pos++;
        while (pos < src.length() && (Character.isDigit(cur()) || cur() == '.')) pos++;
        String num = src.substring(start, pos);
        return num.contains(".") ? Double.valueOf(num) : Integer.valueOf(num);
    }
}