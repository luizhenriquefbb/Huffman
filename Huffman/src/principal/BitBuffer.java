package principal;

public abstract class BitBuffer {

	public static final int[] bits = {
			0x80, 0x40, 0x20, 0x10, 0x8, 0x4, 0x2, 0x1
		};
	
	public static void printBits(byte[] block)
	{
		for (int i = 0; i < block.length; i++)
		{
			for (int j = 0; j < 8; j++)
				System.out.print(((block[i] & bits[j]) > 0 ? "1" : "0"));
			
			System.out.println();			
		}
	}
	
}
