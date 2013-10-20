package com.ahbrendan.structured.util;

import java.util.ArrayList;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;

public class LineManager {
	public LineManager(EditText editor)
	{
		this.editor = editor;
	}
	
	EditText editor;
	
	public EditText getEditor()
	{
		return editor;
	}
	
	public Editable getEditable()
	{
		return editor.getEditableText();
	}
	
	ArrayList<LineProcessor> processors = new ArrayList<LineProcessor>();
	public void addProcessor(LineProcessor processor)
	{
		processors.add(processor);
	}
	
	ArrayList<Class> usingSpanTypes = new ArrayList<Class>();
	
	public void setSpan(Object style, LineContext context)
	{
		if(!usingSpanTypes.contains(style.getClass())){
			usingSpanTypes.add(style.getClass());
		}
		this.getEditable().setSpan(style, context.getStartIndex(), context.getEndIndex() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
	public void process()
	{
		for(Class type : usingSpanTypes) {
			Object[] toRemoveSpans = this.getEditable().getSpans(0, this.getEditable().length(), type);
			for (int i = 0; i < toRemoveSpans.length; i++) 
			    this.getEditable().removeSpan(toRemoveSpans[i]);
		}
		usingSpanTypes.clear();
		
		int startIndex = 0;
		int endIndex = 0;
		while(startIndex < this.getEditable().length())
		{
			if(endIndex >= this.getEditable().length() - 1 || this.getEditable().charAt(endIndex) == '\n')
			{
				if(startIndex == endIndex)
				{
					startIndex = endIndex + 1;
					endIndex = startIndex;
					continue;
				}
				else
				{
					doProcess(startIndex, endIndex);
					startIndex = endIndex + 1;
					endIndex = startIndex;
					continue;
				}
			}
			endIndex = endIndex + 1;
		}
		

	}
	
	private void doProcess(int start, int end) {
		if(end > this.getEditable().length() - 1)
		{
			end = this.getEditable().length() - 1;
		}
		int trueEnd = end;
		while(this.getEditable().charAt(trueEnd) == '\n')
		{
			trueEnd--;
		}
		if(trueEnd < start)
		{
			return;
		}
		LineContext context = new LineContext(this, start, trueEnd);
		for(LineProcessor processor : processors) {
			processor.process(context);
		}
	}
}
