package graph;

import java.util.Objects;

public class Vertex
{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(Objects.equals(name, ""))
        {
            System.out.println("Nome vazio não será adicionado");
        }
        this.name = name;
    }

    public Vertex(String name) {
        this.name = name;
    }
}
