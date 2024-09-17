import java.io.File;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //Caminho relativo dos arquivos CSV
    public static String dir = "./uploads/";


    public static void main(String[] args) {

        System.out.println("Lista de cidades: \n");
        File[] listaArquivos = leitorDiretorio(dir);

        for (File arquivo : listaArquivos) {

            ArrayList<Cidade> listaCidades =  CidadeCSV.listarCidade(dir+arquivo.getName());
            for (Cidade cidade : listaCidades) {
                System.out.println(cidade);
            }

        }

    }

    //Método para ler todos os arquivos do diretório
    public static File[] leitorDiretorio(String dir) {

        File[] arquivos;
        File diretorio = new File(dir);
        arquivos = diretorio.listFiles();

        return arquivos;
    }

}