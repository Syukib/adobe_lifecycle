package com.crawler.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class CrawlerUtils {
//Get a HtmlPage
	public static HtmlPage getPage (String url){
		/* turn off annoying htmlunit warnings */
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
				
		HtmlPage page = null;
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setUseInsecureSSL(true);
		wc.getOptions().setSSLInsecureProtocol("TLSv1.2");
		wc.getOptions().setJavaScriptEnabled(false); // ����JS��������Ĭ��Ϊtrue
		wc.getOptions().setCssEnabled(false); // ����css֧��
		wc.getOptions().setThrowExceptionOnScriptError(false); // js���д���ʱ���Ƿ��׳��쳣
		wc.getOptions().setTimeout(100000); // �������ӳ�ʱʱ�� ��������10S�����Ϊ0���������ڵȴ�
		wc.getOptions().setDoNotTrackEnabled(false);
		wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wc.getOptions().setActiveXNative(false);
		try {
			page = wc.getPage(url);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

//Get all urls from a specifc HtmlPage	
	public static ArrayList getUrls (HtmlPage page){
		ArrayList<String> URLlist = new ArrayList<String>();
		DomNodeList<DomElement> links = page.getElementsByTagName("a");
		for (DomElement link : links) {
			String urls = link.getAttribute("href");
			URLlist.add(urls);	
		}
		
		return URLlist;
	}

//Remove duplicates from a URL list	
	public static ArrayList removeDuplicate(ArrayList list) {  
		   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {  
		     for ( int j = list.size() - 1 ; j > i; j -- ) {  
		       if (list.get(j).equals(list.get(i))) {  
		         list.remove(j);  
		       }   
		      }   
		    }   
		    
		      Iterator<String> it = list.iterator();
		      while (it.hasNext()) {
					String url = it.next();
		      }	   
		    return list;
		}
	
//Generate Urls	
	public static ArrayList generateUrls (int i, int j,String basicurl1,String basicurl2, String basicurl3){
		ArrayList<String> URLlist = new ArrayList<String>();
		String url = null;
		for (int k = i; k<=j;k++){
			url = basicurl1 + k + basicurl2 + k + basicurl3;
			URLlist.add(url);
		}
		return URLlist;
	}
	
//Download a file from Internet
	//fileSavingpath example: "C:\\CrawlerTesting\\price.pdf"
	//resourceUrl example: "http://www8.garmin.com/marine/brochures/2015-price-list.pdf"
	public static void downloadFile (String fileSavingpath, String resourceUrl){
		try {
			URL url = new URL(resourceUrl);
			URLConnection urlconnection = url.openConnection();
			InputStream inputStream = urlconnection.getInputStream();
			OutputStream outputStream = new FileOutputStream (fileSavingpath);
			
			byte[] buffer=new byte[1024*30];
			int len=-1;
			while((len=inputStream.read(buffer))>0){
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
			inputStream.close();
			
			System.out.println("Download Done!");
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

//Get a Url list from an external txt file
	public static ArrayList getUrlsFromTxt (){
		ArrayList<String> URLlist = new ArrayList<String>(); 
		InputStream urls =  CrawlerUtils.class.getClassLoader().getResourceAsStream("urls.txt");
		BufferedReader br4url = new BufferedReader (new InputStreamReader(urls));
		try {
			for (String s = null; (s = br4url.readLine())!=null;){
				URLlist.add(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (null!=br4url){
				try {
					br4url.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return URLlist;
	}
	
	public static void writeContent(String fileName, String content) {
		// ȷ���ļ�·��
		File dir = new File("C:/", "CrawlerTesting");
		boolean path = false;
		path = dir.exists();
		if (path) {
			System.out.println("��Ŀ¼�Ѿ�����");
		} else {
			dir.mkdir();// ����test�ļ���
		}
		// ȷ���ļ�����
		String aviewAS = fileName;
		File file = new File(dir, aviewAS);

		try {
			FileWriter filewriter = new FileWriter(file, true);
			boolean canw = false;
			canw = file.canWrite();
			if (!canw) {
				System.out.println("�ļ�������д��");
				return;
			} else {
				// �������FileWriter�������Ƚ����ݴ洢����������߶�д�ٶ�
				BufferedWriter bufferwriter = new BufferedWriter(filewriter);
				bufferwriter.write(content);
				bufferwriter.newLine();
				System.out.println("Writing Done");
				bufferwriter.flush();// ˢ����
				bufferwriter.close();// �ر���
				filewriter.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ����FileWriter�����
	}
	
}