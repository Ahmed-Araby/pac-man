# pac-man
pac man game using javaFX.

# core principle is replace static assets with code generated graphics as much as possible
  - drawing pac man and open/close mouse animations using simple circle and arc math.
  - generate random mazes using maze generation algorithms.

# Table Of Contents
- [Drawing Pac Man with open and closed Mouth using simple circle and Arc Math.](#drawing-pac-man-with-open-and-closed-mouth-using-simple-circle-and-arc-math)
- [Pac Man open and close mouth animation](#pac-man-open-and-close-mouth-animation)
- [Generating the Maze Programmatically](#generating-the-maze-programmatically)
- [Generating the Sugar](#generating-the-sugar)
- [Collision Detection](#collision-detection)
    - [Pac Man to Wall](#packman-to-wall)
    - [Pac Man to sugar](#pacman-to-sugar) 
- [Features And Fixes](#features-and-fixes)
  
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

<br><br>
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
 
<br><br><br><br>
## Pac Man open and close mouth animation.
![pac-man_open-close-mouth-animation](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_open-close-mouth-animation.gif)

to achieve the close mouse effect, there is a defined amount of pixels called **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** and a helper class called **PixelStrideTracker** that keeps track of the amount of pixels that pac-man has strided, and as long as the accumulated amount is less than **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** then pac-man will keep his mouse closed. after this threshold is reached pac-man will open his mouse.

to achieve the open mouse effect, there is an additional amount of pixels defined called **PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS**. once after the thresh hold **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** is exceeded pac-man will keep his mouth open as long as the accumulated amount of pixels is less than **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** + **PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS**.

once the accumulated amount of pixels exceeds or equal to **PAC_MAN_CLOSED_MOUSE_DISTANCE_PIXELS** + **PAC_MAN_OPEN_MOUSE_DISTANCE_PIXELS** the **PixelStrideTracker** will rest and start accumulating from the beginning. it's a process that the game repeats indefinitely.

### the following is a diagram illustrating the previous logic
![pac-man_open-and-closed-mouth-animation_logic](https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/pac-man/pac-man_open-and-closed-mouth-animation_logic.png)

<br><br><br><br>
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

<br><br><br><br>
## Generating the Sugar
sugar to be eaten by Pac-Man is a small circle generated at the center of an empty maze cell.

<br><br>
## Collision Detection
### Pac Man to Wall
Pac Man to wall collision Detection is a simple rectangle to rectangle collision, the first rectangle is a virtual rectangle that contains pac man (think of pac man as a circle) and the second rectangle is a maze cell with a wall in it.

however there is a bit of work to identify which Maze Cell / Wall to check for collision with, the following is an illustration of the process:
* Pac-Man is moving in a straight line to the right, and the player want to move downward
    * <img src="https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/collision/pac-man_moving-in-straight-line-to-the-right_before-collision.png" width="50%" height="50%"/>
* assume that Pac Man has moved in the desired direction, now we have the new position of Pac Man but it is not reflected in the game yet. we can call this position a **potentional** position. the following is an illustration of the potentional position (however this won't happen in the real gameplay as it would have been detected as a collision and pac-man would continue to move in straight line to the right instead).
    * <img src="https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/collision/pac-man_to_wall-collision_potentional_position.png" width="50%" height="50%"/>
* the potentional position as every thing else in this game is identified by the top left corner of the virtual rectangle that encloses the sprite (the sprite here is the programatically generated Pac-Man)
* using this top left corner, the other 3 corners of the virtual rectangle are calculated.
* each of the 4 corners of the virtual rectangle enclosing Pac-Man should lie within a maze cell (either empty, wall or out of the maze boundries). here the bottom left and bottom right corners lie within wall cells.
    * <img src="https://github.com/Ahmed-Araby/pac-man/blob/main/documentation/imgs/collision/pac-man_to_wall-collision_potentional_position.png" width="50%" height="50%"/>
* the 4 cells are identified using the 4 corners.
* the potentional position is approved to be the actual position of Pac Man if no cell of the 4 cells is a wall and no cell of the 4 cells lies outside the maze boundries.
* otherwise the potentional position is denied and Pac-Man don't move in the desired direction.
  
## Features And Fixes
- [X] generate random maze using recursive division maze generation algorithm
- [X] render pac man and mouse open/close animations using simple circle/arc math.
- [X] implement collision detection and prevention between pac man and the walls
- [X] enhance package names and class names
- [X] make it easy for the player to move pac man in sharp/tight turns (currently, when pac man size is close to the canvas maze cell size, it is more likely for pac man to get blocked to avoid collision with walls).
- [X] put suger for pac man to eat.
- [X] play sound when pac man eat sugar, use the observer design pattern for SoundPlayer class to get notified about detected collisions between pac man and sugar
- [ ] code refactor
  - [X] use events (observer pattern) for the interactions between the different game components (i.e. collision detection and side effects of this collisions in maze, sounds, pac man mouse animation, etc....)
  - [ ] make consistent use of the concepts Rect (Rectangle), Coordinate of the top left corner, and Canvas Cell
  - [ ] make consistent naming convention for variables
  - [ ] use deceorator/Adapter design pattern to abstract the creation of game ready maze, as the maze generator generate a generic boolean maze where 0 is empty cell and 1 is a cell with a wall.
- [ ] write documenntation and turotial for the work completed so far.
- [ ] put ghosts.
- [ ] track score, move to next level and game over.
- [ ] create the starting menu screen.
- [ ] generate mazes using different maze generation algorithms, and allow player to choose maze pattern (as each algorithm has its own maze pattern characteristics).
- [ ] look for performance enhancements using multithreading. __[reaching this point is my definition of done for this project, and every thing belllow will be made in a separate project]__
- [ ] read the game programming patterns book
- [ ] extract generic stuff into a simple game engine or just helper libraries.
- [ ] write extensive documentation on the implementation details.
- [ ] make the game multi player.
  - [ ] game server.
  - [ ] adapt game client to communicate with the game server.
- [ ] update documentation with the multiplayer stuff
- [ ] lunch the game to the public.
