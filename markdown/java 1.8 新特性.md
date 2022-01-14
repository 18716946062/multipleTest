# java 1.8 新特性

# Basic
## Lamdba 表达式
函数接口：只有一个抽象方法的接口。函数接口可以实现为普通类、内部类以及匿名内部类。


1. 匿名内部类
```java
new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        "inside runnable using an anonymous inner class");
            }
}).start();
```


2. lamdba 表达式
```java
new Thread(() -> System.out.println(
                "inside Thread constructor using lambda")).start();
```
Runnable 函数接口定义：
```java
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
```
lambda 表达式与函数接口必须相容，即参数和返回值类型匹配。


3. 将 lamdba 表达式赋值给变量
```java
Runnable r = () -> System.out.println(
                "lambda expression implementing the run method");
new Thread(r).start();
```
可以将 lamdba 表达式看做实现函数接口的匿名内部类。
```java
public class LamdbaTest {
    public static void main(String[] args) {
        // 匿名内部类
        File directory = new File("./src/main/java");
        String[] names = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        // lambda 表达式，参数类型推断
        String[] names1 = directory.list((dir, name) -> name.endsWith(".java"));
        // lambda 表达式，完整参数
        String[] names2 = directory.list((File dir, String name) ->
                name.endsWith(".java"));
        // lambda 表达式，表达式体多个语句
        String[] names3 = directory.list((File dir, String name) -> {
            return name.endsWith(".java");
        });
    }
}
```


## 方法引用
当 lamdba 表达式仅仅是方法调用时，可以使用方法引用。方法引用一定有等价的 lamdba 表达式表示，反之不然。方法引用使得代码更加简洁。
```java
public class MethodReferences {

    public static void main(String[] args) {
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach(x -> System.out.println(x));
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach(System.out::println);
        Consumer<Integer> printer = System.out::println;
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach(printer);
    }
}
```
方法引用支持以下几种语法：

1. object::instanceMethod

Refer to an instance method using a reference to the supplied object, as in System.out::println

2. Class::staticMethod

Refer to static method, as in Math::max

3. Class::instanceMethod

Invoke the instance method on a reference to an object supplied by the context,as in String::length


```java
public class MethodReferences {

    public static void main(String[] args) {
        // lamdba 表达式与方法引用
        List<String> sorted = strings.stream()
                .sorted((s1, s2) -> s1.compareTo(s2))
                .collect(Collectors.toList());
        List<String> sorted2 = strings.stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
        // Math::random 类静态方法
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
        // String::length 类实例方法；System.out::println 对象实例方法
        Stream.of("this", "is", "a", "stream", "of", "strings")
                .map(String::length)
                .forEach(System.out::println);
    }
}
```


## 构造函数引用
构造函数引用是方法引用的特殊情况，使用关键字 `new` ，完成构造函数调用。
```java
public class ConstructorReferences {

    public static void main(String[] args) {
        List<String> names =
                Arrays.asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace",
                        "Karen Spärck Jones");
        List<Person> people = names.stream()
                .map(name -> new Person(name))
                .collect(Collectors.toList());
        // or, alternatively,
        List<Person> people1 = names.stream()
                .map(Person::new)
                .collect(Collectors.toList());
    }

    public static class Person {
        private String name;

        public Person() {
        }

        public Person(String name) {
            this.name = name;
        }
        // getters and setters ...
        // equals, hashCode, and toString methods ...
    }
}
```
拷贝构造函数：
```java
public class ConstructorReferences {

    public static void main(String[] args) {
        // copy constructor
        Person before = new Person("Grace Hopper");
        people = Stream.of(before)
                .map(Person::new)
                .collect(Collectors.toList());
        Person after = people.get(0);
        assertFalse(before == after);
        assertEquals(before, after);
        before.setName("Rear Admiral Dr. Grace Murray Hopper");
        assertFalse(before.equals(after));

        
    }

    public static class Person {
        private String name;

        public Person() {
        }

        public Person(String name) {
            this.name = name;
        }

        public Person(Person p) {
            this.name = p.name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
```
可变参数构造函数：
```java
public class ConstructorReferences {

    public static void main(String[] args) {
        List<String> names =
                Arrays.asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace",
                        "Karen Spärck Jones");
        // varargs constructor
        names.stream()
                .map(name -> name.split(" "))
                .map(Person::new)
                .collect(Collectors.toList());

    }

    public static class Person {
        private String name;

        public Person() {
        }

        public Person(String name) {
            this.name = name;
        }

        public Person(Person p) {
            this.name = p.name;
        }

        public Person(String... names) {
            this.name = Arrays.stream(names)
                    .collect(Collectors.joining(" "));
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
```
数组，构造函数引用也可以在数组构造中使用，其声明如下：
```java
<A> A[] toArray(IntFunction<A[]> generator)
```
```java
public class ConstructorReferences {

    public static void main(String[] args) {
        List<String> names =
                Arrays.asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace",
                        "Karen Spärck Jones");
        // Creating an array of Person references
        Person[] peoples = names.stream()
                .map(Person::new)
                .toArray(Person[]::new);
    }
}
```
## 函数接口
函数接口满足以下几个条件：

1. 只包含一个抽象方法（包括继承的，object 方法不算）
1. 用 @FunctionalInterface 注解修饰
1. 可以包含 default 和 static 方法



```java
@FunctionalInterface
public interface MyInterface {
    /**
     * 默认就是抽象方法
     * @return
     */
    int myMethod();

    // int myOtherMethod();

    /**
     * 默认方法
     * @return
     */
    default String sayHello() {
        return "Hello, World!";
    }

    /**
     * 静态方法
     */
    static void myStaticMethod() {
        System.out.println("I'm a static method in an interface");
    }
}
```
注解 @FunctionalInterface 是为了编译器验证函数接口是否满足以上条件，比如加上 myOtherMethod 方法，其抽象方法数不为 1 ，编译时会报错。

## 接口默认方法
接口默认方法有两个用处：

1. 实现不需要在实现类中实现的方法
1. 可以向类库中添加方法，而不影响兼容性



java 集合类 Collection 的 isEmpty 和 size 方法并不需要在子类中实现，以前的实现方式在 AbstractCollection 中实现，现在可以在接口中通过默认方法实现。
```java
public boolean isEmpty() {
	return size() == 0;
}
```
java 8 中向 collection 类添加了新方法，如果没有默认方法，会破坏类库兼容性。
```java
default boolean removeIf(Predicate<? super E> filter)
default Stream<E> stream()
default Stream<E> parallelStream()
default Spliterator<E> spliterator()
```
问题：如果一个类实现多个接口时，多个接口包含重复的默认方法如何处理？


## 接口静态方法
java 8 以前存在大量 util 类，包含各种静态方法，现在统一在接口中实现：
```java
public class StaticMethodTest {
    public static void main(String[] args) {
        List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore",
                "Dalton", "Brosnan", "Craig");
        List<String> sorted = bonds.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        // [Brosnan, Connery, Craig, Dalton, Lazenby, Moore]
        sorted = bonds.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        // [Moore, Lazenby, Dalton, Craig, Connery, Brosnan]
        sorted = bonds.stream()
                .sorted(Comparator.comparing(String::toLowerCase))
                .collect(Collectors.toList());
        // [Brosnan, Connery, Craig, Dalton, Lazenby, Moore]
        sorted = bonds.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        // [Moore, Craig, Dalton, Connery, Lazenby, Brosnan]
        sorted = bonds.stream()
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
        // [Craig, Moore, Dalton, Brosnan, Connery, Lazenby]
    }
}

```


接口静态方法规则：

- Static methods must have an implementation
- You cannot override a static method
- Call static methods from the interface name
- You do not need to implement an interface to use its static methods



# java.util.function 包
java 8 中在函数接口的基础上提供了 util.function 包，里面包括常用的 consumer、supplier、predicates 和 functions 。这些都是常用的函数接口，在其他类库和日常开发中得以复用。


## Consumers
consumer 即接收参数并进行消费，比如 foreach 函数。
```java
public class ConsumerTest {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        strings.forEach(s -> System.out.println(s));
        strings.forEach(System.out::println);
        // and then
        strings.forEach(((Consumer<String>) System.out::println)
                .andThen((s)-> System.out.println("and then->"+s)));
    }
}
```
类库中使用 consumer 的地方，比如：

- Optional.ifPresent(Consumer<? super T> consumer)
- Stream.forEach(Consumer<? super T> action)
- Stream.peek(Consumer<? super T> action)



## Suppliers


```java
@FunctionalInterface
public interface Supplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get();
}

```
supplier 可以用于延迟执行，结合 Optional 等：
```java
public class SupplierTest {
    public static void main(String[] args) {
        // supplier
        DoubleSupplier randomSupplier = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return Math.random();
            }
        };
        randomSupplier = () -> Math.random();
        randomSupplier = Math::random;

        //  deferred execution and optional
        List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
                "Zoë", "Jayne", "Simon", "River", "Shepherd Book");
        Optional<String> first = names.stream()
                .filter(name -> name.startsWith("C"))
                .findFirst();
        System.out.println(first);
        System.out.println(first.orElse("None"));
        System.out.println(first.orElse(String.format("No result found in %s",
                names.stream().collect(Collectors.joining(", ")))));
        // supplier
        System.out.println(first.orElseGet(() ->
                String.format("No result found in %s",
                        names.stream().collect(Collectors.joining(", ")))));
    }
}
```


其他使用到 supplier 的地方：

- The **orElseThrow **method in Optional, which takes a Supplier<X extends Exception>. The Supplier is only executed if an exception occurs.
- **Objects.requireNonNull(T obj, Supplier<String> messageSupplier)** only customizes its response if the first argument is null.
- **CompletableFuture.supplyAsync(Supplier<U> supplier)** returns a Completa ble Future that is asynchronously completed by a task running with the value obtained by calling the given Supplier.
- The **Logger **class has overloads for all its logging methods that takes a Supplier <String> rather than just a string



## Predicates


用于判断某个条件是否成立，比如常用于 filter ,常见 predicate ：
```java
default Predicate<T> and(Predicate<? super T> other)
static <T> Predicate<T> isEquals(Object targetRef)
default Predicate<T> negate()
default Predicate<T> or(Predicate<? super T> other)
boolean test(T t)
```
predicate 支持 and or negate 等条件组合，构造复杂的条件判断比较灵活：
```java
public class ImplementPredicate {
    public static final Predicate<String> LENGTH_FIVE = s -> s.length() == 5;
    public static final Predicate<String> STARTS_WITH_S =
            s -> s.startsWith("S");

    public String getNamesSatisfyingCondition(
            Predicate<String> condition, String... names) {
        return Arrays.stream(names)
                .filter(condition)
                .collect(Collectors.joining(", "));
    }
    public String getNamesOfLength(int length, String... names) {
        return Arrays.stream(names)
                .filter(s -> s.length() == length)
                .collect(Collectors.joining(", "));
    }

    public String getNamesStartingWith(String str, String... names) {
        return Arrays.stream(names)
                .filter(s -> s.startsWith(str))
                .collect(Collectors.joining(", "));
    }
}

public class ImplementPredicateTest {
    private ImplementPredicate demo = new ImplementPredicate();
    private String[] names;

    @Before
    public void setUp() {
        names = Stream.of("Mal", "Wash", "Kaylee", "Inara", "Zoë",
                "Jayne", "Simon", "River", "Shepherd Book")
                .sorted()
                .toArray(String[]::new);
    }

    @Test
    public void getNamesOfLength5() throws Exception {
        assertEquals("Inara, Jayne, River, Simon",
                demo.getNamesOfLength(5, names));
    }

    @Test
    public void getNamesStartingWithS() throws Exception {
        assertEquals("Shepherd Book, Simon",
                demo.getNamesStartingWith("S", names));
    }

    @Test
    public void getNamesSatisfyingCondition() throws Exception {
        assertEquals("Inara, Jayne, River, Simon",
                demo.getNamesSatisfyingCondition(s -> s.length() == 5, names));
        assertEquals("Shepherd Book, Simon",
                demo.getNamesSatisfyingCondition(s -> s.startsWith("S"),
                        names));
        assertEquals("Inara, Jayne, River, Simon",
                demo.getNamesSatisfyingCondition(LENGTH_FIVE, names));
        assertEquals("Shepherd Book, Simon",
                demo.getNamesSatisfyingCondition(STARTS_WITH_S, names));
    }

    @Test
    public void composedPredicate() throws Exception {
        assertEquals("Simon",
                demo.getNamesSatisfyingCondition(
                        LENGTH_FIVE.and(STARTS_WITH_S), names));
        assertEquals("Inara, Jayne, River, Shepherd Book, Simon",
                demo.getNamesSatisfyingCondition(
                        LENGTH_FIVE.or(STARTS_WITH_S), names));
        assertEquals("Kaylee, Mal, Shepherd Book, Wash, Zoë",
                demo.getNamesSatisfyingCondition(LENGTH_FIVE.negate(), names));
    }
}
```
其他类库用到 predicate 的：

- Optional.filter(Predicate<? super T> predicate)
- Collection.removeIf(Predicate<? super E> filter)
- Stream.allMatch(Predicate<? super T> predicate)
- Collectors.partitioningBy(Predicate<? super T> predicate)



## Functions
前面的 consumer supplier predicate 都是 functions 的特例。


```java
public class FunctionTest {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
                "Zoë", "Jayne", "Simon", "River", "Shepherd Book");
        List<Integer> nameLengths = names.stream()
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        return s.length();
                    }
                })
                .collect(Collectors.toList());
        nameLengths = names.stream()
                .map(s -> s.length())
                .collect(Collectors.toList());
        nameLengths = names.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.printf("nameLengths = %s%n", nameLengths);
        // nameLengths == [3, 4, 6, 5, 3, 5, 5, 5, 13]
    }
}
```


compose andThen 等变换是函数式编程的基础。


# Streams
stream 是 java 实现函数式编程的基础。stream 通过一个数据源参数数据，经过一系列中间变换，最后通过终止表达式完成数据整个计算。
stream 具有以下特点：

- 不存储数据。流是基于数据源的对象，它本身不存储数据元素，而是通过管道将数据源的元素传递给操作。
- 函数式编程。流的操作不会修改数据源，例如filter不会将数据源中的数据删除。
- 延迟操作。流的很多操作如filter,map等中间操作是延迟执行的，只有到终点操作才会将操作顺序执行。
- 可以解绑。对于无限数量的流，有些操作是可以在有限的时间完成的，比如limit(n) 或 findFirst()，这些操作可是实现"短路"(Short-circuiting)，访问到有限的元素后就可以返回。
- 纯消费。流的元素只能访问一次，类似Iterator，操作没有回头路，如果你想从头重新访问流的元素，对不起，你得重新生成一个新的流。



## 分类
官方将 Stream 中的操作分为两大类：

- `中间操作（Intermediate operations）`，只对操作进行了记录，即只会返回一个流，不会进行计算操作。
- `终结操作（Terminal operations）`，实现了计算操作。

中间操作又可以分为：

- `无状态（Stateless）操作`，元素的处理不受之前元素的影响。
- `有状态（Stateful）操作`，指该操作只有拿到所有元素之后才能继续下去。

终结操作又可以分为：

- `短路（Short-circuiting）`操作，指遇到某些符合条件的元素就可以得到最终结果
- `非短路（Unshort-circuiting）`操作，指必须处理完所有元素才能得到最终结果。





![](https://cdn.nlark.com/yuque/0/2021/jpeg/106439/1613808983666-c8cb3903-f2b0-4e14-b536-b0857ab4c6b2.jpeg)## 创建 stream
创建 stream 的方式：
Stream.of(T... values) and Stream.of(T t)
• Arrays.stream(T[] array), with overloads for int[], double[], and long[]
• Stream.iterate(T seed, UnaryOperator<T> f)
• Stream.generate(Supplier<T> s)
• Collection.stream()
• Using range and rangeClosed:
— IntStream.range(int startInclusive, int endExclusive)
— IntStream.rangeClosed(int startInclusive, int endInclusive)
— LongStream.range(long startInclusive, long endExclusive)
— LongStream.rangeClosed(long startInclusive, long endInclusive)


```java
public class CreateStream {
    public static void main(String[] args) {
        String names = Stream.of("Gomez", "Morticia", "Wednesday", "Pugsley")
                .collect(Collectors.joining(","));
        System.out.println(names);
        // prints Gomez,Morticia,Wednesday,Pugsley

        String[] munsters = { "Herman", "Lily", "Eddie", "Marilyn", "Grandpa" };
        names = Arrays.stream(munsters)
                .collect(Collectors.joining(","));
        System.out.println(names);
        // prints Herman,Lily,Eddie,Marilyn,Grandpa

        List<BigDecimal> nums =
                Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE) )
                        .limit(10)
                        .collect(Collectors.toList());
        System.out.println(nums);
        // prints [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        Stream.iterate(LocalDate.now(), ld -> ld.plusDays(1L))
                .limit(10)
                .forEach(System.out::println);
        // prints 10 days starting from today

        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        List<String> bradyBunch = Arrays.asList("Greg", "Marcia", "Peter", "Jan",
                "Bobby", "Cindy");
        names = bradyBunch.stream()
                .collect(Collectors.joining(","));
        System.out.println(names);
        // prints Greg,Marcia,Peter,Jan,Bobby,Cindy

        List<Integer> ints = IntStream.range(10, 15)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(ints);
        // prints [10, 11, 12, 13, 14]
        List<Long> longs = LongStream.rangeClosed(10, 15)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(longs);
        // prints [10, 11, 12, 13, 14, 15]
    }
}
```
## 原始类型装箱
由于 java 某些类型存在原始类型和装箱类型，stream 并不支持直接从原始类型构建 stream ：
```java
IntStream.of(3, 1, 4, 1, 5, 9).collect(Collectors.toList()); // does not compile
```
可以通过两种方式实现：
```java
public class BoxedStream {
    public static void main(String[] args) {
        //IntStream.of(3, 1, 4, 1, 5, 9).collect(Collectors.toList()); // does not compile
        
        List<Integer> ints = IntStream.of(3, 1, 4, 1, 5, 9)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> ints1 = IntStream.of(3, 1, 4, 1, 5, 9)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
        List<Integer> ints2 = IntStream.of(3, 1, 4, 1, 5, 9)
                .collect(ArrayList<Integer>::new, ArrayList::add, ArrayList::addAll);
        
        int[] intArray = IntStream.of(3, 1, 4, 1, 5, 9).toArray();
    }
}
```


## Reduce
函数式编程常用的一个操作，将一些列值**归约**为一个值，map-reduce 框架中的 reduce 。
```java
public class ReduceTest {

    public static void main(String[] args) {
        String[] strings = "this is an array of strings".split(" ");
        long count = Arrays.stream(strings)
                .map(String::length)
                .count();
        System.out.println("There are " + count + " strings");
        int totalLength = Arrays.stream(strings)
                .mapToInt(String::length)
                .sum();
        System.out.println("The total length is " + totalLength);
        OptionalDouble ave = Arrays.stream(strings)
                .mapToInt(String::length)
                .average();
        System.out.println("The average length is " + ave);
        OptionalInt max = Arrays.stream(strings)
                .mapToInt(String::length)
                .max();
        OptionalInt min = Arrays.stream(strings)
                .mapToInt(String::length)
                .min();
        System.out.println("The max and min lengths are " + max + " and " + min);
		// (1)
        int sum = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> x + y).orElse(0);
    }
}
```
以 (1) 为例，reduce 定义与注释：
```java
OptionalInt reduce(IntBinaryOperator op);
```
```java
// OptionalInt reduce(IntBinaryOperator op) 等价于以下代码
     boolean foundAny = false;
     int result = null;
     for (int element : this stream) {
         if (!foundAny) {
             foundAny = true;
             result = element;
         }
         else
             result = accumulator.applyAsInt(result, element);
     }
     return foundAny ? OptionalInt.of(result) : OptionalInt.empty();
```
其中归约的第一个数即 result ，如果没提供就是数据源第一个数，实际使用时可以自行提供，如下所示：
```java
public class ReduceTest {

    public static void main(String[] args) {
        String s = Stream.of("this", "is", "a", "list")
                .reduce("", String::concat);
        System.out.println(s);

        // 字符串连接更加有效的方式 collect
        String s1 = Stream.of("this", "is", "a", "list")
                .collect(() -> new StringBuilder(),
                        (sb, str) -> sb.append(str),
                        (sb1, sb2) -> sb1.append(sb2))
                .toString();
        
        String s2 = Stream.of("this", "is", "a", "list")
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append)
                .toString();

        String s3 = Stream.of("this", "is", "a", "list")
                .collect(Collectors.joining());
        
        int sum1 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .reduce(0, Integer::sum);
        System.out.println(sum);
    }
}
```
其中 reduce 第一个参数如 0 空串叫做 identity 。
reduce 的一般形式：
```java
<U> U reduce(U identity,
                 BiFunction<U, ? super T, U> accumulator,
                 BinaryOperator<U> combiner);
```
等价于以下代码：
```java
U result = identity;
for (T element : this stream)
    result = accumulator.apply(result, element)
return result;
// 同时 combiner 满足：
combiner.apply(u, accumulator.apply(identity, t)) == accumulator.apply(u, t)

```
combiner 一般在并行计算时使用，并行计算时涉及到将多个结果集合并。
示例：
```java
public class ReduceTest {

    public static void main(String[] args) {
        List<Book> books = new ArrayList(){
            {
                add(new Book(1,"1"));
                add(new Book(2,"2"));
            }
        };
        HashMap<Integer, Book> bookMap = books.stream()
                .reduce(new HashMap<Integer, Book>(),
                        (map, book) -> {
                            map.put(book.getId(), book);
                            return map;
                        },
                        (map1, map2) -> {
                            map1.putAll(map2);
                            return map1;
                        });
        bookMap.forEach((k,v) -> System.out.println(k + ": " + v));
    }
    public static class Book {
        private Integer id;
        private String title;
        public Book(Integer id, String title) {
            this.id = id;
            this.title = title;
        }

        public Integer getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
```
## Stream 调试
stream 中间操作并不会进行实际计算而是产生另一个 stream ，需要调试时不直观，可以采用以下方式调试：

1. map 打印中间结果
```java
public class DebugStreamTest {

    public static void main(String[] args) {

    }
    @Test
    public void sumDoublesDivisibleBy3() throws Exception {
        DebugStreamTest demo = new DebugStreamTest();
        assertEquals(1554, demo.sumDoublesDivisibleBy3(100, 120));
    }

    public int sumDoublesDivisibleBy3(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .sum();
    }

    /**
     * map 打印调试
     * @param start
     * @param end
     * @return
     */
    public int sumDoublesDivisibleBy3Test(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .map(n -> {
                    System.out.println(n);
                    return n;
                })
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .sum();
    }
}
```

2. 使用 peek
```java
public class DebugStreamTest {

    public static void main(String[] args) {

    }
    @Test
    public void sumDoublesDivisibleBy3() throws Exception {
        DebugStreamTest demo = new DebugStreamTest();
        assertEquals(1554, demo.sumDoublesDivisibleBy3Peek(100, 120));
    }

    public int sumDoublesDivisibleBy3Peek(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .peek(n -> System.out.printf("original: %d%n", n))
                .map(n -> n * 2)
                .peek(n -> System.out.printf("doubled : %d%n", n))
                .filter(n -> n % 3 == 0)
                .peek(n -> System.out.printf("filtered: %d%n", n))
                .sum();
    }
}
```

3. IDE 提供的功能



## Stream 常用 API
```java
public class StreamApiTest {
    @Test
    public void countTest() {
        long count = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5).count();
        assertTrue(count == 9);
    }

    @Test
    public void findFirstTest() {
        Optional<Integer> firstEven = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .filter(n -> n % 2 == 0).findFirst();
        assertTrue(firstEven.isPresent());
        assertEquals(4, (int) firstEven.get());
    }

    @Test
    public void findAnyTest() {
        Optional<Integer> any = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .unordered()
                .parallel()
                .map(this::delay)
                .findAny();
        assertTrue(any.isPresent());
    }

    public Integer delay(Integer n) {
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException ignored) {
        }
        return n;
    }

    @Test
    public void testIsPrimeUsingAllMatch() throws Exception {
        assertTrue(IntStream.of(2, 3, 5, 7, 11, 13, 17, 19)
                .allMatch(StreamApiTest::isPrime));
    }
    @Test
    public void testIsPrimeWithComposites() throws Exception {
        assertFalse(Stream.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20)
                .anyMatch(StreamApiTest::isPrime));
    }
    
    @Test
    public void emptyStreamsDanger() throws Exception {
        assertTrue(Stream.empty().allMatch(e -> false));
        assertTrue(Stream.empty().noneMatch(e -> true));
        assertFalse(Stream.empty().anyMatch(e -> true));
    }

    public static boolean isPrime(int num) {
        int limit = (int) (Math.sqrt(num) + 1);
        return num == 2 || num > 1 && IntStream.range(2, limit)
                .noneMatch(divisor -> num % divisor == 0);
    }

    @Test
    public void isPalindrome() throws Exception {
        assertTrue(
                Stream.of("Madam, in Eden, I'm Adam",
                        "Go hang a salami; I'm a lasagna hog",
                        "Flee to me, remote elf!",
                        "A Santa pets rats as Pat taps a star step at NASA")
                        .allMatch(StreamApiTest::isPalindrome));
        assertFalse(isPalindrome("This is NOT a palindrome"));
    }

    public static boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        String forward = sb.toString().toLowerCase();
        String backward = sb.reverse().toString().toLowerCase();
        return forward.equals(backward);
    }

    public static boolean isPalindromeByStream(String s) {
        String forward = s.toLowerCase().codePoints()
                .filter(Character::isLetterOrDigit)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
        String backward = new StringBuilder(forward).reverse().toString();
        return forward.equals(backward);
    }
}

```
注意：空 stream 在 allMatch noneMatch anyMatch 行为不同，allMatch noneMatch 返回 true ，然而 anyMatch 返回 false 不管 predicate 是啥。

## Stream ﬂatMap Versus map


flat 有整平的意思，即对嵌套的集合进行 map 操作。
```java
public class FlatMapTest {

    List<Customer> customers = new ArrayList<>();

    @Before
    public void init(){
        Customer sheridan = new Customer("Sheridan");
        Customer ivanova = new Customer("Ivanova");
        Customer garibaldi = new Customer("Garibaldi");
        sheridan.addOrder(new Order(1))
                .addOrder(new Order(2))
                .addOrder(new Order(3));
        ivanova.addOrder(new Order(4))
                .addOrder(new Order(5));
        customers = Arrays.asList(sheridan, ivanova, garibaldi);
    }

    @Test
    public void mapTest(){
        customers.stream()
                .map(Customer::getName)
                .forEach(System.out::println);
        customers.stream()
                .map(Customer::getOrders)
                .forEach(System.out::println);
        customers.stream()
                .map(customer -> customer.getOrders().stream())
                .forEach(System.out::println);
    }

    @Test
    public void flatMapTest(){
        customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .forEach(System.out::println);
    }

    public static class Customer {
        private String name;
        private List<Order> orders = new ArrayList<>();
        public Customer(String name) {
            this.name = name;
        }
        public String getName() { return name; }
        public List<Order> getOrders() { return orders; }
        public Customer addOrder(Order order) {
            orders.add(order);
            return this;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class Order {
        private int id;
        public Order(int id) {
            this.id = id;
        }
        public int getId() { return id; }

        @Override
        public String toString() {
            return "Order{" +
                    "id=" + id +
                    '}';
        }
    }
}
```


## Concatenating Streams
```java
static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
```


示例：
```java
public class ConcatenatingStreamsTest {

    @Test
    public void concat() throws Exception {
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        List<String> strings = Stream.concat(first, second)
                .collect(Collectors.toList());
        List<String> stringList = Arrays.asList("a", "b", "c", "X", "Y", "Z");
        assertEquals(stringList, strings);
    }

    @Test
    public void concatThree() throws Exception {
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        Stream<String> third = Stream.of("alpha", "beta", "gamma");
        List<String> strings = Stream.concat(Stream.concat(first, second), third)
                .collect(Collectors.toList());
        List<String> stringList = Arrays.asList("a", "b", "c",
                "X", "Y", "Z", "alpha", "beta", "gamma");
        assertEquals(stringList, strings);
    }
    @Test
    public void reduce() throws Exception {
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        Stream<String> third = Stream.of("alpha", "beta", "gamma");
        Stream<String> fourth = Stream.empty();
        List<String> strings = Stream.of(first, second, third, fourth)
                .reduce(Stream.empty(), Stream::concat)
                .collect(Collectors.toList());
        List<String> stringList = Arrays.asList("a", "b", "c",
                "X", "Y", "Z", "alpha", "beta", "gamma");
        assertEquals(stringList, strings);
    }
    @Test
    public void flatMap() throws Exception {
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        Stream<String> third = Stream.of("alpha", "beta", "gamma");
        Stream<String> fourth = Stream.empty();
        List<String> strings = Stream.of(first, second, third, fourth)
                .flatMap(Function.identity())
                .collect(Collectors.toList());
        List<String> stringList = Arrays.asList("a", "b", "c",
                "X", "Y", "Z", "alpha", "beta", "gamma");
        assertEquals(stringList, strings);
    }

    @Test
    public void concatParallel() throws Exception {
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        Stream<String> third = Stream.of("alpha", "beta", "gamma");
        Stream<String> total = Stream.concat(Stream.concat(first, second), third);
        assertTrue(total.isParallel());
    }

    public void flatMapNotParallel() throws Exception {
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        Stream<String> third = Stream.of("alpha", "beta", "gamma");
        Stream<String> fourth = Stream.empty();
        Stream<String> total = Stream.of(first, second, third, fourth)
                .flatMap(Function.identity());
        assertFalse(total.isParallel());
    }
    @Test
    public void flatMapParallel() throws Exception {
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        Stream<String> third = Stream.of("alpha", "beta", "gamma");
        Stream<String> fourth = Stream.empty();
        Stream<String> total = Stream.of(first, second, third, fourth)
                .flatMap(Function.identity());
        assertFalse(total.isParallel());
        total = total.parallel();
        assertTrue(total.isParallel());
    }
}
```
> Use caution when constructing streams from repeated concatenation. Accessing an ele‐
ment of a deeply concatenated stream can result in deep call chains, or even StackOver
flowException

嵌套的 contat 本质上是将 stream 构建成二叉树，嵌套过深可能会造成栈溢出，可以使用 flatMap 将多个 stream “整平”为一个 stream ，由于flatMap 默认情况不支持并行化，故需要手动调用并行化。


## Lazy Streams


stream 的工作方式是惰性计算，遇到终止运算符才会实际进行计算，并且当满足条件后，有可能就停止计算。
```java
public class LazyStreamTest {

    @Test
    public void lazyStreamTest() {
        OptionalInt firstEvenDoubleDivBy3 = IntStream.range(100, 200)
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .findFirst();
        System.out.println(firstEvenDoubleDivBy3);
        firstEvenDoubleDivBy3 = IntStream.range(100, 200)
                .map(this::multByTwo)
                .filter(this::divByThree)
                .findFirst();

    }
    public int multByTwo(int n) {
        System.out.printf("Inside multByTwo with arg %d%n", n);
        return n * 2;
    }
    public boolean divByThree(int n) {
        System.out.printf("Inside divByThree with arg %d%n", n);
        return n % 3 == 0;
    }
}

OptionalInt[204]
Inside multByTwo with arg 100
Inside divByThree with arg 200
Inside multByTwo with arg 101
Inside divByThree with arg 202
Inside multByTwo with arg 102
Inside divByThree with arg 204
```
只有某些短路、无状态终止操作符才支持提前停止计算，如果计算过程时有状态的，比如排序，那么 stream 只有计算完所有数据才能得出结果。

# Comparators and Collectors
java 8 增强了比较器，同时提供了一系列方法将 stream 转换为集合，并支持进一步partitioning 和 grouping 。


## Sorting Using a Comparator
```java
public class SortTest {

    private List<String> sampleStrings =
            Arrays.asList("this", "is", "a", "list", "of", "strings");

    /**
     * java 1.7
     * @return list
     */
    public List<String> defaultSort() {
        Collections.sort(sampleStrings);
        return sampleStrings;
    }

    /**
     * java 1.8
     * @return list
     */
    public List<String> defaultSortUsingStreams() {
        return sampleStrings.stream()
                .sorted()
                .collect(Collectors.toList());
    }
    
    public List<String> lengthSortUsingSorted() {
        return sampleStrings.stream()
                .sorted((s1, s2) -> s1.length() - s2.length())
                .collect(toList());
    }

    public List<String> lengthSortUsingComparator() {
        return sampleStrings.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(toList());
    }
}

```
java 1.7 的方式会修改原始集合，java 1.8 会产生新的集合，对原始集合无影响。另外 java 1.8 支持灵活的比较方式、多个属性比较。
comparator 组合即多熟悉比较：
```java
public class ComposeComparatorTest {

    private List<String> sampleStrings =
            Arrays.asList("this", "is", "a", "list", "of", "strings");

    public List<String> lengthSortThenAlphaSort() {
        return sampleStrings.stream()
                .sorted(comparing(String::length)
                        .thenComparing(naturalOrder()))
                .collect(toList());
    }

    private List<Golfer> golfers = Arrays.asList(
            new Golfer("Jack", "Nicklaus", 68),
            new Golfer("Tiger", "Woods", 70),
            new Golfer("Tom", "Watson", 70),
            new Golfer("Ty", "Webb", 68),
            new Golfer("Bubba", "Watson", 70)
    );

    public List<Golfer> sortByScoreThenLastThenFirst() {
        return golfers.stream()
                .sorted(comparingInt(Golfer::getScore)
                        .thenComparing(Golfer::getLast)
                        .thenComparing(Golfer::getFirst))
                .collect(toList());
    }

    public static class Golfer {
        private String first;
        private String last;
        private int score;

        public Golfer(String first, String last, int score) {
            this.first = first;
            this.last = last;
            this.score = score;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
```
## Converting a Stream into a Collection
使用示例：
```java
public class StreamToCollectionTest {

    @Test
    public void streamToCollectionTest() {

        List<String> superHeroes =
                Stream.of("Mr. Furious", "The Blue Raja", "The Shoveler",
                        "The Bowler", "Invisible Boy", "The Spleen", "The Sphinx")
                        .collect(Collectors.toList());

        Set<String> villains =
                Stream.of("Casanova Frankenstein", "The Disco Boys",
                        "The Not-So-Goodie Mob", "The Suits", "The Suzies",
                        "The Furriers", "The Furriers")
                        .collect(Collectors.toSet());

        List<String> actors =
                Stream.of("Hank Azaria", "Janeane Garofalo", "William H. Macy",
                        "Paul Reubens", "Ben Stiller", "Kel Mitchell", "Wes Studi")
                        .collect(Collectors.toCollection(LinkedList::new));

        String[] wannabes =
                Stream.of("The Waffler", "Reverse Psychologist", "PMS Avenger")
                        .toArray(String[]::new);


        Set<Actor> actorsSet = new HashSet() {
            {
                add(new Actor("a", "b"));
                add(new Actor("c", "d"));
            }
        };
        Map<String, String> actorMap = actorsSet.stream()
                .collect(Collectors.toMap(Actor::getName, Actor::getRole));
        actorMap.forEach((key, value) ->
                System.out.printf("%s played %s%n", key, value));
        
        Map<String, Actor> actorMap1 = 
                actorsSet.stream()
                        .collect(Collectors.toMap(Actor::getName, Function.identity()));
        
    }

    public static class Actor {
        String name;
        String role;

        public Actor(String name, String role) {
            this.name = name;
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
```


Collector 接口注释必读，详细描述了 Collector 的实现原理。核心逻辑：
```java
R container = collector.supplier().get();
for (T t : data)
    collector.accumulator().accept(container, t);
return collector.finisher().apply(container);
```
注意点：

1. To ensure that sequential and parallel executions produce equivalent results, the collector functions must satisfy an **identity** and an **associativity** constraints
1. Collectors are designed to be **composed**

**
## Partition and Group
collector 支持类似 SQL 的分组操作，并且支持对分组结果进一步处理。
```java
public class PartitionAndGroupTest {
    List<String> strings = Arrays.asList("this", "is", "a", "long", "list", "of",
            "strings", "to", "use", "as", "a", "demo");

    @Test
    public void partitionTest() {

        Map<Boolean, List<String>> lengthMap = strings.stream()
                .collect(Collectors.partitioningBy(s -> s.length() % 2 == 0));
        lengthMap.forEach((key, value) -> System.out.printf("%5s: %s%n", key, value));
        //
        // false: [a, strings, use, a]
        // true: [this, is, long, list, of, to, as, demo]
    }

    @Test
    public void groupTest() {

        Map<Integer, List<String>> lengthMap = strings.stream()
                .collect(Collectors.groupingBy(String::length));
        lengthMap.forEach((k, v) -> System.out.printf("%d: %s%n", k, v));
        //
        // 1: [a, a]
        // 2: [is, of, to, as]
        // 3: [use]
        // 4: [this, long, list, demo]
        // 7: [strings]
    }
    @Test
    public void downstreamCollectorTest() {
        Map<Boolean, Long> numberLengthMap = strings.stream()
                .collect(Collectors.partitioningBy(s -> s.length() % 2 == 0,
                        Collectors.counting()));
        numberLengthMap.forEach((k, v) -> System.out.printf("%5s: %d%n", k, v));
        //
        // false: 4
        // true: 8
    }

}
```
## Max and Min
```java
public class MaxMinTest {

    public void maxMinTest() {
        List<Employee> employees = Arrays.asList(
                new Employee("Cersei", 250_000, "Lannister"),
                new Employee("Jamie", 150_000, "Lannister"),
                new Employee("Tyrion", 1_000, "Lannister"),
                new Employee("Tywin", 1_000_000, "Lannister"),
                new Employee("Jon Snow", 75_000, "Stark"),
                new Employee("Robb", 120_000, "Stark"),
                new Employee("Eddard", 125_000, "Stark"),
                new Employee("Sansa", 0, "Stark"),
                new Employee("Arya", 1_000, "Stark"));
        Employee defaultEmployee =
                new Employee("A man (or woman) has no name", 0, "Black and White");
        // 1. reduce
        Optional<Employee> optionalEmp = employees.stream()
                .reduce(BinaryOperator.maxBy(Comparator.comparingInt(Employee::getSalary)));
        System.out.println("Emp with max salary: " +
                optionalEmp.orElse(defaultEmployee));
        // 2. max
        optionalEmp = employees.stream()
                .max(Comparator.comparingInt(Employee::getSalary));
        // 3. maxBy
        optionalEmp = employees.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)));

        OptionalInt maxSalary = employees.stream()
                .mapToInt(Employee::getSalary)
                .max();
        System.out.println("The max salary is " + maxSalary);
        // downstream collector
        Map<String, Optional<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.maxBy(
                                Comparator.comparingInt(Employee::getSalary))));
        map.forEach((house, emp) ->
                System.out.println(house + ": " + emp.orElse(defaultEmployee)));

    }

    public static class Employee {
        private String name;
        private Integer salary;
        private String department;

        public Employee(String name, Integer salary, String department) {
            this.name = name;
            this.salary = salary;
            this.department = department;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSalary() {
            return salary;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}
```
## Immutable Collections
java 1.7 以前创建不可变对象通过以下方式：
```java
static <T> List<T> unmodifiableList(List<? extends T> list)
static <T> Set<T> unmodifiableSet(Set<? extends T> s)
static <K,V> Map<K,V> unmodifiableMap(Map<? extends K,? extends V> m)
    
@SafeVarargs
public final <T> List<T> createImmutableListJava7(T... elements) {
	return Collections.unmodifiableList(Arrays.asList(elements));
}
@SafeVarargs
public final <T> Set<T> createImmutableSetJava7(T... elements) {
	return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(elements)));
}
```
java 1.8 可以使用 collector 的 finisher 参数更加优雅实现：
```java
public class ImmutableTest {
    @SafeVarargs
    public final <T> List<T> createImmutableListJava7(T... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements));
    }

    @SafeVarargs
    public final <T> Set<T> createImmutableSetJava7(T... elements) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(elements)));
    }

    @SafeVarargs
    public final <T> List<T> createImmutableList(T... elements) {
        return Arrays.stream(elements)
                .collect(collectingAndThen(toList(),
                        Collections::unmodifiableList));
    }

    @SafeVarargs
    public final <T> Set<T> createImmutableSet(T... elements) {
        return Arrays.stream(elements)
                .collect(collectingAndThen(toSet(),
                        Collections::unmodifiableSet));
    }
}
```
## Implementing the Collector Interface
绝大部分情况下不需要自己实现一个 collector ，Collectors 类提供的实现已经满足日常开发，了解其实现过程有助于更好的使用 Collectors 。


```java
public interface Collector<T, A, R> {
    /**
     * A function that creates and returns a new mutable result container.
     *
     * @return a function which returns a new, mutable result container
     */
    Supplier<A> supplier();

    /**
     * A function that folds a value into a mutable result container.
     *
     * @return a function which folds a value into a mutable result container
     */
    BiConsumer<A, T> accumulator();

    /**
     * A function that accepts two partial results and merges them.  The
     * combiner function may fold state from one argument into the other and
     * return that, or may return a new result container.
     *
     * @return a function which combines two partial results into a combined
     * result
     */
    BinaryOperator<A> combiner();

    /**
     * Perform the final transformation from the intermediate accumulation type
     * {@code A} to the final result type {@code R}.
     *
     * <p>If the characteristic {@code IDENTITY_TRANSFORM} is
     * set, this function may be presumed to be an identity transform with an
     * unchecked cast from {@code A} to {@code R}.
     *
     * @return a function which transforms the intermediate result to the final
     * result
     */
    Function<A, R> finisher();

    /**
     * Returns a {@code Set} of {@code Collector.Characteristics} indicating
     * the characteristics of this Collector.  This set should be immutable.
     *
     * @return an immutable set of collector characteristics
     */
    Set<Characteristics> characteristics();

}
```
记住以下伪代码即可：
```java
R container = collector.supplier().get();
for (T t : data)
   collector.accumulator().accept(container, t);
return collector.finisher().apply(container);
```
实现 unmodifable SortedSet  示例：
```java
public class CollectorImplTest {

    public SortedSet<String> oddLengthStringSet(String... strings) {
        Collector<String, ?, SortedSet<String>> intoSet =
                Collector.of(TreeSet<String>::new, 				// supplier
                        SortedSet::add,            				// accumulator
                        (left, right) -> {		   				// combiner
                            left.addAll(right);
                            return left;
                        },
                        Collections::unmodifiableSortedSet); 	// finisher
        return Stream.of(strings)
                .filter(s -> s.length() % 2 != 0)
                .collect(intoSet);
    }
}
```
# Optional
optional 的作用：

1. 消除空指针
1. 保证 stream 得以正常处理空值



## 创建 Optional
```java
static <T> Optional<T> empty()
static <T> Optional<T> of(T value)
static <T> Optional<T> ofNullable(T value)
```
Optional 本身不能修改，但是其包裹的对象可以修改：
```java
public class CreateOptionalTest {

    public void createOptionalTest() {
        AtomicInteger counter = new AtomicInteger();
        Optional<AtomicInteger> optional = Optional.ofNullable(counter);
        System.out.println(optional); // Optional[0]
        counter.incrementAndGet();
        System.out.println(optional); // Optional[1]
        optional.get().incrementAndGet();
        System.out.println(optional); // Optional[2]
        optional = Optional.ofNullable(new AtomicInteger());
    }
}
```
## 从 Optional 获取值
不要直接从 optional 中获取值，请使用其他方式，如下：
```java
public class GetValueFromOptionalTest {
    public void getValue() {
        Optional<String> firstOdd =
                Stream.of("five", "even", "length", "string", "values")
                        .filter(s -> s.length() % 2 != 0)
                        .findFirst();
        
        System.out.println(firstOdd.get()); // throws NoSuchElementException

        System.out.println(
                firstOdd.isPresent() ? firstOdd.get() : "No even length strings");

        System.out.println(firstOdd.orElse("No odd length strings"));
        
        System.out.println(firstOdd.orElseGet(()->"default string"));
        
        System.out.println(firstOdd.orElseThrow(NoSuchElementException::new));
        
        firstOdd.ifPresent(val -> System.out.println("Found an even-length string"));
    }
}
```
## Optional 与 Getter Setter
由于 Optional 不能序列化，所以尽量不要再 POJO 的 getter setter 函数使用 optional 。非要使用尽量只在 getter 


## Optional 与 map flatMap
optional 支持 map 和 flatMap ，当遇到嵌套 optional 时，应该使用 flatMap
```java
public class OptionalMapTest {

    @Test
    public void optionalMapTest() {
        Manager mrSlate = new Manager("Mr. Slate");
        Department d = new Department();
        d.setBoss(mrSlate);
        System.out.println("Boss: " + d.getBoss());

        Department d1 = new Department();
        System.out.println("Boss: " + d1.getBoss());
        System.out.println("Name: " +
                d.getBoss().orElse(new Manager("Unknown")).getName());
        System.out.println("Name: " + d.getBoss().map(Manager::getName));
        System.out.println("Name: " + d1.getBoss().map(Manager::getName));
    }
    @Test
    public void flatMapOptionalTest() {
        Manager mrSlate = new Manager("Mr. Slate");
        Department d = new Department();
        d.setBoss(mrSlate);
        Company co = new Company();
        co.setDepartment(d);
        System.out.println("Boss: " + d.getBoss());

        Company co1 = new Company();
        System.out.println("Company Dept: " + co.getDepartment());
        // Company Dept Manager: Optional[Optional[Manager{name='Mr. Slate'}]]
        System.out.println("Company Dept Manager: " + co.getDepartment()
                .map(Department::getBoss));
        System.out.println("Company Dept Manager: " + co1.getDepartment()
                .map(Department::getBoss));
        // Optional[Mr. Slate]
        System.out.println(
                co.getDepartment()
                        .flatMap(Department::getBoss)
                        .map(Manager::getName));
    }
}

```
## Mapping Optional
当需要收集一些列 optional 对象到集合时，需要处理 optional 为空情况，一般有以下两种方式：
```java
public class MappingOptionalTest {
    public Optional<Manager> findEmployeeById(int id) {
        return Optional.of(new Manager(id + ""));
    }

    public List<Manager> findEmployeesByIds(List<Integer> ids) {
        return ids.stream()
                .map(this::findEmployeeById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<Manager> findEmployeesByIds2(List<Integer> ids) {
        return ids.stream()
                .map(this::findEmployeeById)
                .flatMap(optional ->
                        optional.map(Stream::of)
                                .orElseGet(Stream::empty))
                .collect(Collectors.toList());
    }
}

```
findEmployeesByIds 通过 filter 过滤空值，findEmployeesByIds2 是更加 functional  的写法。
# Stream、Lambda 使用注意点
## Objects
objects 中包含很多有用的方法，比如 equals deepEquals isNull noneNull 等
```java
public class ObjectsTest {
    List<String> strings = Arrays.asList(
            "this", null, "is", "a", null, "list", "of", "strings", null);
    List<String> nonNullStrings = strings.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    @Test
    public void testNonNulls() throws Exception {
        List<String> strings =
                Arrays.asList("this", "is", "a", "list", "of", "strings");
        assertTrue(Objects.deepEquals(strings, nonNullStrings));
    }

    public <T> List<T> getNonNullElements(List<T> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
```
## Lambda 访问变量
lamdba 表达式不能修改本地变量：
```java
public class LamdbaVarTest {
    public void finalVarTest() {
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9);
        int total = 0;
        for (int n : nums) {
            total += n;
        }
        total = 0;
        //nums.forEach(n -> total += n);  无法编译
        // 变通方式
        total = nums.stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }
}

```
[Are Java 8 Lambdas Closures?](https://www.bruceeckel.com/2015/10/17/are-java-8-lambdas-closures/)
## Map 新增方法
java 1.8 新增了一些边界的方法：
```java
public class MapTest {
    private Map<Long, BigInteger> cache = new HashMap<>();

    @Test
    public void mapTest() {
        String passage = "NSA agent walks into a bar. Bartender says, " +
                "'Hey, I have a new joke for you.' Agent says, 'heard it'.";
        Map<String, Integer> counts = countWords(passage, "NSA", "agent", "joke");
        counts.forEach((word, count) -> System.out.println(word + "=" + count));
        // Output is agent=1, NSA=2, joke=1
    }

    public Map<String, Integer> fullWordCounts(String passage) {
        Map<String, Integer> wordCounts = new HashMap<>();
        String testString = passage.toLowerCase().replaceAll("\\W", " ");
        Arrays.stream(testString.split("\\s+")).forEach(word ->
                wordCounts.merge(word, 1, Integer::sum));
        return wordCounts;
    }


    public BigInteger fib(long i) {
        if (i == 0) return BigInteger.ZERO;
        if (i == 1) return BigInteger.ONE;
        return cache.computeIfAbsent(i, n -> fib(n - 2).add(fib(n - 1)));
    }

    public static Map<String, Integer> countWords(String passage, String... strings) {
        Map<String, Integer> wordCounts = new HashMap<>();
        Arrays.stream(strings).forEach(s -> wordCounts.put(s, 0));
        Arrays.stream(passage.split(" ")).forEach(word ->
                wordCounts.computeIfPresent(word, (key, val) -> val + 1));
        return wordCounts;
    }
}
```
## 默认方法冲突
java 1.8 接口增加了默认方法，那面一个类实现多个接口后，有可能遇到重复方法，导致冲突；java 遵循以下规则：

1. 当继承类和接口包含冲突方法时，以类方法为准
1. 当两个接口包含相同默认方法时，并且两个接口存在继承关系，那么以子接口为准
1. 如果两个接口包含相同默认方法，且这两个接口无关系时，报错



```java
public class DefaultMethodConflictTest {

    public interface Company {
        default String getName() {
            return "Initech";
        }
        // other methods
    }

    public interface Employee {
        String getFirst();

        String getLast();

        void convertCaffeineToCodeForMoney();

        default String getName() {
            return String.format("%s %s", getFirst(), getLast());
        }
    }

    public class CompanyEmployee implements Company, Employee {
        private String first;
        private String last;

        @Override
        public void convertCaffeineToCodeForMoney() {
            System.out.println("Coding...");
        }

        @Override
        public String getFirst() {
            return first;
        }

        @Override
        public String getLast() {
            return last;
        }

        // 必须实现，可以用 super 调用继承的方法
        @Override
        public String getName() {
            return String.format("%s working for %s",
                    Employee.super.getName(), Company.super.getName());
        }
    }
}
```
## 函数组合
函数式编程的威力：抽象和组合。通过函数组合可以简洁实现很多复杂的功能。
```java
public class ComposeFuncTest {
    @Test
    public void composeFuncTest() {
        Function<Integer, Integer> add2 = x -> x + 2;
        Function<Integer, Integer> mult3 = x -> x * 3;
        Function<Integer, Integer> mult3add2 = add2.compose(mult3);
        Function<Integer, Integer> add2mult3 = add2.andThen(mult3);
        // because (1 * 3) + 2 == 5
        Assert.assertEquals(5, (int) mult3add2.apply(1));
        // because (1 + 2) * 3 == 9
        Assert.assertEquals(9, (int) add2mult3.apply(1));

    }
    @Test
    public void predicateTest() {
        IntPredicate triangular = ComposeFuncTest::isTriangular;
        IntPredicate perfect = ComposeFuncTest::isPerfect;
        IntPredicate both = triangular.and(perfect);
        IntStream.rangeClosed(1, 10_000)
                .filter(both)
                .forEach(System.out::println);
    }

    public static boolean isPerfect(int x) {
        return Math.sqrt(x) % 1 == 0;
    }

    public static boolean isTriangular(int x) {
        double val = (Math.sqrt(8 * x + 1) - 1) / 2;
        return val % 1 == 0;
    }
}
```
## Lambda 与异常
```java
public class LambdaExceptionTest {
    
    public List<Integer> div(List<Integer> values, Integer factor) {
        return values.stream()
                .map( n -> {
                    try {
                        return n / factor;
                    } catch (ArithmeticException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
    private Integer divide(Integer value, Integer factor) {
        try {
            return value / factor;
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Integer> divUsingMethod(List<Integer> values, Integer factor) {
        return values.stream()
                .map(n -> divide(n, factor))
                .collect(Collectors.toList());
    }
}
```
直接将 try catch 写在 lamdba 中使得代码臃肿，最好是将相应方法单独抽出来；保证 stream 处理代码干净，并且有利于测试。


# 参考
[测试代码 git](http://47.101.142.7:6789/backend/java8-feature.git)
[Java Stream 详解](https://colobu.com/2016/03/02/Java-Stream/)
[Java Stream 源码分析](https://my.oschina.net/yano/blog/4772630)
[Java 8 Lambda实现原理分析](https://blog.csdn.net/liupeifeng3514/article/details/80759907)
[Java CompletableFuture 详解](https://colobu.com/2016/02/29/Java-CompletableFuture/)
