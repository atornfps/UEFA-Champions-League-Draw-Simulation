# UEFA CHAMPIONS LEAGUE DRAW PHASE SIMULATION

This project is an Object-Oriented Programming (OOP) Java application that simulates the new UEFA Champions League (UCL) 36-team league format and its complex constraints via the terminal.

## Key Features
* **Dynamic Draw Algorithm:** 36 teams are appropriately allocated into 4 pots according to the official format.
* **Constraint Rules:** Teams from the same country cannot be drawn against each other. A team can play against maximum of 2 teams from any other single country.
* **Deadlock Resolution:** Features an automated "Retry" mechanism that resets and restarts the draw from scratch if the simulation reaches an unresolvable state (deadlock) in the final pots.
* **Symmetrical Matchmaking:** When a team draws an opponent, it is automatically appended to the opponent's fixture list to maintain strict data integrity.
* **Terminal Interface:** Provides a clean console output in a retro broadcast format, utilizing a custom alignment (padding) algorithm and standardized 3-letter country codes.

## Installation and Usage
**Requirements:** JDK 25 or higher

**Running the Project**
1. Upon Running the simulation, the user sequentially inputs the name and country data for the 36 teams across the 4 pots.
2. Once all data is successfully registered, the user progresses through the draw step-by-step using the "ENTER" key.

## System Architecture
**`Team`:** The core model class that encapsulates team data and provides encapsulation for matchmaking validation checks.
**`DrawManager`:** The controller class responsible for handling user inputs, managing the core draw algorithm, detecting deadlocks, and printing the fixtures to the console.
**`Main`:** A clean and simple entry point to initialize and launch the program.

## Known Limitations & Edge Case
* **Data Editing:** Once the user inputs a team's data (name or country), it cannot be edited. There is no undo functionally for incorrect entries.
* **Mathematical Impossibility:** The system requires a mathematically viable distribution of teams. If the input data is mathematically incompatible (e.g., all teams in a single pot belong to the same country), a valid draw cannot be completed. In this deadlocked scenario, the system's automated retry mechanism will enter an infinite loop attempting to resolve it.
* **Historical Data & Home/Away Mechanics:** The simulation focuses strictly on core draw mechanics and does not track home/away designations. Furthermore, historical match are not stored in a database. Consequently, the specific UEFA rule preventing two teams from playing in the same stadium for 3 consecutive seasons is intentionally omitted from the project scope.