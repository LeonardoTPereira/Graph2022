package graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DigraphMap extends AbstractGraph{
    private static final Logger LOGGER = Logger.getLogger("DigraphMap.class");
    private Map<Vertex, List<Edge>> adjacencyMap;

    protected DigraphMap(List<Vertex> vertices) {
        super(vertices);
        initializeAdjacencyMap();
    }

    private void initializeAdjacencyMap() {
        adjacencyMap = new HashMap<>();
    }

    public Map<Vertex, List<Edge>> getAdjacencyMap() {
        return adjacencyMap;
    }

    public void setAdjacencyMap(Map<Vertex, List<Edge>> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
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
        if(!edgeExists(source, destination))
        {
            if(!getAdjacencyMap().containsKey(source))
            {
                getAdjacencyMap().put(source, new ArrayList<>());
            }
            getAdjacencyMap().get(source).add(new Edge(destination));
            source.incrementOutDegree();
            destination.incrementInDegree();
        }
    }

    @Override
    public void addEdge(Vertex source, Vertex destination, float weight)
    {
        if(!edgeExists(source, destination))
        {
            if(!getAdjacencyMap().containsKey(source))
            {
                getAdjacencyMap().put(source, new ArrayList<>());
            }
            getAdjacencyMap().get(source).add(new Edge(destination, weight));
            source.incrementOutDegree();
            destination.incrementInDegree();
        }
    }

    @Override
    public void removeEdge(Vertex source, Vertex destination) {
        List<Edge> sourceEdges = getAdjacencyMap().get(source);
        for (int i = sourceEdges.size()-1; i > -1 ; i--)
        {
            if(sourceEdges.get(i).getDestination() == destination)
            {
                sourceEdges.remove(i);
                source.decrementOutDegree();
                destination.decrementInDegree();
                break;
            }
        }
    }

    @Override
    public boolean edgeExists(Vertex source, Vertex destination) {

        if(!getAdjacencyMap().containsKey(source)) return false;
        List<Edge> sourceEdges = getAdjacencyMap().get(source);
        for (Edge edge: sourceEdges)
        {
            if(edge.getDestination() == destination) return true;
        }
        return false;
    }

    @Override
    public boolean hasAnyEdge(Vertex vertex) {
        if(getAdjacencyMap().containsKey(vertex))
        {
            return true;
        }

        for (var i = 0; i < getNumberOfVertices(); i++)
        {
            for (var j = 0; j < getAdjacencyMap().get(getVertices().get(i)).size(); ++j)
            {
                if(getAdjacencyMap().get(getVertices().get(i)).get(j).getDestination() == vertex)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getFirstConnectedVertexIndex(Vertex vertex) {
        if(!getAdjacencyMap().containsKey(vertex)) return -1;
        return getVertices().indexOf(getAdjacencyMap().get(vertex).get(0).getDestination());
    }

    @Override
    public int getNextConnectedVertexIndex(Vertex vertex, int currentEdge) {
        var currentAdjacentVertexIndex = 0;
        var currentDestination = getAdjacencyMap().get(vertex).get(currentAdjacentVertexIndex).getDestination();
        while(currentDestination != getVertices().get(currentEdge))
        {
            currentAdjacentVertexIndex++;
            currentDestination = getAdjacencyMap().get(vertex).get(currentAdjacentVertexIndex).getDestination();
        }
        if(getAdjacencyMap().get(vertex).size() > currentAdjacentVertexIndex)
        {
            return getVertices().indexOf(currentDestination);
        }
        return -1;
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for(Map.Entry<Vertex, List<Edge>> pair : getAdjacencyMap().entrySet())
        {
            s.append(getVertices().indexOf(pair.getKey())).append(": ");
            for (var j = 0; j < pair.getValue().size(); ++j)
            {
                s.append(pair.getValue().get(j).getWeight()).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public void printInGraphviz(String fileName)
    {

    }

    @Override
    public float getDistance(Vertex source, Vertex destination) {
        for (Edge edge: adjacencyMap.get(source)) {
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
        if(!getAdjacencyMap().containsKey(vertex))
        {
            return null;
        }
        else
        {
            return getAdjacencyMap().get(vertex).get(0).getDestination();
        }
    }


    @Override
    public Vertex getNextConnectedVertex(Vertex source, Vertex currentConnection)
    {
        var currentAdjacentVertexIndex = 0;
        while(getAdjacencyMap().get(source).get(currentAdjacentVertexIndex).getDestination() != currentConnection)
        {
            currentAdjacentVertexIndex++;
        }
        currentAdjacentVertexIndex++;
        if(getAdjacencyMap().get(source).size() > currentAdjacentVertexIndex)
        {
            return getAdjacencyMap().get(source).get(currentAdjacentVertexIndex).getDestination();
        }
        else
        {
            return null;
        }
    }

}
