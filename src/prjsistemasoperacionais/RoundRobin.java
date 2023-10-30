package prjsistemasoperacionais;

import java.util.List;

public class RoundRobin {
    public List<Processo> processos;
    public final int quantum = 4;
    
    public RoundRobin(List<Processo> procs) {
        this.processos = procs;
    }
    
    public void exec() {
        
    }
}
