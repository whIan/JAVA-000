学习笔记
# 2020年12月02日
- 按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
- 读写分离 - 动态切换数据源版本 1.0
> [https://github.com/whIan/JAVA-000/blob/main/Week_07/src/test/java/com/ian/homework/HomeworkApplicationTests.java](https://github.com/whIan/JAVA-000/blob/main/Week_07/src/test/java/com/ian/homework/HomeworkApplicationTests.java)

```
truncateTable
inert one by one
start > 1606918073018
end > 1606918574445
end-start=501427
truncateTable
inert batch by 10000
start > 1606918574496
end > 1606918616543
end-start=42047
1000000
```


- 读写分离 - 动态切换数据源版本 1.0

> [https://github.com/whIan/JAVA-000/blob/main/Week_07/src/test/java/com/ian/homework/HomeworkApplicationTests.java](https://github.com/whIan/JAVA-000/blob/main/Week_07/src/test/java/com/ian/homework/HomeworkApplicationTests.java)

```
[TGoods(id=1, goodsName=iphone 999999, goodsType=tech, goodsDetail=iphone, createTime=1606921449439, createBy=ian, updateTime=1606921449439, updateBy=ian)]
数据库切换 : slave
INFO 65044 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Starting...
INFO 65044 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Start completed.
数据库连接清空：slave
[TGoods(id=1, goodsName=ian, goodsType=ian, goodsDetail=ian, createTime=0, createBy=, updateTime=0, updateBy=)]
```
