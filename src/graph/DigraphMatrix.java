package graph;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;


public class DigraphMatrix extends AbstractGraph
{
    private Edge[][] adjacencyMatrix;

    public Edge[][] getAdjacencyMatrix() {
        return adjacencyMatrix.clone();
    }

    private void setAdjacencyMatrix(Edge[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public DigraphMatrix(List<Vertex> vertices) {
        super(vertices);
        initializeAdjacencyMatrix();
    }

    private void initializeAdjacencyMatrix()
    {
        setAdjacencyMatrix(new
                Edge[getNumberOfVertices()][getNumberOfVertices()]);
        for (int i = 0; i < getNumberOfVertices(); i++) {
            for (int j = 0; j < getNumberOfVertices(); j++) {
                setEdge(i, j, null);
            }
        }
    }

    public void addEdge(Vertex source, Vertex destination, float value)
    {
        if(!edgeExists(source, destination))
        {
            int sourceIndex = getVertices().indexOf(source);
            int destinationIndex = getVertices().indexOf(destination);
            setEdge(sourceIndex, destinationIndex, new Edge(destination, value));
            source.incrementOutDegree();
            destination.incrementInDegree();
        }
    }

    @Override
    public void addVertex(Vertex vertex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeVertex(Vertex vertex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEdge(Vertex source, Vertex destination) {
        addEdge(source, destination, 1);
    }

    public void removeEdge(Vertex source, Vertex destination)
    {
        if(edgeExists(source, destination))
        {
            int sourceIndex = getVertices().indexOf(source);
            int destinationIndex = getVertices().indexOf(destination);
            setEdge(sourceIndex, destinationIndex, null);
            source.decrementOutDegree();
            destination.decrementInDegree();
        }
    }

    public void removeAllEdges()
    {
        for(int i = 0; i < getAdjacencyMatrix().length; i++)
        {
            for (int j = 0; j < getAdjacencyMatrix().length; j++)
            {
                getAdjacencyMatrix()[i][j] = null;
            }
        }
    }

    @Override
    public boolean edgeExists(Vertex source, Vertex destination) {
        int sourceIndex = getVertices().indexOf(source);
        int destinationIndex = getVertices().indexOf(destination);
        return adjacencyMatrix[sourceIndex][destinationIndex] != null;
    }

    @Override
    public boolean hasAnyEdge(Vertex vertex) {
        for (int i = 0; i < getNumberOfVertices(); i++)
        {
            if(edgeExists(vertex, getVertices().get(i)))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getFirstConnectedVertexIndex(Vertex vertex) {
        return getNextConnectedVertexIndex(vertex, 0);
    }

    @Override
    public int getNextConnectedVertexIndex(Vertex vertex, int currentEdge) {
        for (int i = currentEdge; i < getNumberOfVertices(); i++)
        {
            if(edgeExists(vertex, getVertices().get(i)))
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void printInGraphviz(String fileName)
    {

    }

    @Override
    public float getDistance(Vertex source, Vertex destination) {
        int sourceIndex = getVertices().indexOf(source);
        int destinationIndex = getVertices().indexOf(destination);
        var edge = getAdjacencyMatrix()[sourceIndex][destinationIndex];

        if(edge == null) return -1;

        return getAdjacencyMatrix()[sourceIndex][destinationIndex].getWeight();
    }

    @Override
    public Vertex getFirstConnectedVertex(Vertex vertex)
    {
        if(!hasAnyEdge(vertex))
        {
            return null;
        }
        else
        {
            var currentVertexIndex = 0;
            Vertex connected;
            do
            {
                connected = getVertices().get(currentVertexIndex++);
            }while(!edgeExists(vertex, connected));
            return connected;
        }
    }

    @Override
    public Vertex getNextConnectedVertex(Vertex source, Vertex currentConnection)
    {
        Vertex newConnection;
        for (int i = getVertices().indexOf(currentConnection)+1; i < getNumberOfVertices(); i++)
        {
            newConnection = getVertices().get(i);
            if(edgeExists(source, newConnection))
            {
                return newConnection;
            }
        }
        return null;
    }

    private void setEdge(int source, int destination, Edge edge)
    {
        adjacencyMatrix[source][destination] = edge;
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for (var i = 0; i < getNumberOfVertices(); i++)
        {
            s.append(i).append(": ");
            for (var j = 0; j < getNumberOfVertices(); ++j)
            {
                if(edgeExists(getVertices().get(i), getVertices().get(j)))
                {
                    s.append(getAdjacencyMatrix()[i][j].getWeight()).append(" ");
                }
                else
                {
                    s.append(0.0 + " ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public void lockEdge(Vertex source, Vertex destination, int lockID)
    {
        Edge edge = getEdge(source, destination);
        edge.setLockID(lockID);
    }

    @Override
    public Edge getEdge(Vertex source, Vertex destination)
    {
        int sourceIndex = getIndexOfVertex(source);
        int destinationIndex = getIndexOfVertex(destination);
        return getAdjacencyMatrix()[sourceIndex][destinationIndex];
    }

    @Override
    protected DigraphMatrix clone() throws CloneNotSupportedException
    {
        DigraphMatrix cloneGraph = (DigraphMatrix) super.clone();
        cloneGraph.cloneAdjacencyMatrix(this);
        return cloneGraph;
    }

    private void cloneAdjacencyMatrix(DigraphMatrix cloneTarget)
    {
        for(int i = 0; i < cloneTarget.getAdjacencyMatrix().length; i++)
        {
            for (int j = 0; j < cloneTarget.getAdjacencyMatrix().length; j++)
            {
                if(cloneTarget.getAdjacencyMatrix()[i][j] != null)
                {
                    addEdge(getVertices().get(i), getVertices().get(j), cloneTarget.getAdjacencyMatrix()[i][j].getWeight());
                }
            }
        }
    }
}
