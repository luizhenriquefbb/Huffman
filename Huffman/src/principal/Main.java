package principal;


import javax.swing.JOptionPane;
/**
 * @author LuizHenrique
 */
public class Main {
    public static void main(String[] args) {
        //entrdas:
        String des = "arquivos\\";

        String opcao = JOptionPane.showInputDialog(null, "comprimir: c\n"
                + "descomprimir: d");
        
        while (true) {
            if (opcao.equals("c")) {
                Huffman.comprimir();
                break;
            } else if (opcao.equals("d")) {
                Huffman.descomprimir();
                break;
            } else {
                JOptionPane.showMessageDialog(null, "comando invalido");
            }
        }

    }
}
