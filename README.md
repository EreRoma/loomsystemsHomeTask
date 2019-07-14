What is it?
-----------
Solution intend to solve the task of grouping together log file sentences which share the same behavior pattern and write to a file. Pattern can contains any number of words. Output file will contain as many rows of "dd-mm-yyyy hh:mm:ss Name pattern" as in the source file.
For Brief information about the project - user UML diagram [uml](https://github.com/EreRoma/loomsystemsHomeTask/blob/master/loomsystems_UML.pdf).
The original task you can find by link [task](https://github.com/EreRoma/loomsystemsHomeTask/blob/master/developer_task.pdf).

Input file format:
------------------
dd-mm-yyyy hh:mm:ss Name pattern

Output file format:
-------------------
dd-mm-yyyy hh:mm:ss Name pattern  
The pattern is: [X] pattern  
The changing word was: Name1, Name2 ...  

How to use
----------------
Create instance of ILogFileProcessor, it's constructor recieve instanses of IWriteLogResults(String inputFilename), IWriteLogResults(String outputFilename), IWriteLogResults(String outputErrorFileName), IReaderLog, and then execute method ILogFileProcessor.processLogs().  
All lines that are validated will be written to **outputFilename**.  
All lines that are not validated will be written to **outputErrorFileName**.
