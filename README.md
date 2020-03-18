# Run the RPSLS project in different modes

Using this project you can run the **Rock Paper Scissors Lizard Spock** game in three different modes: Sequential, Parallel and Distributed.
</br> </br>
**Sequential mode**: Plainly iterative, using only loops. </br>
**Parallel mode**: Executed in parallel using multiple threads, where each player represents a single thread. In order to maintain atomicity and to avoid inconsistencies, scores are stored as atomic integers. </br>
**Distributed mode**: Executed using MPJ-Express, an MPI (Message passing interface) implementation for Java. The players are distributed and each completes one task - plays games with all the players which come after him. Then the scores are gathered from each distribution and results are given. </br>

### Requirements
* Eclipse (4.8 or higher) or IntelliJ as an alternative </br>
* Java 10 or higher

### Run modes

Import the project via Eclipse, then:
* Sequential mode: Compile and execute the Sequential.java file
* Parallel mode: Compile and execute Parallel.java file
* Distributed mode: 
To run distributed mode install MPJ-Express [here](https://sourceforge.net/projects/mpjexpress/files/releases) and then follow [these instructions](http://mpj-express.org/docs/guides/DebuggingWithEclipseIDE.pdf) (steps 1 to 3) to run the project using Eclipse.
