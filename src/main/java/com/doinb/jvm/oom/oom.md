## OOM异常类型有那些？ 必考题
>java.lang.StackOverflowError  
java.lang.OutOfMemoryError:Java heap space  
java.lang.OutOfMemoryError:GC overhead limit exceeded  
java.lang.OutOfMemoryError:Direct buffer memory  
java.lang.OutOfMemoryError:unable to create new native thread  
java.lang.OutOfMemoryError:Metaspace    

## 4种主要垃圾收集器

| 英文名 | 中文名 | 解释 |
| :---: | :---: | :---:|
|Serial|串行回收|它为单线程环境设计只使用一个线程进行垃圾回收，会暂停所有用户线程。不适合服务器环境。|
|Parallel|并行回收|多个垃圾收集线程并行工作，此时用户线程是暂停的，适用于科学计算/大数据处理首台等弱交互场景。|
|CMS|并发标记清除|  用户线程和垃圾收集线程同时执行(不一定是并行，可能交替执行)，不需要停顿用户线程，互联网公司多用它，适用于对响应时间有要求的场景。|
|G1|garbage first|    --  |


## GC思想
>引用计数、复制拷贝、标记清除、标记整理

## 如何得到OOM的dump文件
* 程序启动时添加 -XX:+HeapDumpOnOutOfMemoryError
一旦出现OOM异常，则会在当前目录生成 java_pidXX.hprof 内存快照，此时使用jdk自带的 jvisualvm.exe 程序来查看内存溢出文件，
该文件在jdk安装的bin目录下，在windows中双击打开，然后点击左上角导入 java_pidXX.hprof 快照文件即可分析内存溢出原因。
