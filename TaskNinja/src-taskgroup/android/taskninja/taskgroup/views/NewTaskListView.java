package android.taskninja.taskgroup.views;

import android.content.Context;
import android.taskninja.views.TitleText;
import android.widget.LinearLayout;

public class NewTaskListView extends LinearLayout {
	
	
	public static NewTaskListView getInstance(Context context){
		return new NewTaskListView(context);
	}

	private NewTaskListView(Context context) {
		super(context);
		
		addView(TitleText.getInstance(getContext(), "Hello From NewTaskListView"));
	}

}
