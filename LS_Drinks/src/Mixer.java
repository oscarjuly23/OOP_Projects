public class Mixer {
    private int id;
    private String name;

    public Mixer() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Mixer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
