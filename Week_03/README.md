# 需求一（必须）

> 整合 https://jolokia.org/
> 实现一个自定义 JMX MBean，通过 Jolokia 做 Servlet 代理

Jolokia是一个JMX-HTTP桥，它提供了一种访问JMX beans的替代方法。想要使用Jolokia，只需添加`org.jolokia:jolokia-core`的依赖。例如，使用Maven需要添加以下配置：

```
<dependency>
    <groupId>org.jolokia</groupId>
    <artifactId>jolokia-core</artifactId>
 </dependency>
```

然后在你的管理HTTP服务器上可以通过`/jolokia`访问Jolokia。

在we b.xml中添加如下

```xml
<servlet>
    <servlet-name>jolokia-agent</servlet-name>
    <servlet-class>org.jolokia.http.AgentServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>jolokia-agent</servlet-name>
    <url-pattern>/jolokia/*</url-pattern>
</servlet-mapping>
```

参考 ：https://jolokia.org/tutorial.html




# 需求二（选做）

> 继续完成 Microprofile config API 中的实现
> 扩展 org.eclipse.microprofile.config.spi.ConfigSource 实现，包括 OS 环境变量，以及本地配置文件
> 扩展 org.eclipse.microprofile.config.spi.Converter 实现，提供 String 类型到简单类型
> 通过 org.eclipse.microprofile.config.Config 读取当前应用名称
> 应用名称 property name = “application.name”