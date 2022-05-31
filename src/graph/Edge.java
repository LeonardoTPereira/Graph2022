package graph;
public class Edge {
    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    private Vertex destination;
    private int value;

    public Edge(Vertex destination) {
        setDestination(destination);
        setValue(1);
    }

    public Edge(Vertex destination, int value) {
        setDestination(destination);
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
