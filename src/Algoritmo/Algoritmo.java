package Algoritmo;

import java.util.List;

import Utils.Processo;

public abstract class Algoritmo {

    protected int cont;
    protected List<Processo> espera;
    protected List<Processo> processos;
    protected int instante;
    public Processo atual;
    public double tempEsperaMedio;
    protected final int atraso = 1000; // 1000 ms => 1 sec

    public Processo getNextProcess() {
        this.cont = 0;
        if (espera.isEmpty()) {
            return null;
        }

        return espera.remove(0);
    }

    public void exec() {

    }

    public void calcTempoEspera() {
        for (Processo proc : processos) {
            if (proc.nome != atual.nome && proc.duracao > proc.temp) {
                proc.tempEspera++;
            }
        }
    }

    public double getTempoEsperaMedio() {
        int esperaTotal = 0;

        for (Processo proc : processos) {
            esperaTotal += proc.tempEspera;
        }

        return esperaTotal / processos.size();
    }
}
