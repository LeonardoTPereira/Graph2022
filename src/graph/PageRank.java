package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PageRank
{
    public static void rankPagesForNGenerations(AbstractGraph graph, int nGenerations){
        var indexToVisit = new ArrayList<Integer>();
        for (int i = 0; i < graph.getNumberOfVertices(); i++)
        {
            indexToVisit.add(i);
        }
        for (int i = 0; i < nGenerations; i++)
        {
            Collections.shuffle(indexToVisit);
            var transferRanks = new ArrayList<Float>();
            for (int j = 0; j < graph.getNumberOfVertices(); j++)
            {
                WebPage currentPage = (WebPage)graph.getVertices().get(j);
                transferRanks.add(currentPage.getPageRankToTransfer());
                currentPage.setRank(0);
            }
            for(Integer integer : indexToVisit)
            {
                Vertex currentVisitedVertex = graph.getVertices().get(integer);
                distributeRankToConnectedVertices(graph, currentVisitedVertex, transferRanks);
            }
        }
    }

    private static void distributeRankToConnectedVertices(AbstractGraph graph, Vertex currentVisitedVertex, ArrayList<Float> transferRanks)
    {
        int currentVertexIndex = graph.getVertices().indexOf(currentVisitedVertex);
        float rankToTransfer = transferRanks.get(currentVertexIndex);
        Vertex adjacentVertex = graph.getFirstConnectedVertex(currentVisitedVertex);
        while(adjacentVertex != null)
        {
            ((WebPage) adjacentVertex).incrementRank(rankToTransfer);
            adjacentVertex = graph.getNextConnectedVertex(currentVisitedVertex, adjacentVertex);
        }
    }
}
