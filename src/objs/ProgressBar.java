package objs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import backends.Entity;
import engine.Window;

public class ProgressBar extends Entity{

	private static final long serialVersionUID = -7884396062702622182L;
	
	//Progress Bar Variables
	ImageIcon image;
	Point left;
	int width, height, x, y, speed = 1;
	boolean showLine = true, looping = false;
	Color lineColor = Color.black;
	
	//Spritesheet vars
	boolean isSheet = false;
	int sheetX=0, sheetY=0, spriteW, spriteH;
	
	//Finished?
	boolean FINISHED = false;
	

	public ProgressBar(Window win, String id, ImageIcon i, Point l, int w, int h) {
		super(win, id);
		image = i;
		left = l;
		width = w > 4 ? w : 5;
		height = h > 0 ? h : 1;
		x = l.x;
		y = l.y;
	}
	
	public void dontShowLine() {
		showLine = false;
	}
	
	public void setColor(Color c) {
		lineColor = c;
	}
	
	public void isSpritesheet(int w, int h) {
		isSheet = true;
		spriteW = w;
		spriteH = h;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public void makeLoop() {
		looping = true;
	}

	@Override
	public void paint(Graphics2D g) {
		if(showLine) {
			g.setStroke(new BasicStroke(height));
			g.setColor(lineColor);
			g.drawLine(left.x, left.y, x, y);
		}
		if(image != null) {
			if(!isSheet) {
				g.drawImage(image.getImage(), x-image.getIconWidth()/2, y-image.getIconHeight()/2, null);
			}else {
				ImageIcon temp = new ImageIcon(((BufferedImage)image.getImage()).getSubimage(sheetX, sheetY, spriteW, spriteH));
				g.drawImage(
					temp.getImage(),
					x-temp.getIconWidth()/2,
					y-temp.getIconHeight()/2,
					null
				);
				if(sheetX + spriteW < image.getIconWidth()) {
					sheetX += spriteW;
				}else {
					sheetX = 0;
					if(sheetY + spriteH < image.getIconHeight()) {
						sheetY += spriteH;
					}else {
						sheetY = 0;
					}
				}
			}
		}else {
			g.fillOval(x-2, y-2, 4, 4);
		}
	}

	@Override
	public void onTick(double delta) {
		if(x >= left.x + width) {
			if(looping) {
				x = left.x;
			}
			FINISHED = true;
		}else {
			x++;
		}
	}

}