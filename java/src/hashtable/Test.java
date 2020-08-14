package hashtable;

/**
 * test
 */
public class Test {

    public static void main(String[] args){
        /**
         * 哈希表 (hash table, 散列表)
         *
         * 1. 哈希表利用的是数组支持按照下标随机访问数据的特性；
         *
         * 2. 哈希函数(hash function)： hash(key)
         *      1) 哈希函数计算得到一个非负整数的哈希值
         *      2) if key1 == key2, hash(key1) == hash(key2)
         *      3) if key1 != key2, hash(key1) != hash(key2)  (这一点几乎无法实现，hash冲突几乎无法完全避免)
         *      4) 哈希函数不能太复杂，太复杂的函数会消耗更多计算时间，影响到哈希表的性能
         *      5) 哈希函数生成的值要尽可能的随机并均匀分布，减小哈希冲突，以及冲突带来的影响(链表法中slot中元素个数)
         *
         * 3. 哈希冲突：根据 鸽巢原理， 由于数组的存储空间有限，哈希冲突无法避免。常用的解决方法有两种：
         *      1) 开放寻址法 (open addressing): jdk 1.8 ThreadLockMap实现
         *          I.如果有出现冲突，就重新探测一个空闲位置，插入数据。探测方式的简单实现: 线性探测 (linear probing)
         *          II.线性探测的基本操作：
         *             1> 插入：如果位置已经被占用，从当前位置开始依次往后查找，直到找到空闲位置
         *             2> 查找：通过哈希函数计算出哈希值，然后比较哈希值对应数组下标中的元素，如果相等说明就是要找的元素；否则，
         *                      继续依次往后查找，如果遍历到空闲位置还没有找到，说明要查找的元素不存在
         *             3> 删除：在查找时遇到空闲位置就确定为不存在，所以删除时不应该简单的将对应的元素删除，这样会导致查找出错。
         *                     way1:可以将要删除的元素特殊标记，查找时遇到标记的元素依然继续往下探测。
         *                     way2:或者rehash表，假设删除元素下标为 i,将i位置数据设为null, i向后探测到 null的位置为 j,
         *                          rehash[i+1, j),这部分是可能由冲突引起探测的数据。
         *          III. 二次探测 (quadratic probing): 类似线性探测，探测步长为 0， 1^2, 2^2, ...
         *          IV.  双重散列 (double hashing): 使用一组哈希函数，hash1(key), hash2(key), hash3(key), ...
         *                   如果使用第一个计算得到的位置被占用，则使用第二个，以此类推，直到找到空闲位置。
         *          V.  开放寻址法数据都存储在数组中，可以有效地利用 CPU缓存；没有链表指针，方便序列化
         *          VI. 冲突的代价较高(所以load factor应设置较小值)，插入、查找、删除操作较复杂
         *          VII.开放寻址法适用于数据量比较小的情况
         *
         *      2) 链表法 (chaining): jdk 1.8 HashMap
         *          I.  通过哈希函数，计算对应的位置，每个位置(slot)对应一个链表, 插入、删除、查找都是对链表的操作。
         *          II. 链表节点在需要时再创建，不需要事先申请好
         *          III.链表节点需要存储指针，对于比较小的对象来说内存消耗较大，对于大对象可以忽略
         *          IV. 链表节点不是连续分布的，对 CPU缓存不友好
         *          V.  链表法的改进：将链表改为其他数据结构，如jdk 1.8 HashMap 中的链表元素数达到 8个时，改用红黑树，
         *                  在极端情况下即使所有元素都分布在一个位置里，查找时间也仅会退化到 O(logn),可以有效避免
         *                  Hash Collision DoS (hash碰撞的拒绝式服务攻击)。但是插入、删除时维护红黑树需要有所消耗，所以
         *                  元素数小于8个时，又转换成链表。
         *
         * 4. 装载因子 (load factor):
         *      1)定义： load factor  = 填入表中的元素个数 / 散列表的长度
         *      2) 散列表的长度是定值，装载因子越大，表明填入表中的元素越多，产生冲突的可能性越大
         *      3) 对于开放寻址法，装载因子应严格控制在 0.7-0.8 以下, jdk 1.8 ThreadLockMap 的 load factor = 2/3
         *      4) 当装载因子过大时，需要进行动态扩容，重新申请一个更大的哈希表，将数据搬移到新的哈希表中(对每个数据重新计算hash值)
         *      5) 插入数据时间复杂度： 最好情况： O(1), 最坏情况:O(n), 需要扩容, 平均情况：O(1)
         *      6) 扩容数据搬移均摊：为了避免在动态扩容时，一次性将数据搬移到新的哈希表中导致操作异常缓慢，可以将搬移操作分批进行；
         *         扩容时申请新的哈希表空间，但是不将旧数据搬移，依然保留旧的哈希表，当有新数据插入时，将新数据插入新的哈希表，
         *         并从旧的哈希表中拿出一个或多个数据插入新的哈希表中; 查找和删除操作需要兼顾新旧哈希表
         *      7) jdk 1.8 HashMap 默认初始化大小为 16，每次扩容为原来的 2倍，事先对数据量有着预估，设定合理的初始大小，可以减少
         *         扩容次数，提升 HashMap性能。
         *      8) 装载因子的选择要权衡空间、时间复杂度，jdk 1.8 HashMap 的 load factor = 0.75
         *      9) 使用链表法解决冲突时，load factor 甚至可以大于 1
         *
         * 5. 哈希表和链表经常一起使用：
         *     1) 哈希表可以高效的插入、删除、查找，但是数据存储是无序的，链表可以实现数据的有序性
         *     2) jdk 1.8 LinkedHashMap 采用的哈希表和链表的组合，实际上 LinkedHashMap中哈希表也是采用链表法解决哈希冲突的(
         *        HashMap的实现)，而数据又通过一个双向链表串联。
         *     3) 哈希表和链表组合，两种数据结构都是基于源数据建立的，互不影响，对于不同的操作运用恰当的数据结构
         *     4) Redis 实践：
         *         I.  按照数据 key 查询: 数据需要按照key建立散列表结构
         *         II. 按照 score分数范围查询: 数据需要按照score建立跳表结构
         *
         *
         */



        /**
         * 哈希算法 (hash algorithm)
         *
         * 1. A hashing algorithm is a cryptographic hash function. It is a mathematical algorithm that maps data of
         *    arbitrary size to a hash of a fixed size.
         *
         * 2.
         *   1) It should be fast to compute the hash value for any kind of data
         *   2) It should be impossible to regenerate a message from its hash value (brute force attack as the only
         *      option)
         *   3) It should avoid hash collisions (in fact, it can not be avoid, only reduced), each message has
         *      its own hash (multiple calculations remain unchanged).
         *   4) Every change to a message, even the smallest one, should change the hash value. It should be completely
         *      different. It’s called the avalanche effect (雪崩效应).
         *
         * 3.哈希算法应用
         *   1) 安全加密：如加密算法 MD5 (MD5 Message-Digest Algorithm, 哈希值是 128位二进制), SHA (Secure Hash Algorithm)
         *   2) 唯一标识：海量图库搜索图片是否存在，可以取图片的部分二进制数据通过哈希算法计算出哈希值作为唯一标识，构建哈希表(图片
         *               标识和图片路径)，判断图库是否存在某张图片时，按照计算唯一标识的方法计算该图的标识，如果该标识存在，可以
         *               继续进行图片二进制数据的全量比较，判断是否一致(这一步是为了避免唯一标识一样，但不是同一张图的情况，
         *               即 hash collision)
         *   3) 数据校验：
         *   4) 哈希函数：上面介绍的哈希函数对于能否从哈希值计算出原数据并不关心，更关注于是否均匀分布
         *   5) 负载均衡：一次会话中所有请求都路由到同一个服务器上：可以对会话 ID计算哈希值，再将结果与服务器数量进行取模，得到服务
         *               器编号
         *   6) 数据分片：海量图片搜索图片中，构造唯一标识的哈希表，当图片特别多时，无法在一台机器中构造哈希表，可以通过哈希值与
         *               服务器数量取模的方式，选择不同的服务器构造哈希表。
         *   7) 分布式存储：使用上述介绍的负载均衡方式 hash(key) = key mod n 映射数据到缓存服务器，如果要增加或者减少一台
         *                 服务器，可能会改变所有数据的 hash值，导致所有缓存失效。
         *       一致性哈希算法 (consistent hashing)：参考 https://www.toptal.com/big-data/consistent-hashing
         *            维基百科定义：使用一致性哈希算法后，哈希表槽位数的改变平均只需要对 K/n个关键字重新映射 (K - 关键字数量，
         *                         n - 槽位数量)
         *            实现：构建抽象的环结构，将服务器通过哈希函数映射到环中，同时数据也映射到环上，如果数据映射不在服务器节点上，
         *                 顺时针(或逆时针)循环直到遇到第一个服务器，则该数据应该分配到此服务器中。
         *            改进：由于服务器节点在环上的分布不均匀，导致每个服务器节点实际的控制范围大小不一致，所以分配到各个服务器的数
         *                 据分布不均匀。可以通过添加虚拟节点的方式，每个服务器分配多个虚拟节点映射到环上，使得控制的范围更加均匀。
         */

    }
}
