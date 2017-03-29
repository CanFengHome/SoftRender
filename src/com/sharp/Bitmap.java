package com.sharp;

import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bitmap
{
	/** The width, in pixels, of the image */
	private final int  m_width;
	/** The height, in pixels, of the image */
	private final int  m_height;
	/** Every pixel component in the image */
	private final byte m_components[];

	/** Basic getter */
	public int GetWidth() { return m_width; }
	/** Basic getter */
	public int GetHeight() { return m_height; }

	public byte GetComponent(int index) { return m_components[index]; }

	/**
	 * Creates and initializes a Bitmap.
	 *
	 * @param width The width, in pixels, of the image.
	 * @param height The height, in pixels, of the image.
	 */
	public Bitmap(int width, int height)
	{
		m_width      = width;
		m_height     = height;
		m_components = new byte[m_width * m_height * 4];
	}

	public Bitmap(String fileName)
	{
		int width = 0;
		int height = 0;
		byte[] components = null;

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();

		int imgPixels[] = new int[width * height];
		image.getRGB(0, 0, width, height, imgPixels, 0, width);
		components = new byte[width * height * 4];

		for(int i = 0; i < width * height; i++)
		{
			int pixel = imgPixels[i];

			components[i * 4]     = (byte)((pixel >> 24) & 0xFF); // A
			components[i * 4 + 1] = (byte)((pixel      ) & 0xFF); // B
			components[i * 4 + 2] = (byte)((pixel >> 8 ) & 0xFF); // G
			components[i * 4 + 3] = (byte)((pixel >> 16) & 0xFF); // R
		}

		m_width = width;
		m_height = height;
		m_components = components;
	}

	/**
	 * Sets every pixel in the bitmap to a specific shade of grey.
	 *
	 * @param shade The shade of grey to use. 0 is black, 255 is white.
	 */
	public void Clear(byte shade)
	{
		Arrays.fill(m_components, shade);
	}


	public void DrawPixel(int x, int y, byte a, byte b, byte g, byte r)
	{
		int index = (x + y * m_width) * 4;
		m_components[index    ] = a;
		m_components[index + 1] = b;
		m_components[index + 2] = g;
		m_components[index + 3] = r;
	}
	
	public void DrawPixel(int x, int y, Color3B color) {
		int index = (x + y * m_width) * 4;
		m_components[index    ] = (byte)255;
		m_components[index + 1] = color.getB();
		m_components[index + 2] = color.getG();
		m_components[index + 3] = color.getR();
	}

	public void CopyPixel(int destX, int destY, int srcX, int srcY, Bitmap src, float lightAmt)
	{
		int destIndex = (destX + destY * m_width) * 4;
		int srcIndex = (srcX + srcY * src.GetWidth()) * 4;
		
		m_components[destIndex    ] = (byte)((src.GetComponent(srcIndex) & 0xFF) * lightAmt);
		m_components[destIndex + 1] = (byte)((src.GetComponent(srcIndex + 1) & 0xFF) * lightAmt);
		m_components[destIndex + 2] = (byte)((src.GetComponent(srcIndex + 2) & 0xFF) * lightAmt);
		m_components[destIndex + 3] = (byte)((src.GetComponent(srcIndex + 3) & 0xFF) * lightAmt);
	}
	
	public void BitBitmap(int destX, int destY, int srcX, int srcY, Bitmap src) {
		if (destX >= m_width || destY >= m_height ||
			(destX + m_width) <= 0 || (destY + m_height) <= 0) {
			return;
		}
		
		assert srcX >= 0 && srcX < src.m_width;
		assert srcY >= 0 && srcY < src.m_height;
		
		int x1 = destX;
		int y1 = destY;
		int x2 = x1 + m_width - 1;
		int y2 = y1 + m_height - 1;
		
		if (x1 < 0)
			x1 = 0;
		if (y1 < 0)
			y1 = 0;
		if (x2 >= m_width)
			x2 = m_width - 1;
		if (y2 >= m_height)
			y2 = m_height - 1;
		
		int x_off = x1 - destX;
		int y_off = y1 - destY;
		int dx = x2 - x1 + 1;
		int dy = y2 - y1 + 1;
		int startSrcX = srcX + x_off;
		int startSrcY = srcY + y_off;
		
		int srcDx = src.m_width - startSrcX;
		int srcDy = src.m_height - startSrcY;
		dx = Math.min(dx, srcDx);
		dy = Math.min(dy, srcDy);
		
		for (int flagY=0; flagY < dy; ++flagY) {
			for (int flagX=0; flagX < dx; ++flagX) {
				int destIndex = ((x1 + flagX) + (y1 + flagY) * m_width) * 4;
				int srcIndex = ((startSrcX + flagX) + (startSrcY + flagY) * src.GetWidth()) * 4;
				
				m_components[destIndex    ] = (byte)((src.GetComponent(srcIndex) & 0xFF) );
				m_components[destIndex + 1] = (byte)((src.GetComponent(srcIndex + 1) & 0xFF) );
				m_components[destIndex + 2] = (byte)((src.GetComponent(srcIndex + 2) & 0xFF) );
				m_components[destIndex + 3] = (byte)((src.GetComponent(srcIndex + 3) & 0xFF) );
			}
		}
	}

	/**
	 * Copies the Bitmap into a BGR byte array.
	 *
	 * @param dest The byte array to copy into.
	 */
	public void CopyToByteArray(byte[] dest)
	{
		for(int i = 0; i < m_width * m_height; i++)
		{
			dest[i * 3    ] = m_components[i * 4 + 1];
			dest[i * 3 + 1] = m_components[i * 4 + 2];
			dest[i * 3 + 2] = m_components[i * 4 + 3];
		}
	}
}
