package net.alevel.asteroids.engine.input.enums;

import static org.lwjgl.glfw.GLFW.*;

public enum NonPrintableChars {
	BACKSPACE(GLFW_KEY_BACKSPACE),
	ENTER(GLFW_KEY_ENTER),
	RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),
	RIGHT_CTRL(GLFW_KEY_RIGHT_CONTROL),
	ALT_GR(GLFW_KEY_RIGHT_ALT),
	ALT(GLFW_KEY_LEFT_ALT),
	LEFT_CTRL(GLFW_KEY_LEFT_CONTROL),
	LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
	TAB(GLFW_KEY_TAB),
	INSERT(GLFW_KEY_INSERT),
	HOME(GLFW_KEY_HOME),
	PAGE_UP(GLFW_KEY_PAGE_UP),
	PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
	END(GLFW_KEY_END),
	DELETE(GLFW_KEY_DELETE),
	ESCAPE(GLFW_KEY_ESCAPE);
	private final int glId;
	private NonPrintableChars(int glId) {
		this.glId = glId;
	}
	
	public int getGlId() {
		return this.glId;
	}
}
