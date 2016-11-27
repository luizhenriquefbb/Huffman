package Arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  abrir e salvar um objeto
 */
public class IO_object {
    /**salva um objeto*/
    public static void salvar(String caminho, Object objeto){
        try{
            FileOutputStream outStream = new FileOutputStream(caminho);
            ObjectOutputStream obOutStream = new ObjectOutputStream(outStream);
            
            obOutStream.writeObject(objeto);
            obOutStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO_object.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO_object.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**carrega um objeto salvo
     * OBS: TEM QUE FAZER UM CAST
     * @param caminho
     * @param objeto
     * @return 
     */
    public static Object carregar(String caminho){
        File f = new File(caminho);
        Object retorno=null;
         try{
            FileInputStream inStream = new FileInputStream(f.getAbsolutePath());
            ObjectInputStream obInStream = new ObjectInputStream(inStream);
            
            retorno =  obInStream.readObject();
            obInStream.close();
            return retorno;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO_object.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IO_object.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
         return retorno;
    }
}
