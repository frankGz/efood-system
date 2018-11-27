package com.gz.efood.model.Utlis;

public class Tools
{
	/**
	 * Given a string a hex digits, convert it to an array of bytes.
	 **/
	public static byte[] hexToBytes(String string)
	{
		assert (string != null);
		if (string.length() % 2 != 0)
			string += "0";
		int half = string.length() / 2;
		byte[] buffer = new byte[half];
		for (int i = 0; i < half; i++)
		{
			String pair = string.substring(2 * i, 2 * i + 2);
			buffer[i] = (byte) Integer.parseInt(pair, 16);
		}
		return buffer;
	}
	
	
    /**
     * Round the given double value to
     * two decimal points, for money.
     *
     * @param  x double value
     * @return   rounded double value
     */
    public static double roundOff(double x) {
        long val = Math.round(x*100); // cents
        return val/100.0;
    }
	

}
