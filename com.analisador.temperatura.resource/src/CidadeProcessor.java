import java.util.*;
import java.util.stream.Collectors;

public class CidadeProcessor implements Runnable {
    private final List<Cidade> cidades;
    private final Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade;
    private final String nomeArquivo;

    public CidadeProcessor(List<Cidade> cidades, Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade, String nomeArquivo) {
        this.cidades = cidades;
        this.temperaturasPorCidade = temperaturasPorCidade;
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void run() {
        Map<Integer, Map<Integer, List<Cidade>>> cidadesAgrupadas = CalculoCidades.agruparPorAnoMes(new ArrayList<>(cidades));
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

        synchronized (temperaturasPorCidade) {
            temperaturasPorCidade.put(nomeArquivo, temperaturasPorAno);
        }
    }
}