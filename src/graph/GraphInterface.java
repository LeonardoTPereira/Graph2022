package graph;

public interface GraphInterface
{
    void addVertex(Vertex vertex);
    void removeVertex(Vertex vertex);

    void addEdge(Vertex source, Vertex destination);
    void addEdge(Vertex source, Vertex destination, float weight);
    void removeEdge(Vertex source, Vertex destination);
    void removeAllEdges();

    boolean edgeExists(Vertex source, Vertex destination);

    boolean hasAnyEdge(Vertex vertex);

    int getFirstConnectedVertexIndex(Vertex vertex);
    int getNextConnectedVertexIndex(Vertex vertex, int currentEdge);

    String toString();

    void printInGraphviz(String fileName);

    float getDistance(Vertex source, Vertex destination);

    Vertex getFirstConnectedVertex(Vertex vertex);

    Vertex getNextConnectedVertex(Vertex source, Vertex currentConnection);

    void lockEdge(Vertex source, Vertex destination, int lockID);

    Edge getEdge(Vertex source, Vertex destination);
}
