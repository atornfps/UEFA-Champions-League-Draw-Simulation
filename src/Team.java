import java.util.ArrayList;
import java.util.List;

public class    Team {
    private String name;
    private String country;
    private int potNumber;
    private List<Team> opponents;

    public Team(String name,String country,int potNumber){
        this.name=name;
        this.country=country;
        this.potNumber=potNumber;
        this.opponents=new ArrayList<>();
    }

    public boolean canPlayAgainst(Team otherTeam){
        //eğer aynı takımsa eşleşemez
        if(this.name.equalsIgnoreCase(otherTeam.name)){
            return false;
        }
        //aynı ülkenin takımları birbiriyle eşleşemez
        if(this.country.equalsIgnoreCase((otherTeam.country))){
            return false;
        }
        //hali hazırda eşleşmişler ise eşleşemezler
        if(this.opponents.contains(otherTeam)){
            return false;
        }

        int samePotCount=0;//bir torbada eşleşme sayacını kontrol eden bir değişken atanıyor
        int sameCountryOpponentCount=0;

        for(Team opp:this.opponents){ //seçilen takımın mevcut rakiplerinin tutulmuş olduğu listede dolaşır.
            if(opp.getPotNumber()==otherTeam.getPotNumber()){
                samePotCount++;
            }

            if(opp.getCountry().equalsIgnoreCase(otherTeam.getCountry())){
                sameCountryOpponentCount++;
            }
        }

        if(sameCountryOpponentCount>=2){
            return false;
        }

        return samePotCount<2;
    }

    public void addOpponent(Team otherTeam){
        this.opponents.add(otherTeam);
        otherTeam.getOpponents().add(this);
    }

    public String getName(){return name;}
    public String getCountry(){return country;}
    public int getPotNumber(){return potNumber;}
    public List<Team> getOpponents(){return opponents;}
}
