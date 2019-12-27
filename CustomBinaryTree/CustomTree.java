
import javax.lang.model.element.NestingKind;
import java.io.Serializable;
import java.util.*;


public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    int count = 0;

    public CustomTree() {
        this.root = new Entry<>("0");
    }
    // ДОБАВИТЬ ЭЛЕМЕНТ В ДЕРЕВО
    @Override
    public boolean add(String s) {
        Queue<Entry> queue = new LinkedList<> ();
        count = count+1;
        queue.add(root);

        while (!queue.isEmpty()){
            Entry top = queue.poll();
            if(top.availableToAddLeftChildren){
                Entry entry = new Entry(s);
                top.leftChild = entry;
                entry.parent = top;
                top.availableToAddLeftChildren = false;
                queue.clear();
            }
            else if(top.availableToAddRightChildren){
                Entry entry = new Entry(s);
                top.rightChild = entry;
                entry.parent = top;
                top.availableToAddRightChildren = false;
                queue.clear();
            }
            else {
                queue.add(top.leftChild);
                queue.add(top.rightChild);
            }
        }
        return true;
    }

    //УДАЛИТЬ ЭЛЕМЕНТ
    @Override
    public boolean remove(Object o) {
        if(o instanceof String){
            Queue<Entry> queue = new LinkedList<> ();
            queue.add(root);
            while (!queue.isEmpty()){
                String name = (String)o;
                Entry top = queue.poll();
                if(!top.availableToAddLeftChildren){
                    if(top.leftChild.elementName.equals(name)){
                        top.availableToAddLeftChildren = true;
                        queue.clear();
                        Queue<Entry> queue2 = new LinkedList<> ();
                        queue2.add(top.leftChild);
                        while (!queue2.isEmpty()){
                            Entry top2 = queue2.poll();
                            if(!top2.availableToAddLeftChildren){
                                queue2.add(top2.leftChild);
                            }
                            if(!top2.availableToAddRightChildren){
                                queue2.add(top2.rightChild);
                            }
                            top2 = null;
                            count--;
                        }

                    }else queue.add(top.leftChild);
                }
                if(!top.availableToAddRightChildren){
                    if(top.rightChild.elementName.equals(name)){
                        top.availableToAddRightChildren = true;
                        queue.clear();
                        Queue<Entry> queue2 = new LinkedList<> ();
                        ((LinkedList<Entry>) queue2).add(top.rightChild);
                        while (!queue2.isEmpty()){
                            Entry top2 = queue2.poll();
                            if(!top2.availableToAddLeftChildren){
                                queue2.add(top2.leftChild);
                            }
                            if(!top2.availableToAddRightChildren){
                                queue2.add(top2.rightChild);
                            }
                            top2 = null;
                            count--;
                        }

                    }else queue.add(top.rightChild);
                }
            }

            return true;
        }
        else throw new UnsupportedOperationException();
    }

    //УЗНАТЬ КОЛ-ВО ЭЛЕМЕНТОВ
    @Override
    public int size() {
        return count;
    }


    // ПОЛУЧИТЬ РОДИТЕЛЬСКИЙ УЗЕЛ ЭЛЕМЕНТА
    public String getParent(String s) {

        Queue<Entry> queue = new LinkedList<> ();
        queue.add(root);

        while (!queue.isEmpty()){
            Entry top = queue.poll();
            if(!top.availableToAddLeftChildren){
                if(top.leftChild.elementName.equals(s)){
                    return top.elementName;
                }
                else{
                    try {
                        queue.add(top.leftChild);
                    }catch (NullPointerException e){
                        return null;
                    }
                }

            } if(!top.availableToAddRightChildren){
                if(top.rightChild.elementName.equals(s)){
                    return top.elementName;
                }
                else{
                    try{
                        queue.add(top.rightChild);
                    }catch (NullPointerException e){
                        return null;
                    }
                }
            }
        }
        return null;
    }


    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }


    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    static class Entry<T> implements Serializable {
        public String elementName;
        public boolean availableToAddLeftChildren, availableToAddRightChildren;
        public Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
