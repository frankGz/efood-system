package com.gz.efood.model.Utlis;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.gz.efood.model.JAXBbeans.OrderType;

public class XMLUtils
{
	/**
	 * @param filename
	 * @return
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	public static OrderType FileToJAXBbeans(String filename) throws JAXBException, FileNotFoundException {
		System.out.println(filename);
		String path = "/home/user/POxmls/";
		JAXBContext jaxbContext = JAXBContext.newInstance(OrderType.class);
		File f = new File(path+filename);
		InputStream stream = new FileInputStream(f);
		OrderType orderType = ((JAXBElement<OrderType>) jaxbContext.createUnmarshaller().unmarshal(stream)).getValue();
		
		
		return orderType;
	}
	
	

}
