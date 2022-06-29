package graph;

import javax.swing.*;

public class DungeonController {

    public static void main(String[] args) {
        var dungeonGenerator = new DungeonGenerator(20);
        var dungeon = dungeonGenerator.getDungeon();
        DelaunayTriangulation.triangulateGraphVertices(dungeon);
        SwingUtilities.invokeLater(() -> new DungeonGraphic(dungeon).setVisible(true));


    }

}
