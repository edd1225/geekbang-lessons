# geekbang-lessons

## 小马哥Java实战项目训练营 week02
```
要求：
通过课堂上的简易版依赖注入和依赖查找，实现用户注册功能
通过 UserService 实现用户注册注册用户需要校验
Id：必须大于 0 的整数
密码：6-32 位 电话号码: 采用中国大陆方式（11 位校验）
```
## 第一步，引入下面两个jar

hibernate-validator-4.1.0.Final.jar

validation-api-1.0.0.GA.jar

### 添加 hibernate 验证 框架 在maven 的pom
## 第二步，实体类加上注解
```
    @Id
    @GeneratedValue(strategy = AUTO)
//    @NotNull
    @Min(1)
    // 必须大于 0 的整数
    private Long id;

    @Column
    private String name;

    @Column
    @Length(min = 6, max = 32)
    // 6-32 位
    private String password;

    @Column
    @Email
    private String email;

    @Column
    @Length(min = 11, max = 11 ,message = "手机号长度必须是11")
    @NotNull(message = "手机号不允许为空")
    // 采用中国大陆方式（11 位校验）
    private String phoneNumber;

```

