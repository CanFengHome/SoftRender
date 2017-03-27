package com.sharp;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;

public class GameWindow extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private final JFrame m_frame;
	private final Input m_input;
	
	// The bitmap representing the final image to display
	private final Bitmap m_frameBuffer;
	
	// Used to display the framebuffer in the window
	private final BufferedImage m_displayImage;
	// The pixels of the display image, as an array of byte components
	private final byte[] m_displayComponents;
	// The buffers in the Canvas
	private final BufferStrategy m_bufferStrategy;
	// A graphics object that can draw into the Canvas's buffers
	private final Graphics m_graphics;
	
	public Bitmap getFrameBuffer() {
		return m_frameBuffer;
	}
	
	public Input getInput() {
		return m_input;
	}
	
	public GameWindow(int width, int height, String title) {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		m_frameBuffer = new Bitmap(width, height);
		m_displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		m_displayComponents =  ((DataBufferByte)m_displayImage.getRaster().getDataBuffer()).getData();
		m_frameBuffer.Clear((byte)0x80);
		
		m_frame = new JFrame();
		m_frame.add(this);
		m_frame.pack();
		m_frame.setResizable(false);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setLocationRelativeTo(null);
		m_frame.setTitle(title);
		m_frame.setSize(width, height);
		m_frame.setVisible(true);
		
		//Allocates 1 display buffer, and gets access to it via the buffer
		//strategy and a graphics object for drawing into it.	
		createBufferStrategy(1);
		m_bufferStrategy = getBufferStrategy();
		m_graphics = m_bufferStrategy.getDrawGraphics();
		
		m_input = new Input();
		addKeyListener(m_input);
		addFocusListener(m_input);
		addMouseListener(m_input);
		addMouseMotionListener(m_input);
		
		setFocusable(true);
		requestFocus();
	}

	public void swapBuffers()
	{
		//Display components should be the byte array used for displayImage's pixels.
		//Therefore, this call should effectively copy the frameBuffer into the
		//displayImage.
		m_frameBuffer.CopyToByteArray(m_displayComponents);
		m_graphics.drawImage(m_displayImage, 0, 0, 
			m_frameBuffer.GetWidth(), m_frameBuffer.GetHeight(), null);
		m_bufferStrategy.show();
	}
}
