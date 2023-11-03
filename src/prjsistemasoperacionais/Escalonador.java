package prjsistemasoperacionais;

import Algoritmo.*;
import Telas.Controller;

public class Escalonador implements Runnable{

    public Controller con;
    public int algoritmo;

    public Escalonador(Controller con, int algoritmo) {
        this.con = con;
        this.algoritmo = algoritmo;
    }

    @Override
    public void run() {
        Algoritmo al;
        switch(algoritmo) {
            case 0 -> al = new RoundRobin(con);
            case 1 -> al = new Fifo(con);
            case 2 -> al = new Sjf(con);
            case 3 -> al = new Prioridade(con);
            default -> { return; }
        }
        String resultado = al.exec();
        System.out.println(resultado);
    }
}
