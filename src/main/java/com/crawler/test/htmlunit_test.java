package com.crawler.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class htmlunit_test {
	//for github actions testing
	private static Path lifecycle_result_data = Paths.get("lifecycle_result_data.csv");
	
	public static void main(String[] args) throws IOException {
        String url = "https://helpx.adobe.com/support/programs/eol-matrix.html";
		HtmlPage page = CrawlerUtils.getPage(url);
		List<HtmlTable> tables = (List<HtmlTable>) page.getByXPath("//table");
		
        if (Files.exists(lifecycle_result_data)) {
        	Files.delete (lifecycle_result_data);
            Files.createFile(lifecycle_result_data);
        }
		
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "Publisher".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "Product".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "Version".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "Build".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "End of core support".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "End of extended support".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
        Files.write(lifecycle_result_data, "\n".getBytes(), StandardOpenOption.APPEND);
        
		for (HtmlTable table : tables) {
			int rowCount = table.getRowCount();
			for (int r = 1; r < rowCount; r++) {
				int c = 0;
				String product = table.getCellAt(r, c++).asText().replaceAll("(?i) *Please see.*", "").trim().replaceAll("\r|\n", "");
				String version = table.getCellAt(r, c++).asText().trim().replaceAll("\r|\n", "");
				String sp = table.getCellAt(r, c++).asText().trim().replaceAll("\r|\n", "");
				String gaDate = table.getCellAt(r, c++).asText().trim().replaceAll("\r|\n", "");
				String endDate = table.getCellAt(r, c++).asText().trim().replaceAll("\r|\n", "");
				System.out.println(product + " | " + version + " | " + sp);
				
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "Publisher".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, product.getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, version.getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, sp.getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
		        Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
				
				for (int i = 1; i <= 2; i++) {
					switch (i) {
					case 1:
                    System.out.println("End of core support: " + endDate);
                    Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
                    Files.write(lifecycle_result_data, endDate.getBytes(), StandardOpenOption.APPEND);
                    Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
                    Files.write(lifecycle_result_data, ",".getBytes(), StandardOpenOption.APPEND);
                    break;
					case 2:
					endDate = table.getCellAt(r, c++).asText().trim().replaceAll("\r|\n", "");
					System.out.println("End of extended support : " + endDate);
                    Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
                    Files.write(lifecycle_result_data, endDate.getBytes(), StandardOpenOption.APPEND);
                    Files.write(lifecycle_result_data, "\"".getBytes(), StandardOpenOption.APPEND);
                    Files.write(lifecycle_result_data, "\n".getBytes(), StandardOpenOption.APPEND);
					break;
					default:
					break;
					}
					
				}
				System.out.println("______________________________________________");
			}
		}

	}
	
}
