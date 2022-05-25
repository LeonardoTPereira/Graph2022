package graph;

import java.util.List;

public abstract class AbstractGraph implements GraphInterface
{
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
}
