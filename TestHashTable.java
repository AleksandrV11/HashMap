public class TestHashTable {


    public static void main(String[] args) {

        MyMap myMap = new MyHashTable();

        myMap.put(0, "1");
        myMap.put(1, "2");
        myMap.put(2, "3");
        myMap.put(3, "4");
        myMap.put(4, "5");
        myMap.put(5, "6");
        myMap.put(6, "7");
        myMap.put(7, "8");
        myMap.put(8, "9");
        myMap.put(10, "11");
        myMap.put(17, "12");
        myMap.put(33, "13");

        System.out.println(myMap);


        if (myMap.get(2) == "3") {
            System.out.println("Все ок");           //проверяем наличие после заполнения
        } else {
            System.out.println("Натупил");
        }
        if (myMap.get(33) == "13") {                 //проверяем метод гет
            System.out.println("Все ок");
        } else {
            System.out.println("Натупил");
        }
        myMap.remove(0);
        if (myMap.get(0) == null) {                         //проверяем наличие после удаления
            System.out.println("Все ок");
        } else {
            System.out.println("Натупил");
        }


    }
}
