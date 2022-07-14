package graph;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class DungeonController {

    private AbstractGraph dungeon;

    private static final int TOTAL_LOCKS = 3;
    private Room entrance;
    private Room exit;

    public static void main(String[] args) {
        DungeonController dungeonController = new DungeonController();
        createRandomDungeon(dungeonController);
        DelaunayTriangulation.triangulateGraphVertices(dungeonController.dungeon);
        ReplaceDungeonWithMST(dungeonController);
        setSpecialRooms(dungeonController);
        List<Vertex> traversalPath = getPathFromEntranceToExit(dungeonController);
        setLocksAndKeys(dungeonController);
        SwingUtilities.invokeLater(() -> new DungeonGraphic(dungeonController.dungeon, traversalPath).setVisible(true));
    }

    private static void setLocksAndKeys(DungeonController dungeonController)
    {
        AbstractGraph dungeon = dungeonController.dungeon;
        TraversalStrategy traversalStrategy;
        traversalStrategy = new KeyLockGenerator(dungeon, TOTAL_LOCKS);
        traversalStrategy.traverseGraph(dungeonController.entrance);
    }

    private static void createRandomDungeon(DungeonController dungeonController)
    {
        System.out.println("What will be the random seed?");
        Scanner scanner = new Scanner(System.in);
        int seed = Integer.parseInt(scanner.nextLine());
        RandomSingleton.getInstance(seed);
        System.out.println("How many rooms will the dungeon have?");
        int nRooms = Integer.parseInt(scanner.nextLine());
        var randomDungeonGenerator = new DungeonGenerator(nRooms);
        dungeonController.dungeon = randomDungeonGenerator.getDungeon();
    }

    private static void ReplaceDungeonWithMST(DungeonController dungeonController)
    {
        AbstractGraph dungeon = dungeonController.dungeon;
        TraversalStrategy traversalStrategy;
        traversalStrategy = new PrimMSTTraversal(dungeon);
        traversalStrategy.traverseGraph(dungeon.getVertices().get(0));
        dungeonController.dungeon = GraphConverter.predecessorListToGraph(dungeon, traversalStrategy.getPredecessorArray());
    }

    private static void setSpecialRooms(DungeonController dungeonController)
    {
        AbstractGraph dungeon = dungeonController.dungeon;
        TraversalStrategy traversalStrategy = new FloydWarshallTraversal(dungeon);
        traversalStrategy.traverseGraph(dungeon.getVertices().get(0));
        Room center = (Room) dungeon.getCentermostVertex(((FloydWarshallTraversal)traversalStrategy).getDistanceMatrix());
        center.setCheckPoint(true);
        Room entrance = (Room) dungeon.getOuterMostVertex(((FloydWarshallTraversal)traversalStrategy).getDistanceMatrix());
        entrance.setEntrance(true);
        dungeonController.entrance = entrance;
        Room exit = (Room) dungeon.getMostDistantVertex(((FloydWarshallTraversal)traversalStrategy).getDistanceMatrix(), entrance);
        exit.setExit(true);
        dungeonController.exit = exit;
    }

    private static List<Vertex> getPathFromEntranceToExit(DungeonController dungeonController)
    {
        AbstractGraph dungeon = dungeonController.dungeon;
        TraversalStrategy aStar = new AStarPathFind(dungeon);
        aStar.traverseGraph(dungeonController.entrance, dungeonController.exit);
        return aStar.getShortestPath(dungeonController.entrance, dungeonController.exit);
    }
}
