package com.taskninjapro.android.app;

public interface Constants {

	// Settings
	static final String PREFS = "settings";
	static final int PREFS_MODE = 0;
	
	static final String PRIORITY = "PRIORITY";
	static final boolean PRIORITY_DEFAULT = true;
	
	static final String DUE_DATE = "DUE_DATE";
	static final boolean DUE_DATE_DEFAULT = true;
	
	static final String ALERTS = "ALERTS";
	static final boolean ALERTS_DEFAULT = true;
	
	static final String QUEUING = "QUEUING";
	static final boolean QUEUING_DEFAULT = true;
	
	static final String SUBTASKING = "SUBTASKING";
	static final boolean SUBTASKING_DEFAULT = false;
	
	static final String QUEUE = "QUEUE";
	static final String QUEUE_ALERT = "queue_alert";
	static final String QUEUE_ALERT_HOUR = "queue_alert_hour";
	static final String QUEUE_ALERT_MINUTE = "queue_alert_minute";
	
	static final int MAX_TITLE_LENGTH = 70;

//	static final long SECOND_MILLIS = DateUtils.SECOND_IN_MILLIS;
//	static final long MINUTE_MILLIS = DateUtils.MINUTE_IN_MILLIS;
//	static final long HOUR_MILLIS = DateUtils.HOUR_IN_MILLIS;
//	static final long DAY_MILLIS = DateUtils.DAY_IN_MILLIS;
//
//	static final long OFFSET_MILLIS = Calendar.getInstance().get(
//			Calendar.ZONE_OFFSET)
//			+ Calendar.getInstance().get(Calendar.DST_OFFSET);
//	static final long TIME_MILLIS = System.currentTimeMillis() + OFFSET_MILLIS;
//	static final long TODAY_MILLIS = (TIME_MILLIS / DAY_MILLIS) * DAY_MILLIS
//			+ DAY_MILLIS / 2 + 1;
//	static final long TOMORROW_MILLIS = (TIME_MILLIS / DAY_MILLIS) * DAY_MILLIS
//			+ DAY_MILLIS + DAY_MILLIS / 2 + 1;
	


	

	
	// TASK DATABASE
	static final int DATABASE_VERSION = 1;
	static final String TASKS_DATABASE = "tasks.db";
	static final String TASKS_TABLE = "tasks";
	
	static final String _ID = android.provider.BaseColumns._ID;
	static final String KEY_WHAT = "what";
	static final String KEY_NOTES = "notes";
	static final String KEY_DUE_DATE = "dueDate";
	static final String KEY_COMPLETED = "completed";
	static final String KEY_TASKS = "subtasks";
	static final String KEY_PARENT = "parent";
	static final String KEY_PRIORITY = "priority";
	
	static final String KEY_SINGLE_ALERT = "singleAlert";
	static final String KEY_SINGLE_ALERT_TIME = "singleAlertTime";
	
	static final String KEY_RECURRING_ALERT = "KEY_RECURRING_ALERT";
	static final String KEY_RECURRING_ALERT_TIME = "KEY_RECURRING_ALERT_TIME";
	static final String KEY_RECURRING_ALERT_MONDAY = "KEY_RECURRING_ALERT_MONDAY";
	static final String KEY_RECURRING_ALERT_TUESDAY = "KEY_RECURRING_ALERT_TUESDAY";
	static final String KEY_RECURRING_ALERT_WEDNESDAY = "KEY_RECURRING_ALERT_WEDNESDAY";
	static final String KEY_RECURRING_ALERT_THURSDAY = "KEY_RECURRING_ALERT_THURSDAY";
	static final String KEY_RECURRING_ALERT_FRIDAY = "KEY_RECURRING_ALERT_FRIDAY";
	static final String KEY_RECURRING_ALERT_SATURDAY = "KEY_RECURRING_ALERT_SATURDAY";
	static final String KEY_RECURRING_ALERT_SUNDAY= "KEY_RECURRING_ALERT_SUNDAY";
	
	static final String KEY_SINGLE_QUEUING = "KEY_SINGLE_QUEUING";
	static final String KEY_SINGLE_QUEUING_TIME = "KEY_SINGLE_QUEUING_TIME";
	static final String KEY_SINGLE_QUEUEING_FRONT = "KEY_SINGLE_QUEUEING_FRONT";
	
	static final String KEY_RECURRING_QUEUING = "KEY_RECURRING_QUEUING";
	static final String KEY_RECURRING_QUEUING_TIME = "KEY_RECURRING_QUEUING_TIME";
	static final String KEY_RECURRING_QUEUING_MONDAY = "KEY_RECURRING_QUEUING_MONDAY";
	static final String KEY_RECURRING_QUEUING_TUESDAY = "KEY_RECURRING_QUEUING_TUESDAY";
	static final String KEY_RECURRING_QUEUING_WEDNESDAY = "KEY_RECURRING_QUEUING_WEDNESDAY";
	static final String KEY_RECURRING_QUEUING_THURSDAY = "KEY_RECURRING_QUEUING_THURSDAY";
	static final String KEY_RECURRING_QUEUING_FRIDAY = "KEY_RECURRING_QUEUING_FRIDAY";
	static final String KEY_RECURRING_QUEUING_SATURDAY = "KEY_RECURRING_QUEUING_SATURDAY";
	static final String KEY_RECURRING_QUEUING_SUNDAY = "KEY_RECURRING_QUEUING_SUNDAY";
	static final String KEY_RECURRING_QUEUING_FRONT = "KEY_RECURRING_QUEUING_FRONT";

	static final String[] TASK_KEYS = {
		
			_ID, 
			KEY_WHAT, 
			KEY_NOTES,
			KEY_DUE_DATE, 
			KEY_COMPLETED,
			KEY_TASKS,
			KEY_PARENT,
			KEY_PRIORITY, 
			
			KEY_SINGLE_ALERT, 
			KEY_SINGLE_ALERT_TIME,
			
			KEY_RECURRING_ALERT,
			KEY_RECURRING_ALERT_TIME,
			KEY_RECURRING_ALERT_MONDAY,
			KEY_RECURRING_ALERT_TUESDAY,
			KEY_RECURRING_ALERT_WEDNESDAY,
			KEY_RECURRING_ALERT_THURSDAY,
			KEY_RECURRING_ALERT_FRIDAY,
			KEY_RECURRING_ALERT_SATURDAY,
			KEY_RECURRING_ALERT_SUNDAY,
			
			KEY_SINGLE_QUEUING,
			KEY_SINGLE_QUEUING_TIME,
			KEY_SINGLE_QUEUEING_FRONT,
			
			KEY_RECURRING_QUEUING,
			KEY_RECURRING_QUEUING_TIME,
			KEY_RECURRING_QUEUING_MONDAY,
			KEY_RECURRING_QUEUING_TUESDAY,
			KEY_RECURRING_QUEUING_WEDNESDAY,
			KEY_RECURRING_QUEUING_THURSDAY,
			KEY_RECURRING_QUEUING_FRIDAY,
			KEY_RECURRING_QUEUING_SATURDAY,
			KEY_RECURRING_QUEUING_SUNDAY,
			KEY_RECURRING_QUEUING_FRONT
		};
	
	// ALARM DATABSE
	static final int ALARM_DATABASE_VERSION = 1;
	static final String ALARMS_DATABASE = "alarms.db";
	static final String ALARMS_TABLE = "alarms";
	
	static final String KEY_TASK_ID = "KEY_TASK_ID";
	static final String KEY_WHEN = "KEY_WHEN";
	static final String KEY_RECURRING = "KEY_RECURRING";
	static final String KEY_QUEUING = "KEY_QUEUING";
	static final String KEY_NOTIFICATION = "KEY_NOTIFICATION";
	
	static final String[] ALARM_KEYS = {
		_ID, KEY_TASK_ID, KEY_WHEN, KEY_RECURRING, KEY_QUEUING, KEY_NOTIFICATION
	};
	
}
