package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstTraversal implements TraversalInterface
{
    @Override
    public String traverseGraph(AbstractGraph graph, Vertex source) {

        var visited = new float[graph.getNumberOfVertices()];
        Arrays.fill(visited, -1);
        visited[graph.getVertices().indexOf(source)] = 0;

        Queue<Vertex> verticesToVisit = new LinkedList<>();
        verticesToVisit.add(source);

        var visitedPath = new StringBuilder();

        Vertex currentVisitedVertex;

        while(!verticesToVisit.isEmpty())
        {
            currentVisitedVertex = verticesToVisit.poll();
            if(currentVisitedVertex != null)
            {
                createVertexString(graph, visited, visitedPath, currentVisitedVertex);
                var adjacentVertexIndex = graph.getFirstConnectedVertexIndex(
                        currentVisitedVertex);
                while(adjacentVertexIndex != -1)
                {
                    if(visited[adjacentVertexIndex] < 0)
                    {
                        var currentDistance = visited[graph.getVertices().
                                indexOf(currentVisitedVertex)];
                        var adjacentVertex = graph.getVertices().get(adjacentVertexIndex);
                        var nextDistance = graph.getDistance(currentVisitedVertex, adjacentVertex);
                        visited[adjacentVertexIndex] = currentDistance + nextDistance;
                        verticesToVisit.add(adjacentVertex);
                    }
                    adjacentVertexIndex = graph.getNextConnectedVertexIndex(currentVisitedVertex,
                            adjacentVertexIndex);
                }
            }
        }
        return visitedPath.toString();
    }

    private void createVertexString(AbstractGraph graph, float[] visited, StringBuilder visitedPath, Vertex currentVisitedVertex) {
        visitedPath.append(currentVisitedVertex).append(' ');
        visitedPath.append("Distance: ");
        var vertexIndex = graph.getVertices().indexOf(currentVisitedVertex);
        visitedPath.append(visited[vertexIndex]);
        visitedPath.append(' ');
    }
}
