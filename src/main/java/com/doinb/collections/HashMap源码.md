[HashMap源码分析](https://www.jianshu.com/p/2e2a18d02218)

## 视频讲解资料
[HashMap视频地址](https://www.bilibili.com/video/BV16J411H7f5?p=40)

## HashMap在jdk7和jdk8的区别？
   * jdk8中会将链表转换为红黑树，阈值是(TREEIFY_THRESHOLD-1)=7;
   * 新节点插入链表的顺序不相同(jdk7是插入头节结,jdk8因为要遍历链表把链表变为红黑树所以采用插入尾结点)
   * hash算法简化
   * resize的逻辑修改(jdk7会出现环链死锁，jdk8不会)
  
