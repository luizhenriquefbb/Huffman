package principal;


import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        //entrdas:
        String des = "arquivos\\";

        String opcao = JOptionPane.showInputDialog(null, "comprimir: -c\n"
                + "descomprimir: -d");
        
        while (true) {
            if (opcao.equals("c")) {
                Huffman.comprimir();
                break;
            } else if (opcao.equals("d")) {
                Huffman.descomprime();
                break;
            } else {
                JOptionPane.showMessageDialog(null, "comando invalido");
            }
        }

    }
}
