package graph;

import java.util.LinkedList;
import java.util.Queue;

public class KahnTopologicalSort extends TraversalStrategy
{
    protected KahnTopologicalSort(AbstractGraph graph)
    {
        super(graph);
    }

    @Override
    void traverseGraph(Vertex source, Vertex destination)
    {
        traverseGraph(source);
    }

    @Override
    void traverseGraph(Vertex source)
    {
        var inDegree = new int[getGraph().getNumberOfVertices()];
        Queue<Vertex> visitQueue = new LinkedList<>();

        for (int i = 0; i < getGraph().getNumberOfVertices(); i++)
        {
            var vertex = getGraph().getVertices().get(i);
            inDegree[i] = vertex.getInDegree();
            if(inDegree[i] == 0)
            {
                visitQueue.add(vertex);
            }
        }

        var nVisited = 0;

        while(!visitQueue.isEmpty())
        {
            var currentVisitedVertex = visitQueue.poll();
            if (currentVisitedVertex != null)
            {
                addToPath(currentVisitedVertex);
                var adjacentVertex = getGraph().getFirstConnectedVertex(currentVisitedVertex);
                while(adjacentVertex != null)
                {
                    int adjacentVertexIndex = getGraph().getVertices().indexOf(adjacentVertex);
                    inDegree[adjacentVertexIndex]--;
                    if(inDegree[adjacentVertexIndex] == 0)
                    {
                        visitQueue.add(adjacentVertex);
                    }
                    adjacentVertex = getGraph().getNextConnectedVertex(currentVisitedVertex, adjacentVertex);
                }
                nVisited++;
            }
        }

        if(nVisited != getGraph().getNumberOfVertices())
        {
            System.out.println("There is a cycle in the graph");
        }
    }
}
