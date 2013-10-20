package com.ahbrendan.structured.util;

import android.text.Editable;

public class LineContext {
	public LineContext(LineManager manager, int start, int end)
	{
		this.manager = manager;
		this.start = start;
		this.end = end;
	}
	
	LineManager manager;
	int start;
	int end;
	
	public LineManager getManager() {
		return manager;
	}
	
	public Editable getEditable() {
		return getManager().getEditable();
	}

	public int getStartIndex() {
		return start;
	}

	public int getEndIndex() {
		return end;
	}

	public String getContent()
	{
		return this.getEditable().subSequence(start, end + 1).toString();
	}
	
	public void setSpan(Object style)
	{
		getManager().setSpan(style, this);
	}
}
