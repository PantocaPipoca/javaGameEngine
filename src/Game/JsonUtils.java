package Game;

public class JsonUtils {

    public static int extractInt(String text, String key) {
        int i = text.indexOf(key);
        if (i == -1) return 0;
        i += key.length();
        String num = text.substring(i).split("[,:}]", 2)[0].trim();
        return Integer.parseInt(num);
    }

    public static double extractDouble(String text, String key) {
        int i = text.indexOf(key);
        if (i == -1) return 0.0;
        i += key.length();
        String num = text.substring(i).split("[,:}]", 2)[0].trim();
        return Double.parseDouble(num);
    }

    public static String extractString(String text, String key) {
        int i = text.indexOf(key);
        if (i == -1) return null;
        i = text.indexOf(":", i) + 1;
        int start = text.indexOf("\"", i) + 1;
        int end = text.indexOf("\"", start);
        return text.substring(start, end);
    }

    public static String extractBlock(String text, String startKey, String endKey) {
        int start = text.indexOf(startKey);
        int end = text.indexOf(endKey);
        if (start == -1 || end == -1) return "";
        return text.substring(start, end);
    }

    public static String extractObject(String json, String startKey) {
        int keyIndex = json.indexOf(startKey);
        if (keyIndex == -1) return "";

        int startBrace = json.indexOf("{", keyIndex);
        if (startBrace == -1) return "";

        int braceCount = 1;
        int i = startBrace + 1;

        while (i < json.length() && braceCount > 0) {
            char c = json.charAt(i);
            if (c == '{') braceCount++;
            else if (c == '}') braceCount--;
            i++;
        }

        return json.substring(startBrace, i);
    }
}