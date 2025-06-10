# pac-man
pac man game using javaFX.

### core principle is replace static assets with code generated graphics as much as possible
  - drawing pac man and open/close mouse animations using simple circle and arc math.
  - generate random mazes using maze generation algorithms.


### Features and fixes
- [X] generate random maze using recursive division maze generation algorithm
- [X] render pac man and mouse open/close animations using simple circle/arc math.
- [X] implement collision detection and prevention between pac man and the walls
- [X] make it easy for the player to move pac man in sharp/tight turns (when pac man size is close to the canvas maze cell size, it is more likely for pac man to get blocked to avoid collision with walls).
- [ ] enhance package names and class names
- [ ] put suger for pac man to eat.
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
