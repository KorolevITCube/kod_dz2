import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DZ2_task1 {

    private static ArrayList<String> kat1 = new ArrayList<>(Arrays.asList(
            "Шутка1","Шутка2","Шутка3"
    ));
    private static ArrayList<String> kat2 = new ArrayList<>(Arrays.asList(
            "кат2 - очень смешная шутка","кат2 - шутейка","кат2 - шутеха","кат2 - я пошутил"
    ));
    private static ArrayList<String> kat3 = new ArrayList<>(Arrays.asList(
            "кат3 - ух ты","кат3 - шутка минутка"
    ));

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Привет, пользователь!\n");
        while(true){
            System.out.println("Выбери нужную тебе категорию анекдота:" +
                    "\n\t1:Шутки за 300" +
                    "\n\t2:Интеллектуальные анекдоты" +
                    "\n\t3:Актуальное" +
                    "\n\t4:Выйти" +
                    "\nВведи цифру выбранного пункта");
            var command = 0;
            try{
                command = scan.nextInt();
            }catch(Exception e){
                System.out.println("Вводить нужно только числа!!!");
            }
            switch (command){
                case 1 -> {
                    System.out.println(kat1.get(rand.nextInt(kat1.size())));
                }
                case 2 -> {
                    System.out.println(kat2.get(rand.nextInt(kat2.size())));
                }
                case 3 -> {
                    System.out.println(kat3.get(rand.nextInt(kat3.size())));
                }
                case 4 -> {
                    System.out.println("До свидания!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Не понимаю команду, давайте попробуем сначала...");
                }
            }
        }
    }
}
