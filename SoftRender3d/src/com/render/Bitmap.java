package com.render;

import java.util.Arrays;

public class Bitmap
{
	protected final int m_width;
	protected final int m_height;
	protected final byte m_components[];

	public int getWidth() {
		return m_width;
	}

	public int getHeight() {
		return m_height;
	}

	public Bitmap(int width, int height) {
		m_width = width;
		m_height = height;
		m_components = new byte[m_width * m_height * 4];
	}

	public void clear(byte shade)
	{
		Arrays.fill(m_components, shade);
	}

	public void drawPixel(int x, int y, byte a, byte b, byte g, byte r)
	{
		int index = (x + y * m_width) * 4;
		m_components[index    ] = a;
		m_components[index + 1] = b;
		m_components[index + 2] = g;
		m_components[index + 3] = r;
	}

	public void copyPixel(int destX, int destY, int srcX, int srcY, Bitmap src)
	{
		int destIndex = (destX + destY * m_width) * 4;
		int srcIndex = (srcX + srcY * src.getWidth()) * 4;
		m_components[destIndex    ] = src.m_components[srcIndex];
		m_components[destIndex + 1] = src.m_components[srcIndex + 1];
		m_components[destIndex + 2] = src.m_components[srcIndex + 2];
		m_components[destIndex + 3] = src.m_components[srcIndex + 3];
	}
	
	public void copyToByteArray(byte[] dest)
	{
		for(int i = 0; i < m_width * m_height; i++)
		{
			dest[i * 3    ] = m_components[i * 4 + 1];
			dest[i * 3 + 1] = m_components[i * 4 + 2];
			dest[i * 3 + 2] = m_components[i * 4 + 3];
		}
	}
}
