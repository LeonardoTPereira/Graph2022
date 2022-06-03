package graph;
public class Edge {
    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    private Vertex destination;
    private int weight;

    public Edge(Vertex destination) {
        setDestination(destination);
        setWeight(1);
    }

    public Edge(Vertex destination, int value) {
        setDestination(destination);
        setWeight(value);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
