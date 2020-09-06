
## 2020/4/24 xx公司 电话面
MQ消息丢失问题说一下？  
MQ消息重复消费如何处理？  
JVM内存结构介绍一下？  
说说G1工作原理？  
JDK11的ZGC工作原理知道吗？  
你使用的是那个线程池？使用Executors创建线程可以吗？  
说说线程池的底层原理？  
线程池的参数如何设置？  
springboot和springmvc的区别？  
springboot除了有自带的Tomcat，你还知道其他容器吗？(spring-boot-starter-undertow建议了解一下)  
springboot是如何区分环境的（dev、prod环境区分 ConfigFileApplicationListener 配置类中实现）？  
maven有使用过吗？说说maven是如何区分环境打包的？    

## 2020/4/25 周六下午 xx公司 现场二面就问了2个问题其余聊家常
###1.说说你对事务的了解? 事务的传播特性说一说?
> 事务ACID原则要答对， springboot和springmvc中如何启动事务，事务的失效有哪几种情况?  
> 事务的传播特性七种类：  
1、PROPAGATION_REQUIRED：默认事务类型，如果没有，就新建一个事务；如果有，就加入当前事务。适合绝大多数情况。  
2、PROPAGATION_REQUIRES_NEW：如果没有，就新建一个事务；如果有，就将当前事务挂起。  
3、PROPAGATION_NESTED：如果没有，就新建一个事务；如果有，就在当前事务中嵌套其他事务。  
4、PROPAGATION_SUPPORTS：如果没有，就以非事务方式执行；如果有，就使用当前事务。  
5、PROPAGATION_NOT_SUPPORTED：如果没有，就以非事务方式执行；如果有，就将当前事务挂起。即无论如何不支持事务。  
6、PROPAGATION_NEVER：如果没有，就以非事务方式执行；如果有，就抛出异常。  
7、PROPAGATION_MANDATORY：如果没有，就抛出异常；如果有，就使用当前事务。  
接着介绍一下事务的隔离级别，以及不同隔离级别会出现什么样的情况?    

### 2.假如你当前单个服务具备限流功能，但是让你设计一个分布式的限流架构，你是如何考虑的?
> 个人想法： 如果由单击限流，转换为分布式限流，那么我会优先考虑采用服务网关的统一限流方式，通过zuul或则getaway网关对注册上来的服务进行负载均衡，  
>根据服务器的配置，网络IO，磁盘IO，CUP等综合算力，合理分配权重，届时对外暴露的将是一个统一的网关API，而不是单一的服务；同时网关也应当支持动态  
>扩容的能力，当遇到流量洪峰的时候进行弹性扩容，增加机器等硬件设施，达到高并发高可用的分布式架构要求。    

### 3. 你能接收到加班强度?


xx基金公司面试
1. 选一个比较中意的项目介绍一下（a. 介绍技术栈 b. 介绍项目业务）
2. 说说java的基本数据类型有那些？（四类八种）
3. char是几个字节，长度多少？int是几个字节？ 
4. StringBuilder和StringBuffer的区别？
5. 说说Set和List的区别？
6. ArrayList和LinkedList的区别？ 
7. ArrayList和LinkedList哪一个查找效率快？哪一个删除效率慢？ 
8. HashMap和TreeSet的区别？
9. 说说JVM运行时内存结构？
10. 说说何如划分堆内存和栈内存的？
	（ 从三个方面作答：
	物理地址:
	堆的物理地址分配对对象是不连续的。因此性能慢些。在GC的时候也要考虑到不连续的分配，所以有各种算法。比如，标记-消除，复制，标记-压缩，
	分代（即新生代使用复制算法，老年代使用标记——压缩）栈使用的是数据结构中的栈，先进后出的原则，物理地址分配是连续的。所以性能快。
	内存分别:
	堆因为是不连续的，所以分配的内存是在运行期确认的，因此大小不固定。一般堆大小远远大于栈。
	栈是连续的，所以分配的内存大小要在编译期就确认，大小是固定的。
	存放的内容:
	堆存放的是对象的实例和数组。因此该区更关注的是数据的存储
	栈存放：局部变量，操作数栈，返回结果。该区更关注的是程序方法的执行。
	）
11. 说说垃圾回收算法有那些？ 什么时候使用复制算法？
12. synchronize和volatile的区别？
13. 除了synchronize和volatile你还知道那些锁？（CAS自旋锁）
14. 说说ReentrantLock的原理？ （公平锁和非公平锁，初始化是非公平锁）
15. 说说非公平锁NonfairSync和公平锁FairSync的区别？ （公平锁：先申请锁先获得锁。 非公平锁：先申请不一定先获得，和当前线程的权重有关系。）
16. 在项目中如何选择synchronize和ReentrantLock作为锁的？ (参考： 单列模式时使用synchronize，发送短信邮件或者创建订单号时用ReentrantLock)
17. 打开电脑写一个单列模式，并讲解为什么使用双检锁机制实现这个单列？ 
	public class SingletonDemo {
		private static volatile SingletonDemo instance = null;
		private SingletonDemo() {
		}
		public static SingletonDemo getInstance(){
			if (instance == null){
				synchronized (SingletonDemo.class){
					if (instance == null){
						instance = new SingletonDemo();
					}
				}
			}
			return instance;
		}
	}

18. 再写一个二分查找法，不能使用递归的形式实现,并且说说他的实现原理？