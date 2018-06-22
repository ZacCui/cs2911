 package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map{
	private static String path = "src/maps/";
	private List<String> mapData;
	
	public Map(String mapName) {
		BufferedReader stream;
		try {
		    stream = new BufferedReader(new FileReader(new File(path + mapName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		String s = null;
		mapData = new ArrayList<>();
		try {
			while((s = stream.readLine()) != null){
				mapData.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public List<String> getMapData() {
		return mapData;
	}
	
	//return new map from blocks, handle key stroke
	public static List<String> creatMapData(Block[] blocks,int row,int column){
		List<String> res = new ArrayList<>();
		for(int i = 0;i<row;i++){
			String data="";
			for(int j = 0;j<column;j++){
				data += blocks[i*column + j].getIconType();
			}
			res.add(data);
		}
		return res; 
	}
	
	//return all tasks
	public static List<String> getAllMapName() {
		 File[] files = new File(path).listFiles();  
		 List<String> res = new ArrayList<>();
		 for (File file : files) {
			res.add(file.getName());
		 }
		 return res;
	}
}
