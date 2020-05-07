## ArrayList 的使用及原理

## ArrayList的继承关系  
> 进入 ArrayList 源码，鼠标放在 ArrayList 类上，Ctrl+Shift+Alt+U，查看继承关系图。

##如何定义一个ArrayList？  
ArrayList有三个构造函数：① 无参；②参数为整数；③参数为集合。  

##举个栗子：
//默认创建一个ArrayList集合  
ArrayList<String> a1 = new ArrayList<>();    

//创建一个初始长度为12的ArrayList集合  
ArrayList<String> a2 = new ArrayList<>(12);  

//将其他类型的集合转为ArrayList  
ArrayList<String> a3 = new ArrayList<>(new HashSet());  
我们读ArrayList构造函数的源码之前，先看看ArrayList的属性的情况：  
`
    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    transient Object[] elementData; 

    private int size;
`
可以看到，ArrayList 是非线程安全的容器，底层实现是 Object[]，数据会添加到ArrayList的elementData数组中，而且默认容量DEFAULT_CAPACITY为10。  
但其实从jdk7之后，ArrayList的默认容量就是0了，而DEFAULT_CAPACITY在扩容的过程中才会用到。接下来我们来看看定义ArrayList的三种构造函数。  
ArrayList有参构造函数——入参类型为整型时.   

`
public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }
`
可以看到，如果传入正整数，则 elementData 数组容量初始化为 initialCapacity；如果传入0，则elementData数组赋值为一个空数组。  
可能有读者发现了ArrayList类中有两个属性定义为空数组。为什么ArrayList会定义两个空数组？  

其实注释已经给出了官方解释：  
`
    /*
     * Shared empty array instance used for empty instances.
     * 此共享空数组实例用于空实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /*
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     * 此共享空数组用于默认大小的空实例。我们将此与 EMPTY_ELEMENTDATA 区分开，以了解填充第一个元素时需要多少（空间）
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
  `  
简单说，EMPTY_ELEMENTDATA与DEFAULTCAPACITY_EMPTY_ELEMENTDATA在功能上有不同的含义，前者单纯用于赋值为空数组，后者是给elementData数组初始化的。  

ArrayList有参构造函数——入参类型为集合类  

`public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
`
可以看到，只要是实现了Collection的集合类，都会调用toArray()将集合类中的数组赋给elementData。而且toArray()返回的数组类型不是Object[]类型时…等等，  
toArray()返回的数组类型为什么会不是Object[]类型？举个栗子：  
`
public class Test<E> extends ArrayList {
    @Override
    public Integer[] toArray() {
        return new Integer[]{0, 23};
    }
    public static void main(String[] args){
        Object[] elementData = new Test<Integer>().toArray();
        System.out.println(elementData.getClass());
        System.out.println(Object[].class);
        System.out.println(elementData.getClass() == Object[].class);
    }
}`
运行结果为：  
class [Ljava.lang.Integer;  
class [Ljava.lang.Object;  
false  
好，我们接着说当toArray()返回的数组类型不是Object[]类型时，会调用Arrays.copyOf()将原数组拷贝到新数组中去，而且类型还定义为Object类：  
`
public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
`
ArrayList无参构造函数  
`
public ArrayList() {
  this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}
`
在这里，我们可以看到JDK 1.8之后的ArrayList的默认容量为0。如果ArrayList的容量为0，还可以添加数据吗？当然可以，通过扩容机制变可以扩充ArrayList的容量：  
`
public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }
   ` 
在这里我们可以看到，如果elementData数组依然为  
DEFAULTCAPACITY_EMPTY_ELEMENTDATA（即初始状态），则通过 ensureExplicitCapacity() 方法将ArrayList的容量扩充为10（DEFAULT_CAPACITY）。  
为什么无参构造函数中对ArrayList容量的初始化改变了呢？  
虽然JDK 1.8中的ArrayList默认容量为0，但在JDK1.6的无参构造函数的ArrayList默认容量为10：  
public ArrayList() {  
  this(10);  
}  
个人认为，JDK 1.8中延迟初始化ArrayList的实际容量，应该是考虑如果一开始就初始化为10，那么大小为10的数组中存的全部是null，这种数组多了也会占用大量的空间。  
所以这也是为了节省不必要浪费的空间，体现了懒加载的思想。  

## 怎么使用ArrayList？

既然我们已经知道了怎么去定义一个ArrayList，接下来就是要使用ArrayList了，而ArrayList提供的方法中常见的有：add、addAll、set、get、remove、size、isEmpty等。   
因为ArrayList的一些方法会涉及到数据位置的变换，为了更直观地感受这些变化，在这里我们来举个栗子：   
疫情以来，想必很多朋友已经很想念那些美食了。咱们在这里报一下菜单吧：  
`
ArrayList<String> list = new ArrayList<>();  
list.add("阿花");  
list.add("阿云"); 
list.add("阿秋");  
list.add("阿兰");  
list.add("阿思");    
`
也就是说，elementData数组中有这些数据,让我们看看add这个操作在源码中是怎样的过程：  
`
public boolean add(E e) {  
    ensureCapacityInternal(size + 1);  
    elementData[size++] = e;  
    return true;  
}   
`
首先，会先通过 ensureCapacityInternal(size+1) 检查elementData数组的容量是否充足，然后再将数据放入数组中，我们来看看ArrayList是怎么进行容量检查。    
`
private void ensureCapacityInternal(int minCapacity) {  
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {  
      minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);  
    }  
    ensureExplicitCapacity(minCapacity);  
}  
`
在这个方法中，确定elementData数组是不是空数组，如果是的话，将形参minCapacity赋值为10（DEFAULT_CAPACITY)，然后进入 ensureExplicitCapacity() 方法。  
`
private void ensureExplicitCapacity(int minCapacity) {  
        modCount++;  
        if (minCapacity - elementData.length > 0)  
            grow(minCapacity);  
    }
`
此方法中的 modCount 变量是从 AbstractList 继承下来的，用于记录对 ArrayList 对象操作的次数：  
`protected transient int modCount = 0;`
可以看到当形参 minCapacity 的数值比当前的 elementData 数组的长度大，则要调用grow()方法进行扩容操作。  
`
private void grow(int minCapacity) {  
        int oldCapacity = elementData.length;  
        int newCapacity = oldCapacity + (oldCapacity >> 1);    
        if (newCapacity - minCapacity < 0)  
            newCapacity = minCapacity;  
        if (newCapacity - MAX_ARRAY_SIZE > 0)  
            newCapacity = hugeCapacity(minCapacity);  
        elementData = Arrays.copyOf(elementData, newCapacity);  
    }
  `  
可以看到，扩容规则为“数组当前的容量+（数组当前的容量/2）” oldCapacity >> 1就是原来的基础上右移2^1，右移是变小操作即: 数组当前的容量/(2^1)，  
即扩容后的数组容量为之前数组容量的1.5倍。当然，如果超过了最大值，在 hugeCapacity() 方法中会对其进行处理。  
`
private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0){
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }
 `   
如果形参 minCapacity 大于 MAX_ARRAY_SIZE，则赋值为 Integer.MAX_VALUE，实际上 MAX_ARRAY_SIZE 与 Integer.MAX_VALUE 相差8。  
如果需要在既定位置安插数据，则需要通过 rangeCheckForAdd() 方法判断数组是否越界。  
`
private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
set(int index, E element)
`
在add()方法过程中，如果要在数组中（除了数组末尾）安插一个数据，需要将安插位置之后的数据往后移动一位。但如果需要替换数组某个位置的数据，则需要找到对应位置，并替换元素即可。  
`
public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }
`    
在set()方法执行过程中，需要进行rangeCheck()数组范围检查之后，再将原数据取出用于返回，并在该数组位置替换新数据：  

ArrayList中的get()方法获取数据的方式也很简单，只需先判断传入的数组下标是否越界，并通过下标查找，转换类型即可。  
`
public E get(int index) {
    rangeCheck(index);
    return elementData(index);
}
`
其中的elementData()方法的实现如下：  
`
E elementData(int index) {
  return (E) elementData[index];
}
`
remove(int index) 和 remove(Object o)
ArrayList 有两种删除方式，一种是通过下标选择删除的元素，另一个是通过值对象删除元素。我们先来了解一下前者：  
`
public E remove(int index) {
        rangeCheck(index);
        modCount++;
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        elementData[--size] = null; // 使GC回收对其起作用
        return oldValue;
    }
    `
通过下标选择删除的元素的 remove(int index) 方法中，在删除元素之前，需要先对下标进行范围检查 rangeCheck()，然后再计算出需要移动的元素个数，  
通过 arraycopy() 即数组复制的方式，将既定位置之后的元素都向前移动一位，最后再设置 elementData[size-1]为null，使GC回收对其起作用。  
接下来看Object入参：  
`
public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
`    
通过值对象删除元素的 remove(Object o) 方法中，程序将null单独进行处理，因为null是一种状态，不属于任何类型的值。  
在选中了既定值对象对应的下标值之后，删除操作将会在 fastRemove() 方法中进行，而 fastRemove() 方法中的操作过程就跟 remove(int index) 类似，在此就不赘述了。  
其他方法：  
上文对ArrayList的增删改查方法进行了介绍，接下来简单介绍一下ArrayList的其他方法：  
size()：用于获取集合的长度，可通过定义在ArrayList中的私有变量size得到。  
isEmpty()：用于确定ArrayList是否为空，可通过定义在ArrayList中的私有变量size得到。  
contains(Object o)：用于确定ArrayList是否包含某个元素，先通过遍历底层数组elementData，再通过equals或==判断。  
clear()：将ArrayList里的数据清空，通过遍历底层数组elementData，将数组里的值都设置为null。  

打完收工！