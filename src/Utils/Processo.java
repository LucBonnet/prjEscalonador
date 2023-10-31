package Utils;

import java.util.List;

public class Processo {

    public String nome;
    public int duracao;
    public int chegada;
    public List<Integer> interrupcao;
    public int temp;
    public int tempEspera;

    public Processo(String nome, int duracao, int chegada, List<Integer> interrupcao) {
        this.nome = nome;
        this.duracao = duracao;
        this.chegada = chegada;
        this.interrupcao = interrupcao;
        this.temp = 0;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Nome = " + nome + "\n";
        result += "Duracao = " + duracao + "\n";
        result += "Instante da chegada do processo = " + chegada + "\n";

        for (int interrup : interrupcao) {
            result += "instantes de I/O = " + interrup + "\n";
        }

        return result;
    }
}
