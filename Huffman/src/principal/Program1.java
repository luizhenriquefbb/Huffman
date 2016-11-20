package principal;

import java.io.File;

/** classe de testes*/
public class Program1 {

	public static void main(String[] args)
	{	
                //entrdas:
                String des = "C:\\Users\\RenataMaria\\Desktop\\";
                args = new String[4];
                //args[0]= "-c";
                String in = args[1] = des+"a";
                String out = args[2] = des+"b";
                args[3] = des + "c";
                
                File inFile = new File(in);
                File outFile = new File(out);
                Huffman.compress(inFile, outFile);
                
                in = args[2];
                out = args[3];
                
                
                inFile = new File(in);
                outFile = new File(out);
                Huffman.decompress(inFile, outFile);

                
                
                
//                    if (args.length == 4)
//                    {
//                            boolean compress = true;
//                            String in = null, out = null;
//                            
//                    
//                            for (String arg : args)
//                            {
//                                   //comprimir
//                                    if (arg.equals("-c"))
//                                            compress = true;
//                                    //descomprimir
//                                    else if (arg.equals("-d"))
//                                            compress = false;
//                                    //local de entrada
//                                    else if (in == null)
//                                            in = arg;
//                                    //local de saida
//                                    else if (out == null)
//                                            out = arg;
//
//                                    else
//                                            out = null;
//                            }
//
//                            if (out != null)
//                            {
//                                    File inFile = new File(in);
//                                    File outFile = new File(out);
//
//                                    if (compress)
//                                            Huffman.compress(inFile, outFile);
//                                    else
//                                            Huffman.decompress(inFile, outFile);
//
//                                    return;
//                            }
//                    }
            }
}
