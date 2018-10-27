死信队列 Dead Letter Exchange
消息没有被消费，就成了死信

 **变成死信的情况**

 >* 消息被拒绝 basic.reject,basic.nack，requeue = false
 >* TTL 过期
 >* 队列达到最大长度

 DLX是一个正常的 Exchange，和一般的 Exchange 没有区别。

 如何使用死信队列：
 首先需要设置死信队列的 exchange 和 queue，然后进行绑定

 >* Exchange dlx.exchange (自定义)
 >* queue dlx.queue （自定义）
 >* RoutingKey # （所有的消息都可以路由到）
 >* 正常的声明交换机，队列，绑定，最后在队列是上添加一个参数
    arguments.put("x-dead-letter-exchange","dlx.exchange"), key 是固定的，value 为交换机名称


