package Arquivo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LerESalva {
    public static void salvarArquivo(String caminho, String txt){
        try {
            FileOutputStream arq = new FileOutputStream(caminho);
            PrintWriter pr = new PrintWriter(arq);
            
            pr.println(txt);
            
            pr.close();
            arq.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String lerArquivo(String caminho){
        String txt="";
        FileInputStream arq;
        try {
            arq = new FileInputStream(caminho);
            InputStreamReader input = new InputStreamReader(arq, "UTF-8");
            BufferedReader br = new BufferedReader(input);
            
            
            String linha;
            do{
                linha = br.readLine();
                if (linha!=null){
                    txt+=linha+"\n";
                }
            }while(linha!=null);
            
            br.close();
            input.close();
            arq.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return txt;
    }
    
    /**
     * lê até uma certa linha no arquivo, não o arquivo todo
     * @param caminho
     * @param limite
     * @return 
     */
    public static String lerArquivo(String caminho, int limite){
        String txt="";
        FileInputStream arq;
        int cont=1;
        try {
            arq = new FileInputStream(caminho);
            InputStreamReader input = new InputStreamReader(arq, "UTF-8");
            BufferedReader br = new BufferedReader(input);
            
            
            String linha;
            do{
                linha = br.readLine();
                if (linha!=null){
                    txt+=linha+"\n";
                }
                cont++;
            }while(cont<=limite && linha!=null);
            
            br.close();
            input.close();
            arq.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return txt;
    }
    
    public static String lerArquivoANSI(String caminho){
        String txt="";
        FileInputStream arq;
        try {
            arq = new FileInputStream(caminho);
            InputStreamReader input = new InputStreamReader(arq, "ANSI");
            BufferedReader br = new BufferedReader(input);
            
            
            String linha;
            do{
                linha = br.readLine();
                if (linha!=null){
                    txt+=linha+"\n";
                }
            }while(linha!=null);
            
            br.close();
            input.close();
            arq.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LerESalva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return txt;
    }
}
