package graph;

import java.text.DecimalFormat;
import java.util.logging.Logger;

public class FloydWarshallTraversal extends TraversalStrategy
{
    private static final Logger LOGGER = Logger.getLogger("FloydWarshallTraversal.class");

    public float[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(float[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    private float [][] distanceMatrix;

    protected FloydWarshallTraversal(AbstractGraph graph) {
        super(graph);
        distanceMatrix = new float[graph.getNumberOfVertices()][graph.getNumberOfVertices()];
    }

    @Override
    void traverseGraph(Vertex source, Vertex destination)
    {
        traverseGraph(source);
    }

    @Override
    void traverseGraph(Vertex source)
    {
        for (int i = 0; i < getGraph().getNumberOfVertices(); i++) {
            for (int j = 0; j < getGraph().getNumberOfVertices(); j++) {
                Vertex origin = getGraph().getVertices().get(i);
                Vertex destination = getGraph().getVertices().get(j);
                if(getGraph().edgeExists(origin, destination))
                {
                    distanceMatrix[i][j] = getGraph().getDistance(origin, destination);
                }
                else
                {
                    distanceMatrix[i][j] = Float.POSITIVE_INFINITY;
                }
            }
        }
        for (int k = 0; k < getGraph().getNumberOfVertices(); k++) {
            for (int i = 0; i < getGraph().getNumberOfVertices(); i++) {
                for (int j = 0; j < getGraph().getNumberOfVertices(); j++) {
                    float newDistance = distanceMatrix[i][k] + distanceMatrix[k][j];
                    if(newDistance < distanceMatrix[i][j])
                    {
                        distanceMatrix[i][j] = newDistance;
                    }
                }
            }
        }
        printDistanceMatrix();
    }

    private void printDistanceMatrix() {
        var decimalFormat = new DecimalFormat("00.00");
        var distanceMatrixString = new StringBuilder();
        distanceMatrixString.append('\n');
        for (float[] row: distanceMatrix) {
            for(float value: row) {
                distanceMatrixString.append(decimalFormat.format(value));
                distanceMatrixString.append(' ');
            }
            distanceMatrixString.append('\n');
        }
        LOGGER.info(distanceMatrixString.toString());
    }
}
