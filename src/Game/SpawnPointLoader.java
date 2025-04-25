package Game;

import Figures.Point;

import java.util.ArrayList;
import java.util.List;

public class SpawnPointLoader {

    public static List<Point> parseFromBlock(String block) {
        List<Point> points = new ArrayList<>();

        String cleanBlock = block.replace("[", "").replace("]", "").trim();
        String[] entries = cleanBlock.split("\\},\\s*\\{");

        for (String entry : entries) {
            int x = JsonUtils.extractInt(entry, "\"x\":");
            int y = JsonUtils.extractInt(entry, "\"y\":");
            points.add(new Point(x, y));
        }

        return points;
    }
}