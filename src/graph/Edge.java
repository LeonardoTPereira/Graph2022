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
    private int lockID;

    public Edge(Vertex destination) {
        setDestination(destination);
        setWeight(1);
        this.lockID = -1;
    }

    public Edge(Vertex destination, float value) {
        setDestination(destination);
        setWeight(value);
        this.lockID = -1;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getLockID()
    {
        return lockID;
    }

    public void setLockID(int lockID)
    {
        this.lockID = lockID;
    }
}
