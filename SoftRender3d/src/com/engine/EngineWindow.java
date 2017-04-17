package com.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;

import com.render.RenderContext;

public class EngineWindow extends Canvas {
	private static final long serialVersionUID = 1L;
	private int winWidth;
	private int winHeight;
	private final JFrame jFrame;
	private final Input input;

	private final RenderContext frameBuffer;
	private final BufferedImage displayImage;
	private final byte[] displayComponents;
	
	private final BufferStrategy bufferStrategy;
	private final Graphics graphics;

	public EngineWindow(int width, int height, String title) {
		winWidth = width;
		winHeight = height;
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		// Creates images used for display.
		frameBuffer = new RenderContext(width, height);
		displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		displayComponents = ((DataBufferByte) displayImage.getRaster().getDataBuffer()).getData();

		frameBuffer.clear((byte) 0x80);
		frameBuffer.drawPixel(100, 100, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF);

		jFrame = new JFrame();
		jFrame.add(this);
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle(title);
		jFrame.setVisible(true);
		
		createBufferStrategy(1);
		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
		
		input = new Input();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		setFocusable(true);
		requestFocus();
	}

	public void swapBuffers() {
		// draw framebuffer content
		frameBuffer.copyToByteArray(displayComponents);
		graphics.drawImage(displayImage, 0, 0, frameBuffer.getWidth(), frameBuffer.getHeight(), null);
		bufferStrategy.show();
	}

	public int getWinWidth() {
		return winWidth;
	}

	public int getWinHeight() {
		return winHeight;
	}

	public RenderContext getFrameBuffer() {
		return frameBuffer;
	}
	
	public Input getInput() {
		return input;
	}
}












