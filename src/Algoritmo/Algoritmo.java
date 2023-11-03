package Algoritmo;

import Telas.Controller;
import java.util.List;

import Utils.Processo;

public abstract class Algoritmo {

    protected int cont;
    protected Controller con;
    protected List<Processo> espera;
    protected List<Processo> processos;
    protected int instante;
    public Processo atual;
    public double tempEsperaMedio;
    protected final int atraso = 200; // 1000 ms => 1 sec

    public void getNextProcess(int algoritmo) {
        this.cont = 0;
        if (espera.isEmpty()) {
            this.atual = null;
            return;
        }

        this.atual = espera.remove(0);
        con.criarVisualProcesso(atual.nome, instante, algoritmo);
    }

    public void exec() {
        
    }

    public void calcTempoEspera() {
        for (Processo proc : processos) {
            if (!proc.nome.equals(atual.nome) && proc.duracao > proc.temp) {
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
