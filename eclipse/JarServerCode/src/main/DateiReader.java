package main;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



public class DateiReader {

	
	public static void illo() {
		System.out.println();
		System.out.println("MAIN:          !!!Programm started!!!");
		System.out.println();
		
		
		
		
		
		
		
		
		
		
		String path = System.getProperty("user.home");
		Path p = Paths.get(path);
		Path datei = p.resolve("Programmzeugs\\Data.txt");
		
		
		
		delete(datei);
		create(datei);
		write(datei, "Die magische Zahl ist: "+String.valueOf(7+10-30+100-67+346+ 57-234));
		
		
		
		
		
		read(datei);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println();
		System.out.println("MAIN:          !!!Programm stopped!!!");
		System.out.println();
	}
	
	
	public static void create(Path datei) {
		try(BufferedWriter bw = Files.newBufferedWriter(datei, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
			if(!Files.exists(datei)) {
			bw.close();
			System.out.println("Datei wurde erstellt");
			}else {
			}
		} catch (Exception e) {
		}
		
	}
	
	public static void run(String path) {
		File f = new File(path);
		Path p = Paths.get(path);
		try {
			
		
			if(Files.exists(p)) {
				Desktop.getDesktop().open(f);
			}else {
				System.out.println("Error: Data not found!");
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void search(String location, String type) {
		
		String home = System.getProperty("user.home");
		Path phome = Paths.get(location);
		try(DirectoryStream<Path> files = Files.newDirectoryStream(phome)){
			for (Path found : files) {
				if(Files.isRegularFile(found) && found.toString().toLowerCase().endsWith(type)) {
					System.out.println("Found:   >>   "+found.toString());
					
				}
			}
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
public static void list(String location) {
		
		String home = System.getProperty("user.home");
		Path phome = Paths.get(location);
		try(DirectoryStream<Path> files = Files.newDirectoryStream(phome)){
			for (Path found : files) {
				
				System.out.println("Found:   >>   "+found.toString());
					
				
			}
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public static void read(Path datei) {
		
		if(Files.exists(datei)) {
		
		try(BufferedReader br = Files.newBufferedReader(datei)) {
			
			String line;
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
			
			
		} catch (Exception e) {
		}
		}else {
			System.out.println("Error: Data not found!");
		}
	}
	
public static boolean FilecontainsString(Path datei, String search) {
		
		if(Files.exists(datei)) {
		
		try(BufferedReader br = Files.newBufferedReader(datei)) {
			
			String line;
			while((line = br.readLine()) != null) {
				if(line.equalsIgnoreCase(search))
					return true;
			}
			
			
		} catch (Exception e) {
		}
		}else {
			System.out.println("Error: Data not found!");
		}
		return false;
	}
	
	
	public static String readfirst(Path datei) {
		

		if(Files.exists(datei)) {
		
		try(BufferedReader br = Files.newBufferedReader(datei)) {
			
			String line;
			while((line = br.readLine()) != null) {
				return line;
			}
			
			
		} catch (Exception e) {
		}
		
		}else {
			System.out.println("Error: Data not found!");
		}
		
		
		return null;
		
		
		
		
	}
	
	public static void write(Path datei, String text) {
		
		if(Files.exists(datei)) {
			
		
		
		
		try(BufferedWriter bw = Files.newBufferedWriter(datei, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
			
			bw.write(text);
			System.out.println("");
			System.out.println("Der text< " + text + " >wurde der Datei Hinzugefügt!");
			System.out.println("");
			
	
			
		} catch (Exception e) {
		}
		
		}else {
			System.out.println("Error: Data not found!");
		}
		
		
	}
	
	
	public static void info(Path datei) {
		try {
			System.out.println("Searching for Data at< " + datei + " >");
			if(Files.exists(datei)) {
				System.out.println("Data Found!");
			
			
				System.out.println("");
				System.out.println("Name " + datei.toAbsolutePath());
				System.out.println("Größe: " + Files.size(datei));
				System.out.println("Besitzer: " + Files.getOwner(datei).getName());
			}else {
				
				System.out.println("Error: Data not found!");
			}
		} catch (Exception e) {
		}
		
		
		
	}
	
	
	public static void delete(Path datei) {
		
		
		
		
		
		if(Files.exists(datei)) {
			try {

		
				Files.delete(datei);
				
				System.out.println("Datei wurde gelöscht!");
				
			} catch (Exception e) {
			}	
			
		}else {
			System.out.println("Error: Data not found!");
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
