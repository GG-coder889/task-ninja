package android.taskninja.mobile.views;

import android.app.Activity;

public class TaskListView extends CustomLayout {
	
	TaskView mActiveTaskView;
	Activity mActivity;

	public TaskListView(Activity activity) {
		super(activity);

		mActivity = activity;
		
		setOrientation(VERTICAL);
		
	}
	
	public void updatePositions(){
		for (int i = 0 ; i < getChildCount(); i++){
			((TaskView) getChildAt(i)).setPosition(i+1);
		}
	
	}
	
	
	public void moveUp(TaskView view){
		int currentIndex = indexOfChild(view);
		int futureIndex = currentIndex - 1;
		if (futureIndex >= 0){
			TaskView otherView = (TaskView) getChildAt(futureIndex);
			removeView(otherView);
			addView(otherView, currentIndex);
			view.setPosition(futureIndex + 1);
			otherView.setPosition(currentIndex + 1);
		}
	
	}
	
	
	public void moveDown(TaskView view){
		int currentIndex = indexOfChild(view);
		int futureIndex = currentIndex + 1;
		TaskView otherView = (TaskView) getChildAt(futureIndex);
		if (otherView != null){
			removeView(view);
			addView(view, futureIndex);
			view.setPosition(futureIndex + 1);
			otherView.setPosition(currentIndex + 1);
		} 	
	}
	
	
	public void setActive(TaskView view) {
		if (mActiveTaskView != null){
			if (!mActiveTaskView.equals(view)){
				mActiveTaskView.setMenuShown(false);
			}
		}
		mActiveTaskView = view;
	}

	public Activity getActivity() {
		return mActivity;
	}

}
