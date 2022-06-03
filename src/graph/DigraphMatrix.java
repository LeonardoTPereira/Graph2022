package graph;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

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

    public void addEdge(Vertex source, Vertex destination, int value)
    {
        if(!edgeExists(source, destination))
        {
            int sourceIndex = getVertices().indexOf(source);
            int destinationIndex = getVertices().indexOf(destination);
            setEdge(sourceIndex, destinationIndex, new Edge(destination));
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
    public void printInGraphviz(String fileName) {
        MutableGraph g = mutGraph("example").setDirected(true);
        for (int i = 0; i < getNumberOfVertices(); i++) {
            for (int j = 0; j < getNumberOfVertices(); j++) {
                if(adjacencyMatrix[i][j] != null)
                {
                    g.add(mutNode(getVertices().get(i).getName())
                            .addLink(getVertices().get(j).getName()));
                }
            }
        }
        try {
            Graphviz.fromGraph(g).width(800).render(Format.PNG).toFile(
                    new File("Example/"+fileName+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float getDistance(Vertex source, Vertex destination) {
        int sourceIndex = getVertices().indexOf(source);
        int destinationIndex = getVertices().indexOf(destination);
        var edge = getAdjacencyMatrix()[sourceIndex][destinationIndex];

        if(edge == null) return -1;

        return getAdjacencyMatrix()[sourceIndex][destinationIndex].getWeight();
    }

    private void setEdge(int source, int destination, Edge edge)
    {
        adjacencyMatrix[source][destination] = edge;
    }

}
