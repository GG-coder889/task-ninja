package android.taskninja.app.settings;

public enum Settings {
	TaskNinja("Task Ninja"),
	Subtasks("Sub-Tasks"),
	Notification("Notifications"),
	Queue("Queue"),
	DueDate("Due Date");
	
	private String mTitle;
	
	Settings(String title){
		mTitle = title;
	}
	
	@Override
	public String toString(){
		return mTitle;
	}
}
