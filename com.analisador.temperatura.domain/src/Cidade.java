

public class Cidade {

    private String pais;
    private String nome;
    private Data data;
    private double temperatura;


    public Cidade(String pais, String nome, Data data, double temperatura) {
        this.pais = pais;
        this.nome = nome;
        this.data = data;
        this.temperatura = temperatura;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "Cidade{" +
                "pais='" + pais + '\'' +
                ", nome='" + nome + '\'' +
                ", data=" + data +
                ", temperatura=" + temperatura +
                '}';
    }

}

