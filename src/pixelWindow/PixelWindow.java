package pixelWindow;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class PixelWindow {

	private int width, height;
	private String title;

	private JFrame frame;
	private Canvas canvas = new Canvas();
	private BufferedImage image;
	private int[] pixels;

	public PixelWindow(int width, int height) {
		this(width, height, "PixelWindow");
	}

	public PixelWindow(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		fixFrame();
	}
	
	private void fixFrame() {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		canvas.setPreferredSize(new Dimension(width, height));

		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.createBufferStrategy(3);
	}

	/**
	 * Sets the pixel at position (x, y) to the color specified by the parameter color.
	 * The updated pixel won't show until the method render() has been called.
	 * @param x The x position of the pixel
	 * @param y The x position of the pixel
	 * @param color The RGB value of the color
	 */
	public void setPixel(int x, int y, int color) {
		pixels[x + y * width] = color;
		if (x + y * width > pixels.length) {
			System.out.printf("x = %d, y = %d, pixel = %d\n", x, y, x + y * width);
			System.out.println(pixels.length);
		}
	}

	/** 
	 * Renders the image, showing all the pixels in the frame.
	 */
	public void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		bs.show();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		frame.setTitle(title);
	}
}
