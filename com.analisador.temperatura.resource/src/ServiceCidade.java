
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceCidade {

    //Método para retornar todas as cidades de um diretório
    public static Map<String, ArrayList<Cidade>> getDirCidades(String dir) {
        Map<String, ArrayList<Cidade>> cidadesPorArquivo = new HashMap<>();

        File[] listaArquivos = leitorDiretorio(dir); //Lê todos os arquivos do diretório

        for (File arquivo : listaArquivos) { //Percorrer todos os arquvios do diretório especificado
            ArrayList<Cidade> listaCidades = CidadeCSV.lerCidadeCSV(dir + arquivo.getName());
            cidadesPorArquivo.put(arquivo.getName(), listaCidades);
        }

        return cidadesPorArquivo;
    }

    //Método para ler todos os arquivos do diretório
    public static File[] leitorDiretorio(String dir) {
        File diretorio = new File(dir); //Diretório
        return diretorio.listFiles(); //Retorna todos os arquivos do diretório
    }





}
