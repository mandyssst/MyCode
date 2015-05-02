/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactBook;

/**
 *
 * @author songx544
 */
public class MyArrayList implements MyList {
    public int capacity;
    public int size;
    public Object[] array;
    
    public MyArrayList()
    {
        array = (Object[]) new Object[2];
        size = 0;
        capacity = 2;
    };
    
    public MyArrayList(int cap)
    {
        this.capacity = cap;
        array =  new Object[cap];
    }
    
    public MyArrayList(Object[] items)
    {
        array = items;
        this.capacity = items.length;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean add(Object item) {
        if (size>=capacity) {
            grow();
        }
        array[size] = item;
        size++;
        return true;
    }
    
    private void grow()
    {
        Object[] temp = new Object[capacity];
        System.arraycopy(array, 0, temp, 0, temp.length);
        array = new Object[capacity*2];
        System.arraycopy(temp, 0, array, 0, temp.length);
        capacity = capacity *2;
    }

    @Override
    public boolean insert(int index, Object o) {
        if (index<0 || index >size || o==null) {
            return false;
        }
        if (size+1 > capacity) {
            grow();
        }
        
        if (index == size+1) {
            array[size] = o;
        }
        
        else
        {
            for (int i = size; i > index; i--) {
                array[i] = array[i-1];
                
            }
            array[index] = o;
        }
        size++;
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < array.length; i++) {
            //question here
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(int index) {
        if (index+1>size|| index<0) {
            return null;
        }
        if (array[index]==null) {
            return null;
        }
        return array[index];
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            //question here
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i]==o) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < array.length; i++) {
            if (array[i]!=null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object remove(int index) {
        Object temp = array[index];
        //question
        if (index+1>size) {
            return null;
        }
        
         for (int i = index; i < size; i++) 
        {
            array[i] = array[i+1];
        }
        
        array[size-1] = null;
        size--;
        return temp;
    }

    @Override
    public boolean remove(Object o) {
        int temp = -1;
        if (size ==0) {
            return false;
        }
        if (size ==1) {
            array[0] = null;
            return true;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i]==o) {
                 temp = i;
            }
        } 
        if (temp ==-1) {
            return false;
        }
        
        if (temp == size-1) {
            array[temp] = null;
            return true;
        }
        for (int i = temp; i < size-1; i++) {
            array[i] = array[i+1];
        }
        array[size-1]=null;
        size--;
        return true;
    }

    @Override
    public void set(int index, Object o) {
        //question: bound is size or capacity?
        if (index>=size||o==null) {
            return;
        }
        array[index] = o;
    }

    @Override
    public int size() {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]!=null) {
                count++;
            }
        }
        return count;
    }


}
