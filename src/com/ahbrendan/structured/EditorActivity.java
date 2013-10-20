package com.ahbrendan.structured;

import java.util.ArrayList;
import java.util.List;

import com.ahbrendan.structured.util.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.style.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class EditorActivity extends Activity {
	
	LineManager lineManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_editor);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);
		
		lineManager = new LineManager((EditText)contentView);
		lineManager.addProcessor(new LineProcessor() {

			@Override
			public void process(LineContext context) {
				// TODO Auto-generated method stub
				char[] chars = context.getContent().toCharArray();
				Log.i("doLineStyleSetting", "chars are: " + chars[chars.length - 1]);
				if(chars.length > 1 && chars[chars.length - 1] == ':')
				{
					context.setSpan(new ForegroundColorSpan(Color.RED));
				}
			}
			
		});
		
		((EditText)contentView).addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable e)
			{
				lineManager.process();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});
	}


}
