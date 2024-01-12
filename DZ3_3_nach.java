import java.util.ArrayList;
import java.util.Scanner;

public class DZ3_3_nach {
    private static final AnimalRepository refugeList = new AnimalRepository();
    private static final AnimalRepository placedList = new AnimalRepository();
    private static final EmployerRepository employers = new EmployerRepository();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Добро пожаловать в MES приюта");
        while (true){
            System.out.println("Выберите пункт меню:" +
                    "\n1. Добавить животное" +
                    "\n2. Внести запись о смерти животного" +
                    "\n3. Внести запись о пристройстве животного" +
                    "\n4. Добавить нового сотрудника" +
                    "\n5. Уволить сотрудника" +
                    "\n6. Вывести список пристроенных животных" +
                    "\n7. Вывести список животных в приюте" +
                    "\n8. Вывести список сотрудников" +
                    "\n9. Выйти");
            try {
                int number = scan.nextInt();
                switch (number){
                    case 1 ->{
                        System.out.println("Введите имя животного:");
                        refugeList.add(new Animal(scan.next()));
                    }
                    case 2 ->{
                        System.out.println("Введите имя животного:");
                        var animal = refugeList.find(scan.next());
                        if(animal == null) {
                            System.out.println("Животное с таким именем не найдено.");
                            break;
                        }
                        animal.die(refugeList);
                    }
                    case 3 ->{
                        System.out.println("Введите им животного:");
                        var animal = refugeList.find(scan.next());
                        if(animal == null) {
                            System.out.println("Животное с таким именем не найдено.");
                            break;
                        }
                        animal.giveToHuman(refugeList,placedList);
                    }
                    case 4 ->{
                        System.out.println("Введите имя сотрудника:");
                        employers.add(new Employer(scan.next()));
                    }
                    case 5 -> {
                        System.out.println("Введите имя сотрудника:");
                        var empl = employers.find(scan.next());
                        if(empl == null) {
                            System.out.println("Сотрудник с таким именем не найден.");
                            break;
                        }
                        empl.fire(employers);
                    }
                    case 6 ->{
                        System.out.println("Список пристроенных животных:");
                        for (Animal a:placedList.getAll()) {
                            System.out.println(a.getName());
                        }
                    }
                    case 7 ->{
                        System.out.println("Список животных в приюте:");
                        for (Animal a:refugeList.getAll()) {
                            System.out.println(a.getName());
                        }
                    }
                    case 8 ->{
                        System.out.println("Список сотрудников:");
                        for (Employer e:employers.getAll()) {
                            System.out.println(e.getName());
                        }
                    }
                    case 9 ->{
                        System.exit(0);
                    }
                }
            }catch(Exception e){
                System.out.println("Что-то пошло не так, " +
                        "попробуйте снова");
            }
        }
    }
}



class Animal{
    private String name;

    public String getName() {
        return name;
    }

    public Animal(String name){
        this.name = name;
        System.out.println("Питомец " + this.getName() + " добавлен в приют.");
    }
    public void die(AnimalRepository list){
        System.out.println("Питомец " + this.getName() + " умер.");
        list.remove(this);
    }

    public void giveToHuman(AnimalRepository list,AnimalRepository humansList){
        System.out.println("Питомец " + this.getName() + " успешно пристроен.");
        list.remove(this);
        humansList.add(this);
    }
}

class Employer{
    private String name;

    public String getName() {
        return name;
    }

    public Employer(String name){
        this.name = name;
    }

    public void fire(EmployerRepository list){
        System.out.println("Вы уволили работника " + this.getName());
        list.remove(this);
    }
}

interface IRepository<T>{
    void add(T being);
    void remove(T being);
    T find(String name);
    ArrayList<T> getAll();
}

class AnimalRepository implements IRepository<Animal>{
    private final ArrayList<Animal> list = new ArrayList<>();

    @Override
    public void add(Animal being) {
        if (!this.list.contains(being)) {
            this.list.add(being);
        }else{
            System.out.println("Ошибка! Это животное уже добавлено");
        }
    }

    @Override
    public void remove(Animal being) {
        this.list.remove(being);
        System.out.println("Вы убрали животное " + being.getName());
    }

    @Override
    public Animal find(String name) {
        for (int i = 0; i<=list.size();i++){
            if (this.list.get(i).getName().equals(name)){
                return this.list.get(i);
            }
        } return null;
    }

    @Override
    public ArrayList<Animal> getAll() {
        return list;
    }
}

class EmployerRepository implements IRepository<Employer>{
    private final ArrayList<Employer> list = new ArrayList<>();

    @Override
    public void add(Employer being) {
        if (!this.list.contains(being)) {
            this.list.add(being);
            System.out.println("Вы добавили сотрудника " + being.getName() + " в приют.");
        }else{
            System.out.println("Ошибка! Этот сотрудник уже добавлен.");
        }
    }

    @Override
    public void remove(Employer being) {
        this.list.remove(being);
        System.out.println("Вы уволили сотрудника " + being.getName() +".");
    }

    @Override
    public Employer find(String name) {
        for (int i = 0; i<=list.size();i++){
            if (this.list.get(i).getName().equals(name)){
                return this.list.get(i);
            }
        } return null;
    }

    @Override
    public ArrayList<Employer> getAll() {
        return list;
    }
}
