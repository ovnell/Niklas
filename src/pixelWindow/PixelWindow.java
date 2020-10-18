package pixelWindow;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class PixelWindow extends Canvas {
	
	public static int getColorCode(int r, int g, int b) {
		return (r << 16) + (g << 8) + b;
	}

	private static final long serialVersionUID = 1L;
	private int width, height;

	private JFrame frame = new JFrame();
	private BufferedImage image;
	private int[] pixels;

	private int mouseX, mouseY;
	private boolean[] mouseButtonsPressed = new boolean[20];

	public PixelWindow(int width, int height) {
		this(width, height, "PixelWindow");
	}

	public PixelWindow(int width, int height, String title) {
		super();
		this.width = width;
		this.height = height;
		addMouseMotionListener(new MousePosition());
		addMouseListener(new ButtonListener());
		fixFrame(title);
	}

	private void fixFrame(String title) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		setPreferredSize(new Dimension(width, height));

		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		createBufferStrategy(3);
	}

	/**
	 * Sets the pixel at position (x, y) to the color specified by the parameter
	 * color. The updated pixel won't show until the method render() has been
	 * called.
	 * 
	 * @param x     The x position of the pixel
	 * @param y     The x position of the pixel
	 * @param color The RGB value of the color
	 */
	public void setPixel(int x, int y, int color) {
		pixels[x + y * width] = color;
	}

	/**
	 * Renders the image, showing all the pixels in the frame.
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		bs.show();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void addKeyListener(KeyListener e) {
		super.addKeyListener(e);
		frame.addKeyListener(e);
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public boolean mouseButtonPressed(int button) {
		return mouseButtonsPressed[button];
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}

	public String getTitle() {
		return frame.getTitle();
	}

	private class ButtonListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			mouseButtonsPressed[e.getButton()] = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mouseButtonsPressed[e.getButton()] = false;
		}
	}

	private class MousePosition implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}
}
