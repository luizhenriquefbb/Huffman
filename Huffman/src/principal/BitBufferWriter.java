package principal;

import java.util.ArrayList;
import java.util.List;

public class BitBufferWriter {

	private List<byte[]> groups = new ArrayList<byte[]>();
	private byte[] data;
	private int ibyte = 0;
	private int ibit = 0;
	
	public BitBufferWriter(int groupSize) {
		data = new byte[groupSize];
	}
	
	public BitBufferWriter() {
		this(256);
	}
	
	/* Salta bytes
	 * Cria novo bloco em caso de overflow */
	public void jump(int n)
	{
		while (n >= data.length)
		{
			groups.add(data);
			data = new byte[data.length];
			n -= data.length;
		}
		
		if (n > 0)
		{
			ibyte += n;
			
			if (ibyte >= data.length)
			{
				jump(data.length);
				ibyte -= data.length;
			}
		}
	}
	
	public void write(int zero, boolean one)
	{
		if (zero >= 8)
			jump(zero / 8);
		
		ibit += zero % 8;
		
		if (ibit >= 8)
		{
			jump(1);
			ibit %= 8;			
		}

		if (one)
		{
			data[ibyte] |= BitBuffer.bits[ibit];
			ibit = (ibit + 1) % 8;
			
			// Se 0: passou para o pr�ximo byte
			if (ibit == 0) jump(1);
		}
	}
	
	public boolean hasData() {
		return groups.size() > 0 || ibyte > 0 || ibit > 0;
	}
	
	/* Retorna cada grupo de byte */
	public byte[] pullData()
	{
		if (groups.size() > 0)
			return groups.remove(0);
		
		if (ibyte > 0 || ibit > 0)
			return data;
		
		return new byte[0];
	}
	
	/* Retorna todos os bytes escritos */
	public byte[] getData()
	{
		byte[] all = new byte[groups.size() * data.length + ibyte + (ibit > 0 ? 1 : 0)];
		
		for (int i = 0; i < groups.size(); i++)
			System.arraycopy(groups.get(i), 0, all, i * data.length, data.length);
		
		if (ibyte > 0 || ibit > 0)
			System.arraycopy(data, 0, all, groups.size() * data.length, ibyte + (ibit > 0 ? 1 : 0));
		
		return all;
	}
	
	public int countBitsLeft() {
		return (data.length - ibyte) * 8 - ibit; 
	}
	
	public int countLastByteBitsLeft() {
		return 8 - ibit;
	}
	
	public void printCurrentBlock() {
		BitBuffer.printBits(data);
	}
	
}
