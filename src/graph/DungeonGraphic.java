package graph;

import javax.swing.*;
import java.awt.*;

public class DungeonGraphic extends JFrame
{
    AbstractGraph graph;
    public DungeonGraphic(AbstractGraph graph)
    {
        super("Dungeon");
        this.graph = graph;
        getContentPane().setBackground(Color.black);
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.repaint();
    }

    private void drawDungeon(Graphics g)
    {
        var graphics2D = (Graphics2D) g;
        if(graph != null)
        {
            for (var i = 0; i < graph.getNumberOfVertices(); i++)
            {
                graphics2D.setColor(Color.BLUE);
                var currentVertex = graph.getVertices().get(i);
                graphics2D.draw(((Room)currentVertex).getRoom());
                if(((Room)currentVertex).isCheckPoint())
                {
                    graphics2D.setColor(Color.YELLOW);
                    graphics2D.fill(((Room) currentVertex).getRoom());
                }
                else if(((Room)currentVertex).isExit())
                {
                    graphics2D.setColor(Color.RED);
                    graphics2D.fill(((Room) currentVertex).getRoom());
                }
                else if(((Room)currentVertex).isEntrance())
                {
                    graphics2D.setColor(Color.GREEN);
                    graphics2D.fill(((Room) currentVertex).getRoom());
                }
                g.setColor(Color.PINK);
                var adjacentVertex = graph.getFirstConnectedVertex(currentVertex);
                while(adjacentVertex != null)
                {
                    g.drawLine((int)((Room) currentVertex).getPoint().getX(), (int)((Room) currentVertex).getPoint().getY(),
                            (int)((Room) adjacentVertex).getPoint().getX(),(int) ((Room) adjacentVertex).getPoint().getY());
                    adjacentVertex = graph.getNextConnectedVertex(currentVertex, adjacentVertex);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        drawDungeon(g);
    }
}