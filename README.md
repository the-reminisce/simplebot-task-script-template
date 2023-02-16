# simplebot-task-script-template
## Introduction
This repository is a template for creating a script for the Simplebot API that is based on the task system.

## Detailed Description

This template is intended to provide a starting point for developing a script for the Simplebot API that utilizes the task system. The task system is a powerful tool that allows you to structure your script into smaller, more manageable tasks.

The main class of the script, `TemplateScript`, sets up the script and initializes the task system. It also handles paint and mouse events. The paint event is used to display information about the script on the screen, such as its current status, runtime statistics, and other relevant information. The mouse event is used to handle any mouse interactions that the script may need, such as clicking on objects or interacting with the game world.

The `TemplateConstants` class holds any constants that the script uses to define specific values or locations in the game world that the script needs to interact with. This can include things like the names of offensive prayers, specific coordinates, or other values that are used throughout the script. By defining these values as constants in a separate class, they can be easily reused and referenced throughout the script, making the code more maintainable and readable.

The `TemplateSettings` class holds the settings for the script. In this template, it is initialized with an instance of `OffensivePrayer`, which is an enum that holds the different types of offensive prayers. You can modify the `TemplateSettings` class to hold any other settings that your script requires. Additionally, `TemplateSettings` is also used for saving and loading settings from the GUI using JSON.

The `TemplateState` class holds the state variables for the script. This includes information such as the current task, the time that the script started, and any other variables that need to be tracked throughout the execution of the script. The `TemplateState` class also has a method for generating random numbers in a normal distribution, which can be useful for adding some randomness to your script.

The `TemplateTask` class is a template for a task that can be added to the task system. It provides access to the script instance, settings, and state, allowing you to perform actions and make decisions based on the current state of the script.

The `OffensivePrayer` enum holds the different types of offensive prayers that the script can use.

The `PaintHelper` class provides a customizable paint for the script. It can be modified to add custom lines, shapes, and other features to the script's display. This can be useful for displaying information about the script's progress or for debugging purposes.

By using this template and following the guidelines provided, you should be able to create a well-structured, maintainable script for the Simplebot API.
