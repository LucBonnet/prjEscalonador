package prjsistemasoperacionais;

import java.util.List;

public class Escalonador {

    public List<Processo> processos;

    public Escalonador(List<Processo> procs) {
        this.processos = procs;
    }

    public void run() {
        Algoritmo al;

        al = new RoundRobin(processos);

        al.exec();
    }
}
