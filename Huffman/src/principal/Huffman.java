package principal;


import Arquivo.IO_object;
import java.io.Serializable;
import java.util.PriorityQueue;

class Huffman {
    static String texto; //texto original
    static String binario;
    
    static int[] lerArquivo() {
        int[] caracteres = new int[16384]; //Java reconhece 16384 carcteres diferentes, cada um tem uma frequencia

        //ler o arquivo e colocar numa String (String a ser comprimida)
        texto = Arquivo.LerESalva.lerArquivo("arquivos\\"+"a.txt");

        //cada caractere é uma posiçao no array
        //varre caractere a caractere e a cada ocorrencia, soma no array
        for (int i = 0; i < texto.length(); i++) {
            caracteres[(int) texto.charAt(i)]++;
        }

        return caracteres;
    }

    public static void comprimir() {
        //construir a arvore
        HuffmanArvore arvore = HuffmanArvore.construirArvore(lerArquivo());
        
        System.out.println(arvore); //imprime a arvore (tabela de frequencia e codigo)
        
        //agr que ja temos a arvore (tabela, podemos construir o binario)
        //construir binario
        binario = texto;
        for (int i = 0;i<binario.length();){ //caractere a caractere
            char c = binario.charAt(i);
            int codigo = arvore.busca(binario.charAt(i));
            String codigoString = arvore.codigoToString(codigo); //relacao codigo-binario
            binario = binario.replaceFirst(""+c, ""+codigoString); //substitui
            i+=codigoString.length(); //anda o numero de caracteres inseridos
        }
        System.out.println("\n\n"+binario);
        Arquivo.LerESalva.salvarArquivo("arquivos\\compactado.txt", binario);
        
        /* note que ao salvar nao esta compactado pois esta salvando String, nao binario.
        aqui cada numero usa 8 bits e nao apenas 1*/
        
    }

    public static void descomprimir() {
        
        // Ler arvore
        HuffmanArvore arvore = (HuffmanArvore) IO_object.carregar("arquivos\\arvore.temp");
        int altura = arvore.getAltura();

        //ler String binaria
        String binaria = Arquivo.LerESalva.lerArquivo("arquivos\\compactado.txt");
        String original = "";
        
        //o caractere é definido pela quatidade de zeros que foi encontrado em sequencia
        int zeros =0;
        for (int i=0;i<binaria.length();i++){
            if(zeros == altura-1){ //CASO ESPECIAL: ultimo caractere nao tem 1
                original+=arvore.busca(zeros);
                zeros=0;
                i--;
                continue;
            }
            
            if(binaria.charAt(i)=='1' ){ //outros caracteres terminam com 1
                original+=arvore.busca(zeros);
                zeros=0;
                continue;
            }
            zeros++;
            
        }

        System.out.println(original);
        Arquivo.LerESalva.salvarArquivo("arquivos\\descompactado.txt", original);

        
    }

    /**
     * subclasse
     * @author LuizHenrique
     */
    public static class HuffmanArvore implements Serializable, Comparable<HuffmanArvore> {

        private char ch;
        private HuffmanArvore esq, dir;
        private transient int freq;
        private transient int codigo;
        private String binario;

        /**
         * construtor
         */
        private HuffmanArvore(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        private HuffmanArvore setEsq(HuffmanArvore esq) {
            this.esq = esq;
            return this;
        }

        private HuffmanArvore setDir(HuffmanArvore dir) {
            this.dir = dir;
            return this;
        }

        public int getAltura() {
            HuffmanArvore sub = this;
            int cont = 1;

            /*sabemos que a arvore de huffman eh desbalanceada para a direita,
             entao sabemos que os maiores ramos sao os da direita*/
            while (sub.dir != null) {
                sub = sub.dir;
                cont++;
            }

            return cont;
        }

        /**
         * procurar codigo de pelo caractere
         *
         * @param c
         * @return
         */
        public int busca(char c) {
            HuffmanArvore aux = this;
            while (true) {
                if (aux.esq == null) {
                    return aux.codigo;
                }

                if (aux.esq.ch == c) {
                    return aux.esq.codigo;
                } else {
                    aux = aux.dir;
                }
            }
        }

        /**
         * procurar char pelo codigo
         *
         * @param codigo
         * @return
         */
        public char busca(int codigo) {
            HuffmanArvore aux = this;

            while (codigo-- > 0) {
                if (aux.dir != null) {
                    aux = aux.dir;
                } else {
                    return 0;
                }
            }

            if (aux.esq == null) {
                return aux.ch;
            }

            return aux.esq.ch;
        }

        /**
         * construir uma arvore
         */
        public static HuffmanArvore construirArvore(int[] ocorrencias) {
            //fila de caracter
            PriorityQueue<HuffmanArvore> fila = new PriorityQueue<HuffmanArvore>();
            //TODO: CONSTRUIR FILA

            for (int i = 0; i < ocorrencias.length; i++) {
                if (ocorrencias[i] != 0) //adiciona cada caracter na fila (esta ordenado)
                {
                    fila.add(new HuffmanArvore((char) i, ocorrencias[i]));
                }
            }

            while (fila.size() > 1) {
                //concatena de dois em dois os caracteres menos frequentes
                /* remove os dois menos frequentes e une em uma sub-árvore (a '\0')
                 * adiciona esse '\0' a fila
                 * repete, ate sobrar apenas um elemento na fila (vira uma arvore só)
                 */

                HuffmanArvore dir = fila.poll();
                HuffmanArvore esq = fila.poll();
                HuffmanArvore novo = new HuffmanArvore('\0', 0);
                novo.setDir(dir);
                novo.setEsq(esq);
                fila.offer(novo);

            }

            HuffmanArvore arvore = fila.poll();
            arvore.gerarBinario(arvore, 0);
            
            //salvar arvore
            IO_object.salvar("arquivos\\arvore.temp", arvore);
            return arvore;
        }

        /** metodo que checa se o elemento é uma folha da arvore*/
        public boolean isFolha() {
            return esq == null && dir == null;
        }

        public int getFreq() {
            return freq;
        }
        
        
        /**usado para ordenar a fila*/
        @Override
        public int compareTo(HuffmanArvore t) {
            return freq - t.freq;
        }

        /**
         * setar o codigo recursivamente: codigo == qnt de zeros
         */
        private void gerarBinario(HuffmanArvore no, int zeros) {
            if (no.isFolha()) {
                no.codigo = zeros;
            } else {
                no.esq.codigo = zeros;
                gerarBinario(no.dir, zeros + 1);
            }
        }

        /** imprime a tabela: frequencia e codigo*/
        @Override
        public String toString() {
            String s = "";
            HuffmanArvore aux = this;
            s += ("CHAR\tFREQUENCIA\tCODIGO\n");
            while (aux.dir != null) {
                s += "\n" + aux.esq.ch + "\t" + aux.esq.freq + "\t\t" + codigoToString(aux.esq.codigo);
                aux = aux.dir;
            }
            s += "\n" + aux.ch + "\t" + aux.freq + "\t\t" + codigoToString(aux.codigo);
            return s;
        }

        /**
         * trasnforma o codigo de int em binario
         */
        private String codigoToString(int codigo) {
            String retorno = "";
            int altura = getAltura();

            //ex: se codigo = 2 => 001
            //codigo int equivale a qnt de zeros
            for (int i = 0; i < codigo; i++) {
                retorno += "0";
            }

            //se a folha nao for a ultima posicao, termina com 1
            //caractere menos frequnete nao termina com 1
            if (codigo < altura - 1) {
                retorno += "1";
            }

            return retorno;
        }

    }

}
