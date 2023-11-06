package Algoritmo;

import Telas.Controller;
import java.util.ArrayList;

import Utils.Processo;
import Utils.Utils;

public class RoundRobin extends Algoritmo {

    public int quantum = 4;
    private final int ALNUM = 1;

    public RoundRobin(Controller con) {
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

    @Override
    public String exec() {
        resultado += """
                           ***********************************
                           ***** ESCALONADOR ROUND ROBIN *****
                           -----------------------------------
                           ------- INICIANDO SIMULACAO -------
                           -----------------------------------\n""";

        while (true) {
            resultado += "********** TEMPO " + instante + " *************\n";

            // Verifica se o acabou o tempo do processo
            if (this.cont + 1 == quantum) {
                this.cont = 0;
                resultado += "#[evento] FIM QUANTUM <" + atual.nome + ">\n";
                if (atual.duracao - atual.temp > 0) {
                    espera.add(atual);
                }
                getNextProcess(ALNUM);
            } else if (atual.duracao - atual.temp <= 0) {
                resultado += "#[evento] ENCERRANDO <" + atual.nome + ">\n";
                getNextProcess(ALNUM);
            } else {
                this.cont++;
            }

            // Verifica se o instante atual é interrupção do processo atual
            if (atual != null) {
                if (!atual.interrupcao.isEmpty()) {
                    if (atual.interrupcao.get(0) == atual.temp) {
                        espera.add(atual);
                        atual.interrupcao.remove(0);
                        resultado += "#[evento] OPERACAO I/O <" + atual.nome + ">\n";

                        getNextProcess(ALNUM);
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

            // Não existe processos na fila de esper
            // Exibir processo na CPU
            resultado += "CPU: " + atual.nome + "(" + (atual.duracao - atual.temp) + ")\n";
            con.aumentaProcesso(ALNUM);
            calcTempoEspera();

            // Aumenta o tempo do processo
            atual.temp++;
            instante++;
            // Espera um segundo
            Utils.sleep(atraso);

        }
        con.criarVisualProcesso("", instante, ALNUM);
        
        resultado += "ACABARAM OS PROCESSOS!!!\n";
        resultado += "-----------------------------------\n";
        resultado += "------- Encerrando simulacao ------\n";
        resultado += "-----------------------------------\n";

        resultado += "&&\n";
        String tempoEsperaMedio = "";
        tempoEsperaMedio += "Tempo de espera:\n";
        for (Processo proc : processos) {
            tempoEsperaMedio += proc.nome + ": " + proc.tempEspera + "\n";
        }
        tempoEsperaMedio += "Tempo de espera médio: " + getTempoEsperaMedio() + "\n";
        
        resultado += tempoEsperaMedio + "\n";

        return resultado;
    }
}
