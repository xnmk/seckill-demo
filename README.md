**商品秒杀项目**

本项目主要针对秒杀的场景进行的开发工作的案例，其中包含用户登陆、商品功能、安全优化、系统压测等模块。完成了后台代码的编写，解决了用户下单、超买、超卖等问题。通过 Jmeter 压力测试，将系统的 QPS 由 150/s 提升到 2000/s。

- 使用 Redis 作为缓存中间件，作用于缓存预热、预减库存；
- 基于 RabbitMQ 实现异步下单，加速接口响应，并达到削峰效果；
- 针对秒杀接口地址进行隐藏，并设置接口限流防刷；
- 针对高并发进行了页面优化，缓存页面至浏览器，前后端分离降低服务器压力，加快用户访问速度。

**秒杀接口逻辑流程图：**

![seckill](https://xnmk-markdown-img.oss-cn-shenzhen.aliyuncs.com/seckill.png)