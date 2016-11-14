# FolderWatch

Purpose:
--------
FolderWatch is a utility that watches a specific folder for creation of any new file or folder in it and moves them
to another folder.

Input Specifics:
---------------
The FolderWatch will take two parameters for input. First will be the folder to be watched and second will be the folder
wo which the files or folders are to be moved.

Output Specifics:
----------------
The user will be notified if the program detects a new file or folder in the directory of interest and will notify again 
when they are moved. 

How to test:
-----------------------
Unit test cases are included in the build to test. The user can test the FolderWatch using the main method included in 
com.test.FileMove

How to stop the program:
----------------
The program uses an infinite loop in order to continuosly watch the folder. To break the loop at any time, the user will
have to close the base program (cmd in case of Windows using ctrl+c). 

