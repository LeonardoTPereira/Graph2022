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
        graph = new DigraphList(vertices);

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(1), vertices.get(3));
        graph.addEdge(vertices.get(2), vertices.get(0));
        graph.addEdge(vertices.get(4), vertices.get(2));
        graph.addEdge(vertices.get(3), vertices.get(4));
        graph.addEdge(vertices.get(4), vertices.get(1));

        traversalStrategy = new DijkstraTraversal(graph);
        traversalStrategy.traverseGraph(graph.getVertices().get(0));
        traversalStrategy.printDistances();
        traversalStrategy.printPath();
        traversalStrategy.printVisitTree();
        graph.printInGraphviz("GraphList");
    }
}
