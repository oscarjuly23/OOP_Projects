import java.util.ArrayList;
import java.util.Arrays;

public class Alcohol {
    private String nom;
    private float graduation;
    private String procedence;
    private int year;
    private int type;
    private Founder[] founders;
    private int[] combinations;

    public Alcohol() {
    }

    public String getNom() {
        return nom;
    }

    public float getGraduation() {
        return graduation;
    }

    public String getProcedence() {
        return procedence;
    }

    public int getYear() {
        return year;
    }

    public int getType() {
        return type;
    }

    public int[] getCombinations() {
        return combinations;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setGraduation(float graduation) {
        this.graduation = graduation;
    }

    public void setProcedence(String procedence) {
        this.procedence = procedence;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCombinations(int[] combinations) {
        this.combinations = combinations;
    }

    public Founder[] getFounders() {
        return founders;
    }

    public void setFounders(Founder[] founders) {
        this.founders = founders;
    }

    @Override
    public String toString() {
        return "Alcohol{" +
                "nom='" + nom + '\'' +
                ", graduation=" + graduation +
                ", procedence='" + procedence + '\'' +
                ", year=" + year +
                ", type=" + type +
                ", founders=" + Arrays.toString(founders) +
                ", combinations=" + Arrays.toString(combinations) +
                '}';
    }
}