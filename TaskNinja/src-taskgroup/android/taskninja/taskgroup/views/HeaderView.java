package android.taskninja.taskgroup.views;

import android.content.Context;
import android.taskninja.R;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeaderView extends LinearLayout {
	
	public static HeaderView getInstance(Context context, String title){
		return new HeaderView(context, title);
	}

	private HeaderView(Context context, String title) {
		super(context);
		
		inflate(getContext(), R.layout.list_header_text, this);
		TextView tv = (TextView) findViewById(R.id.headerText);
		tv.setText(title);
		tv.setClickable(false);
		tv.setFocusable(false);

	}

}
