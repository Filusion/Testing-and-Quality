package domaca_naloga2;

class DiplomatPrimerjajPoImenu implements java.util.Comparator<Diplomat> {
    @Override
    public int compare(Diplomat o1, Diplomat o2) {
        String ime1 = o1.getPriimek() + ", " + o1.getIme();
        String ime2 = o2.getPriimek() + ", " + o2.getIme();
        return ime1.compareToIgnoreCase(ime2);
    }
}

class DiplomatPrimerjajPoVpSt implements java.util.Comparator<Diplomat> {
    @Override
    public int compare(Diplomat o1, Diplomat o2) {
        String s1 = o1.getVpisnaStevilka();
        String s2 = o2.getVpisnaStevilka();

        s1 = s1.substring(s1.length()-7);
        s2 = s2.substring(s2.length()-7);
        return -s1.compareTo(s2);
    }
}

public class Diplomat implements java.io.Serializable {
    protected String ime;
    protected String priimek;
    protected String vpisnaStevilka;
    protected String FRIProgram;
    protected String letoDiplome;
    protected String naslovDiplome;

    public Diplomat() {}

    public Diplomat(String ime, String priimek, String vpisnaStevilka,
                    String FRIProgram, String letoDiplome, String naslovDiplome) {
        this.ime = ime;
        this.priimek = priimek;
        this.vpisnaStevilka = vpisnaStevilka;
        this.FRIProgram = FRIProgram;
        this.letoDiplome = letoDiplome;
        this.naslovDiplome = naslovDiplome;
    }

    public String getIme() {
        return ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public String getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    @Override
    public String toString() {
        return "\t" + this.vpisnaStevilka + " | " + this.priimek + ", " + this.ime + " | " +
                this.FRIProgram + " | " + this.letoDiplome + " | " + this.naslovDiplome;
    }
}
