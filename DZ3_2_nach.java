import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class DZ3_2_nach {
    public static void main(String[] args){
        var budget = new Budget();
        Scanner scan = new Scanner(System.in);
        System.out.println("Добрый день, в этом номере не получилось добиться вывода, который указан в комментарии." +
                "\nЕсли проблема повторится - опишите входные данные более конкретно.");
        System.out.println("Добро пожаловать в спортивный календарь");
        while (true){
            System.out.println("Выберите пункт меню:" +
                    "\n1. Добавить поступление" +
                    "\n2. Добавить трату" +
                    "\n3. Вывести статистику за период" +
                    "\n4. Вывести оставшийся бюджет" +
                    "\n5. Выйти");
            try {
                int number = scan.nextInt();
                switch (number){
                    case 1 ->{
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
                        System.out.println("Введите сумму:");
                        int money = scan.nextInt();
                        if(money < 0){
                            throw new IOException();
                        }
                        budget.addEntering(money,month,day);
                    }
                    case 2 ->{
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
                        System.out.println("Введите сумму:");
                        int money = scan.nextInt();
                        if(money < 0){
                            throw new IOException();
                        }
                        budget.addRemoval(money,month,day);
                    }
                    case 3 ->{
                        System.out.println("Введите месяц начала периода:");
                        int monthStart = scan.nextInt();
                        if(monthStart < 1 || monthStart > 12){
                            throw new IOException();
                        }
                        System.out.println("Введите месяц конца периода:");
                        int monthEnd = scan.nextInt();
                        if(monthEnd < 1 || monthEnd > 12){
                            throw new IOException();
                        }
                        var data = budget.getMoneyForPeriod(monthStart,monthEnd);
                        System.out.println("Статистика:");
                        System.out.println("Всего потрачено: " + data.getRemove());
                        System.out.println("Всего заработано: " + data.getAdd());
                        System.out.println("Осталось денег вообще(не в период): " + data.getAvailable());
                    }
                    case 4 ->{
                        System.out.println("Осталось денег: " + budget.getAvailable());
                    }
                    case 5 ->{
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

class Budget{
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void addEntering(Integer money,Integer month,Integer day){
        transactions.add(new Transaction(money,true,createDate(month,day)));
    }

    public void addRemoval(Integer money, Integer month,Integer day){
        transactions.add(new Transaction(money,false,createDate(month,day)));
    }

    private Calendar createDate(Integer month, Integer day){
        var cal = new GregorianCalendar();
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DAY_OF_MONTH,day);
        return cal;
    }

    public BudgetData getMoneyForPeriod(Integer startMonth, Integer finishMonth){
        var start = new GregorianCalendar();
        start.set(Calendar.MONTH,startMonth-1);
        start.set(Calendar.DAY_OF_MONTH,1);
        var finish = new GregorianCalendar();
        finish.set(Calendar.MONTH,finishMonth-1);
        finish.set(Calendar.DAY_OF_MONTH,finish.getMaximum(Calendar.DAY_OF_MONTH));
        int add = 0;
        int remove = 0;
        int available = 0;
        for (Transaction tr:transactions) {
            if(tr.transactionDate.after(start) && tr.transactionDate.before(finish)){
                if(tr.adding){
                    add+=tr.money;
                }else{
                    remove+=tr.money;
                }
            }
            if(tr.adding){
                available+=tr.money;
            }else{
                available-=tr.money;
            }
        }
        if(available < 0) available = 0;
        return new BudgetData(add,remove,available);
    }

    public Integer getAvailable(){
        int available = 0;
        for (Transaction tr:transactions) {
            if(tr.adding){
                available+=tr.money;
            }else{
                available-=tr.money;
            }
        }
        return available;
    }

    class BudgetData{
        private Integer add;
        private Integer remove;
        private Integer available;

        public BudgetData(Integer add, Integer remove, Integer available) {
            this.add = add;
            this.remove = remove;
            this.available = available;
        }

        public Integer getAdd() {
            return add;
        }

        public Integer getRemove() {
            return remove;
        }

        public Integer getAvailable() {
            return available;
        }
    }

    class Transaction{
        private Integer money;
        private Boolean adding;
        private Calendar transactionDate;

        public Transaction(Integer money, Boolean adding, Calendar transactionDate) {
            this.money = money;
            this.adding = adding;
            this.transactionDate = transactionDate;
        }
    }
}
