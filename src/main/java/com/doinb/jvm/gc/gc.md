
### 4种主要垃圾收集器
* Serial    串行回收      它为单线程环境设计只使用一个线程进行垃圾回收，会暂停所有用户线程。不适合服务器环境。
* Parallel  并行回收      多个垃圾收集线程并行工作，此时用户线程是暂停的，适用于科学计算/大数据处理首台等弱交互场景。
* CMS       并发标记清除  用户线程和垃圾收集线程同时执行(不一定是并行，可能交替执行)，不需要停顿用户线程，互联网公司多用它，适用于对响应时间有要求的场景。
* G1        G1            


###思想：
引用计数  
复制拷贝  
标记清除  
标记整理   
* 输入命令: 查看默认垃圾收集器
> java -XX:+PrintCommandLineFlags -version

### 落地实现：
串行回收：   -XX:+UseSerialGC  
并行回收：   -XX:+UseParallelGC  
并发回收：   -XX:+UseConcMarkSweepGC  
G1：        -XX:+UseG1GC  


### 参数说明：
DefNew      Default New Generation 指定的UseSerialGC垃圾收集器     
Tenured     Old
ParNew      Parallel New Generation
PSYoungGen  Parallel Scavenge
ParOldGen   Parallel Old Generation


>参考参数：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:PrintCommandLineFlags -XX:UseSerialGC
### 吞吐量
Thoughput=运行用户代码时间/(运行用户代码时间+垃圾收集时间),比如：程序运行100分钟,垃圾收集时间1分钟,吞吐量就是99%)。高吞吐量意味着
高效利用CPU的时间,它多用于在后台运算而不需要太多交互的任务。

### 新生代使用的算法：
* 串行GC：运行时STW stop the world  对应的JVM参数：-XX:+UseSerialGC,简单高效,但是慢,需要暂停其他的工作线程。 
* 并行GC：新生代的并行多线程收集器,也是需要暂停其他工程。 对应JVM参数：-XX:+UseParNewGC,启用后：ParNew(Young区用)+Serial Old组合。
* 并行回收GC: JVM参数：-XX:+UseParallelOldGC 使用Parallel Old收集器,设置参数后,新生代Parallel+老年代Parallel Old。
* G1垃圾收集器：最大的好处是化整为零，避免全内存扫描。配置参数：-XX:+UseG1GC -Xmx32g -XX:+MaxGCPauseMillis=100


### 垃圾收集器的选择

| 参数 | 新生代垃圾收集器 | 新生代算法 | 老年代垃圾收集器 | 老年代算法 |
| :------:| :------: | :------: | :------: | :------: |
|-XX+UseSerialGC | SerialGC | 复制算法 | SerialOldGC | 标记整理 | 
|-XX:+UseParNewGC | ParNew | 复制算法 | SerialOldGC | 标记整理 |
|-XX:+UseParallelGC/-XX:+UseParallelOldGC | Parallel[Scavenge] |复制算法|Parallel Old|标记整理|
|-XX:+UseConcMarkSweepGC|ParNew|复制算法|CMS+Serial Old的收集器组合(Serial Old作为CMS出错的后备收集器)|标记清除|
|-XX:+UseG1GC|G1整体上采用标记-整理算法|局部通过复制算法,不会产生内存碎片|-|-|

G1比起CMS有两个优势：
* 1 G1不会产生内存碎片。
* 2 是可以精确控制停顿。该收集器是把整个(新生代、老生代)划分成多个固定大小的区域,每次根据允许停顿的
    时间去收集垃圾最多的区域。

### markdown画表格语法说明
* 1）|、-、:之间的多余空格会被忽略，不影响布局。
* 2）默认标题栏居中对齐，内容居左对齐。
* 3）-:表示内容和标题栏居右对齐，:-表示内容和标题栏居左对齐，:-:表示内容和标题栏居中对齐。
* 4）内容和|之间的多余空格会被忽略，每行第一个|和最后一个|可以省略，-的数量至少有一个。
