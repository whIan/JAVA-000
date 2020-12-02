# 2020年12月02日
- 按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

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
