package principal;

import java.util.ArrayList;
import java.util.List;

public class BitBufferReader {
	
	private List<byte[]> groups = new ArrayList<byte[]>();
	private byte[] data;
	private int ibyte = 0;
	private int ibit = 0;
	private int empty = 0;
	
	public BitBufferReader() { }
	
	public BitBufferReader setEmptyLength(int length) {
		this.empty = length;
		return this;
	}
	
	public BitBufferReader insertBlock(byte[] block)
	{
		if (data != null)
			groups.add(data);
		
		data = block;
		return this;
	}
	
	public boolean hasData() {
		return countBlockBitsLeft() > empty || countBlocksLeft() > 0;
	}
	
	public int read()
	{
		int zero = 0;
		
		while (true)
		{
			if (ibit == 8)
			{
				ibyte++;
				ibit = 0;
			}
			
			while (ibyte < data.length)
			{
				if (data[ibyte] == 0)
					zero += 8 - ibit;
				
				else while (ibit < 8)
					if ((data[ibyte] & BitBuffer.bits[ibit++]) == 0)
						zero++;
					else
						return zero;
				
				ibit = 0;
				ibyte++;
			}
			
			if (groups.size() > 0)
			{
				data = groups.remove(0);
				ibyte = 0;
			}
			else break;
		}
		
		return zero - empty;
	}
	
	public int countBlocksLeft() {
		return groups.size();
	}
	
	public int countBlockBitsLeft() {
		return (data.length - ibyte) * 8 - ibit; 
	}
	
}
