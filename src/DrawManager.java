import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;

public class DrawManager {

    public void presentDrawResults(){
        System.out.println("\n==================================================");
        System.out.println("UEFA CHAMPIONS LEAGUE DRAW SIMULATION");
        System.out.println("==================================================");

        System.out.println("\nPress 'ENTER' to start the draw ceremony (or type 'Q' to quit): ");
        String choice=scanner.nextLine().trim();

        if(choice.equalsIgnoreCase("Q")){
            System.out.println("Draw ceremony canceled. Exiting...");
            return;
        }

        for(int potNum=1;potNum<=4;potNum++){
            System.out.println("\n\n**************************************************");
            System.out.println("COMMENCING POT "+potNum+" DRAW");
            System.out.println("**************************************************\n");

            List<Team> currentPotTeams=new ArrayList<>(pots.get(potNum));
            Collections.shuffle(currentPotTeams);

            for(Team team:currentPotTeams){
                System.out.println("========================================");
                System.out.println("Drawing the next team... (PRESS ENTER): ");
                System.out.println("========================================");
                scanner.nextLine();

                String mainCountry=team.getCountry().length()>=3?
                        team.getCountry().substring(0,3).toUpperCase():
                        team.getCountry().toUpperCase();

                String mainTeamInfo= team.getName().toUpperCase()+"("+mainCountry+")";
                System.out.print(mainTeamInfo);

                String padding=" ".repeat(mainTeamInfo.length());

                for(int i=1;i<=4;i++){
                    int currentPotTarget=i;

                    List<Team> potOpponents=new ArrayList<>();
                    for(Team opp:team.getOpponents()){
                        if(opp.getPotNumber()==i){
                            potOpponents.add(opp);
                        }
                    }

                    String opponentsString="";
                    for(int j=0;j<potOpponents.size();j++){
                        Team opp=potOpponents.get(j);

                        String oppCountry=opp.getCountry().length()>=3?
                                opp.getCountry().substring(0,3).toUpperCase():
                                opp.getCountry().toUpperCase();

                        opponentsString +=opp.getName()+"("+oppCountry+")";

                        if(j<potOpponents.size()-1){
                            opponentsString+="-";
                        }
                    }

                    if(i==1){
                        System.out.println("(pot"+i+") "+opponentsString);
                    }else{
                        System.out.println(padding+"(pot"+i+") "+opponentsString);
                    }
                }
                System.out.println();
            }
        }

        System.out.println("\n");
        System.out.println("☆★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★☆");
        System.out.println("☆UEFA CHAMPIONS LEAGUE DRAW CONCLUDED!☆");
        System.out.println("☆★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★☆");
    }
    //Torbaları tutmak için
    private Map<Integer, List<Team>> pots;
    //Aynı takımın iki kere yazılmasını engellemek için bir Map
    private Map<String, Team> allTeams;
    private Scanner scanner;


    public DrawManager(){
        pots=new HashMap<>();
        for(int i=1;i<=4; i++){
            pots.put(i,new ArrayList<>());
        }
        allTeams=new HashMap<>();
        scanner=new Scanner(System.in);
    }

    public void setupTeams(){
        System.out.println("Welcome to UEFA Champions League Draw Simulation!");
        System.out.println("Please enter the 36 teams sequentially for the 4 pots \n");

        for(int potNum=1;potNum<=4;potNum++){
            System.out.println("--- Pot "+potNum+" Teams ---");
            List<Team> currentPot=pots.get(potNum);

            while(currentPot.size()<9){
                System.out.print("Team "+(currentPot.size() +1)+" Name: ");
                String teamName=scanner.nextLine().trim();

                if(teamName.isEmpty()){
                    System.out.println("Warning! Team name cannot be empty.");
                    continue;
                }

                if(allTeams.containsKey(teamName.toLowerCase())){
                    System.out.println("ERROR! "+teamName+" has already been added. Please enter a different team.");
                    continue;
                }

                System.out.print("Country of "+teamName+" : ");
                String country=scanner.nextLine().trim();

                Team newTeam=new Team(teamName,country,potNum);
                currentPot.add(newTeam);
                allTeams.put(teamName.toLowerCase(), newTeam);
            }
            System.out.println("Pot "+potNum+" succefully completed.\n");
        }
        System.out.println("All 36 teams have been succefully registered.");
        System.out.println("Ready for the draw");
    }

    private void resetDraw(){
        for (int i=1;i<=4;i++){
            for(Team team: pots.get(i)){
                team.getOpponents().clear();
            }
        }
    }

    public void simulateDraw(){
        System.out.println("\n Starting the Draw Simulation.");
        Random random=new Random();
        boolean drawSuccesful=false;
        int attemptCount=0;

        while(!drawSuccesful){
            attemptCount ++;
            resetDraw();
            boolean deadlockOccured=false;

            drawLoop:

            for(int potNum=1;potNum<=4;potNum++){
                for(Team currentTeam:pots.get(potNum)){
                    for(int targetPot=1;targetPot<=4;targetPot++){


                        int currentOpponentsFromTargetPot=0;
                        for(Team opp:currentTeam.getOpponents()){
                            if(opp.getPotNumber()==targetPot){
                                currentOpponentsFromTargetPot++;
                            }
                        }
                        int neededOpponents=2-currentOpponentsFromTargetPot;


                        while(neededOpponents>0){
                            List<Team> validOpponents=new ArrayList<>();

                            for (Team potentialOpponent:pots.get(targetPot)){
                                if(currentTeam.canPlayAgainst(potentialOpponent)&&potentialOpponent.canPlayAgainst(currentTeam)){
                                    validOpponents.add(potentialOpponent);
                                }
                            }

                            if(validOpponents.isEmpty()){
                                deadlockOccured=true;
                                break drawLoop;
                            }

                            int randomIndex=random.nextInt(validOpponents.size());
                            Team chosenOpponent=validOpponents.get(randomIndex);

                            currentTeam.addOpponent(chosenOpponent);
                            neededOpponents--;
                        }
                    }
                }
            }
            if (!deadlockOccured){
                drawSuccesful=true;
                System.out.println("\nDraw Simulation Completed Successfully.");
            }
        }
    }
}
