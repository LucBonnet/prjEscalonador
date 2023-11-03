package Utils;

import java.awt.Color;

public class ProcFila {
    public String nome;
    public int inicio;
    public int duracao;
    public Color cor;
    
    public ProcFila(String nome, int inicio) {
        this.nome = nome;
        this.inicio = inicio;
    }
    
    public void addDuracao() {
        this.duracao++;
    }
}
