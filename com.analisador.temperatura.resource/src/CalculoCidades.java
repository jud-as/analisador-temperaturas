import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CalculoCidades {


    //Método para agrupar registros por anos e meses:
    public static Map<Integer, Map<Integer, List<Cidade>>> agruparPorAnoMes(ArrayList<Cidade> listaCidades) {
        return listaCidades.stream()
                .collect(Collectors.groupingBy(
                        cidade -> cidade.getData().getAno(),
                        Collectors.groupingBy(cidade -> cidade.getData().getMes())
                ));

    }

    // Método para agrupar registros por arquivo, ano e mês:
    public static Map<String, Map<Integer, Map<Integer, List<Cidade>>>> agruparPorArquivoAnoMes(Map<String, ArrayList<Cidade>> cidadesPorArquivo) {
        Map<String, Map<Integer, Map<Integer, List<Cidade>>>> agrupamentoFinal = new HashMap<>();

        for (Map.Entry<String, ArrayList<Cidade>> entry : cidadesPorArquivo.entrySet()) {
            agrupamentoFinal.put(entry.getKey(), agruparPorAnoMes(entry.getValue()));
        }

        return agrupamentoFinal;
    }

    // Método para calcular temperaturas média, máxima e mínima por mês para cada cidade:
    public static Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> calcularTemperaturas(Map<String, ArrayList<Cidade>> cidadesPorArquivo) {
        Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade = new HashMap<>();
        for (Map.Entry<String, ArrayList<Cidade>> entry : cidadesPorArquivo.entrySet()) {
            String nomeArquivo = entry.getKey();
            ArrayList<Cidade> listaCidades = entry.getValue();

            Map<Integer, Map<Integer, List<Cidade>>> cidadesAgrupadas = agruparPorAnoMes(listaCidades);

            Map<Integer, Map<Integer, Map<String, Double>>> temperaturasPorAno = new HashMap<>();

            for (Map.Entry<Integer, Map<Integer, List<Cidade>>> anoEntry : cidadesAgrupadas.entrySet()) {
                int ano = anoEntry.getKey();
                Map<Integer, List<Cidade>> cidadesPorMes = anoEntry.getValue();

                Map<Integer, Map<String, Double>> temperaturasPorMes = new HashMap<>();

                for (Map.Entry<Integer, List<Cidade>> mesEntry : cidadesPorMes.entrySet()) {
                    int mes = mesEntry.getKey();
                    List<Cidade> cidades = mesEntry.getValue();

                    DoubleSummaryStatistics stats = cidades.stream()
                            .collect(Collectors.summarizingDouble(Cidade::getTemperatura));

                    Map<String, Double> temperaturas = new HashMap<>();
                    temperaturas.put("media", stats.getAverage());
                    temperaturas.put("maxima", stats.getMax());
                    temperaturas.put("minima", stats.getMin());

                    temperaturasPorMes.put(mes, temperaturas);
                }

                temperaturasPorAno.put(ano, temperaturasPorMes);
            }
            temperaturasPorCidade.put(nomeArquivo, temperaturasPorAno);

        }
        return temperaturasPorCidade;
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

