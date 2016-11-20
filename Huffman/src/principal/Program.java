package principal;

import java.io.File;
import javax.swing.JOptionPane;

public class Program {

    public static void main(String[] args) {
        //entrdas:
        String des = "C:\\Users\\RenataMaria\\Desktop\\";

        String opcao = JOptionPane.showInputDialog(null, "comprimir: -c\n"
                + "descomprimir: -d");
        String entrada = des + "a.txt";
        String saida = des + "b.txt";

        boolean compress = true;

        //comprimir
        if (opcao.equals("c")) {
            compress = true;
        } //descomprimir
        else if (opcao.equals("d")) {
            compress = false;
        }
        else{
            //opcao invalida
        }

        File inFile = new File(entrada);
        File outFile = new File(saida);

        if (compress) {
            Huffman.compress(inFile, outFile);
        } else {
            Huffman.decompress(inFile, outFile);
        }

    }
}
