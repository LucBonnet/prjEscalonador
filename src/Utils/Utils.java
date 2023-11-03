package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    public static void sleep(int s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public static List<Processo> getFileInfo() {
        System.out.println(System.getProperty("user.dir"));
        String pathFile = System.getProperty("user.dir") + "/src/prjsistemasoperacionais/arquivo.txt";
        File arquivo = new File(pathFile);
        
        List<Processo> processos = null;
        
         try (Scanner scan = new Scanner(arquivo)) {
            processos = new ArrayList<>();
            while (scan.hasNextLine()) {
                String linha = scan.nextLine();

                String partes[] = linha.split(" ");

                String PID = partes[0];
                int duracao = Integer.parseInt(partes[1]);
                int chegada = Integer.parseInt(partes[2]);
                
                int prioridade = Integer.parseInt(partes[3]);

                int numIO = 0;
                String strIO[] = null;
                if (partes.length == 5) {
                    strIO = partes[4].split(",");
                    numIO = strIO.length;
                }

                List<Integer> instantesIO = new ArrayList<>();
                for (int i = 0; i < numIO; i++) {
                    instantesIO.add(Integer.valueOf(strIO[i]));
                }

                processos.add(new Processo(PID, duracao, chegada, prioridade, instantesIO));
            }
        } catch (FileNotFoundException ex) {
             System.out.println("Erro ao ler o arquivo");
        }
        
        return processos;
    }
}
