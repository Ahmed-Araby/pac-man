# pac-man
pac man game using javaFX.

### core principle is replace static assets with code generated graphics as much as possible
  - drawing pac man and open/close mouse animations using simple circle and arc math.
  - generate random mazes using maze generation algorithms.


### Features and fixes
- [X] generate random maze using recursive division maze generation algorithm
- [X] render pac man and mouse open/close animations using simple circle/arc math.
- [X] implement collision detection and prevention between pac man and the walls
- [X] enhance package names and class names
- [X] make it easy for the player to move pac man in sharp/tight turns (currently, when pac man size is close to the canvas maze cell size, it is more likely for pac man to get blocked to avoid collision with walls).
- [X] put suger for pac man to eat.
- [X] play sound when pac man eat sugar, use the observer design pattern for SoundPlayer class to get notified about detected collisions between pac man and sugar
- [ ] code refactor
  - [ ] use events (observer pattern) for the interactions between the different game components (i.e. collision detection and side effects of this collisions in maze, sounds, pac man mouse animation, etc....)
  - [ ] make consistent use of the concepts Rect (Rectangle), Coordinate of the top left corner, and Canvas Cell
  - [ ] make consistent naming convention for variables
  - [ ] try to make use of PixelStrideTracker class as a mechanism for TurnBuffer to simplfy TurnBuffer implementation
- [ ] write documenntation and turotial for the work completed so far.
- [ ] put ghosts.
- [ ] track score, move to next level and game over.
- [ ] create the starting menu screen.
- [ ] generate mazes using different maze generation algorithms, and allow player to choose maze pattern (as each algorithm has its own maze pattern characteristics).
- [ ] look for performance enhancements using multithreading.
- [ ] extract generic stuff into a simple game engine or just helper libraries.
- [ ] write extensive documentation on the implementation details.
- [ ] make the game multi player.
  - [ ] game server.
  - [ ] adapt game client to communicate with the game server.
- [ ] update documentation with the multiplayer stuff
- [ ] lunch the game to the public.
