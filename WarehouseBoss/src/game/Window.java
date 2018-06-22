package game;
import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

class Window extends JFrame implements KeyListener,ActionListener{
	private List<String> initmapData;
	private List<String> ResetData;
	private List<String> mapData;
	private Pictures imgSrc;
	private int column;
	private int row;
	private Block[] blocks;
	private Vector<Block> terminals;
	private Block men;
	private Dimension picSize;
	private BGM bgm;
	private int level;
	private List<JButton> btns;
	private Player player1;
	private Player player2;
	private Stack<List<String>> backStack1;
	private Stack<List<String>> backStack2;
	
	class Player extends JPanel{
		private Block[] blocks;
		private Block men;
		private Canva canva;
		
		private Vector<Block> terminals;
		Player(){
			init(initmapData);
			setPreferredSize(new Dimension(800,524));
			setLayout(new BorderLayout());
			//canva
			canva = new Canva();
			canva.addKeyListener(Window.this);
			add(canva);
		}	
		
		public void init(List<String> md) {
			terminals = new Vector<>();
			mapData = md;
			imgSrc  = Pictures.getInstance();
			column = mapData.get(0).length();
			row	   = mapData.size();
			blocks  = new Block[column*row];
			picSize = new Dimension(imgSrc.getImgByIndex(0).getIconWidth(), imgSrc.getImgByIndex(0).getIconHeight());
			
			//initialize all blocks
			for(int i = 0;i<row;i++){
				for(int j = 0;j<column;j++){
					ImageIcon imageIcon = imgSrc.getImgByIndex(mapData.get(i).charAt(j)-'0');
					int type = Integer.parseInt(imageIcon.getDescription());
					int pos = i*column+j;
					blocks[pos] = new Block(new Point(j*picSize.width, i*picSize.height), picSize,imageIcon);
					if(Block.isMen(type)) men = blocks[pos];
					if(Block.TERMINAL==type)   terminals.add(blocks[pos]);
				}
			}
			setSize(column*picSize.width + 86, row*picSize.height + 44);
		}
		
		private boolean isWin(){
			for (Block block : terminals) {
				if(block.getIconType() != Block.BOX_DONE)
					return false;
			}
			return true;
		}
		
		private class Canva extends Canvas{
			@Override
			public void paint(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				for (Block block : blocks) {
					g2d.draw(block);
					g2d.drawImage(block.getIcon().getImage(), block.getLocation().x, block.getLocation().y,this);
				}
			}	
		}
		
		private boolean blockMove(Point point,int keyCode){
			int x = point.x, y = point.y;
			int currPos = (y/picSize.height)*column + (x/picSize.width);
			int men_type = Block.MEN_DOWN;
			switch (keyCode) {
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					x+=picSize.width;
					men_type = Block.MEN_RIGHT;
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:	
					y+=picSize.height;
					men_type = Block.MEN_DOWN;
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					x-=picSize.width;
					men_type = Block.MEN_LEFT;
					break;
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:	
					y-=picSize.height;
					men_type = Block.MEN_UP;
					break;
				default:	
					break;
			}
			int nextPos = (y/picSize.height)*column + (x/picSize.width);
			System.out.println("currPos:"+ currPos+"  "+"newtPos:" +nextPos);
			int curr_box_type =blocks[currPos].getIconType(),
				next_box_type =blocks[nextPos].getIconType();
			//reach border
			if(Block.BORDER==next_box_type) 
				return false;
			//two box side by side
			if(Block.isBox(curr_box_type) && Block.isBox(next_box_type))
				return false;
			//box move
			if(Block.isBox(curr_box_type) && Block.isRoad(next_box_type)){
				blocks[nextPos].setIcon(imgSrc.getImgByIndex(next_box_type == Block.TERMINAL?Block.BOX_DONE:Block.BOX));//goal position or not
				blocks[currPos].setIcon(imgSrc.getImgByIndex(blocks[currPos].getBlockType()));
				return true;
			}
			//next is box
	        if(Block.isBox(next_box_type)){
	        	if(!blockMove(new Point(x, y), keyCode))//box can be push or not
	        		return false;
	        }	
	        next_box_type =blocks[nextPos].getIconType();
			if(Block.isRoad(next_box_type)) {
				System.out.println("check road");
				blocks[nextPos].setIcon(imgSrc.getImgByIndex(men_type));
				blocks[currPos].setIcon(imgSrc.getImgByIndex(blocks[currPos].getBlockType()));
				men = blocks[nextPos];
				return true;
			}
			return false;
		}

	}
	
	public Window(String mapName) {
		backStack1 = new Stack<List<String>>();
		backStack2 = new Stack<List<String>>();
		initmapData = new Map(mapName).getMapData();
		backStack1.push(initmapData);
		backStack2.push(initmapData);
		player1 = null;
		player2 = null;
		HomePage();
		initBGM();
	}
	

	public void GameStart(int numPlayer){
		setResizable(false);

		setSize(800*numPlayer+86*numPlayer,524);
		setMenu();
		//button
		JButton reset = new JButton("Reset");
		reset.addActionListener(this);
        JButton ret = new JButton("Return"); //return to home page
        ret.addActionListener(this);
		JButton back = new JButton("Back");
		back.addActionListener(this);
		JButton quit = new JButton("Quit");
	    quit.addActionListener((ActionEvent event) -> {
	    	System.exit(0);
	    });
	    // set tool bar
        JPanel bottom = new JPanel(new GridLayout(4, 1, 0, 70));
        bottom.add(back);
        bottom.add(reset);
        bottom.add(ret);
        bottom.add(quit);
        
        JPanel basic = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        basic.add(bottom, gbc);

        setTitle("WarehouseBoss");
        setLocationRelativeTo(null);
       
		//canva
        if(numPlayer==1){
        	player1 = new Player();
    		setLayout(new BorderLayout());//border with no gap between components
    		add(basic,BorderLayout.EAST);//menu on top
    		add(player1,BorderLayout.CENTER);//map in center
        }
        else{
    		//button
    		setBounds(100, 100, 800*numPlayer+86*numPlayer, 524);
    		setLayout(new BorderLayout(0, 0));
    		JPanel contentPane = new JPanel();
    		contentPane.setLayout(new BorderLayout(0, 0));
    		setContentPane(contentPane);
    		JPanel panel = new JPanel();
    		add(panel, BorderLayout.CENTER);
    		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    		player1 = new Player();
    		panel.add(player1);
    		panel.add(basic);
    		player2 = new Player();
        	panel.add(player2);    		
    		JButton reset2 = new JButton("Reset2");
    		reset2.addActionListener(this);
            JButton ret2 = new JButton("Return2"); //return to home page
            ret2.addActionListener(this);
    		JButton back2 = new JButton("Back2");
    		back2.addActionListener(this);
    		JButton quit2 = new JButton("Quit2");
    	    quit2.addActionListener((ActionEvent event) -> {
    	    	System.exit(0);
    	    });
            JPanel bottom2 = new JPanel(new GridLayout(4, 1, 0, 70));
            bottom2.add(back2);
            bottom2.add(reset2);
            bottom2.add(ret2);
            bottom2.add(quit2);
            JPanel basic2 = new JPanel(new GridBagLayout());
    	    GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.anchor = GridBagConstraints.CENTER;
            basic2.add(bottom2, gbc2);
            panel.add(basic2);

        }
			setDefaultCloseOperation(EXIT_ON_CLOSE);//system exit
			setVisible(true);
			this.ResetData = this.mapData;
	}
	
	//keyboard listening
	@SuppressWarnings("deprecation")
	@Override
	public void keyPressed(KeyEvent e) {
		Point point=null;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_LEFT||
				e.getKeyCode()==KeyEvent.VK_UP){
			point = player1.men.getLocation();
			if(player1.blockMove(point, e.getKeyCode())){
				System.out.println(player1.blocks);
				backStack1.push(Map.creatMapData(player1.blocks, row, column));
				player1.canva.repaint();
				setVisible(true);
			}
		}
		if(player2!=null && (e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_S||
				e.getKeyCode()==KeyEvent.VK_D)){
			point = player2.men.getLocation();
			if(player2.blockMove(point, e.getKeyCode())){
				backStack2.push(Map.creatMapData(player2.blocks, row, column));
				player2.canva.repaint();
				setVisible(true);
			}
		}
		if(player1.isWin()||(player2!=null && player2.isWin())){
			int result = 0;
			boolean on = bgm.isMUSIC_ON();
			if(on){
				bgm.BGM();
			}
			bgm.winMusic();
			result = JOptionPane.showConfirmDialog(null, "You win!!", "Congratulations!!", JOptionPane.DEFAULT_OPTION, 
					  result, new ImageIcon("src/imgs/10.png"));
			if (result != -1 && on) {
				bgm.BGM();
				if(Map.getAllMapName().size()-1 <= this.level) return;
				this.level += 1;
				backStack1 = new Stack<>();
				String next = Integer.toString(this.level) + ".map";
				player1.init(new Map(next).getMapData()); 
				this.ResetData = new Map(next).getMapData();
				player1.canva.repaint();
				if(player2 != null){
					backStack2 = new Stack<>();
					player2.init(new Map(next).getMapData()); 
					player2.canva.repaint();
				}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}	
	
	//button listening: back or menu
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand()=="Back"){
			System.out.print("back");
			if(backStack1.size()==0)
				return;
			List<String> temp = backStack1.pop();
			player1.init(temp);//stack return top
		}else if(e.getActionCommand()=="Reset"){
			backStack1 = new Stack<>();
			player1.init(this.ResetData);
		}else if(e.getActionCommand()=="Continue"){
			if(ResetData == null) return;
			for(int i=0; i<btns.size();i++){
				this.remove((Component) this.btns.get(i));
			}
			// ==========================================
			//GameStart();
		}else if(e.getActionCommand()=="Back2"){
			if(backStack2.size()==0)
				return;
			List<String> temp = backStack2.pop();
			player2.init(temp);
		}else if(e.getActionCommand()=="Reset2"){
			backStack2 = new Stack<>();
			player2.init(this.ResetData);
		}else if(e.getActionCommand()=="Single Player"){
			for(int i=0; i<btns.size();i++){
				this.remove((Component) this.btns.get(i));
			}
			GameStart(1);
		}else if(e.getActionCommand()=="Two Players"){
			for(int i=0; i<btns.size();i++){
				this.remove((Component) this.btns.get(i));
			}
			GameStart(2);
		}else if(e.getActionCommand()=="Return"){
			setJMenuBar(null);
			HomePage();
		}else if(e.getActionCommand()=="On"){
			if (!bgm.isMUSIC_ON()) bgm.BGM();
			return;
		}else if(e.getActionCommand()=="Off"){
			if (bgm.isMUSIC_ON()) bgm.BGM();
			return;
		}else{//select menu
			backStack1 = new Stack<>();
			setLevel(e.getActionCommand());
			player1.init(new Map(e.getActionCommand()).getMapData());
			this.ResetData = new Map(e.getActionCommand()).getMapData();
			if(player2 != null){
				backStack2 = new Stack<>();
				player2.init(new Map(e.getActionCommand()).getMapData());
			}
		}
		player1.canva.repaint();
		if(player2!=null){
			player2.canva.repaint();
		}
		setVisible(true);
	}	
	
	public void HomePage(){
		setLayout(new BorderLayout());
		setTitle("WarehouseBoss");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setHomePageMan();
        try {
        	setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/imgs/Home.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton single = new JButton("Single Player");
        single.setSize(180, 100);
        single.setLocation(350, 230);
        single.addActionListener(this);
        single.setFont(new Font("Bradley Hand", Font.BOLD, 24));
        JButton two = new JButton("Two Players");
        two.setSize(180, 100);
        two.setLocation(200, 350);
        two.setFont(new Font("Bradley Hand", Font.BOLD, 24));
        setLocationRelativeTo(null);
        two.addActionListener(this);
        add(single);
        add(two);
        btns = new ArrayList<JButton>();
        btns.add(single);
        btns.add(two);
        pack();
        setVisible(true);
	}	
	
	public void setHomePageMan(){
		setJMenuBar(null);
		JMenu music = new JMenu("Music");
		JMenuItem on = new JMenuItem("On");
		JMenuItem off = new JMenuItem("Off");
		on.addActionListener(this);
		off.addActionListener(this);
		music.add(on);
		music.add(off);
		JMenu game = new JMenu("Game");
		JMenuItem single = new JMenuItem("Single Player");
		JMenuItem two = new JMenuItem("Two Players");
		JMenuItem con = new JMenuItem("Continue");
		con.addActionListener(this);
		single.addActionListener(this);
		two.addActionListener(this);
		game.add(con);
		game.add(single);
		game.add(two);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(game);
		menuBar.add(music);
		setJMenuBar(menuBar);
	}
	public void setMenu(){
		setJMenuBar(null);
		//menu
		JMenu music = new JMenu("Music");
		JMenuItem on = new JMenuItem("On");
		JMenuItem off = new JMenuItem("Off");
		on.addActionListener(this);
		off.addActionListener(this);
		music.add(on);
		music.add(off);
		JMenu pick = new JMenu("Choose map");
		List<String> map = Map.getAllMapName();
		for(String name:map){
			JMenuItem item = new JMenuItem(name);
			item.addActionListener(this);
			pick.add(item);
		}	
		//menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(pick);
		menuBar.add(music);
		setJMenuBar(menuBar);				
	}

	private void setLevel(String map){
		String[] parts = map.split("\\.");
		this.level = Integer.parseInt(parts[0]);
	}
	public void initBGM(){
		bgm = new BGM();
		bgm.newBGM();
		bgm.Get_Music();
		bgm.BGM();
	}
}