# EventPort

module|eventport|eventport-compiler
---|---|---
version|[![](https://jitpack.io/v/mm-sgf/EventPort.svg)](https://jitpack.io/#mm-sgf/EventPort)|[![](https://jitpack.io/v/mm-sgf/EventPort.svg)](https://jitpack.io/#mm-sgf/EventPort)

[EventPort 好用轻量的事件总线](https://www.jianshu.com/p/fa1c318ebd3e)
### 设计背景
不影响对象本身的生命周期的前提下建立一个易维护，使用方便的事件总线机制

### 功能
1. 一对一的对象事件
2. 一对多的对象事件

注意：不支持进程间使用

### 快速接入
#### 在主项目build.gradle 中 添加如下配置
```
kapt {
    arguments {
        arg('EventPort', 'true')
    }
}
```
#### 在使用的模块中添加kapt插件
```
plugins {
    id 'kotlin-kapt'
}
```
### 添加依赖
```
implementation 'com.github.mm-sgf.EventPort:eventport:v1.0.0'
kapt 'com.github.mm-sgf.EventPort:eventport-compiler:v1.0.0'
```

### API 使用
#### 定义事件接口
在EventPort中的所有的消息事件都基于接口也必须是定义的借口，如果将事件注解定义在非接口事件上在编译时会抛出异常导致无法正常编译。    
##### 一对一(SingleEvent)
```
@SingleEvent
interface ModuleSingleEvent {
    fun getMessage() : Int
}
```
##### 一对多(MultiEvent)
```
@MultiEvent
interface ModuleMultiEvent {
    fun printModuleMessage(msg: String)
}
```
#### 事件接口注解介绍
事件接口事通过注解进行定义的，事件注解目前只有两种类型
注解|介绍
---|---
@SingleEvent|该注解定义的是一对一事件，一个事件只能有一个接收者，接口中方法可以有返回值
@MultiEvent|该注解定义的是一对多事件，一个事件可以有多个接收者，接口中方法不可以有返回值

#### 添加事件接受不了者
接收事件同样非常简单，
1. 在需要接收事件的类添加@ReceiveEvent注释
2. 实现事件接口
3. 在实体类创建时将实体类注册到EventPort

##### 一对一(SingleEvent)
注意：如果在程序运行中多次使用`EventPort.inject(this)`注册同一个接收事件类的不同实例，只有最后一次的注册的会生效
```
/**
 * 1.设置接收注释
 * 2.实现事件接口
 */
@ReceiveEvent
class ModuleReceive : ModuleSingleEvent {
    init {
        // 注入接收事件实体
        EventPort.inject(this)
    }

    override fun getMessage(): String {
        return "hello world"
    }
}
```

##### 一对多(MultiEvent)
```
/**
 * 1.设置接收注释
 * 2.实现事件接口
 */
@ReceiveEvent
class ModuleReceive : ModuleMultiEvent {
    init {
        // 注入接收事件实体
        EventPort.inject(this)
    }
    
    override fun printModuleMessage(msg: String) {
        // 接收一对多事件
    }
}
```

#### 发送事件
每一个事件都有对应的一个事件handler , 获取到这个事件handler 就可以发送事件
##### SingleEvent 事件发送
```
val eventHandler = EventPort.findEventHandler(ModuleSingleEvent::class.java)
eventHandler?.getMessage()
```
##### MultiEvent 事件发送

```
val eventHandler = EventPort.findEventHandler(ModuleMultiEvent::class.java)
eventHandler?.printModuleMessage("hello world")
```


