import java.io.IOException;
import java.util.*;

public class DZ3_3_baza {
    public static void main(String[] args) {
        EventRepository repos = new EventRepository();
        Scanner scan = new Scanner(System.in);
        System.out.println("Добро пожаловать в спортивный календарь");
        while (true){
            System.out.println("Выберите пункт меню:" +
                    "\n1. Добавить" +
                    "\n2. Удалить" +
                    "\n3. Расписание на дату" +
                    "\n4. Выйти");
            try {
                int number = scan.nextInt();
                switch (number){
                    case 1 ->{
                        System.out.println("Введите название:");
                        String name = scan.next();
                        System.out.println("Введите год");
                        int year = scan.nextInt();
                        if(year > 3000 || year < 1500){
                            throw  new IOException();
                        }
                        System.out.println("Введите номер месяца");
                        int month = scan.nextInt();
                        if(month < 1 || month > 12){
                            throw new IOException();
                        }
                        System.out.println("Введите день");
                        int day = scan.nextInt();
                        if(day < 1 || day > 31){
                            throw new IOException();
                        }
                        System.out.println("Введите час");
                        int hour = scan.nextInt();
                        if(hour < 1 || hour > 24){
                            throw new IOException();
                        }
                        System.out.println("Введите минуты");
                        int minutes = scan.nextInt();
                        if(minutes < 1 || minutes > 60){
                            throw new IOException();
                        }
                        repos.add(name,year,month,day,hour,minutes);
                    }
                    case 2 ->{
                        System.out.println("Введите название события, которое хотите удалить:");
                        String name = scan.next();
                        if(repos.remove(name)){
                            System.out.println("Успешно удалено");
                        }else{
                            System.out.println("Не найдено");
                        }
                    }
                    case 3 ->{
                        System.out.println("Введите год:");
                        int year = scan.nextInt();
                        if(year > 3000 || year < 1500){
                            throw  new IOException();
                        }
                        System.out.println("Введите месяц:");
                        int month = scan.nextInt();
                        if(month < 1 || month > 12){
                            throw new IOException();
                        }
                        System.out.println("Введите день:");
                        int day = scan.nextInt();
                        if(day < 1 || day > 31){
                            throw new IOException();
                        }
                        System.out.println("События на выбранный день:");
                        var events = repos.find(year,month,day);
                        for(Event e: events){
                            e.print();
                        }
                    }
                    case 4 ->{
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

class EventRepository{
    private ArrayList<Event> arr = new ArrayList();

    public ArrayList<Event> getArr(){
        return arr;
    }

    public void add(String name,
                    int year,
                    int month,
                    int day,
                    int hour,
                    int minutes){
        Calendar c = new GregorianCalendar();
        c.set(year,month-1,day,hour,minutes);
        this.arr.add(new Event(name,c));
    }

    public void add(Event e){
        this.arr.add(e);
    }

    public boolean remove(String name){
        boolean flag = false;
        for (int i = 0; i < arr.size();i++) {
            Event e = arr.get(i);
            if(e.getName().equals(name)){
                this.arr.remove(i);
                i--;
                flag = true;
            }
        }
        return flag;
    }

    public ArrayList<Event> find(int year, int month, int day){
        ArrayList<Event> result = new ArrayList<>();
        for (Event e:arr) {
            if(e.getDate().get(Calendar.YEAR) == year &&
                    e.getDate().get(Calendar.MONTH) == (month-1) &&
                    e.getDate().get(Calendar.DAY_OF_MONTH) == day){
                result.add(e);
            }
        }
        result.sort(new Event());
        return result;
    }
}

class Event implements Comparator<Event> {
    private String name;
    private Calendar date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Event(String name, Calendar date) {
        this.name = name;
        this.date = date;
    }

    public Event() {
    }

    public void print(){
        System.out.println("Название события: "+name);
        System.out.println("Дата события: "+date.getTime());
    }

    @Override
    public int compare(Event o1, Event o2) {
        return (int) (o1.date.getTimeInMillis()-o2.date.getTimeInMillis());
    }
}
