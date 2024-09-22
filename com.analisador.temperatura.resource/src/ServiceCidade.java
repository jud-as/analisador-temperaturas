
import java.io.*;
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



    // Método para exportar o resultado para um arquivo TXT
    public static void exportarParaTxt(Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Map.Entry<String, Map<Integer, Map<Integer, Map<String, Double>>>> arquivoEntry : temperaturasPorCidade.entrySet()) {
                String nomeArquivo = arquivoEntry.getKey();
                writer.write("Arquivo: " + nomeArquivo);
                writer.newLine();

                Map<Integer, Map<Integer, Map<String, Double>>> temperaturasPorAno = arquivoEntry.getValue();
                for (Map.Entry<Integer, Map<Integer, Map<String, Double>>> anoEntry : temperaturasPorAno.entrySet()) {
                    int ano = anoEntry.getKey();
                    writer.write("  Ano: " + ano);
                    writer.newLine();

                    Map<Integer, Map<String, Double>> temperaturasPorMes = anoEntry.getValue();
                    for (Map.Entry<Integer, Map<String, Double>> mesEntry : temperaturasPorMes.entrySet()) {
                        int mes = mesEntry.getKey();
                        writer.write("    Mês: " + mes);
                        writer.newLine();

                        Map<String, Double> temperaturas = mesEntry.getValue();
                        writer.write("      Média: " + temperaturas.get("media"));
                        writer.newLine();
                        writer.write("      Máxima: " + temperaturas.get("maxima"));
                        writer.newLine();
                        writer.write("      Mínima: " + temperaturas.get("minima"));
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
