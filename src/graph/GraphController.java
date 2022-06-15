package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphController {


    public static void main(String[] args)
    {
        AbstractGraph graph;
        TraversalStrategy traversalStrategy;

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex("Joao"));
        vertices.add(new Vertex("Maria"));
        vertices.add(new Vertex("Jose"));
        vertices.add(new Vertex("Marcos"));
        vertices.add(new Vertex("Pedro"));
        graph = new GraphMatrix(vertices);

        graph.addEdge(vertices.get(0), vertices.get(1), 10);
        graph.addEdge(vertices.get(1), vertices.get(3), 5);
        graph.addEdge(vertices.get(2), vertices.get(0), 8);
        graph.addEdge(vertices.get(4), vertices.get(2), 7);
        graph.addEdge(vertices.get(3), vertices.get(4), 4);
        graph.addEdge(vertices.get(4), vertices.get(1), 2);
        graph.addEdge(vertices.get(3), vertices.get(0), 12);
        graph.addEdge(vertices.get(3), vertices.get(1), 6);

        traversalStrategy = new PrimMSTTraversal(graph);
        traversalStrategy.traverseGraph(graph.getVertices().get(0));
        traversalStrategy.printDistances();
        traversalStrategy.printPath();
        traversalStrategy.printVisitTree();
        graph.printInGraphviz("GraphList");
    }
}
