package cz.cuni.amis.clear2d.engine.components;

import java.awt.Color;

import cz.cuni.amis.clear2d.engine.Component;
import cz.cuni.amis.clear2d.engine.SceneElement;
import cz.cuni.amis.clear2d.engine.fonts.FontAtlas;

public class CLabel extends Component {

	public CQuad cBackground;
	
	public CText cText;
	
	/**
	 * @param owner initial owner the component; if null, does nothing
	 */
	public CLabel(SceneElement owner) {
		super(owner);
	}
	
	public void initLabel(FontAtlas fontAtlas, String text, Color backgroundColor, Color outlineColor) {
		this.cText = new CText(fontAtlas, text);
		this.cText.pos.x = 1;
		this.cText.pos.y = 0;
		
		this.cBackground = new CQuad(owner);
		this.cBackground.initQuad(this.cText.textWidth + 4, this.cText.textHeight + 4, backgroundColor, outlineColor);
		
		this.owner.addComponent(cText);
	}
	
	public String getText() {
		if (cText == null) return null;
		return cText.text;
	}
	
	public void setText(String text) {
		if (cText == null) {
			throw new RuntimeException("Initialize first!");
		}
		cText.text = text;
		cText.updateDimensions();
		cBackground.setSize(this.cText.textWidth + 4, this.cText.textHeight + 4);
	}
	
}
