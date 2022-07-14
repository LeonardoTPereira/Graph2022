package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGraph implements GraphInterface, Cloneable
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

    public int getIndexOfVertex(Vertex vertex)
    {
        return getVertices().indexOf(vertex);
    }

    public Vertex getCentermostVertex(float[][] distanceMatrix)
    {
        float []maxDistanceInCollumn = new float[distanceMatrix.length];
        Arrays.fill(maxDistanceInCollumn, Float.NEGATIVE_INFINITY);
        for (int i = 0; i < distanceMatrix.length; i++)
        {
            for (int j = 0; j < distanceMatrix[0].length; j++)
            {
                if (maxDistanceInCollumn[i] < distanceMatrix[i][j])
                {
                    maxDistanceInCollumn[i] = distanceMatrix[i][j];
                }
            }
        }
        int vertexIndex = getMinDistanceIndexInCollumn(maxDistanceInCollumn);
        return vertices.get(vertexIndex);
    }

    public Vertex getOuterMostVertex(float[][] distanceMatrix)
    {
        var maxDistanceInCollumn = new float[distanceMatrix.length];
        Arrays.fill(maxDistanceInCollumn, Float.NEGATIVE_INFINITY);
        for (var i = 0; i < distanceMatrix.length; i++)
        {
            for (var j = 0; j < distanceMatrix[0].length; j++)
            {
                if (maxDistanceInCollumn[i] < distanceMatrix[i][j])
                {
                    maxDistanceInCollumn[i] = distanceMatrix[i][j];
                }
            }
        }
        int vertexIndex = getMaxDistanceIndexInCollumn(maxDistanceInCollumn);
        return vertices.get(vertexIndex);
    }

    private int getMinDistanceIndexInCollumn(float[] distanceArray)
    {
        var minIndex = 0;
        var minDistance = distanceArray[0];
        for (var i = 1; i < distanceArray.length; i++)
        {
            if(minDistance > distanceArray[i])
            {
                minDistance = distanceArray[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private int getMaxDistanceIndexInCollumn(float[] distanceArray)
    {
        var maxIndex = 0;
        float maxDistance = distanceArray[0];
        for (var i = 1; i < distanceArray.length; i++)
        {
            if(maxDistance < distanceArray[i])
            {
                maxDistance = distanceArray[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public Vertex getMostDistantVertex(float[][] distanceMatrix, Vertex source)
    {
        var maxDistance = Float.NEGATIVE_INFINITY;
        var mostDistantVertexIndex = -1;
        var sourceVertexIndex = getIndexOfVertex(source);
        for (var i = 0; i < distanceMatrix[sourceVertexIndex].length; i++)
        {
            if(distanceMatrix[sourceVertexIndex][i] > maxDistance)
            {
                maxDistance = distanceMatrix[sourceVertexIndex][i];
                mostDistantVertexIndex = i;
            }
        }
        return vertices.get(mostDistantVertexIndex);
    }

    @Override
    protected AbstractGraph clone() throws CloneNotSupportedException
    {
        var graphClone = (AbstractGraph)super.clone();
        graphClone.setNumberOfVertices(this.getNumberOfVertices());
        graphClone.setVertices(new ArrayList<>(this.getVertices()));
        return graphClone;
    }
}
