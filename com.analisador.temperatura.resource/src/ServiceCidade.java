
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServiceCidade {

    //Método para retornar todas as cidades de um diretório
    public static ArrayList<Cidade> getDirCidades(String dir) {
        ArrayList<Cidade> listaCidades = new ArrayList<>();

        File[] listaArquivos = leitorDiretorio(dir); //Lê todos os arquivos do diretório

        for (File arquivo : listaArquivos) { //Percorrer todos os arquvios do diretório especificado

            listaCidades = CidadeCSV.lerCidadeCSV(dir + arquivo.getName());
            for (Cidade cidade : listaCidades) {
                System.out.println(cidade); //Print para depuração
            }

        }

        return listaCidades;
    }


    //Método para ler todos os arquivos do diretório
    public static File[] leitorDiretorio(String dir) {

        File[] arquivos; //Array de arquivos
        File diretorio = new File(dir); //Diretório
        arquivos = diretorio.listFiles(); //Lista de arquivos do diretório

        return arquivos;
    }

    //Método para escrever e exportar um arquivo TXT
    public static void escreverTXT() {


    }




}
