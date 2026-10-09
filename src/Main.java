public class Main {
    public static void main(String[] args){
        DrawManager manager=new DrawManager();

        manager.setupTeams();

        manager.simulateDraw();

        manager.presentDrawResults();
    }
}
