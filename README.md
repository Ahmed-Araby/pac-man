# pac-man
pac man game using javaFX.

**core principle is to replace static assets with code generated graphics as much as possible**
  - drawing pac man and open/close mouse animations using simple circle and arc math.
  - generate random mazes using maze generation algorithms.

# Table Of Contents
- [Sprites Class Structure](#sprites-class-structure)
- [Ghost Mode Class Structure](#ghost-mode-class-structure)
- [Drawing Pac Man with open and closed Mouth using simple circle and Arc Math.](#drawing-pac-man-with-open-and-closed-mouth-using-simple-circle-and-arc-math)
- [Pac Man open and close mouth animation](#pac-man-open-and-close-mouth-animation)
- [Generating the Maze Programmatically](#generating-the-maze-programmatically)
- [Generating the Sugar](#generating-the-sugar)
- [Collision Detection](#collision-detection)
    - [Architecture](#architecture) 
    - [Pac Man to Wall](#pac-man-to-wall)
    - [Pac Man to sugar](#pac-man-to-sugar)
    - [Pac Man to Ghost](#pac-man-to-ghost)
- [Turn Buffer](#turn-buffer)
- [Ghosts](#ghosts)
- [Backlog](#backlog)
- [Resources](#resources)

<br><br>
## Sprites Class Structure
![Sprites Class Architecture](https://github.com/Ahmed-Araby/pac-man/blob/collision-detection-refactoring/documentation/imgs/sprite-class-structure.png)

<br><br>
## Ghost Mode Class Structure
![Ghost Mode Class Structure](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/ghost-mode-class-structure.png)

<br><br>
## Drawing Pac Man with open and closed Mouth using simple circle and Arc Math.
Pac-Man with open mouth is just an Arc on a Circle. an Arc on a Circle is defined by the circle diameter, the start and end angles and drawing direction from the start angle to the end angle.

### JavaFx provide a fillArc method that requires
* the top left corner row and col of the rectangle that virtually encloses the circle (annotated in the following diagaram as (x, y)
* the diameter of the circle that the Arc is virtually drawn on its circumference.
* start angle in degress, where the angle 0 is in the middle of the circle at the right side.
* arc extent which is the distance in degress between the start angle and the end angle while moving in **counter clock wise direction**.
* arc type, in our situation it will be closed and this type give the desired output of a filled circle (Pac-Man) with clipped portion (the open mouth).

### The following is a diagram illustrating the arguments expected by fillArc method and the final result rendered in the game play.
![pac-man_circle-arc_generating_right-open-mouth](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_circle-arc_generating_right-open-mouth.drawio.png)


### The following are the exact start angle and arc extent in degrees that I have used to draw Pac-Man 4 different open mouth positions and Pac-Man with closed mouth.
* Pac-Man with mouth open to the right
    * start angle =  45 degree
    * arc extent = 270
    * result: ![pac-man_with_right-open-mouth](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_with_right-open-mouth.png)
* Pac-Man with mouth open upward
    * start angle =  135 degree
    * arc extent = 270
    * result: ![pac-man_with_mouth-open-upward](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_with_mouth-open-upward.png)
* Pac-Man with mouth open to the left
    * start angle =  225 degree
    * arc extent = 270
    * result: ![pac-man_with_mouth-open-to-the-left](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_with_mouth-open-to-the-left.png)
* Pac-Man with mouth open to Down
    * start angle =  315 degree
    * arc extent = 270
    * result: ![pac-man_with_mouth-open-downward](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_with_mouth-open-downward.png)
* Pac-Man with closed mouth
    * start angle =  0 degree
    * arc extent = 360
    * result: ![pac-man_with_closed-mouth](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_with_closed-mouth.png)

<br><br>
## Pac Man open and close mouth animation.
![pac-man_open-close-mouth-animation](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_open-close-mouth-animation.gif)

to achieve the close mouse effect, there is a defined amount of pixels called **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** and a helper class called **PixelStrideTracker** that keeps track of the amount of pixels that pac-man has strided, and as long as the accumulated amount is less than **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** then pac-man will keep his mouse closed. after this threshold is reached pac-man will open his mouse.

to achieve the open mouse effect, there is an additional amount of pixels defined called **PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS**. once after the thresh hold **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** is exceeded pac-man will keep his mouth open as long as the accumulated amount of pixels is less than **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** + **PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS**.

once the accumulated amount of pixels exceeds or equal to **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** + **PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS** the **PixelStrideTracker** will rest and start accumulating from the beginning. it's a process that the game repeats indefinitely.

### the following is a diagram illustrating the previous logic
![pac-man_open-and-closed-mouth-animation_logic](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_open-and-closed-mouth-animation_logic.png)

<br><br>
## Generating the Maze Programmatically
sticking to the main princible of this project, the Maze is programmatically generated instead of using a static asset.

there are different algorithms to generate random mazes. the one I have used called Recursive Division. 

### Recursice Divisoin for Random Maze generation
![recursive-division-for-random-maze-generation](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/maze/recursive-division-for-random-maze-generation.png)
*Image Source (Wikipedia): [Maze_generation_algorithm](https://en.wikipedia.org/wiki/Maze_generation_algorithm)*
<br><br>
* Chamber = maze
* a maze is 2D boolean array. a wall is true, a passage is false.
* start with the maze empty, i.e. the maze is filled with flase.
* pick a random column that doesn't lie on the borders and fill this column with walls
* pick a random row that doesn't lie on the borders and fill this row with walls
* **then you will end up with 4 sub mazes, however they are isolated from each other yet**
* select 3 random sides around the intersection point of the chosen row and column.
* make a passage (open a wall) in each of these sides.
* **now the 4 sub mazes are connected and can reach each other**
* now, solve the problem recursively on each of the 4 sub mazes

**I am planning to implement other algorithms mentioned in Wikipedia page and give the palyer an option to select the algorithm used to generate the maze that he will play in.**

<br><br>
## Generating the Sugar
sugar to be eaten by Pac-Man is a small circle generated at the center of an empty maze cell.

<br><br>
## Collision Detection
### Architecture
![Collision Detection Architecture](https://github.com/Ahmed-Araby/pac-man/blob/collision-detection-refactoring/documentation/imgs/collision/collision-code-architecture.png)
### Pac Man to Wall
Pac Man to wall collision Detection is a simple rectangle to rectangle collision, the first rectangle is a virtual rectangle that contains pac man (think of pac man as a circle) and the second rectangle is a maze cell with a wall in it.

however there is a bit of work to identify which Maze Cell / Wall to check for collision with, the following is an illustration of the process:
* Pac-Man is moving in a straight line to the right, and the player want to move downward
    * <img src="https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/collision/pac-man_moving-in-straight-line-to-the-right_before-collision.png" width="50%" height="50%"/>
* assume that Pac Man has moved in the desired direction, now we have the new position of Pac Man but it is not reflected in the game yet. we can call this position a **potentional** position. **the following is an illustration of the potentional position (however this won't happen in the real gameplay as it would have been detected as a collision and pac-man would continue to move in straight line to the right instead).**
    * <img src="https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/collision/pac-man_to_wall-collision_potentional_position.png" width="50%" height="50%"/>
* the potentional position as every thing else in this game is identified by the top left corner of the virtual rectangle that encloses the sprite (the sprite here is the programatically generated Pac-Man)
* using this top left corner, the other 3 corners of the virtual rectangle are calculated.
* each of the 4 corners of the virtual rectangle enclosing Pac-Man should lie within a maze cell (either empty, wall or out of the maze boundries). **here the bottom left and bottom right corners lie within wall cells.**
    * <img src="https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/collision/pac-man_to_wall-collision_potentional_position.png" width="50%" height="50%"/>
* the 4 cells are identified using the 4 corners.
* the potentional position is approved to be the actual position of Pac Man if no cell of the 4 cells is a wall and no cell of the 4 cells lies outside the maze boundries.
* otherwise the potentional position is denied and Pac-Man don't move in the desired direction.


### Pac Man to Sugar
it is as simple as checking that the virtual rectangle enclosing the sugar lies completely within the virtual rectangle enclosing Pac-Man.

### Pac Man to Ghost
TBD

<br><br>
## Turn buffer
it is almost impossible for the player to instruct Pac Man to take a turn manually, the player will always do this at a position such that taking the turn will cause collision between Pac-Man and a Wall. to solve this problem, an Automatic Assistance is provided for the player.

when the player instruct Pac-Man to take a turn and fail, the game remember the intent of the player to take the next turn. then the game will do this automatically for him in a way that sounds very natural.

the way this is implemented is by recording the current position of Pac-Man and the direction of the turn that failed and keep this instruction buffered until Pac-Man reaches the next Cell (in respect to the position recorded when the turn attempt failed). until the next Maze cell is reached by Pac-Man the game keeps trying to take the next turn on behalf of the player, hence it is guaranted that the game will make the turn at the perfect position where taking a turn won't cause coliision with a Wall.

if the turn is still not possible and Pac-Man is now in the next Cell (in respect to the position recorded when the turn attempt failed) then the buffer is erased as it might be the case that the player made instruction to take a right turn but all the cells to the right of Pac-Man are walls, hence it is impossible to make this turn.

## Ghosts
### $\color{red}{\textsf{Blinky }}$
| Mode    | Description | Entering Condition  | Initial Behaviour | Movement Strategy | Exit Condition | Special Rules |
| -------- | ------- |  ------- |  ------- |  ------- |  ------- |  ------- | 
| Scatter  | -    | - | - | - | turn around when entering this mode after leaving Chaser mode | switch to Chaser mode if a certain low number of dots/suger (20 dot in the first level, )remain in the Playground |
| Chaser   | -    | - | - | - | turn around | - |
| Frightened    | ghosts become food for PacMan, they turn into blue and start to move randomely | PacMan eating power pallet / super suger | turn around | Pick eligable direction at random | time up | - |
| Eaten    | -    | - | - | - | - | - |

### $\color{cyan}{\textsf{Inky }}$
| Mode    | Description | Entering Condition  | Initial Behaviour | Target Tile | Movement Strategy | Exit Condition | Special Rules |
| -------- | ------- |  ------- |  ------- |  ------- |  ------- |  ------- |  ------- | 
| Scatter  | -    | timer based | turn around when entering this mode after leaving chaser mode | calculate the tile 2 steps ahead of Pac Man, call it Intermediate Tile. then calculate a vector from Blinky position to the Intermediate Tile. then double this vector. the tile at the end of this doubled vector is the target tile for Inky | pick the next adjacent tile that is closest to the target tile | timer up | - |
| Chaser   | -    | timer based | turn around | - | pick the next adjacent tile that is closes to the target tile | timer up | the 4th transition from scattered to chaser is the last one between these 2 modes |
| Frightened    | ghosts become food for PacMan, they turn into blue and start to move randomely | PacMan eating power pallet / super suger | turn around | - | Pick eligable direction at random | timer up | - | 
| Eaten    | -    | - | - | - | - | - |


### $\color{pink}{\textsf{Pinky }}$ ![Pinky](https://github.com/Ahmed-Araby/pac-man/blob/pinky/documentation/imgs/ghosts/pinky.png)
| Mode     | Description | Entering Condition  | Initial Behaviour | Target Tile | Movement Strategy | Exit Condition | Special Rules |
| -------- | ------- |  ------- |  ------- |  ------- |  ------- |  ------- |  ------- | 
| Scatter  | Move towards a corner | timer based | turn around when entering this mode after leaving chaser mode | top left corner of the playground | move to an the adjacent cell clossest to target tile | timer up | - |
| Chaser   | chase pacman | timer based  | turn around | 4 tiles infront of pacman | ? | timer up | after the 4th transition from Scatter to Chaser mode, this mode becomes permanent and the ghost doesn't switch back to Scatter mode again |

## Backlog
- [X] generate random maze using recursive division maze generation algorithm
- [X] render pac man and mouse open/close animations using simple circle/arc math.
- [X] implement collision detection and prevention between pac man and the walls
- [X] enhance package names and class names
- [X] make it easy for the player to move pac man in sharp/tight turns (currently, when pac man size is close to the canvas maze cell size, it is more likely for pac man to get blocked to avoid collision with walls).
- [X] put suger for pac man to eat.
- [X] play sound when pac man eat sugar, use the observer design pattern for SoundPlayer class to get notified about detected collisions between pac man and sugar
- [X] code refactor: use events (observer pattern) for the interactions between the different game components (i.e. collision detection and side effects of this collisions in maze, sounds, pac man mouse animation, etc....)
- [X] write documenntation and turotial for the work completed so far.
- [X] Ghosts
  - [X] Blinky
  - [X] Inky
  - [X] Pinky
  - [X] Clyde
- [ ] Frozen, Ghost Mode
- [ ] Refactoring
  - [X] Collision Detection
  - [ ] combine data with its behaviour when appropriate (tell, don't ask prinsible)
  - [ ] Event Structure
  - [ ] sprite movement in relation to frames and time.
  - [ ] sprite animation in relation to sprite movement.
  - [X] pac man movement
  - [ ] Ghost Utility classes
  - [ ] maze vs canvas
  - [ ] logging
  - [ ] unify Scattered Modes
  - [ ] use builder pattern instead of constructors
- [ ] Dynamic Maze Generation
  - [X] Fix Bug: make sure the maze is a connected graph, i.e. Pac Man and Ghosts can reach any place in the maze
  - [X] put Ghost house in the playground
  - [ ] implement at least one more dynamic maze generation Algorithm.
- [ ] Game Levels
- [ ] Performance Enhancement
  - [ ] use multi threading in maze generation
  - [ ] use different threads to execute logic that is not core to the game loop

## Resources
- [TAC380 - Pac Man](https://itp380.org/Lab05.html)
- [Paper, the Pac Man Benchmark](https://cescg.org/wp-content/uploads/2017/03/Smid-The-Pacman-Benchmark-3.pdf)\
- [Maze Generation, Resurcive division](https://www.cs.columbia.edu/~sedwards/classes/2021/4995-fall/reports/Maze-Solver.pdf)
- [Maze Generation, Resurcive division](https://weblog.jamisbuck.org/2011/1/12/maze-generation-recursive-division-algorithm)
- [State Design Pattern, Youtube video](https://www.youtube.com/watch?v=5OzLrbk82zY)
- [State Design Pattern, Game Programming Patterns Book](https://gameprogrammingpatterns.com/state.html)
- [State Design Pattern](https://refactoring.guru/design-patterns/state)
- [BFS](https://cp-algorithms.com/graph/breadth-first-search.html#__tabbed_1_2)
- [classic Pac Man maze asset](https://github.com/bobeff/pacman/blob/master/assets/maze.txt)
- [Pac Man, Ghost AI Explained, Youtube video](https://www.youtube.com/watch?v=ataGotQ7ir8)
- [Building Pac Man, Blog](https://pacmancode.com/)
- [tell, don't ask OOP design principal](https://martinfowler.com/bliki/TellDontAsk.html)
