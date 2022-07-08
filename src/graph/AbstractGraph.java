package graph;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraph implements GraphInterface
{
    protected static final int GRAPHVIZ_IMAGE_WIDTH = 400;
    protected static final String GRAPHVIZ_FOLDER = "example/";
    protected static final String GRAPHVIZ_FILE_EXTENSION = ".png";
    private int numberOfVertices;
    private List<Vertex> vertices;

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    protected void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    protected AbstractGraph(List<Vertex> vertices)
    {
        numberOfVertices = vertices.size();
        this.vertices = vertices;
    }

    protected AbstractGraph()
    {
        vertices = new ArrayList<>();
        numberOfVertices = 0;
    }

    public void addVertex(Vertex vertex)
    {
        vertices.add(vertex);
        numberOfVertices = vertices.size();
    }

    public void addEdge(Vertex source, Vertex destination)
    {
        addEdge(source, destination, 1);
    }

    public void addEdge(Vertex source, Vertex destination, float weight)
    {
        addEdge(source, destination, 1);
    }
}
