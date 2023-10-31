package prjsistemasoperacionais;

import java.util.List;

public abstract class Algoritmo {

    protected int cont;
    protected List<Processo> espera;
    protected List<Processo> processos;
    protected int instante;
    public Processo atual;
    protected final int atraso = 1000; // 1000 ms => 1 sec

    protected Processo getNextProcess() {
        this.cont = 0;
        if (espera.isEmpty()) {
            return null;
        }

        return espera.remove(0);
    }

    public void exec() {

    }
}
