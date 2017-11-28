package cz.cuni.amis.clear2d.engine.prefabs;

import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.components.CSprite;
import cz.cuni.amis.clear2d.engine.iface.IDrawable;

/**
 * {@link Grid} represents a grid of regular textures ({@link IDrawable}s to be precise) that has the same width (represetned by {@link #cellWidth}) 
 * and height (represented by {@link #cellHeight}).
 * 
 * @author Jimmy
 */
public class Grid extends SceneElement {
	
	/**
	 * Width of grid-cell in pixels.
	 */
	public float cellWidth;
	
	/**
	 * Height of grid-cell in pixels.
	 */
	public float cellHeight;
	
	/**
	 * How many cells to render along the X axis.
	 */
	public int cellsXCount;
	
	/**
	 * How many cells to render along the Y axis.
	 */
	public int cellsYCount;
	
	/**
	 * Respective cells to be rendered, [row,column] ordering.
	 */
	public CSprite[][] cells;
	
	public Grid() {		
	}
	
	public Grid(float cellWidth, float cellHeight, int cellXCount, int cellYCount) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.cellsXCount = cellXCount;
		this.cellsYCount = cellYCount;
		
		cells = new CSprite[cellXCount][cellYCount];
		
		for (int x = 0; x < cellXCount; ++x) {
			for (int y = 0; y < cellYCount; ++y) {
				cells[x][y] = new CSprite(this);
				cells[x][y].pos.x = x * cellWidth;
				cells[x][y].pos.y = y * cellHeight;
			}
			
		}
	}
	
	/**
	 * Returns top-left x-coordinate (pixel) of the cell. 
	 * @param x
	 */
	public float getCellPosX(int cellX) {
		return cellX*cellWidth;
	}
	
	/**
	 * Returns top-left y-coordinate (pixel) of the cell. 
	 * @param x
	 */
	public float getCellPosY(int cellY) {
		return cellY*cellHeight;
	}
	
}
