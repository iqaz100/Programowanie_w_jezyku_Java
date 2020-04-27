package Tables;

public class Payment {
    private int id;
    private int idInstallation;
    private int prize;
    private String term;

    public Payment(){}

    public Payment(int id,int idInstallation,int prize, String term){
        this.id = id;
        this.idInstallation = idInstallation;
        this.prize = prize;
        this.term = term;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInstallation() {
        return idInstallation;
    }

    public void setIdInstallation(int idInstallation) {
        this.idInstallation = idInstallation;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
