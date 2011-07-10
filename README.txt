--------------------------------------------------------------------------------------------------------------

README: Task Ninja Pro

--------------------------------------------------------------------------------------------------------------

version-1-0
- Queue
- Queue selector
- Master list
- Setting
- Task settings
- Subtasks
- Single and recurring alerts
- Single and recurring queuing
- Notes
- Priority
- Due date
- Queue widget
- Main menu
- Info

version-1-1
- improve automated testing
- remove main menu
- remove priority
- remove queue selector
- add "put on queue" button to master list
- incorporate DbModel


version 1-2
- improve info
- change "alerts" to "notifications"
- mirror google tasks data structure

version-1-3
- update UX to new data structure
    - List
    - Lists
    - All tasks

version-1-4
- sync with google tasks

version-1-5
- add IntelligentScrollView
   - scrolls intelligently when child view changes size
   - scrolls when child view moves around within the scroll view
- add ButtonLayout
- due date alarms


parked features:
- some way to change the opacity and shade of windows to make them
readable and appealing based on the appearance (color, darkness, pattern) of the background wall paper.
- automated testing for time based features such as notification and queuing.
- custom edit text
- db model listeners, i.e., 
    - tells ModelViews when data changes 
    - tells connected models when data changes.
- add ToggleImageButton
- custom menu icons
- handle sub tasks on the queue better
- show notes in standard task views



--------------------------------------------------------------------------------------------------------------

Task Queue:

Existing tasks can be added to the task queue through either queuing or the task queue selector.  New tasks can be added directly 	to the task queue.  "Deleting" tasks from the task queue only removes the task from the queue and does not actually delete the 	task.  The first uncompleted task on the task queue will appear on the task queue widget.

--------------------------------------------------------------------------------------------------------------

Queue Selector:

All selected tasks on the queue selector are added to the task queue.  Tasks selected here that are not already on the queue will be added to the end of the queue.

--------------------------------------------------------------------------------------------------------------

Queue Widget:

The queue widget displays the first uncompleted task on the task queue.  Clicking on the queue widget completes the task.

--------------------------------------------------------------------------------------------------------------

Queuing:

Through the queuing function of task settings, which can be found be clicking on the more button of a task, tasks can be added to either the front or the back of the queue at the specified time(s).

--------------------------------------------------------------------------------------------------------------