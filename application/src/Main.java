import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    //Caminho relativo do diretório
    public static String dir = "./uploads/";


    public static void main(String[] args) {

        //Obter todas as cidades do diretório agrupadas por arquivo
        Map<String, ArrayList<Cidade>> cidadesPorArquivo = ServiceCidade.getDirCidades(dir);

        // Calcula as temperaturas
        Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade = CalculoCidades.calcularTemperaturas(cidadesPorArquivo);

        // Exporta para TXT
        ServiceCidade.exportarParaTxt(temperaturasPorCidade, "resultado.txt");

    }


}
