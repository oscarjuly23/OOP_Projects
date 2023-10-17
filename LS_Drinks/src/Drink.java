import java.util.ArrayList;

public class Drink {
    private ArrayList<Alcohol> alcohols = new ArrayList();
    private ArrayList<Type> types = new ArrayList();
    private ArrayList<Mixer> mixers = new ArrayList();

    public Drink() {
    }

    public ArrayList<Alcohol> getAlcohols() {
        return alcohols;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public ArrayList<Mixer> getMixers() {
        return mixers;
    }

    public void setAlcohols(ArrayList<Alcohol> alcohols) {
        this.alcohols = alcohols;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }

    public void setMixers(ArrayList<Mixer> mixers) {
        this.mixers = mixers;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "alcohols=" + alcohols +
                ", types=" + types +
                ", mixers=" + mixers +
                '}';
    }
}