import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CidadeCSV {


    //Método para receber uma cidade do arquivo CSV
    public static ArrayList<Cidade> listarCidade(String dir) {
        ArrayList<Cidade> listaCidades = new ArrayList<>();

        try {
            // Abrir o leitor de arquivos
            BufferedReader leitor = new BufferedReader(new FileReader(dir));
            // Ler o arquivo linha por linha
            String linha;
            boolean primeiraLinha = true;

            while((linha = leitor.readLine()) != null) {
                // Ignorar a primeira linha
                if(primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Separar os dados da linha
                String[] dados = linha.split(",");

                // Criar um objeto Cidade com os dados
                Data data = new Data(Integer.parseInt(dados[3]), Integer.parseInt(dados[2]), Integer.parseInt(dados[4]));
                Cidade cidade = new Cidade(dados[0], dados[1], data, Double.parseDouble(dados[5]));

                // Adicionar a cidade na lista
                listaCidades.add(cidade);

            }

        } catch(IOException e) {
            e.printStackTrace();
        }
        return listaCidades;
    }
}



