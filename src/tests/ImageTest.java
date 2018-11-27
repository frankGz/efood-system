package tests;

import com.gz.efood.model.dao.CategoryDAO;

public class ImageTest
{
	public static void main(String[] args)
	{
		CategoryDAO dao = new CategoryDAO();
		byte[] p = dao.getPicture(3);
		System.out.println(p);
		System.out.println(new String(p)); //png
	}
}
