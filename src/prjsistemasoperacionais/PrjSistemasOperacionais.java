package prjsistemasoperacionais;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import prjsistemasoperacionais.Util.Processo;

public class PrjSistemasOperacionais {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(System.getProperty("user.dir"));
        String pathFile = System.getProperty("user.dir") + "/src/prjsistemasoperacionais/arquivo.txt";
        File arquivo = new File(pathFile);
        Scanner scan = new Scanner(arquivo);

        List<Processo> processos = new ArrayList<Processo>();

        while (scan.hasNextLine()) {
            String linha = scan.nextLine();

            String partes[] = linha.split(" ");

            String PID = partes[0];
            int duracao = Integer.parseInt(partes[1]);
            int chegada = Integer.parseInt(partes[2]);

            int numIO = 0;
            String strIO[] = null;
            if (partes.length == 4) {
                strIO = partes[3].split(",");
                numIO = strIO.length;
            }

            List<Integer> instantesIO = new ArrayList<>();
            for (int i = 0; i < numIO; i++) {
                instantesIO.add(Integer.valueOf(strIO[i]));
            }

            processos.add(new Processo(PID, duracao, chegada, instantesIO));
        }

        scan.close();

        Escalonador esc = new Escalonador(processos);
        esc.run();
    }

}
