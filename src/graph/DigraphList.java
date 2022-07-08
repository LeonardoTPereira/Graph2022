package graph;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import static guru.nidi.graphviz.model.Factory.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DigraphList extends AbstractGraph
{
    private static final Logger LOGGER = Logger.getLogger("DigraphList.class");

    public List<List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<List<Edge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    List<List<Edge>> adjacencyList;

    protected DigraphList(List<Vertex> vertices) {
        super(vertices);
        initializeAdjacencyList();
    }

    protected DigraphList() {
        super();
        initializeAdjacencyList();
    }

    private void initializeAdjacencyList()
    {
        setAdjacencyList(new ArrayList<>());
        for (int i = 0; i < getNumberOfVertices(); i++) {
            getAdjacencyList().add(new ArrayList<>());
        }
    }

    @Override
    public void addVertex(Vertex vertex) {
        super.addVertex(vertex);
        getAdjacencyList().add(new ArrayList<>());
    }

    @Override
    public void removeVertex(Vertex vertex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addEdge(Vertex source, Vertex destination) {
        if(!edgeExists(source, destination))
        {
            int sourceIndex = getVertices().indexOf(source);
            getAdjacencyList().get(sourceIndex).add(new Edge(destination));
            source.incrementOutDegree();
            destination.incrementInDegree();
        }
    }

    @Override
    public void addEdge(Vertex source, Vertex destination, float weight) {
        if(!edgeExists(source, destination))
        {
            int sourceIndex = getVertices().indexOf(source);
            getAdjacencyList().get(sourceIndex).add(new Edge(destination, weight));
            source.incrementOutDegree();
            destination.incrementInDegree();
        }
    }

    @Override
    public void removeEdge(Vertex source, Vertex destination)
    {
        int sourceIndex = getVertices().indexOf(source);
        List<Edge> sourceEdges = getAdjacencyList().get(sourceIndex);
        sourceEdges.removeIf(edge -> edge.getDestination() == destination);
        source.decrementOutDegree();
        destination.decrementInDegree();
    }

    @Override
    public boolean edgeExists(Vertex source, Vertex destination) {
        int sourceIndex = getVertices().indexOf(source);
        List<Edge> sourceEdges = getAdjacencyList().get(sourceIndex);
        for (Edge edge : sourceEdges )
        {
            if(edge.getDestination() == destination)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAnyEdge(Vertex vertex) {
        int vertexIndex = getVertices().indexOf(vertex);

        if(!getAdjacencyList().get(vertexIndex).isEmpty())
        {
            return true;
        }

        for (int i = 0; i < getNumberOfVertices(); i++)
        {
            for (Edge edge : getAdjacencyList().get(i))
            {
                if(edge.getDestination() == vertex)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getFirstConnectedVertexIndex(Vertex vertex) {
        int vertexIndex = getVertices().indexOf(vertex);
        if(getAdjacencyList().get(vertexIndex).isEmpty())
        {
            return -1;
        }
        else
        {
            Vertex destinationVertex = getAdjacencyList().get(vertexIndex).get(0).getDestination();
            return getVertices().indexOf(destinationVertex);
        }
    }

    @Override
    public int getNextConnectedVertexIndex(Vertex vertex, int currentEdge) {
        int vertexIndex = getVertices().indexOf(vertex);
        int currentAdjacentVertexIndex = 0;
        Vertex currentDestinationVertex = getVertices().get(currentEdge);
        List<Edge> destinations = getAdjacencyList().get(vertexIndex);
        while(destinations.get(currentAdjacentVertexIndex).getDestination() != currentDestinationVertex)
        {
            currentAdjacentVertexIndex++;
        }
        currentAdjacentVertexIndex++;
        if(getAdjacencyList().get(vertexIndex).size() > currentAdjacentVertexIndex)
        {
            Vertex destinationVertex = getAdjacencyList().get(vertexIndex)
                    .get(currentAdjacentVertexIndex).getDestination();
            return getVertices().indexOf(destinationVertex);
        }
        return -1;
    }

    @Override
    public void printInGraphviz(String fileName)
    {
        MutableGraph g = mutGraph("example1Digraph").setDirected(true);
        for (var i = 0; i < getNumberOfVertices(); i++)
        {
            for (var j = 0; j < getAdjacencyList().get(i).size(); ++j)
            {
                int destinationIndex = getVertices().indexOf(getAdjacencyList().get(i).get(j).getDestination());
                float weight = getAdjacencyList().get(i).get(j).getWeight();
                g.add(mutNode(getVertices().get(i).getName()).addLink(to((mutNode(getVertices().get(destinationIndex).getName()))).add(Label.of(String.valueOf(weight)))));
            }
        }
        try
        {
            Graphviz.fromGraph(g).width(GRAPHVIZ_IMAGE_WIDTH).render(Format.PNG).toFile(new File(GRAPHVIZ_FOLDER+fileName+GRAPHVIZ_FILE_EXTENSION));
        }
        catch ( IOException e )
        {
            LOGGER.log(Level.SEVERE, "IO Exception thrown when saving Graphviz file", e);
        }
    }

    @Override
    public float getDistance(Vertex source, Vertex destination) {
        int vertexIndex = getVertices().indexOf(source);
        for (Edge edge : getAdjacencyList().get(vertexIndex)) {
            if(edge.getDestination() == destination)
            {
                return edge.getWeight();
            }
        }
        return -1;
    }

    @Override
    public Vertex getFirstConnectedVertex(Vertex vertex)
    {
        if(getAdjacencyList().get(getVertices().indexOf(vertex)).isEmpty())
        {
            return null;
        }
        else
        {
            return getAdjacencyList().get(getVertices().indexOf(vertex)).get(0).getDestination();
        }
    }

    @Override
    public Vertex getNextConnectedVertex(Vertex source, Vertex currentConnection)
    {
        int vertexIndex = getVertices().indexOf(source);
        var currentAdjacentVertexIndex = 0;
        while(getAdjacencyList().get(vertexIndex).get(currentAdjacentVertexIndex).getDestination() != currentConnection)
        {
            currentAdjacentVertexIndex++;
        }
        currentAdjacentVertexIndex++;
        if(getAdjacencyList().get(vertexIndex).size() > currentAdjacentVertexIndex)
        {
            return getAdjacencyList().get(vertexIndex).get(currentAdjacentVertexIndex).getDestination();
        }
        else
        {
            return null;
        }
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for (var i = 0; i < getNumberOfVertices(); i++)
        {
            s.append(i).append(": ");
            for (var j = 0; j < getAdjacencyList().get(i).size(); ++j)
            {
                s.append(getAdjacencyList().get(i).get(j).getWeight()).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
