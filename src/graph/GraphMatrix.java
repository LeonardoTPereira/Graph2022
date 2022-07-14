package graph;

import java.util.List;

public class GraphMatrix extends DigraphMatrix
{

    public GraphMatrix(List<Vertex> vertices) {
        super(vertices);
    }

    @Override
    public void addEdge(Vertex source, Vertex destination, float value)
    {
        super.addEdge(source, destination, value);
        super.addEdge(destination, source, value);
    }

    @Override
    public void removeEdge(Vertex source, Vertex destination)
    {
        super.removeEdge(source, destination);
        super.removeEdge(destination, source);
    }

    @Override
    protected GraphMatrix clone() throws CloneNotSupportedException
    {
        return (GraphMatrix)super.clone();
    }

    @Override
    public void lockEdge(Vertex source, Vertex destination, int lockID)
    {
        super.lockEdge(source, destination, lockID);
        super.lockEdge(destination, source, lockID);
    }
}
