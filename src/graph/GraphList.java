package graph;

import java.util.List;

public class GraphList extends DigraphList
{
    protected GraphList(List<Vertex> vertices) {
        super(vertices);
    }

    @Override
    public void addEdge(Vertex source, Vertex destination) {
        super.addEdge(source, destination);
        super.addEdge(destination, source);
    }

    @Override
    public void removeEdge(Vertex source, Vertex destination) {
        super.removeEdge(source, destination);
        super.removeEdge(destination, source);
    }

    @Override
    public boolean hasAnyEdge(Vertex vertex) {
        int vertexIndex = getVertices().indexOf(vertex);
        return !getAdjacencyList().get(vertexIndex).isEmpty();
    }
}
