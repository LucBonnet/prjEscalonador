package Telas;

import Utils.Processo;
import Utils.Utils;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import prjsistemasoperacionais.Escalona;

public class Controller {

    private JPanel panel;
    private JScrollPane scPanel;
    private JButton btn;
    private JPanel pnl1, pnl2, pnl3, pnl4;
    private JTextArea resultado;
    private String rtRR, rtFifo, rtSJF, rtPri;
    private String rRR, rFifo, rSJF, rPri;

    private final int altura = 90;
    private final int larSegundo = 30;
    public List<Processo> processos;
    private final Color[] cores = {new Color(244, 67, 54), new Color(33, 150, 243), new Color(76, 175, 80), new Color(255, 193, 7), new Color(156, 39, 176)};

    public Controller(JPanel panel, JScrollPane scPanel, JTextArea resultado, JButton btn) {
        this.panel = panel;
        this.scPanel = scPanel;
        this.resultado = resultado;
        this.btn = btn;
        
        rRR = rFifo = rSJF = rPri = "";
        rtRR = rtFifo = rtSJF = rtPri = "";

        panel.setLayout(null);
    }

    private void atualizar() {
        scPanel.revalidate();
        scPanel.repaint();
    }

    private int getIndice(String nome) {
        for (int i = 0; i < processos.size(); i++) {
            if (processos.get(i).nome.equals(nome)) {
                return i;
            }
        }

        return -1;
    }

    public void start(boolean brr, boolean bfifo, boolean bsjf, boolean bpri) {
        btn.setEnabled(false);
        this.panel.removeAll();
        this.processos = Utils.getFileInfo();
        int cont = 0;
        Thread thRR, thFIFO, thSJF, thPri;

        if (brr) {
            thRR = new Thread(new Escalona(this, 0));

            pnl1 = new JPanel();
            pnl1.setLayout(null);
            pnl1.setLocation(10, 10 + (cont * (altura + 70)));
            pnl1.setSize(500, altura + 60);

            JLabel lbl = new JLabel("Round Robin:");
            lbl.setLocation(0, 0);
            lbl.setSize(500, 30);
            lbl.setOpaque(true);
            lbl.setFont(new Font(lbl.getFont().getFamily(), Font.BOLD, 14));
            pnl1.add(lbl);

            panel.add(pnl1);
            atualizar();

            cont++;
            thRR.start();
        }

        if (bfifo) {
            thFIFO = new Thread(new Escalona(this, 1));

            this.pnl2 = new JPanel();
            pnl2.setLayout(null);
            pnl2.setLocation(10, 10 + (cont * (altura + 70)));
            pnl2.setSize(500, altura + 60);

            JLabel lbl = new JLabel("First come, first served (FIFO):");
            lbl.setLocation(0, 0);
            lbl.setSize(500, 30);
            lbl.setOpaque(true);
            lbl.setFont(new Font(lbl.getFont().getFamily(), Font.BOLD, 14));
            pnl2.add(lbl);

            panel.add(pnl2);
            atualizar();

            cont++;
            thFIFO.start();
        }

        if (bsjf) {
            thSJF = new Thread(new Escalona(this, 2));

            this.pnl3 = new JPanel();
            pnl3.setLayout(null);
            pnl3.setLocation(10, 10 + (cont * (altura + 70)));
            pnl3.setSize(500, altura + 60);

            JLabel lbl = new JLabel("Shortest job first (SJF):");
            lbl.setLocation(0, 0);
            lbl.setSize(500, 30);
            lbl.setOpaque(true);
            lbl.setFont(new Font(lbl.getFont().getFamily(), Font.BOLD, 14));
            pnl3.add(lbl);

            panel.add(pnl3);
            atualizar();

            cont++;
            thSJF.start();
        }

        if (bpri) {
            thPri = new Thread(new Escalona(this, 3));

            this.pnl4 = new JPanel();
            pnl4.setLayout(null);
            pnl4.setLocation(10, 10 + (cont * (altura + 70)));
            pnl4.setSize(500, altura + 60);

            JLabel lbl = new JLabel("Prioridade:");
            lbl.setLocation(0, 0);
            lbl.setSize(500, 30);
            lbl.setOpaque(true);
            lbl.setFont(new Font(lbl.getFont().getFamily(), Font.BOLD, 14));
            pnl4.add(lbl);

            panel.add(pnl4);
            atualizar();

            cont++;
            thPri.start();
        }
    }

    public void criarVisualProcesso(String nome, int inicio, int al) {
        JPanel pnl = null;

        switch (al) {
            case 1 ->
                pnl = pnl1;
            case 2 ->
                pnl = pnl2;
            case 3 ->
                pnl = pnl3;
            case 4 ->
                pnl = pnl4;
        }

        if (pnl == null) {
            return;
        }

        JLabel lbl = new JLabel(nome, SwingConstants.CENTER);
        lbl.setLocation(inicio * larSegundo, 30);
        lbl.setSize(0, altura);
        lbl.setVerticalTextPosition(SwingConstants.CENTER);
        lbl.setBackground(cores[getIndice(nome)]);
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setBorder(BorderFactory.createLineBorder(cores[getIndice(nome)].darker()));
        pnl.add(lbl);
        
        JLabel lblInst = new JLabel(inicio + "");
        lblInst.setLocation(inicio * larSegundo, 20 + altura + 10);
        lblInst.setSize(30, 20);
        pnl.add(lblInst, 0);
                
        atualizar();
    }

    public void aumentaProcesso(int al) {
        JPanel pnl = null;

        switch (al) {
            case 1 ->
                pnl = pnl1;
            case 2 ->
                pnl = pnl2;
            case 3 ->
                pnl = pnl3;
            case 4 ->
                pnl = pnl4;
        }
        
        pnl.setSize(pnl.getSize().width + larSegundo, pnl.getSize().height);

        JLabel lbl = (JLabel) pnl.getComponent(pnl.getComponentCount() - 1);
        lbl.setSize(lbl.getSize().width + larSegundo, lbl.getSize().height);
    }
    
    public void setResultado(String resul, int algoritmo) {
        String resultTempo = resul.split("&&")[1];
        String result = resul.split("&&")[0];
        switch (algoritmo) {
            case 0 -> {
                rtRR = "Round Robin: \n" + resultTempo;
                rRR = result + "\n";
            }
            case 1 -> {
                rtFifo = "FIFO: \n" + resultTempo;
                rFifo = result + "\n";
            }
            case 2 -> {
                rtSJF = "SJF: \n" + resultTempo;
                rSJF = result + "\n";
            }
            case 3 -> {
                rtPri = "Prioridade: \n" + resultTempo;
                rPri = result + "\n";
            }
        }
        
        String pathFile = System.getProperty("user.dir") + "/src/prjsistemasoperacionais/saida.txt";
        Utils.escreveArquivo(rRR + rFifo + rSJF + rPri, pathFile);
        
        resultado.setText(rtRR + rtFifo + rtSJF + rtPri);
        
        btn.setEnabled(true);
    }

}
