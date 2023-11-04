package Algoritmo;

import Telas.Controller;
import Utils.Processo;
import Utils.Utils;
import java.util.ArrayList;

public class Prioridade extends Algoritmo {

    private final int ALNUM = 4;

    public Prioridade(Controller con) {
        this.processos = Utils.getFileInfo();
        this.atual = processos.get(0);
        this.cont = 0;
        this.con = con;
        this.resultado = "";
        this.espera = new ArrayList<>();

        // Atual = primeiro processo em ordem de chegada
        for (Processo p : processos) {
            if (p.chegada < atual.chegada) {
                atual = p;
            }
        }

        this.instante = 0;
        con.criarVisualProcesso(atual.nome, instante, ALNUM);
    }

    private void getNextProcess() {
        this.cont = 0;
        if (espera.isEmpty()) {
            this.atual = null;
            return;
        }

        int index = 0;
        int maiorPrioridade = espera.get(0).prioridade;
        for (int i = 1; i < espera.size(); i++) {
            if (espera.get(1).prioridade > maiorPrioridade) {
                index = i;
            }
        }

        this.atual = espera.remove(index);
        con.criarVisualProcesso(atual.nome, instante, ALNUM);
    }

    @Override
    public String exec() {
        resultado += """
                           ***********************************
                           ***** ESCALONADOR  Prioridade *****
                           -----------------------------------
                           ------- INICIANDO SIMULACAO -------
                           -----------------------------------\n""";

        while (true) {
            resultado += "********** TEMPO " + instante + " *************\n";

            // Verifica se o acabou o tempo do processo
            if (atual.duracao - atual.temp <= 0) {
                resultado += "#[evento] ENCERRANDO <" + atual.nome + ">\n";
                getNextProcess();
            }

            // Verifica se o instante atual é interrupção do processo atual
            if (atual != null) {
                if (!atual.interrupcao.isEmpty()) {
                    if (atual.interrupcao.get(0) == atual.temp) {
                        espera.add(atual);
                        atual.interrupcao.remove(0);
                        resultado += "#[evento] OPERACAO I/O <" + atual.nome + ">\n";

                        getNextProcess();
                    }
                }
            }

            // Adiciona o processo na fila de acordo com a chegada
            int i;
            for (i = 0; i < processos.size(); i++) {
                if (processos.get(i).chegada == instante && !atual.nome.equals(processos.get(i).nome)) {
                    resultado += "#[evento] CHEGADA <" + processos.get(i).nome + ">\n";
                    espera.add(processos.get(i));
                }
            }

            // Exibir Fila
            resultado += "Fila: ";
            for (i = 0; i < espera.size(); i++) {
                Processo p = espera.get(i);
                resultado += p.nome + "(" + (p.duracao - p.temp) + ") ";
            }
            if (i == 0) {
                resultado += "Nao ha processos na fila\n";
            } else {
                resultado += "\n";
            }

            // Não existe processos na fila de espera
            if (atual == null) {
                break;
            }

            // Exibir processo na CPU
            resultado += "CPU: " + atual.nome + "(" + (atual.duracao - atual.temp) + ")\n";
            calcTempoEspera();

            // Aumenta o tempo do processo
            atual.temp++;
            con.aumentaProcesso(ALNUM);
            instante++;

            // Espera um segundo
            Utils.sleep(atraso);
        }
        resultado += "ACABARAM OS PROCESSOS!!!\n";

        resultado += "-----------------------------------\n";
        resultado += "------- Encerrando simulacao ------\n";
        resultado += "-----------------------------------\n";

        resultado += "&&\n";
        resultado += "Tempo de espera:\n";
        for (Processo proc : processos) {
            resultado += proc.nome + ": " + proc.tempEspera + "\n";
        }
        resultado += "Tempo de espera médio: " + getTempoEsperaMedio() + "\n\n";
        
        return resultado;
    }
}
