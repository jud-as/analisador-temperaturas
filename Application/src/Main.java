import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //Caminho relativo do diretório
    public static String dir = "./uploads/";



    public static void main(String[] args) {

        //Obter todas as cidades do diretório
        ArrayList<Cidade> listaCidades = ServiceCidade.getDirCidades(dir);

    }
}