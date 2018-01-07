# Connect Four Game
* An implementation of [Connect Four](https://en.wikipedia.org/wiki/Connect_Four) game
* An assignment from [NYU Production Quality Software](https://cs.nyu.edu/~michaels/pqsfall2017.html) course
* An alignment with [Google Java Style](https://google.github.io/styleguide/javaguide.html) guide

The game is played by two players who alternate dropping chips into a 7-column, 6-row grid.  The first player to get 4 in a row wins.

### Features
* A Swing GUI
* Support for 2-players playing at the same machine
* Support for a computer opponent that looks ahead a single move and makes that move if it results in a win

### Design
* Use of the Observer, Builder, Factory, and Singleton factory patterns
* Unit tests for the non-GUI code

### Execution
* *Option 1* import as **IntelliJ** project (environment all set)
* *Option 2* import as **Eclipse** project (environment all set)
* *Option 3* build **Ant** project, supports:
`ant init clean compile runapp runtests dist`


#### Note: Academic Integrity
Academic Integrity [policy & rules](https://cs.nyu.edu/home/undergrad/policy.html).