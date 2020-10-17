package pixelWindow;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class PixelWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private int width, height;
	private String title;

	private Canvas canvas = new Canvas();
	private BufferedImage image;
	private int[] pixels;

	public PixelWindow(int width, int height) {
		this(width, height, "PixelWindow");
	}

	public PixelWindow(int width, int height, String title) {
		super();
		this.width = width;
		this.height = height;
		this.title = title;
		fixFrame();
	}

	private void fixFrame() {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		canvas.setPreferredSize(new Dimension(width, height));

		setResizable(false);
		setTitle(title);
		add(canvas);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		canvas.createBufferStrategy(3);
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

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void addMouseWheelListener(MouseWheelListener e) {
		canvas.addMouseWheelListener(e);
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener e) {
		canvas.addMouseMotionListener(e);
	}

	@Override
	public void addMouseListener(MouseListener e) {
		canvas.addMouseListener(e);
	}
}
