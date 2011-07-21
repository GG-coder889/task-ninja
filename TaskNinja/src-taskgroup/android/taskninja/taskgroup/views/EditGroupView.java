package android.taskninja.taskgroup.views;

import android.content.Context;
import android.taskninja.R;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditGroupView extends LinearLayout {

	public static EditGroupView getInstance(Context context) {
		return new EditGroupView(context);
	}
	
	private EditGroupView(Context context) {
		super(context);
		inflate(getContext(), R.layout.list_header_text, this);
		TextView tv = (TextView) findViewById(R.id.headerText);
		tv.setText("Edit Group");
	}

}
