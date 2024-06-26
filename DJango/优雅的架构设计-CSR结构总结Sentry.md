# 优雅的架构设计-CSR结构总结Sentry

解决的问题 ： 应用的错误，异常监控统计，报警通知；性能监控系统，对问题进行跟踪

## Sentry架构之美
* API简单、易用，自动集成；安装简单：架构依赖多，但是使用Docker可以一个命令安装
* 自动对错误，异常进行统计聚合，按照上下文tag进行聚合
* 可以对性能进行统计分析，可抽样；可视化的趋势分析
* 多租户，支持双因素认证，敏感内容自动脱敏
* 开放的架构：可与AD域账号集成，与Google、Stackoverflow等账号集成
* 开放的架构：有完善的插件支持：webhook、gitlab、jira、slack、PushOver、...
* 支持不同环境（开发、测试、预发、线上）；可以配置灵活的告警
* 跨平台，跨端的支持：python、Java、JavaScript、Ruby、Go、Android、iOS、Web
    
    
## Sentry中的概念
* `Symbolicator`:用来解析函数名，堆栈中的文件位置，代码上下文
* `Relay`: 用来处理收到的请求，会立刻返回200或者429，然后把事件放在内存中排队， 然后发到 Kafka ingest-events
* `ingest-consumer`: 消费处理 Relay 发出的消息
* `process_event`: 堆栈处理，插件预处理
* `postgresql`: 用来保存完整的事件数据
* `Snuba`： 事件数据的存储和查询服务
* `clickhouse`: 用作数据仓库，用于 OLAP，搜索，聚合，标签统计
    
## Snuba 的作用
* 用作搜索，图计算，规则处理查询
* Snuba 对 ClickHouse 进行抽象和隔离
* 由两个部分组成的服务
* 提供数据的查询、聚合统计分析服务