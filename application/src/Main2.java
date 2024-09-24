import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main2 {

    // Caminho relativo do diretório
    public static String dir = "./uploads/";

    public static void main(String[] args) {
        // Array com diferentes contagens de threads para o experimento
        int[] threadCounts = {1, 2, 4, 8, 16, 32, 64, 80, 160, 320};

        // Loop para cada contagem de threads
        for (int threadCount : threadCounts) {
            // Marca o tempo inicial
            long tempoInicial = System.currentTimeMillis();

            // Obtém todas as cidades do diretório agrupadas por arquivo
            Map<String, ArrayList<Cidade>> cidadesPorArquivo = ServiceCidade.getDirCidades(dir);

            // Mapa para armazenar as temperaturas calculadas
            Map<String, Map<Integer, Map<Integer, Map<String, Double>>>> temperaturasPorCidade = new HashMap<>();

            // Cria um ExecutorService com um pool de threads fixo
            ExecutorService cidadeExecutor = Executors.newFixedThreadPool(threadCount);

            // Itera sobre cada entrada no mapa de cidades por arquivo
            for (Map.Entry<String, ArrayList<Cidade>> entry : cidadesPorArquivo.entrySet()) {
                String nomeArquivo = entry.getKey();
                ArrayList<Cidade> listaCidades = entry.getValue();

                // Calcula o tamanho de cada chunk de cidades para cada thread
                int chunkSize = (int) Math.ceil((double) listaCidades.size() / threadCount);

                // Cria e executa tarefas para processar sublistas de cidades
                for (int i = 0; i < threadCount; i++) {
                    int start = i * chunkSize;
                    if (start >= listaCidades.size()) break;
                    int end = Math.min(start + chunkSize, listaCidades.size());
                    List<Cidade> sublist = listaCidades.subList(start, end);

                    cidadeExecutor.execute(() -> {
                        Map<Integer, Map<Integer, List<Cidade>>> cidadesAgrupadas = CalculoCidades.agruparPorAnoMes(new ArrayList<>(sublist));
                        ExecutorService anoExecutor = Executors.newCachedThreadPool();
                        for (Map.Entry<Integer, Map<Integer, List<Cidade>>> anoEntry : cidadesAgrupadas.entrySet()) {
                            int ano = anoEntry.getKey();
                            Map<Integer, List<Cidade>> cidadesPorMes = anoEntry.getValue();
                            anoExecutor.execute(new AnoProcessor(ano, cidadesPorMes, temperaturasPorCidade, nomeArquivo));
                        }
                        anoExecutor.shutdown();
                        try {
                            anoExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            // Aguarda a conclusão de todas as tarefas
            cidadeExecutor.shutdown();
            try {
                cidadeExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Exporta os resultados para um arquivo TXT
            ServiceCidade.exportarParaTxt(temperaturasPorCidade, "resultado_" + threadCount + "_threads.txt");

            // Imprime o tempo de execução
            System.out.println("Thread count: " + threadCount + ", Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");
        }
    }
}
