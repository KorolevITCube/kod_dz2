
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DZ3_2_baza {

    public static int account_id = 0;

    private static Account find(ArrayList<Account> list, Integer id){
        for (Account a:list) {
            if(a.getId() == id){
                return a;
            }
        }
        return null;
    }

    public static void main(String[] args){
        ArrayList<Account> accounts = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Добро пожаловать в банковское приложение");
        while (true){
            System.out.println("Выберите пункт меню:" +
                    "\n1. Создать кредитный счет" +
                    "\n2. Создать дебетовый счет" +
                    "\n3. Снять со счета" +
                    "\n4. Положить на счет" +
                    "\n5. Показать все счета" +
                    "\n6. Выйти");
            try {
                int number = scan.nextInt();
                switch (number){
                    case 1 ->{
                        account_id++;
                        System.out.println("Введите ФИО владельца счета:");
                        var fio = scan.next();
                        System.out.println("Введите кредитный лимит: ");
                        var limit = scan.nextInt();
                        if(limit <= 0){
                            System.out.println("Лимит не может быть меньше нуля");
                            throw new IOException();
                        }
                        accounts.add(new CreditAccount(account_id,fio,limit));
                        System.out.println("Успешно создано!");
                    }
                    case 2 ->{
                        account_id++;
                        System.out.println("Введите ФИО владельца счета:");
                        var fio = scan.next();
                        System.out.println("Введите начальный баланс: ");
                        var balance = scan.nextInt();
                        if(balance <= 0){
                            System.out.println("Начальный баланс не может быть меньше нуля");
                            throw new IOException();
                        }
                        System.out.println("Введите начисляемый процент (0-100): ");
                        var percent = scan.nextInt() / 100.0f;
                        accounts.add(new AdjectiveAccount(account_id,balance,fio,percent));
                        System.out.println("Успешно создано!");
                    }
                    case 3 ->{
                        System.out.println("Введите id счета для снятия: ");
                        var id = scan.nextInt();
                        var acc = find(accounts,id);
                        if(acc == null){
                            System.out.println("Счет не найден.");
                            throw new IOException();
                        }
                        System.out.println("Введите сумму снятия: ");
                        var sum = scan.nextInt();
                        if(acc.takeOff(sum)){
                            System.out.println("Успешно снято.");
                        }else {
                            System.out.println("Ошибка снятия.");
                        }
                    }
                    case 4 ->{
                        System.out.println("Введите id счета для пополнения: ");
                        var id = scan.nextInt();
                        var acc = find(accounts,id);
                        if(acc == null){
                            System.out.println("Счет не найден.");
                            throw new IOException();
                        }
                        System.out.println("Введите сумму пополнения: ");
                        var sum = scan.nextInt();
                        if(acc.put(sum)){
                            System.out.println("Успешно пополнено.");
                        }else {
                            System.out.println("Ошибка пополнения.");
                        }
                    }
                    case 5 ->{
                        for (Account a:accounts){
                            a.print();
                        }
                    }
                    case 6 ->{
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

class Account{
    protected Integer accountNumber;
    protected Integer balance;
    protected String accountOwner;

    public Account(Integer accountNumber, Integer balance, String accountOwner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountOwner = accountOwner;
    }

    public boolean takeOff(Integer money){
        if(money > 0) {
            if (balance - money > 0) {
                balance -= money;
                System.out.println("Снято со счета: " + money);
                return true;
            } else {
                System.out.println("Нехватает средств");
                return false;
            }
        }else{
            System.out.println("Неверные входные данные");
            return false;
        }
    }

    public boolean put(Integer money){
        if(money > 0) {
            balance += money;
            System.out.println("Зачислено на счет: " + money);
            return true;
        }else{
            System.out.println("Отрицательное пополнение");
            return false;
        }
    }

    public Integer getBalance(){
        System.out.println("Баланс счета " + accountNumber + " : " + balance);
        return balance;
    }

    public Integer getId(){
        return this.accountNumber;
    }

    public void print() {
        System.out.println("Номер счета: " + this.accountNumber);
        System.out.println("Владелец счета: "+ this.accountOwner);
        System.out.println("Баланс счета: " + this.balance);
    }
}

class CreditAccount extends Account{
    private Integer creditLimit;

    public CreditAccount(Integer accountNumber, String accountOwner, Integer creditLimit) {
        super(accountNumber, creditLimit, accountOwner);
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean put(Integer money) {
        if(money < 0){
            System.out.println("Некорректный ввод");
            return false;
        }
        if(balance + money > creditLimit){
            System.out.println("Вы не можете положить эту сумму, тк она превышает ваш лимит: " + creditLimit);
            return false;
        }else{
            return super.put(money);
        }
    }

    @Override
    public void print(){
        System.out.println("Кредитный счет");
        super.print();
        System.out.println("Лимит счета: " + creditLimit);
    }
}

class AdjectiveAccount extends Account{
    private Float percent;

    public AdjectiveAccount(Integer accountNumber, Integer balance, String accountOwner, Float percent) {
        super(accountNumber, balance, accountOwner);
        this.percent = percent;
    }

    @Override
    public boolean put(Integer money) {
        var result = super.put(money);
        if(result){
            balance += (int)(balance * percent);
        }
        return result;
    }

    @Override
    public boolean takeOff(Integer money) {
        var result = super.takeOff(money);
        if(result){
            balance += (int)(balance * percent);
        }
        return result;
    }

    @Override
    public void print(){
        System.out.println("Накопительный счет");
        super.print();
        System.out.println("Процент на остаток счета: " + percent);
    }
}