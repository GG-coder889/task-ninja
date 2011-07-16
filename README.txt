--------------------------------------------------------------------------------------------------------------

README: Task Ninja

--------------------------------------------------------------------------------------------------------------

User Stories:

As a user I want to access my tasks without opening the application.

As a user I want to have my tasks divided up into smaller more manageable lists.

As a user I dont want my applications to break me out of my device experience.

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
- location aware tasks
- automated testing for time based features such as notification and queuing.
- custom edit text
- db model listeners, i.e., 
    - tells ModelViews when data changes 
    - tells connected models when data changes.
- add ToggleImageButton
- custom menu icons
- handle sub tasks on the queue better
- show notes in standard task views
