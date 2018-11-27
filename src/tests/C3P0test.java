package tests;

import java.util.List;

import com.gz.efood.model.beans.Category;
import com.gz.efood.model.beans.Item;
import com.gz.efood.model.dao.CategoryDAO;
import com.gz.efood.model.dao.ItemDAO;

public class C3P0test
{

	public static void main(String[] args)
	{
		
		ItemDAO dao = new ItemDAO();
		//List<Item> ls = dao.getAllItem();
		List<Item> ls = dao.getItemLike("%Honey%");
		for(Item item : ls) {
			System.out.println(item.toString());
		}
		
		
		//System.out.println("item = " + dao.getByItemNumber("0905A984") );
		/*
		CategoryDAO dao = new CategoryDAO();
		List<Category> ls = dao.getAll();
		//List<Item> ls = dao.getByCatId(3);
		for(Category item : ls) {
			System.out.println(item.toString());
		}
		*/

	}

}
