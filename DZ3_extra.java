import java.util.ArrayList;
import java.util.Scanner;

public class DZ3_extra {
    public static void main(String[] args){
        Store store = new Store();
        Cart cart = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("В интреактивном режиме реализованы только те метод, которые требовались в 4м пункте. Остальное просто реализовано. Функционал не совсем логичен.");
        System.out.println("Генерация тестовых товаров,тк создание не предусмотрено условием...");
        var p = new Product("test1","rus",1000,10);
        store.add(p);
        p = new Product("test2","en",1500,4);
        store.add(p);
        p = new Product("test3","fran",800,6);
        store.add(p);
        p = new Product("test4","belg",700,8);
        store.add(p);
        while(true){
            try{
                System.out.println("Выберите нужный пункт меню:\n" +
                        "1. Просмотреть список товаров\n" +
                        "2. Добавить товар в корзину\n" +
                        "3. Оформить заказ\n" +
                        "4. Посмотреть статистику\n" +
                        "5. Выйти из приложения");
                int command = sc.nextInt();
                switch (command){
                    case 1 -> {
                        ArrayList<Product> temp = store.getStock();
                        for (int i = 0; i < temp.size(); i++){
                            System.out.println("ID - " + i);
                            System.out.println(temp.get(i).toString());
                        }
                    }
                    case 2 -> {
                        if(cart == null){
                            System.out.println("Создана новая корзина");
                            cart = new Cart();
                        }
                        System.out.println("Введите id товара:");
                        int id = sc.nextInt();
                        cart.add(store.getStock().get(id));
                        System.out.println("Товар успешно добавлен");
                    }
                    case 3 -> {
                        if(cart != null) {
                            store.addOrder(cart);
                            int count = 0;
                            for (Product pr : cart.getProducts()) {
                                count += pr.getCost();
                            }
                            System.out.println("Заказ успешно оформлен. " +
                                    "Сумма заказа: " + count);
                            cart = null;
                        }else{
                            System.out.println("Корзина пуста");
                        }
                    }
                    case 4 -> {
                        System.out.println("Всего заказано на сумму: " + store.getStat());
                    }
                    case 5 -> {
                        System.exit(0);
                    }
                }
            }catch (Exception e){
                System.out.println("Что-то пошло не так," +
                        " попробуйте снова.");
            }
        }
    }
}

class Product{
    public Product(String name, String madeIn, Integer cost, Integer count) {
        this.name = name;
        this.madeIn = madeIn;
        this.cost = cost;
        this.count = count;
    }

    private String name;
    private String madeIn;
    private Integer cost;
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Товар  " +
                "название='" + name + '\'' +
                ", Сделан в стране='" + madeIn + '\'' +
                ", Стоимость=" + cost +
                ", Количество=" + count;
    }
}

class Cart{
    private ArrayList<Product> products = new ArrayList();

    public void add(Product prod){
        products.add(prod);
    }

    public void remove(Product prod){
        products.remove(prod);
    }

    public int countCost(){
        int count = 0;
        for (Product p:products){
            count += p.getCost();
        }
        return count;
    }

    public ArrayList<Product> getProducts(){
        return this.products;
    }
}

class Store{
    private ArrayList<Product> stock = new ArrayList<>();
    private ArrayList<Cart> orders = new ArrayList<>();

    public ArrayList<Product> getStock() {
        return stock;
    }

    public ArrayList<Cart> getOrders() {
        return orders;
    }

    public void add(Product prod){
        stock.add(prod);
    }

    public void remove(Product prod){
        stock.remove(prod);
    }

    public void update(Product prod, Integer cost, Integer count){
        if(cost != null) {
            prod.setCost(cost);
        }
        if(count != null) {
            prod.setCount(count);
        }
    }

    public void addOrder(Cart cart){
        orders.add(cart);
    }

    public int getStat(){
        int count = 0;
        for (Cart c: orders){
            for (Product p: c.getProducts()){
                count += p.getCost();
            }
        }
        return count;
    }
}