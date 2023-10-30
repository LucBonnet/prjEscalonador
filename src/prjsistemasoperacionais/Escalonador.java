package prjsistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class Escalonador {

    public Processo atual;
    public List<Processo> espera;
    public List<Processo> processos;
    public int instante;
    public int cont;
    public int quantum = 4;

    public Escalonador(List<Processo> procs) {
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

    public Processo getNextProcess() {
        this.cont = 0;
        if (espera.isEmpty()) {
            return null;
        }

        return espera.remove(0);
    }

    public void run() {
        while (true) {
            System.out.println("Tempo: " + instante);

            // Verifica se o acabou o tempo do processo
            if (this.cont == quantum) {
                this.cont = 0;
                System.out.println(atual.nome + ": quantum");
                if (atual.duracao - atual.temp > 0) {
                    espera.add(atual);
                }
                atual = getNextProcess();
            } else if (atual.duracao - atual.temp <= 0) {
                System.out.println("Encerrando: " + atual.nome);
                atual = getNextProcess();
            } else {
                this.cont++;
            }

            // Verifica se o instante atual é interrupção do processo atual
            if (!atual.interrupcao.isEmpty()) {
                if (atual.interrupcao.get(0) == atual.temp) {
                    espera.add(atual);
                    atual.interrupcao.remove(0);
                    System.out.println("Interrupção: " + atual.nome + "(" + (atual.duracao - atual.temp) + ") ");

                    atual = getNextProcess();
                }
            }

            // Adiciona o processo na fila de acordo com a chegada
            int i = 0;
            for (i = 0; i < processos.size(); i++) {
                if (processos.get(i).chegada == instante && !atual.nome.equals(processos.get(i).nome)) {
                    System.out.println("Chegada: " + processos.get(i).nome);
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

            // Exibir processo na CPU
            System.out.println("CPU: " + atual.nome + "(" + (atual.duracao - atual.temp) + ")");

            // Não existe processos na fila de espera
            if (atual == null) {
                break;
            }

            System.out.println("");

            // Aumenta o tempo do processo
            atual.temp++;
            instante++;

            // Espera um segundo
            Utils.sleep(1); // 1 sec
        }

    }
}
