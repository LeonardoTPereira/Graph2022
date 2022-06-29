package graph;
public class Edge {
    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    private Vertex destination;
    private float weight;

    public Edge(Vertex destination) {
        setDestination(destination);
        setWeight(1);
    }

    public Edge(Vertex destination, float value) {
        setDestination(destination);
        setWeight(value);
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
