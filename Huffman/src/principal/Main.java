package principal;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        //entrdas:
        String des = "arquivos\\";

        String opcao = JOptionPane.showInputDialog(null, "comprimir: -c\n"
                + "descomprimir: -d");
        String entrada = des + "a.txt";
        
        try {
            FileInputStream inFile = new FileInputStream(entrada);
        
        

            while (true) {
                if (opcao.equals("c")) {
                    Huffman.comprimir(inFile);
                    break;
                } else if (opcao.equals("d")) {
                    Huffman.descomprime();
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "comando invalido");
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo nao encontrado");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
