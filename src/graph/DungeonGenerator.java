package graph;

import java.awt.*;

public class DungeonGenerator {

    private AbstractGraph dungeon;

    private static final int ROOM_MAX_WIDTH = 100;
    private static final int ROOM_MAX_HEIGHT = 100;

    public AbstractGraph getDungeon()
    {
        return dungeon;
    }

    public DungeonGenerator(int nRooms)
    {
        createGraphWithRooms(nRooms);
    }

    private void createGraphWithRooms(int nRooms) {
        dungeon = new GraphList();

        for (int i = 0; i < nRooms; i++) {
            boolean roomIsValid;
            Rectangle newRectangle;
            do {
                roomIsValid = true;
                newRectangle = createRandomRectangle(ROOM_MAX_WIDTH, ROOM_MAX_HEIGHT);
                for (int j = 0; (j < dungeon.getNumberOfVertices()) && roomIsValid; j++) {
                    if(newRectangle.intersects(((Room)dungeon.getVertices().get(j)).getRoom()))
                    {
                        roomIsValid = false;
                    }
                }
            }while(!roomIsValid);
            dungeon.addVertex(new Room(newRectangle));
        }
    }

    private Rectangle createRandomRectangle(int roomMaxWidth, int roomMaxHeight) {
        var random = RandomSingleton.getInstance();
        var width = random.nextInt(10, roomMaxWidth);
        var height = random.nextInt(10, roomMaxHeight);
        var x = random.nextInt(800);
        var y = random.nextInt(800);
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }
}
