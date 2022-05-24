package graph;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class DigraphMatrix {
    private int numberOfVertices;
    private List<Vertex> vertices;
    private Edge[][] adjacencyMatrix;

    public Edge[][] getAdjacencyMatrix() {
        return adjacencyMatrix.clone();
    }

    private void setAdjacencyMatrix(Edge[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public DigraphMatrix(List<Vertex> vertices) {
        numberOfVertices = vertices.size();
        this.vertices = vertices;
        initializeAdjacencyMatrix();
    }

    private void initializeAdjacencyMatrix()
    {
        setAdjacencyMatrix(new
                Edge[getNumberOfVertices()][getNumberOfVertices()]);
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                setEdge(i, j, null);
            }
        }
    }

    private void setEdge(int source, int destination, Edge edge)
    {
        adjacencyMatrix[source][destination] = edge;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    private void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void printInGraphViz(String fileName)
    {
        MutableGraph g = mutGraph("example").setDirected(true);
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                if(adjacencyMatrix[i][j] != null)
                {
                    g.add(mutNode(getVertices().get(i).getName())
                            .addLink(getVertices().get(j).getName()));
                }
            }
        }
        try {
            Graphviz.fromGraph(g).width(800).render(Format.PNG).toFile(
                    new File("Example/"+fileName+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        DigraphMatrix digraphMatrix;
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex("Joao"));
        vertices.add(new Vertex("Maria"));
        vertices.add(new Vertex("Jose"));
        vertices.add(new Vertex("Marcos"));
        vertices.add(new Vertex("Pedro"));
        digraphMatrix = new DigraphMatrix(vertices);

        digraphMatrix.setEdge(0, 1, new Edge(1));
        digraphMatrix.setEdge(0, 2, new Edge(1));
        digraphMatrix.setEdge(3, 4, new Edge(1));
        digraphMatrix.setEdge(4, 1, new Edge(1));
        digraphMatrix.setEdge(4, 0, new Edge(1));

        digraphMatrix.printInGraphViz("Digraph.png");
    }
}
