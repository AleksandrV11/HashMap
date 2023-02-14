import java.util.Arrays;
public class MyHashTable<K, V> implements MyMap<K, V> {
    Node[] values = new Node[16];
    Node<K, V> nodeNull;
    public class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        final int hashcode;
        public Node(K key, V value, int hashcode) {
            this.key = key;
            this.value = value;
            this.hashcode = hashcode;
        }
        public Node(int hashcode) {
            this.hashcode = hashcode;
        }
    }
    private int provZapoln() {  //подсчет заполненых бакетов (возвращ инт заполн бакетов)
        int zap = 0;
        for (int i = 0; i < values.length; ++i) {
            if (values[i] != null) {
                ++zap;
            }
        }
        return zap;
    }
    private void zapoln() {   //проверка наличия в бакетах инфы ( пропуск или отправка в метод перезаписи)
        Node<K, V>[] vremValues = this.values;
        values = new Node[values.length * 2];
        for (int i = 0; i < vremValues.length; i++) {
            if (vremValues[i] == null) {        //пустой бакет пропускаем
                continue;
            }
            Node<K, V> vrm = vremValues[i];
            while (vrm.next != null) {       //в бакете есть больше одного
                put(vrm.key, vrm.value);
                vrm = vrm.next;
            }
            put(vrm.key, vrm.value);     //в бакете один
        }
    }
    private void proverka() {  //при перезаписи
        //сравнение отношений заполнен бакетов с коефиц длинны массива(валуес)
        if ((double) provZapoln() == (double) values.length * 0.75) {
            zapoln();  // метод расширения
            proverka();  //возможно необходимо проверить
        }
    }
    public void put(K key, V value) {
        proverka();  //проверка для перезаписи

        if (key == null) {  //по налл индексу
            Node<K, V> node = new Node(key, value, hashCode());
            if (nodeNull == null) {
                nodeNull = node;
                return;
            }
        }
        int ind = key.hashCode() % values.length;   //определение индекса
        int hash = key.hashCode();                  //опред хеша
        Node<K, V> node = new Node(key, value, hash);
        Node<K, V> vrem = values[ind];          //по индексу
        if (vrem == null) {     //если пустой
            values[ind] = node;
            return;
        }
        //если есть ссылка
        while (vrem.next != null) {   //провер на равенство
            if (vrem.hashcode == node.hashcode) {  //подозрение на равенство
                if (vrem.key.equals(node.key)) {  //уточнение равенства
                    vrem.value = node.value;      //перезапись
                    return;
                }
                vrem.next = node;       //запись в продолжение (хеш равер иклс нет)
                return;
            }
            vrem = vrem.next;
        }
        vrem.next = node;     //запись в конец бакета если в счетчике не чего не записалось
    }

    public String toString() {
        return "MyHashTable{values=" + Arrays.toString(values) + "}";
    }
    public V get(K key) {
        if (key == null) {  // c отдельной ячейки 0
            return nodeNull != null ? nodeNull.value : null;
        }
        int ind = key.hashCode() % values.length;   //определение индекса;
        Node<K, V> vrem = values[ind];
        if (vrem != null) {     // в бакете что то есть
            while (vrem.next != null) {        //если есть продолжение (ссилка)
                if (vrem.hashcode == key.hashCode()) {  //ищем  в листе
                    return vrem.value;
                }
                vrem = vrem.next;
            }
            if (vrem.hashcode == key.hashCode()) {  //в случае если бакет одиночный
                return vrem.value;
            }
        }
        return null;   //если не чего не отработало
    }
    public V remove(K key) {
        if (key == null) {              //работа с налл ячейкой
            Node<K, V> vrem = nodeNull;
            nodeNull = null;
            return vrem.value;
        }
        if (key != null) {
            int ind = key.hashCode() % values.length;   //определение индекса
            Node<K, V> vrem = values[ind];            //переменная по индексу
            Node<K, V> pred = vrem;                //дополн  переменная для перезаписи

            if (vrem.next == null && key == vrem.key) {// если одна  и ключи равны
                pred = vrem;                              //сохраняем для возврата
                values[ind] = null;                              //приравниваем налу
                return pred.value;                                 //возврат
            }
            if (vrem.next != null && vrem.key == key) {  //если первый и не последний
                values[ind] = pred.next;               //приравниваем следуйщиму
                return vrem.value;          //возврат обошедшего
            }
            while (vrem.next != null) {        //если есть ссылки и случай больше одного (перебор)

                if (vrem.key == key) {        // первый заход не отрабатывает ,(если кей равны)
                    pred.next = vrem.next;     //переприсваиваем ссылку в обход елемента с ключем
                    return vrem.value;          //возврат
                }
                pred = vrem;                    //сохраняем в дополнительную
                vrem = vrem.next;                 // (изменение )шаг для цикла
                //принудительная остановка цикла
                if (vrem.next == null && vrem.key == key) {     // если удаляем кей последний
                    pred.next= null;            //обьявление ссылку предпоследнего елемента налом
                    return vrem.value;            //возврат сохраненого последнего елемента
                }
            }
        }
        return null;            //если ключа нет
    }
}



