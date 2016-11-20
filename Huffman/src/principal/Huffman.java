package principal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.PriorityQueue;

public abstract class Huffman {
	//Conta ocorrências de cada caracter unicode
	private static int[] proccess(File file)
	{
                //16384 é o primeiro caracter inválido
		int[] occurences = new int[16384];
		
		try {
			FileReader in = new FileReader(file);
			
			int c;
                        //código sucinto da porra! Pires s2 Prato
			while((c = in.read()) != -1)
				occurences[c]++;
			
			in.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return occurences;
	}

	public static void compress(File inFile, File outFile)
	{
                //construir a arvore
		HuffmanTree tree = HuffmanTree.build(proccess(inFile));
		System.out.println(tree); //impreme a arvore

		try {
			FileReader in = new FileReader(inFile);
			BitBufferWriter bbw = new BitBufferWriter(256);
			int height = tree.getHeight(); //calcula a altura da arvore
			
			int c;
			while ((c = in.read()) != -1) //ler a String de texto
			{
				int zero = tree.find((char) c); //buscar o codigo de cada caracter
				bbw.write(zero, zero != height);
			}
			
			in.close();
			
			byte[] data = bbw.getData();
			System.out.println("Bytes: " + data.length);
			
			// Escrevendo no arquivo
			
			if ( ! outFile.exists())
				outFile.createNewFile();

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile));
			out.writeObject(tree);
			out.writeObject(bbw.countLastByteBitsLeft());
			out.writeObject(data);
			out.flush();
			out.close();
		}	
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void decompress(File inFile, File outFile)
	{
		try {
			// Lendo arquivo
			
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(inFile));
			
			HuffmanTree tree = (HuffmanTree) in.readObject();
			int empty = (Integer) in.readObject();
			byte[] data =(byte[]) in.readObject();
			
			in.close();
			
			// Escrevendo arquivo
			
			BitBufferReader bbr = new BitBufferReader();
			bbr.insertBlock(data);
			bbr.setEmptyLength(empty);
			
			FileWriter out = new FileWriter(outFile);
			
			while (bbr.hasData())
				out.append(tree.find(bbr.read()));
			
			out.flush();
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static class HuffmanTree implements Serializable, Comparable<HuffmanTree> {

		private static final long serialVersionUID = 2710903373720336825L;
		
		private char ch;
		private HuffmanTree left, right;
		private transient int freq;
		private transient int code;

		private HuffmanTree(char ch, int freq) {
			this.ch = ch;
			this.freq = freq;
		}
		
		private HuffmanTree setLeft(HuffmanTree left) {
			this.left = left;
			return this;
		}
		
		private HuffmanTree setRight(HuffmanTree right) {
			this.right = right;
			return this;
		}

		@Override
		public int compareTo(HuffmanTree tree) {
			return freq - tree.freq;
		}
		
		public int getHeight()
		{
			HuffmanTree subTree = this;
			int counter = 1;
			
			while (subTree.right != null)
			{
				subTree = subTree.right;
				counter++;
			}
			
			return counter;
		}
		
		public int find(char c)
		{
			HuffmanTree subTree = this;
			while (true)
			{				
				if (subTree.left == null)
					return subTree.code;
				
				if (subTree.left.ch == c)
					return subTree.left.code;
				
				else subTree = subTree.right;
			}
		}

		public char find(int code)
		{
			HuffmanTree subTree = this;
			
			while (code-- > 0)
				if (subTree.right != null)
					subTree = subTree.right;
				else
					return 0;
			
			if (subTree.left == null)
				return subTree.ch;
			
			return subTree.left.ch;
		}

		public static HuffmanTree build(int[] occurences)
		{
                        //lista de caracter
			PriorityQueue<HuffmanTree> tree_list = new PriorityQueue<HuffmanTree>();
			
			for (int i = 0; i < occurences.length; i++)
				if (occurences[i] != 0)
                                        //adiciona cada caracter na lista (nao esta ordenado)
					tree_list.add(new HuffmanTree((char) i, occurences[i]));

			while(tree_list.size() > 1)
			{   
                                //concatena de dois em dois os caracteres menos frequentes
				tree_list.offer(
						new HuffmanTree('\0', 0)
							.setRight(tree_list.poll())
							.setLeft(tree_list.poll())
					);
			}
			
			HuffmanTree tree = tree_list.poll();
			tree.genCodes(tree, 0);
			return tree;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}
                /** a cada no recebe um inteiro que sera convertido para binario*/
		private void genCodes(HuffmanTree node, int zeros)
		{
			if (node.isLeaf())
				node.code = zeros;
			else {
				node.left.code = zeros;
				genCodes(node.right, zeros + 1);
			}
		}

		public String toString() {
			String s = "";
			HuffmanTree aux = this;
			System.out.println("CHAR\tFREQUENCY\tCODE");
			while(aux.right != null) {
				s += "\n" + aux.left.ch + "\t" + aux.left.freq + "\t\t" + codeToString(aux.left.code);
				aux = aux.right;
			}
			s += "\n" + aux.ch + "\t" + aux.freq + "\t\t" + codeToString(aux.code);
			return s;
		}
                
                /** trasnforma o codigo inteiro em binario*/
		private String codeToString(int code)
		{
			StringBuilder sb = new StringBuilder();
			int height = getHeight();
			
			for (int i = 0; i < code; i++)
				sb.append('0');
			
			if (code < height - 1)
				sb.append('1');
			
			return sb.toString();
		}
		
	}
	
}
