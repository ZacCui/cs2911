package game;
import javax.swing.ImageIcon;

public class Pictures{
	private static String PATH = "src/imgs/";
	private static int PICNUMS = 10;
	private static Pictures singlePic = null;
	private static ImageIcon[] imgs;
	
	private Pictures(){
		imgs = new ImageIcon[PICNUMS];
		for(int i = 0;i<PICNUMS;i++){
			imgs[i] = new ImageIcon(PATH + i + ".png",Integer.toString(i));
		}
		singlePic = this;
	}
	
	public static Pictures getInstance() {
		if(singlePic == null){
			new Pictures();
		}
		return singlePic;
	}
	
	public ImageIcon getImgByIndex(int index) {
		return imgs[index];
	}
}
