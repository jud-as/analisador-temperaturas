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

        // Agrupar dados por arquivo, ano e mês
        Map<String, Map<Integer, Map<Integer, List<Cidade>>>> agrupamentoFinal = CalculoCidades.agruparPorArquivoAnoMes(cidadesPorArquivo);

        // Exportar os resultados para um arquivo .txt
        exportarDadosParaTxt(agrupamentoFinal, "./resultado.txt");

    }

    // Função para exportar dados para um arquivo .txt
    public static void exportarDadosParaTxt(Map<String, Map<Integer, Map<Integer, List<Cidade>>>> dados, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Map.Entry<String, Map<Integer, Map<Integer, List<Cidade>>>> entry : dados.entrySet()) {
                writer.write("Arquivo: " + entry.getKey() + "\n");
                for (Map.Entry<Integer, Map<Integer, List<Cidade>>> anoEntry : entry.getValue().entrySet()) {
                    writer.write("  Ano: " + anoEntry.getKey() + "\n");
                    for (Map.Entry<Integer, List<Cidade>> mesEntry : anoEntry.getValue().entrySet()) {
                        writer.write("    Mês: " + mesEntry.getKey() + "\n");
                        for (Cidade cidade : mesEntry.getValue()) {
                            writer.write("      " + cidade + "\n");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
