
学习数据结构的动态网站：国外一所大学开发的网站
https://www.cs.usfca.edu/~galles/visualization/Algorithms.html


视频地址：
https://www.bilibili.com/video/BV1nJ411676A?p=3

数据结构查询效率排序
二叉树
平衡二叉树（红黑树）
Hash散列
Hash算法作索引，对于等值查询速度极快，但是范围查询时短板，没有B+树性能好，因此使用范围较小。
> 为什么这么说？
* B+树任何一个节点内部，从走到右依次都是递增的。 每两个节点之间有指针连接。只要定位到范围的节点，即可全部取出。


B-Tree

红黑树的演示动态地址：
https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
红黑树插入的时候，会自适应平衡，不会出现二叉树的情况（会出现插入的元素全部在一条直线上，查找效率完全没有优化。）
因此红黑树也叫二叉平衡树。
缺点：数据量大的时候，如果需要查找的数据刚好在叶子节点上，那么需要查找的次数非常大。需要解决树的高度的问题！

B-Tree:
结构：
* 叶节点具有相同的深度，叶节点的指针为空。
* 所有索引元素不重复。
* 节点中的数据索引从左到右递增排列。
特点：
一个节点上可以存储更多的元素。查找的时候，直接找到某个大的节点，然后在内存中比对，取出数据，速度比红黑树快。

B+Tree(它是B-Tree的变种)：
结构：
* 非叶子节点不存储data，只存储索引(冗余)，可以放更多的索引。
* 叶子结点包含所有索引字段。
* 叶子节点用指针连接，提高区间访问的性能。
特点：
* 1. 非叶子节点上是没有数据的，把数据都移到叶子节点上；
* 2. 每两个叶子节点之间还有一个指针，非叶子节点上没有指针。
* 3. 为什么把data元素都移动到叶子节点上？

mysql一次磁盘IO能查询多少数据出来？
show global status like 'innodb_page_size'   查询到：16kb

联合索引的底层数据结构长什么样子？


## 分布式ID


[分布式ID详解](https://mp.weixin.qq.com/s/8CGN6aeMy9UuI58ZWlUGEg)

* 百度（uid-generator）
uid-generator是由百度技术部开发，项目GitHub地址 https://github.com/baidu/uid-generator  
uid-generator是基于Snowflake算法实现的，与原始的snowflake算法不同在于，uid-generator支持自定义时间戳、工作机器ID和 序列号 等各部分的位数，而且uid-generator中采用用户自定义workId的生成策略。  
uid-generator需要与数据库配合使用，需要新增一个WORKER_NODE表。当应用启动时会向数据库表中去插入一条数据，插入成功后返回的自增ID就是该机器的workId数据由host，port组成。  
对于uid-generator ID组成结构：workId，占用了22个bit位，时间占用了28个bit位，序列化占用了13个bit位，需要注意的是，和原始的snowflake不太一样，时间的单位是秒，而不是毫秒，workId也不一样，而且同一应用每次重启就会消费一个workId。  

* 美团（Leaf）
Leaf由美团开发，github地址：https://github.com/Meituan-Dianping/Leaf  
Leaf同时支持号段模式和snowflake算法模式，可以切换使用。  

* 号段模式
先导入源码 https://github.com/Meituan-Dianping/Leaf ，在建一张表leaf_alloc

* snowflake模式
snowflake模式获取分布式自增ID的测试url：http://localhost:8080/api/snowflake/get/test

* 滴滴（Tinyid）
Tinyid由滴滴开发，Github地址：https://github.com/didi/tinyid  
Tinyid是基于号段模式原理实现的与Leaf如出一辙，每个服务获取一个号段（1000,2000]、（2000,3000]、（3000,4000]
Tinyid提供http和tinyid-client两种方式接入具体查看文档实现。
