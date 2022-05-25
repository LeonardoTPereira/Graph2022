package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphController {


    public static void main(String[] args)
    {
        AbstractGraph graph;
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex("Joao"));
        vertices.add(new Vertex("Maria"));
        vertices.add(new Vertex("Jose"));
        vertices.add(new Vertex("Marcos"));
        vertices.add(new Vertex("Pedro"));
        graph = new GraphMatrix(vertices);

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(0), vertices.get(2));
        graph.addEdge(vertices.get(4), vertices.get(2));
        graph.addEdge(vertices.get(3), vertices.get(4));
        graph.addEdge(vertices.get(4), vertices.get(1));

        graph.printInGraphviz("Graph");
    }
}
