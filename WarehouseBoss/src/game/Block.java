package game;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Block extends Rectangle {
	private ImageIcon icon;
	private int type;
	
	// from "BlockType"
	public static final Integer SPACE = 0;
	public static final Integer BORDER = 1;
	public static final Integer ROAD = 2;
	public static final Integer BOX = 3;
	public static final Integer TERMINAL = 4;
	public static final Integer MEN_DOWN = 5;
	public static final Integer MEN_LEFT = 6;
	public static final Integer MEN_RIGHT = 7;
	public static final Integer MEN_UP = 8;
	public static final Integer BOX_DONE = 9;
	
	// changed and don't need to use BlockType.java anymore
	public Block(Point point, Dimension dimension ,ImageIcon icon) {
		super(point,dimension);
		this.icon = icon;
		type = Integer.parseInt(icon.getDescription());
		if(isMen(type) || BOX == type) {
			type = ROAD;
		}else if(BOX_DONE == type) {
			type = TERMINAL;
		}
	}
	
	public int getBlockType() {
		return type;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public int getIconType() {
		return Integer.parseInt(icon.getDescription());
	}
	
	// from "BlockType"
	public static boolean isMen(int type) {
		if(type == MEN_DOWN || type == MEN_LEFT || type == MEN_RIGHT || type == MEN_UP)
			return true;
		return false;
	}
	
	public static boolean isBox(int type) {
		if(type == BOX || type == BOX_DONE)
			return true;
		return false;
	}
	
	public static boolean isRoad(int type) {
		if(type == ROAD || type == TERMINAL)
			return true;
		return false;
	}
}

