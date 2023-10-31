package prjsistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class Fifo extends Algoritmo {

    public Fifo(List<Processo> procs) {
        this.processos = procs;
        this.atual = procs.get(0);
        this.cont = 0;
        this.espera = new ArrayList<>();

        // Atual = primeiro processo em ordem de chegada
        for (Processo p : procs) {
            if (p.chegada < atual.chegada) {
                atual = p;
            }
        }

        this.instante = 0;
    }

    @Override
    public void exec() {
        while (true) {
            System.out.println("********** TEMPO " + instante + " *************");

            // Verifica se o acabou o tempo do processo
            if (atual.duracao - atual.temp <= 0) {
                System.out.println("#[evento] ENCERRANDO <" + atual.nome + ">");
                atual = getNextProcess();
            }

            // Verifica se o instante atual é interrupção do processo atual
            if (atual != null) {
                if (!atual.interrupcao.isEmpty()) {
                    if (atual.interrupcao.get(0) == atual.temp) {
                        espera.add(atual);
                        atual.interrupcao.remove(0);
                        System.out.println("#[evento] OPERACAO I/O <" + atual.nome + ">");

                        atual = getNextProcess();
                    }
                }
            }

            // Adiciona o processo na fila de acordo com a chegada
            int i = 0;
            for (i = 0; i < processos.size(); i++) {
                if (processos.get(i).chegada == instante && !atual.nome.equals(processos.get(i).nome)) {
                    System.out.println("#[evento] CHEGADA <" + processos.get(i).nome + ">");
                    espera.add(processos.get(i));
                }
            }

            // Exibir Fila
            System.out.print("Fila: ");
            for (i = 0; i < espera.size(); i++) {
                Processo p = espera.get(i);
                System.out.print(p.nome + "(" + (p.duracao - p.temp) + ") ");
            }
            if (i == 0) {
                System.out.println("Nao ha processos na fila");
            } else {
                System.out.println("");
            }

            // Não existe processos na fila de espera
            if (atual == null) {
                break;
            }

            // Exibir processo na CPU
            System.out.println("CPU: " + atual.nome + "(" + (atual.duracao - atual.temp) + ")");

            // Aumenta o tempo do processo
            atual.temp++;
            instante++;

            // Espera um segundo
            Utils.sleep(atraso);
        }
        System.out.println("ACABARAM OS PROCESSOS!!!");

        System.out.println("-----------------------------------");
        System.out.println("------- Encerrando simulacao ------");
        System.out.println("-----------------------------------");
    }
}
