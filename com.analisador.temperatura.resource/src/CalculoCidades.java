import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
