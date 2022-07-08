package graph;

public class WebPage extends Vertex
{
    private float rank;

    public float getRank()
    {
        return rank;
    }

    public void setRank(float rank)
    {
        this.rank = rank;
    }

    public void incrementRank(float increment)
    {
        rank += increment;
    }

    public WebPage(String name, float rank)
    {
        super(name);
        this.rank = rank;
    }

    public float getPageRankToTransfer()
    {
        return rank/getOutDegree();
    }

    @Override
    public String toString()
    {
        return "WebPage{" +
                " name = " + getName() +
                " rank = " + rank +
                '}';
    }
}
