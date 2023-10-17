public class Founder {
    private String name;
    private int born;

    public Founder() {
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    @Override
    public String toString() {
        return "Founder{" +
                "name='" + name + '\'' +
                ", born=" + born +
                '}';
    }
}
