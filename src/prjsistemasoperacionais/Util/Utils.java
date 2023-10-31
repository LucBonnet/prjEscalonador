package prjsistemasoperacionais.Util;

public class Utils {
    public static void sleep(int s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            System.out.println("Erro: " + e);
        }
    }
}
