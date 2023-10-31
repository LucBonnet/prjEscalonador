package prjsistemasoperacionais;

import Algoritmo.Algoritmo;
import Algoritmo.Sjf;
import java.util.List;

import Utils.Processo;

public class Escalonador {

    public List<Processo> processos;

    public Escalonador(List<Processo> procs) {
        this.processos = procs;
    }

    public void run() {
        Algoritmo al;
        al = new Sjf(processos);

        al.exec();
    }
}
