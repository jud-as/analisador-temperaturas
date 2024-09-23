import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnoProcessor implements Runnable {
    private final int ano;
    private final Map<Integer, List<Cidade>> cidadesPorMes;
    private final Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade;
    private final String nomeArquivo;


    public AnoProcessor(int ano, Map<Integer, List<Cidade>> cidadesPorMes, Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade, String nomeArquivo) {
        this.ano = ano;
        this.cidadesPorMes = cidadesPorMes;
        this.temperaturasPorCidade = temperaturasPorCidade;
        this.nomeArquivo = nomeArquivo;
    }


    @Override
    public void run() {

        Map<Integer, Map<String, Double>> temperaturasPorMes = new HashMap<>();

        for(Map.Entry<Integer, List<Cidade>> mesEntry : cidadesPorMes.entrySet()){
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
        synchronized (temperaturasPorCidade) {
            temperaturasPorCidade.computeIfAbsent(nomeArquivo, k -> new HashMap<>())
                    .put(ano, temperaturasPorMes);

        }
    }
}
