
GC总结：
1. jdk8默认UseParallelGC
2. 不指定Xmx为总内存1/4，Xms很小
3. 本机单次GC时间可能是：CMS<=G1<UseSerialGC<ParallelGC
4. 在内存充裕的情况下，Xms=Xmx，只跑1s,效果很明细
5. GC的时间跟堆大小直接相关，是不是可以说堆内存并不是越大越好，合适的大小最好
6. 小内存多线程并发下，G1退化


**环境：Mac 2C8G**

- java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis

**默认并行GC，不指定Xmx时，XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 可以看到默认Xmx给了2g。由于初始堆较小，full GC 4次**

```
➜  java java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:4909
```

```
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(335012k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
2020-10-28T21:24:31.938-0800: 0.149: [GC (Allocation Failure) [PSYoungGen: 33176K->5104K(38400K)] 33176K->13389K(125952K), 0.0049030 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T21:24:31.951-0800: 0.162: [GC (Allocation Failure) [PSYoungGen: 38139K->5103K(71680K)] 46424K->24220K(159232K), 0.0060213 secs] [Times: user=0.01 sys=0.01, real=0.00 secs] 
2020-10-28T21:24:31.981-0800: 0.192: [GC (Allocation Failure) [PSYoungGen: 71663K->5117K(71680K)] 90780K->48739K(159232K), 0.0108503 secs] [Times: user=0.01 sys=0.02, real=0.01 secs] 
2020-10-28T21:24:32.000-0800: 0.211: [GC (Allocation Failure) [PSYoungGen: 71677K->5117K(138240K)] 115299K->77215K(225792K), 0.0120544 secs] [Times: user=0.02 sys=0.03, real=0.01 secs] 
2020-10-28T21:24:32.012-0800: 0.224: [Full GC (Ergonomics) [PSYoungGen: 5117K->0K(138240K)] [ParOldGen: 72098K->72554K(145408K)] 77215K->72554K(283648K), [Metaspace: 2710K->2710K(1056768K)], 0.0111582 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T21:24:32.062-0800: 0.273: [GC (Allocation Failure) [PSYoungGen: 133120K->5117K(138240K)] 205674K->111533K(283648K), 0.0138885 secs] [Times: user=0.02 sys=0.03, real=0.01 secs] 
2020-10-28T21:24:32.076-0800: 0.287: [Full GC (Ergonomics) [PSYoungGen: 5117K->0K(138240K)] [ParOldGen: 106416K->102580K(194048K)] 111533K->102580K(332288K), [Metaspace: 2710K->2710K(1056768K)], 0.0127374 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
2020-10-28T21:24:32.105-0800: 0.317: [GC (Allocation Failure) [PSYoungGen: 133120K->45373K(275968K)] 235700K->147954K(470016K), 0.0190699 secs] [Times: user=0.02 sys=0.04, real=0.02 secs] 
2020-10-28T21:24:32.316-0800: 0.528: [GC (Allocation Failure) [PSYoungGen: 275261K->54783K(284672K)] 377842K->208414K(478720K), 0.2245252 secs] [Times: user=0.06 sys=0.06, real=0.23 secs] 
2020-10-28T21:24:32.541-0800: 0.752: [Full GC (Ergonomics) [PSYoungGen: 54783K->0K(284672K)] [ParOldGen: 153631K->179903K(289280K)] 208414K->179903K(573952K), [Metaspace: 2710K->2710K(1056768K)], 0.0514634 secs] [Times: user=0.08 sys=0.02, real=0.05 secs] 
2020-10-28T21:24:32.635-0800: 0.846: [GC (Allocation Failure) [PSYoungGen: 229888K->66369K(487424K)] 409791K->246273K(776704K), 0.0780525 secs] [Times: user=0.03 sys=0.04, real=0.08 secs] 
2020-10-28T21:24:32.794-0800: 1.005: [GC (Allocation Failure) [PSYoungGen: 464193K->95218K(493056K)] 644097K->353363K(782336K), 0.1505508 secs] [Times: user=0.08 sys=0.10, real=0.15 secs] 
2020-10-28T21:24:32.945-0800: 1.156: [Full GC (Ergonomics) [PSYoungGen: 95218K->0K(493056K)] [ParOldGen: 258145K->271910K(406016K)] 353363K->271910K(899072K), [Metaspace: 2710K->2710K(1056768K)], 0.0487070 secs] [Times: user=0.11 sys=0.01, real=0.05 secs] 
Heap
 PSYoungGen      total 493056K, used 16297K [0x0000000795580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 397824K, 4% used [0x0000000795580000,0x000000079656a5f8,0x00000007ada00000)
  from space 95232K, 0% used [0x00000007ada00000,0x00000007ada00000,0x00000007b3700000)
  to   space 141824K, 0% used [0x00000007b7580000,0x00000007b7580000,0x00000007c0000000)
 ParOldGen       total 406016K, used 271910K [0x0000000740000000, 0x0000000758c80000, 0x0000000795580000)
  object space 406016K, 66% used [0x0000000740000000,0x0000000750989808,0x0000000758c80000)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```

---

- java -Xloggc:gc.demo.log -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis

**默认并行GC，指定Xms=Xmx=2G，可以看到无fullGC，Xms的设置效果很明显**
```
➜  java java -Xloggc:gc.demo.log -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:10773
```

```
CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
2020-10-28T21:55:04.456-0800: 0.407: [GC (Allocation Failure) [PSYoungGen: 524800K->87035K(611840K)] 524800K->137787K(2010112K), 0.0545506 secs] [Times: user=0.06 sys=0.11, real=0.06 secs] 
2020-10-28T21:55:04.601-0800: 0.551: [GC (Allocation Failure) [PSYoungGen: 611835K->87039K(611840K)] 662587K->242910K(2010112K), 0.0875817 secs] [Times: user=0.09 sys=0.16, real=0.08 secs] 
2020-10-28T21:55:04.795-0800: 0.746: [GC (Allocation Failure) [PSYoungGen: 611742K->87038K(611840K)] 767612K->361270K(2010112K), 0.0602489 secs] [Times: user=0.09 sys=0.08, real=0.06 secs] 
2020-10-28T21:55:04.932-0800: 0.883: [GC (Allocation Failure) [PSYoungGen: 611838K->87038K(611840K)] 886070K->464862K(2010112K), 0.0517437 secs] [Times: user=0.08 sys=0.07, real=0.05 secs] 
2020-10-28T21:55:05.050-0800: 1.001: [GC (Allocation Failure) [PSYoungGen: 611838K->87037K(611840K)] 989662K->576210K(2010112K), 0.0514836 secs] [Times: user=0.09 sys=0.09, real=0.05 secs] 
Heap
 PSYoungGen      total 611840K, used 303274K [0x0000000795580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 524800K, 41% used [0x0000000795580000,0x00000007a28ab480,0x00000007b5600000)
  from space 87040K, 99% used [0x00000007b5600000,0x00000007baaff418,0x00000007bab00000)
  to   space 87040K, 0% used [0x00000007bab00000,0x00000007bab00000,0x00000007c0000000)
 ParOldGen       total 1398272K, used 489173K [0x0000000740000000, 0x0000000795580000, 0x0000000795580000)
  object space 1398272K, 34% used [0x0000000740000000,0x000000075ddb57a0,0x0000000795580000)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```


---

- java -XX:+UseSerialGC -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis

```
➜  java java -XX:+UseSerialGC -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:9344
```

```
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(252204k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseSerialGC 
2020-10-28T22:09:20.993-0800: 0.203: [GC (Allocation Failure) 2020-10-28T22:09:20.993-0800: 0.203: [DefNew: 34445K->4351K(39296K), 0.0076517 secs] 34445K->12024K(126720K), 0.0077886 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-28T22:09:21.010-0800: 0.220: [GC (Allocation Failure) 2020-10-28T22:09:21.010-0800: 0.220: [DefNew: 39256K->4349K(39296K), 0.0081471 secs] 46929K->22040K(126720K), 0.0082600 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-28T22:09:21.025-0800: 0.235: [GC (Allocation Failure) 2020-10-28T22:09:21.025-0800: 0.235: [DefNew: 39167K->4347K(39296K), 0.0069189 secs] 56858K->33437K(126720K), 0.0070379 secs] [Times: user=0.00 sys=0.01, real=0.01 secs] 
2020-10-28T22:09:21.039-0800: 0.249: [GC (Allocation Failure) 2020-10-28T22:09:21.039-0800: 0.249: [DefNew: 39011K->4349K(39296K), 0.0064348 secs] 68101K->43680K(126720K), 0.0065452 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2020-10-28T22:09:21.050-0800: 0.260: [GC (Allocation Failure) 2020-10-28T22:09:21.050-0800: 0.260: [DefNew: 39238K->4350K(39296K), 0.0064930 secs] 78569K->54152K(126720K), 0.0066498 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-28T22:09:21.063-0800: 0.273: [GC (Allocation Failure) 2020-10-28T22:09:21.063-0800: 0.273: [DefNew: 39269K->4347K(39296K), 0.0081279 secs] 89070K->65048K(126720K), 0.0082343 secs] [Times: user=0.00 sys=0.01, real=0.01 secs] 
2020-10-28T22:09:21.077-0800: 0.287: [GC (Allocation Failure) 2020-10-28T22:09:21.077-0800: 0.287: [DefNew: 39163K->4350K(39296K), 0.0085672 secs] 99865K->77898K(126720K), 0.0086678 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-28T22:09:21.090-0800: 0.300: [GC (Allocation Failure) 2020-10-28T22:09:21.090-0800: 0.300: [DefNew: 39252K->4341K(39296K), 0.0086394 secs] 112800K->90333K(126720K), 0.0087285 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-28T22:09:21.105-0800: 0.315: [GC (Allocation Failure) 2020-10-28T22:09:21.105-0800: 0.315: [DefNew: 39002K->4345K(39296K), 0.0100747 secs]2020-10-28T22:09:21.116-0800: 0.325: [Tenured: 99059K->99159K(99164K), 0.0185109 secs] 124994K->99356K(138460K), [Metaspace: 2709K->2709K(1056768K)], 0.0288961 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] 
2020-10-28T22:09:21.160-0800: 0.370: [GC (Allocation Failure) 2020-10-28T22:09:21.161-0800: 0.370: [DefNew: 65892K->8255K(74432K), 0.0156707 secs] 165051K->121672K(239700K), 0.0158079 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T22:09:21.189-0800: 0.399: [GC (Allocation Failure) 2020-10-28T22:09:21.189-0800: 0.399: [DefNew: 74041K->8251K(74432K), 0.0182739 secs] 187457K->141065K(239700K), 0.0183535 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2020-10-28T22:09:21.217-0800: 0.427: [GC (Allocation Failure) 2020-10-28T22:09:21.217-0800: 0.427: [DefNew: 74369K->8254K(74432K), 0.0199317 secs] 207183K->166786K(239700K), 0.0200242 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2020-10-28T22:09:21.248-0800: 0.458: [GC (Allocation Failure) 2020-10-28T22:09:21.248-0800: 0.458: [DefNew: 74430K->8252K(74432K), 0.0193045 secs]2020-10-28T22:09:21.268-0800: 0.478: [Tenured: 184122K->168431K(184132K), 0.0263463 secs] 232962K->168431K(258564K), [Metaspace: 2710K->2710K(1056768K)], 0.0462474 secs] [Times: user=0.04 sys=0.01, real=0.05 secs] 
2020-10-28T22:09:21.325-0800: 0.535: [GC (Allocation Failure) 2020-10-28T22:09:21.325-0800: 0.535: [DefNew: 112384K->14015K(126400K), 0.0168402 secs] 280815K->204753K(407120K), 0.0169445 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2020-10-28T22:09:21.361-0800: 0.571: [GC (Allocation Failure) 2020-10-28T22:09:21.361-0800: 0.571: [DefNew: 126399K->14015K(126400K), 0.0358004 secs] 317137K->239904K(407120K), 0.0359127 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
2020-10-28T22:09:21.413-0800: 0.623: [GC (Allocation Failure) 2020-10-28T22:09:21.413-0800: 0.623: [DefNew: 126399K->14015K(126400K), 0.0325638 secs] 352288K->277582K(407120K), 0.0326414 secs] [Times: user=0.01 sys=0.01, real=0.03 secs] 
2020-10-28T22:09:21.461-0800: 0.671: [GC (Allocation Failure) 2020-10-28T22:09:21.461-0800: 0.671: [DefNew: 126399K->14015K(126400K), 0.0329548 secs]2020-10-28T22:09:21.494-0800: 0.704: [Tenured: 298841K->247105K(299020K), 0.0418509 secs] 389966K->247105K(425420K), [Metaspace: 2710K->2710K(1056768K)], 0.0751680 secs] [Times: user=0.06 sys=0.01, real=0.07 secs] 
2020-10-28T22:09:21.570-0800: 0.780: [GC (Allocation Failure) 2020-10-28T22:09:21.570-0800: 0.780: [DefNew: 164864K->20543K(185408K), 0.0165397 secs] 411969K->302951K(597252K), 0.0166612 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T22:09:21.607-0800: 0.817: [GC (Allocation Failure) 2020-10-28T22:09:21.607-0800: 0.817: [DefNew: 185407K->20538K(185408K), 0.0373201 secs] 467815K->357519K(597252K), 0.0374363 secs] [Times: user=0.02 sys=0.01, real=0.04 secs] 
2020-10-28T22:09:21.665-0800: 0.875: [GC (Allocation Failure) 2020-10-28T22:09:21.665-0800: 0.875: [DefNew: 185248K->20543K(185408K), 0.0333433 secs] 522229K->411380K(597252K), 0.0334863 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] 
2020-10-28T22:09:21.717-0800: 0.927: [GC (Allocation Failure) 2020-10-28T22:09:21.718-0800: 0.927: [DefNew: 185407K->20543K(185408K), 0.0337324 secs]2020-10-28T22:09:21.751-0800: 0.961: [Tenured: 441771K->297192K(441836K), 0.0444913 secs] 576244K->297192K(627244K), [Metaspace: 2710K->2710K(1056768K)], 0.0785749 secs] [Times: user=0.06 sys=0.02, real=0.08 secs] 
2020-10-28T22:09:21.820-0800: 1.029: [GC (Allocation Failure) 2020-10-28T22:09:21.820-0800: 1.030: [DefNew: 198144K->24765K(222912K), 0.0219832 secs] 495336K->361949K(718236K), 0.0221314 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2020-10-28T22:09:21.866-0800: 1.075: [GC (Allocation Failure) 2020-10-28T22:09:21.866-0800: 1.075: [DefNew: 222909K->24766K(222912K), 0.0228730 secs] 560093K->421908K(718236K), 0.0230039 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
2020-10-28T22:09:21.913-0800: 1.123: [GC (Allocation Failure) 2020-10-28T22:09:21.914-0800: 1.123: [DefNew: 222910K->24767K(222912K), 0.0168864 secs] 620052K->478125K(718236K), 0.0170056 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
Heap
 def new generation   total 222912K, used 220355K [0x0000000740000000, 0x000000074f1e0000, 0x000000076aaa0000)
  eden space 198144K,  98% used [0x0000000740000000, 0x000000074bf00c60, 0x000000074c180000)
  from space 24768K,  99% used [0x000000074d9b0000, 0x000000074f1dffd8, 0x000000074f1e0000)
  to   space 24768K,   0% used [0x000000074c180000, 0x000000074c180000, 0x000000074d9b0000)
 tenured generation   total 495324K, used 453357K [0x000000076aaa0000, 0x0000000788e57000, 0x00000007c0000000)
   the space 495324K,  91% used [0x000000076aaa0000, 0x000000078655b630, 0x000000078655b800, 0x0000000788e57000)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```

---
- java -XX:+UseSerialGC -Xms2048m -Xmx2048m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis

```
➜  java java -XX:+UseSerialGC -Xms2048m -Xmx2048m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:8552
```

```
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(1228788k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseSerialGC 
2020-10-28T22:25:47.472-0800: 0.422: [GC (Allocation Failure) 2020-10-28T22:25:47.472-0800: 0.422: [DefNew: 559232K->69888K(629120K), 0.1033514 secs] 559232K->148762K(2027264K), 0.1035015 secs] [Times: user=0.06 sys=0.04, real=0.10 secs] 
2020-10-28T22:25:47.677-0800: 0.627: [GC (Allocation Failure) 2020-10-28T22:25:47.677-0800: 0.627: [DefNew: 629120K->69887K(629120K), 0.1229534 secs] 707994K->269601K(2027264K), 0.1230677 secs] [Times: user=0.07 sys=0.05, real=0.13 secs] 
2020-10-28T22:25:47.874-0800: 0.824: [GC (Allocation Failure) 2020-10-28T22:25:47.874-0800: 0.824: [DefNew: 629119K->69887K(629120K), 0.0848971 secs] 828833K->386694K(2027264K), 0.0850390 secs] [Times: user=0.05 sys=0.03, real=0.08 secs] 
2020-10-28T22:25:48.035-0800: 0.985: [GC (Allocation Failure) 2020-10-28T22:25:48.035-0800: 0.985: [DefNew: 629119K->69887K(629120K), 0.0953475 secs] 945926K->513232K(2027264K), 0.0954556 secs] [Times: user=0.05 sys=0.04, real=0.10 secs] 
Heap
 def new generation   total 629120K, used 103831K [0x0000000740000000, 0x000000076aaa0000, 0x000000076aaa0000)
  eden space 559232K,   6% used [0x0000000740000000, 0x0000000742125fb0, 0x0000000762220000)
  from space 69888K,  99% used [0x0000000762220000, 0x000000076665fff0, 0x0000000766660000)
  to   space 69888K,   0% used [0x0000000766660000, 0x0000000766660000, 0x000000076aaa0000)
 tenured generation   total 1398144K, used 443344K [0x000000076aaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 1398144K,  31% used [0x000000076aaa0000, 0x0000000785b94338, 0x0000000785b94400, 0x00000007c0000000)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```

---
- java -Xloggc:gc.demo.log -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis

```
➜  java java -Xloggc:gc.demo.log -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:7633

```


```
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(337184k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:MaxNewSize=348966912 -XX:MaxTenuringThreshold=6 -XX:OldPLABSize=16 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC 
2020-10-28T22:38:41.141-0800: 0.131: [GC (Allocation Failure) 2020-10-28T22:38:41.141-0800: 0.131: [ParNew: 34729K->4341K(39296K), 0.0052277 secs] 34729K->13212K(126720K), 0.0053630 secs] [Times: user=0.01 sys=0.01, real=0.00 secs] 
2020-10-28T22:38:41.156-0800: 0.146: [GC (Allocation Failure) 2020-10-28T22:38:41.156-0800: 0.146: [ParNew: 38978K->4352K(39296K), 0.0061239 secs] 47848K->25526K(126720K), 0.0062108 secs] [Times: user=0.02 sys=0.02, real=0.01 secs] 
2020-10-28T22:38:41.168-0800: 0.158: [GC (Allocation Failure) 2020-10-28T22:38:41.168-0800: 0.158: [ParNew: 39278K->4344K(39296K), 0.0086172 secs] 60452K->39045K(126720K), 0.0087003 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.182-0800: 0.172: [GC (Allocation Failure) 2020-10-28T22:38:41.182-0800: 0.172: [ParNew: 39076K->4344K(39296K), 0.0094190 secs] 73777K->53759K(126720K), 0.0095168 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.192-0800: 0.182: [GC (CMS Initial Mark) [1 CMS-initial-mark: 49414K(87424K)] 54047K(126720K), 0.0001660 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-28T22:38:41.192-0800: 0.182: [CMS-concurrent-mark-start]
2020-10-28T22:38:41.212-0800: 0.202: [CMS-concurrent-mark: 0.019/0.019 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-28T22:38:41.212-0800: 0.202: [GC (Allocation Failure) 2020-10-28T22:38:41.212-0800: 0.202: [ParNew: 39012K->4351K(39296K), 0.0086540 secs] 88426K->66835K(126720K), 0.0087482 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.221-0800: 0.211: [CMS-concurrent-preclean-start]
2020-10-28T22:38:41.221-0800: 0.211: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-28T22:38:41.221-0800: 0.211: [CMS-concurrent-abortable-preclean-start]
2020-10-28T22:38:41.226-0800: 0.216: [GC (Allocation Failure) 2020-10-28T22:38:41.226-0800: 0.216: [ParNew: 39227K->4351K(39296K), 0.0074394 secs] 101711K->77406K(126720K), 0.0075378 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.238-0800: 0.228: [GC (Allocation Failure) 2020-10-28T22:38:41.238-0800: 0.228: [ParNew: 39108K->4339K(39296K), 0.0057918 secs] 112163K->86312K(126720K), 0.0059014 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.249-0800: 0.239: [GC (Allocation Failure) 2020-10-28T22:38:41.249-0800: 0.239: [ParNew: 39283K->4337K(39296K), 0.0081073 secs] 121256K->99227K(134656K), 0.0081904 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.262-0800: 0.252: [GC (Allocation Failure) 2020-10-28T22:38:41.262-0800: 0.252: [ParNew: 38828K->4350K(39296K), 0.0079853 secs] 133718K->111892K(147464K), 0.0080971 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.275-0800: 0.265: [GC (Allocation Failure) 2020-10-28T22:38:41.275-0800: 0.265: [ParNew: 39248K->4345K(39296K), 0.0079470 secs] 146791K->123974K(159420K), 0.0080484 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.288-0800: 0.278: [GC (Allocation Failure) 2020-10-28T22:38:41.288-0800: 0.278: [ParNew: 38993K->4345K(39296K), 0.0077222 secs] 158623K->135939K(171388K), 0.0077878 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.300-0800: 0.290: [GC (Allocation Failure) 2020-10-28T22:38:41.300-0800: 0.291: [ParNew: 39184K->4344K(39296K), 0.0083307 secs] 170778K->148648K(184260K), 0.0084327 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
2020-10-28T22:38:41.314-0800: 0.304: [GC (Allocation Failure) 2020-10-28T22:38:41.314-0800: 0.304: [ParNew: 39204K->4351K(39296K), 0.0092850 secs] 183507K->162954K(198568K), 0.0093952 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.329-0800: 0.319: [GC (Allocation Failure) 2020-10-28T22:38:41.329-0800: 0.319: [ParNew: 39024K->4347K(39296K), 0.0085431 secs] 197627K->175939K(211412K), 0.0086180 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.342-0800: 0.332: [GC (Allocation Failure) 2020-10-28T22:38:41.342-0800: 0.332: [ParNew: 39291K->4345K(39296K), 0.0067013 secs] 210883K->186280K(221904K), 0.0068062 secs] [Times: user=0.02 sys=0.01, real=0.00 secs] 
2020-10-28T22:38:41.354-0800: 0.344: [GC (Allocation Failure) 2020-10-28T22:38:41.354-0800: 0.344: [ParNew: 39187K->4349K(39296K), 0.0098111 secs] 221123K->198517K(233976K), 0.0099000 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.369-0800: 0.359: [GC (Allocation Failure) 2020-10-28T22:38:41.369-0800: 0.360: [ParNew: 38718K->4349K(39296K), 0.0099411 secs] 232886K->211203K(246824K), 0.0100359 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2020-10-28T22:38:41.389-0800: 0.379: [GC (Allocation Failure) 2020-10-28T22:38:41.389-0800: 0.379: [ParNew: 39198K->4343K(39296K), 0.0111604 secs] 246052K->223725K(259360K), 0.0112672 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-28T22:38:41.408-0800: 0.398: [GC (Allocation Failure) 2020-10-28T22:38:41.408-0800: 0.398: [ParNew: 38652K->4337K(39296K), 0.0089009 secs] 258034K->233680K(269248K), 0.0091793 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.426-0800: 0.416: [GC (Allocation Failure) 2020-10-28T22:38:41.426-0800: 0.416: [ParNew: 39266K->4339K(39296K), 0.0136777 secs] 268609K->247476K(283024K), 0.0138020 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
2020-10-28T22:38:41.450-0800: 0.440: [GC (Allocation Failure) 2020-10-28T22:38:41.450-0800: 0.440: [ParNew: 39152K->4346K(39296K), 0.0190562 secs] 282289K->260823K(296444K), 0.0191777 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.480-0800: 0.470: [GC (Allocation Failure) 2020-10-28T22:38:41.480-0800: 0.470: [ParNew: 39266K->4350K(39296K), 0.0114132 secs] 295742K->274746K(310312K), 0.0115166 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.499-0800: 0.489: [GC (Allocation Failure) 2020-10-28T22:38:41.499-0800: 0.489: [ParNew: 38588K->4345K(39296K), 0.0078874 secs] 308984K->286266K(321872K), 0.0079783 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.512-0800: 0.502: [GC (Allocation Failure) 2020-10-28T22:38:41.512-0800: 0.503: [ParNew: 39279K->4319K(39296K), 0.0103147 secs] 321200K->298653K(334336K), 0.0104197 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.528-0800: 0.518: [GC (Allocation Failure) 2020-10-28T22:38:41.528-0800: 0.518: [ParNew: 39263K->4345K(39296K), 0.0092473 secs] 333597K->311280K(346856K), 0.0093591 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.543-0800: 0.533: [GC (Allocation Failure) 2020-10-28T22:38:41.543-0800: 0.533: [ParNew: 38800K->4351K(39296K), 0.0068790 secs] 345735K->321377K(356964K), 0.0069841 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.555-0800: 0.545: [GC (Allocation Failure) 2020-10-28T22:38:41.555-0800: 0.545: [ParNew: 39128K->4349K(39296K), 0.0074684 secs] 356154K->331430K(366996K), 0.0075747 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.568-0800: 0.558: [GC (Allocation Failure) 2020-10-28T22:38:41.568-0800: 0.558: [ParNew: 39293K->4349K(39296K), 0.0095829 secs] 366374K->342806K(378320K), 0.0096840 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.584-0800: 0.574: [GC (Allocation Failure) 2020-10-28T22:38:41.584-0800: 0.574: [ParNew: 39270K->4348K(39296K), 0.0279796 secs] 377728K->354349K(389872K), 0.0281208 secs] [Times: user=0.07 sys=0.00, real=0.03 secs] 
2020-10-28T22:38:41.617-0800: 0.607: [GC (Allocation Failure) 2020-10-28T22:38:41.617-0800: 0.607: [ParNew: 38741K->4337K(39296K), 0.0194638 secs] 388742K->365079K(400640K), 0.0195767 secs] [Times: user=0.02 sys=0.01, real=0.02 secs] 
2020-10-28T22:38:41.643-0800: 0.633: [GC (Allocation Failure) 2020-10-28T22:38:41.643-0800: 0.633: [ParNew: 39210K->4347K(39296K), 0.0111560 secs] 399952K->378888K(414572K), 0.0112569 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.660-0800: 0.650: [GC (Allocation Failure) 2020-10-28T22:38:41.660-0800: 0.650: [ParNew: 39266K->4350K(39296K), 0.0115153 secs] 413806K->391488K(427096K), 0.0116077 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.678-0800: 0.668: [GC (Allocation Failure) 2020-10-28T22:38:41.678-0800: 0.668: [ParNew: 39212K->4343K(39296K), 0.0054740 secs] 426351K->397896K(433456K), 0.0055700 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.689-0800: 0.679: [GC (Allocation Failure) 2020-10-28T22:38:41.689-0800: 0.679: [ParNew: 39125K->4351K(39296K), 0.0093774 secs] 432677K->409543K(445228K), 0.0094782 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.703-0800: 0.694: [GC (Allocation Failure) 2020-10-28T22:38:41.704-0800: 0.694: [ParNew: 39046K->4351K(39296K), 0.0099346 secs] 444238K->421144K(456828K), 0.0100292 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.719-0800: 0.709: [GC (Allocation Failure) 2020-10-28T22:38:41.719-0800: 0.709: [ParNew: 39214K->4349K(39296K), 0.0077656 secs] 456007K->431349K(466944K), 0.0078990 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.733-0800: 0.723: [GC (Allocation Failure) 2020-10-28T22:38:41.733-0800: 0.723: [ParNew: 39203K->4351K(39296K), 0.0073602 secs] 466203K->442173K(477792K), 0.0074723 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.746-0800: 0.736: [GC (Allocation Failure) 2020-10-28T22:38:41.746-0800: 0.736: [ParNew: 39167K->4350K(39296K), 0.0089244 secs] 476988K->455740K(491328K), 0.0090070 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.760-0800: 0.750: [GC (Allocation Failure) 2020-10-28T22:38:41.760-0800: 0.750: [ParNew: 39123K->4347K(39296K), 0.0082979 secs] 490513K->466943K(502648K), 0.0083986 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2020-10-28T22:38:41.773-0800: 0.763: [GC (Allocation Failure) 2020-10-28T22:38:41.773-0800: 0.763: [ParNew: 39160K->4351K(39296K), 0.0078552 secs] 501756K->478443K(514092K), 0.0079628 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.786-0800: 0.776: [GC (Allocation Failure) 2020-10-28T22:38:41.786-0800: 0.776: [ParNew: 39075K->4350K(39296K), 0.0081428 secs] 513167K->489375K(525096K), 0.0082328 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.800-0800: 0.791: [GC (Allocation Failure) 2020-10-28T22:38:41.801-0800: 0.791: [ParNew: 39140K->4342K(39296K), 0.0092308 secs] 524165K->501724K(537372K), 0.0093091 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.815-0800: 0.806: [GC (Allocation Failure) 2020-10-28T22:38:41.816-0800: 0.806: [ParNew: 39286K->4350K(39296K), 0.0095262 secs] 536668K->513975K(549624K), 0.0096131 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.831-0800: 0.821: [GC (Allocation Failure) 2020-10-28T22:38:41.831-0800: 0.821: [ParNew: 39164K->4349K(39296K), 0.0119121 secs] 548790K->526517K(562236K), 0.0120071 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.850-0800: 0.840: [GC (Allocation Failure) 2020-10-28T22:38:41.850-0800: 0.840: [ParNew: 39170K->4349K(39296K), 0.0122904 secs] 561338K->540122K(575760K), 0.0123905 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.869-0800: 0.859: [GC (Allocation Failure) 2020-10-28T22:38:41.869-0800: 0.859: [ParNew: 39293K->4329K(39296K), 0.0103082 secs] 575066K->551644K(587296K), 0.0103927 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.887-0800: 0.878: [GC (Allocation Failure) 2020-10-28T22:38:41.888-0800: 0.878: [ParNew: 39193K->4341K(39296K), 0.0089698 secs] 586508K->562585K(598288K), 0.0090505 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.904-0800: 0.894: [GC (Allocation Failure) 2020-10-28T22:38:41.904-0800: 0.894: [ParNew: 39285K->4345K(39296K), 0.0090816 secs] 597529K->574064K(609840K), 0.0091694 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.921-0800: 0.911: [GC (Allocation Failure) 2020-10-28T22:38:41.921-0800: 0.911: [ParNew: 39289K->4349K(39296K), 0.0098434 secs] 609008K->587101K(622884K), 0.0099224 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.937-0800: 0.927: [GC (Allocation Failure) 2020-10-28T22:38:41.937-0800: 0.927: [ParNew: 39132K->4349K(39296K), 0.0100468 secs] 621884K->600105K(635884K), 0.0101198 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:41.952-0800: 0.942: [GC (Allocation Failure) 2020-10-28T22:38:41.952-0800: 0.942: [ParNew: 39293K->4342K(39296K), 0.0116294 secs] 635049K->612563K(648232K), 0.0117069 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:41.969-0800: 0.959: [GC (Allocation Failure) 2020-10-28T22:38:41.969-0800: 0.959: [ParNew: 39194K->4347K(39296K), 0.0113838 secs] 647415K->625748K(661512K), 0.0114582 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
2020-10-28T22:38:41.986-0800: 0.976: [GC (Allocation Failure) 2020-10-28T22:38:41.986-0800: 0.976: [ParNew: 39256K->4340K(39296K), 0.0084299 secs] 660658K->635123K(670880K), 0.0085054 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:42.000-0800: 0.990: [GC (Allocation Failure) 2020-10-28T22:38:42.000-0800: 0.990: [ParNew: 39188K->4345K(39296K), 0.0099990 secs] 669971K->647334K(682968K), 0.0100717 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:42.016-0800: 1.006: [GC (Allocation Failure) 2020-10-28T22:38:42.016-0800: 1.006: [ParNew: 39254K->4349K(39296K), 0.0133544 secs] 682243K->661307K(696964K), 0.0134266 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:38:42.036-0800: 1.026: [GC (Allocation Failure) 2020-10-28T22:38:42.036-0800: 1.026: [ParNew: 39067K->4341K(39296K), 0.0103657 secs] 696025K->673029K(708816K), 0.0104492 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:38:42.055-0800: 1.045: [GC (Allocation Failure) 2020-10-28T22:38:42.055-0800: 1.045: [ParNew: 38948K->4350K(39296K), 0.0147091 secs] 707635K->687231K(722932K), 0.0148176 secs] [Times: user=0.02 sys=0.01, real=0.02 secs] 
2020-10-28T22:38:42.078-0800: 1.068: [GC (Allocation Failure) 2020-10-28T22:38:42.078-0800: 1.068: [ParNew: 39159K->4349K(39296K), 0.0142002 secs] 722040K->698693K(734324K), 0.0142967 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
Heap
 par new generation   total 39296K, used 28308K [0x0000000740000000, 0x0000000742aa0000, 0x0000000754cc0000)
  eden space 34944K,  68% used [0x0000000740000000, 0x0000000741765a60, 0x0000000742220000)
  from space 4352K,  99% used [0x0000000742220000, 0x000000074265f5a8, 0x0000000742660000)
  to   space 4352K,   0% used [0x0000000742660000, 0x0000000742660000, 0x0000000742aa0000)
 concurrent mark-sweep generation total 695028K, used 694343K [0x0000000754cc0000, 0x000000077f37d000, 0x00000007c0000000)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```


---

- java -Xloggc:gc.demo.log -XX:+UseConcMarkSweepGC -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis

```
➜  java java -Xloggc:gc.demo.log -XX:+UseConcMarkSweepGC -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:10177

```

```
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(882088k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:MaxNewSize=348966912 -XX:MaxTenuringThreshold=6 -XX:NewSize=348966912 -XX:OldPLABSize=16 -XX:OldSize=697933824 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC 
2020-10-28T22:40:28.796-0800: 0.275: [GC (Allocation Failure) 2020-10-28T22:40:28.797-0800: 0.275: [ParNew: 272640K->34048K(306688K), 0.0341185 secs] 272640K->83277K(2063104K), 0.0342212 secs] [Times: user=0.05 sys=0.07, real=0.04 secs] 
2020-10-28T22:40:28.874-0800: 0.353: [GC (Allocation Failure) 2020-10-28T22:40:28.874-0800: 0.353: [ParNew: 306688K->34048K(306688K), 0.0389974 secs] 355917K->156583K(2063104K), 0.0391067 secs] [Times: user=0.06 sys=0.07, real=0.04 secs] 
2020-10-28T22:40:28.949-0800: 0.428: [GC (Allocation Failure) 2020-10-28T22:40:28.949-0800: 0.428: [ParNew: 306688K->34047K(306688K), 0.0560452 secs] 429223K->233211K(2063104K), 0.0561454 secs] [Times: user=0.13 sys=0.02, real=0.06 secs] 
2020-10-28T22:40:29.039-0800: 0.518: [GC (Allocation Failure) 2020-10-28T22:40:29.039-0800: 0.518: [ParNew: 306687K->34048K(306688K), 0.0487648 secs] 505851K->304394K(2063104K), 0.0488780 secs] [Times: user=0.16 sys=0.03, real=0.05 secs] 
2020-10-28T22:40:29.124-0800: 0.602: [GC (Allocation Failure) 2020-10-28T22:40:29.124-0800: 0.602: [ParNew: 306688K->34048K(306688K), 0.0693548 secs] 577034K->379285K(2063104K), 0.0694862 secs] [Times: user=0.19 sys=0.03, real=0.07 secs] 
2020-10-28T22:40:29.231-0800: 0.709: [GC (Allocation Failure) 2020-10-28T22:40:29.231-0800: 0.710: [ParNew: 306688K->34048K(306688K), 0.0593626 secs] 651925K->459734K(2063104K), 0.0594579 secs] [Times: user=0.17 sys=0.03, real=0.06 secs] 
2020-10-28T22:40:29.328-0800: 0.807: [GC (Allocation Failure) 2020-10-28T22:40:29.328-0800: 0.807: [ParNew: 306688K->34048K(306688K), 0.0624914 secs] 732374K->542670K(2063104K), 0.0626370 secs] [Times: user=0.18 sys=0.03, real=0.07 secs] 
2020-10-28T22:40:29.427-0800: 0.906: [GC (Allocation Failure) 2020-10-28T22:40:29.428-0800: 0.906: [ParNew: 306688K->34048K(306688K), 0.0599141 secs] 815310K->624809K(2063104K), 0.0600534 secs] [Times: user=0.18 sys=0.03, real=0.06 secs] 
2020-10-28T22:40:29.521-0800: 1.000: [GC (Allocation Failure) 2020-10-28T22:40:29.521-0800: 1.000: [ParNew: 306688K->34048K(306688K), 0.0610924 secs] 897449K->710916K(2063104K), 0.0612146 secs] [Times: user=0.19 sys=0.03, real=0.06 secs] 
Heap
 par new generation   total 306688K, used 292619K [0x0000000740000000, 0x0000000754cc0000, 0x0000000754cc0000)
  eden space 272640K,  94% used [0x0000000740000000, 0x000000074fc82ea8, 0x0000000750a40000)
  from space 34048K, 100% used [0x0000000752b80000, 0x0000000754cc0000, 0x0000000754cc0000)
  to   space 34048K,   0% used [0x0000000750a40000, 0x0000000750a40000, 0x0000000752b80000)
 concurrent mark-sweep generation total 1756416K, used 676868K [0x0000000754cc0000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```

---
- java -Xloggc:gc.demo.log -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis


```
➜  java java -Xloggc:gc.demo.log -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:6797
```

```
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(1468292k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC 
2020-10-28T22:45:27.932-0800: 0.103: [GC pause (G1 Evacuation Pause) (young), 0.0054868 secs]
   [Parallel Time: 4.3 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 103.0, Avg: 104.2, Max: 105.8, Diff: 2.8]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.4, Max: 0.8, Diff: 0.8, Sum: 1.5]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.5, Avg: 2.1, Max: 3.1, Diff: 2.6, Sum: 8.4]
      [Termination (ms): Min: 0.0, Avg: 0.5, Max: 0.9, Diff: 0.9, Sum: 1.8]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 1.4, Avg: 3.0, Max: 4.2, Diff: 2.7, Sum: 11.9]
      [GC Worker End (ms): Min: 107.1, Avg: 107.2, Max: 107.2, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.1 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.8 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 14.0M(14.0M)->0.0B(12.0M) Survivors: 0.0B->2048.0K Heap: 16.0M(128.0M)->6575.5K(128.0M)]
 [Times: user=0.02 sys=0.00, real=0.00 secs] 
2020-10-28T22:45:27.947-0800: 0.118: [GC pause (G1 Evacuation Pause) (young), 0.0028172 secs]
   [Parallel Time: 2.5 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 118.3, Avg: 118.6, Max: 119.3, Diff: 1.0]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.5]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.2, Max: 1, Diff: 1, Sum: 1]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.7, Avg: 1.6, Max: 2.1, Diff: 1.4, Sum: 6.5]
      [Termination (ms): Min: 0.0, Avg: 0.4, Max: 0.6, Diff: 0.6, Sum: 1.5]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 1.4, Avg: 2.1, Max: 2.4, Diff: 1.1, Sum: 8.5]
      [GC Worker End (ms): Min: 120.7, Avg: 120.7, Max: 120.8, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 12.0M(12.0M)->0.0B(25.0M) Survivors: 2048.0K->2048.0K Heap: 21.3M(128.0M)->10.3M(128.0M)]
 [Times: user=0.00 sys=0.01, real=0.01 secs] 
2020-10-28T22:45:27.967-0800: 0.138: [GC pause (G1 Evacuation Pause) (young), 0.0037715 secs]
   [Parallel Time: 3.4 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 138.3, Avg: 138.3, Max: 138.4, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 0.5]
         [Processed Buffers: Min: 1, Avg: 1.5, Max: 3, Diff: 2, Sum: 6]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 2.3, Avg: 2.6, Max: 2.8, Diff: 0.5, Sum: 10.2]
      [Termination (ms): Min: 0.0, Avg: 0.5, Max: 0.8, Diff: 0.8, Sum: 2.1]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 3.3, Avg: 3.4, Max: 3.4, Diff: 0.0, Sum: 13.4]
      [GC Worker End (ms): Min: 141.7, Avg: 141.7, Max: 141.7, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 25.0M(25.0M)->0.0B(72.0M) Survivors: 2048.0K->4096.0K Heap: 41.8M(128.0M)->21.5M(128.0M)]
 [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T22:45:28.022-0800: 0.193: [GC pause (G1 Evacuation Pause) (young), 0.0089355 secs]
   [Parallel Time: 8.5 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 193.3, Avg: 193.3, Max: 193.3, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.4, Avg: 0.5, Max: 0.5, Diff: 0.1, Sum: 2.0]
         [Processed Buffers: Min: 6, Avg: 8.0, Max: 10, Diff: 4, Sum: 32]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 7.6, Avg: 7.7, Max: 7.7, Diff: 0.1, Sum: 30.7]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 8.3, Avg: 8.4, Max: 8.5, Diff: 0.1, Sum: 33.6]
      [GC Worker End (ms): Min: 201.7, Avg: 201.7, Max: 201.8, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 70.0M(72.0M)->0.0B(34.0M) Survivors: 4096.0K->10.0M Heap: 113.1M(128.0M)->56.2M(157.0M)]
 [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:45:28.043-0800: 0.214: [GC pause (G1 Evacuation Pause) (young), 0.0058775 secs]
   [Parallel Time: 3.9 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 214.0, Avg: 214.6, Max: 215.6, Diff: 1.6]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.3]
      [Update RS (ms): Min: 0.0, Avg: 0.7, Max: 1.3, Diff: 1.3, Sum: 2.9]
         [Processed Buffers: Min: 0, Avg: 9.5, Max: 18, Diff: 18, Sum: 38]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 2.3, Avg: 2.3, Max: 2.3, Diff: 0.1, Sum: 9.1]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.5]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 2.3, Avg: 3.2, Max: 3.9, Diff: 1.6, Sum: 12.9]
      [GC Worker End (ms): Min: 217.8, Avg: 217.9, Max: 217.9, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.9 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 34.0M(34.0M)->0.0B(56.0M) Survivors: 10.0M->6144.0K Heap: 98.3M(157.0M)->68.0M(314.0M)]
 [Times: user=0.01 sys=0.01, real=0.00 secs] 
2020-10-28T22:45:28.087-0800: 0.258: [GC pause (G1 Evacuation Pause) (young), 0.0074628 secs]
   [Parallel Time: 3.8 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 257.6, Avg: 257.6, Max: 257.6, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.1, Avg: 0.2, Max: 0.2, Diff: 0.1, Sum: 0.7]
         [Processed Buffers: Min: 1, Avg: 2.5, Max: 3, Diff: 2, Sum: 10]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 3.4, Avg: 3.4, Max: 3.5, Diff: 0.1, Sum: 13.6]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 3.7, Avg: 3.7, Max: 3.8, Diff: 0.1, Sum: 15.0]
      [GC Worker End (ms): Min: 261.3, Avg: 261.4, Max: 261.4, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 3.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.5 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 56.0M(56.0M)->0.0B(92.0M) Survivors: 6144.0K->8192.0K Heap: 137.0M(314.0M)->91.7M(628.0M)]
 [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T22:45:28.151-0800: 0.322: [GC pause (G1 Evacuation Pause) (young), 0.0092123 secs]
   [Parallel Time: 6.2 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 322.4, Avg: 322.4, Max: 322.4, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.3, Avg: 0.3, Max: 0.3, Diff: 0.0, Sum: 1.2]
         [Processed Buffers: Min: 3, Avg: 4.0, Max: 5, Diff: 2, Sum: 16]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 5.6, Avg: 5.6, Max: 5.7, Diff: 0.1, Sum: 22.5]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.3]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 6.1, Avg: 6.1, Max: 6.2, Diff: 0.1, Sum: 24.6]
      [GC Worker End (ms): Min: 328.5, Avg: 328.5, Max: 328.6, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 3.0 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 92.0M(92.0M)->0.0B(108.0M) Survivors: 8192.0K->13.0M Heap: 207.2M(628.0M)->124.8M(912.0M)]
 [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:45:28.235-0800: 0.406: [GC pause (G1 Evacuation Pause) (young), 0.0175707 secs]
   [Parallel Time: 15.0 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 406.5, Avg: 406.5, Max: 406.6, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.2, Avg: 0.2, Max: 0.3, Diff: 0.0, Sum: 0.9]
         [Processed Buffers: Min: 1, Avg: 2.8, Max: 4, Diff: 3, Sum: 11]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 13.9, Avg: 14.1, Max: 14.5, Diff: 0.6, Sum: 56.2]
      [Termination (ms): Min: 0.0, Avg: 0.5, Max: 0.6, Diff: 0.6, Sum: 1.9]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 14.9, Avg: 14.9, Max: 14.9, Diff: 0.0, Sum: 59.6]
      [GC Worker End (ms): Min: 421.4, Avg: 421.4, Max: 421.5, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 2.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 108.0M(108.0M)->0.0B(117.0M) Survivors: 13.0M->16.0M Heap: 261.3M(912.0M)->169.3M(1140.0M)]
 [Times: user=0.02 sys=0.03, real=0.02 secs] 
2020-10-28T22:45:28.348-0800: 0.519: [GC pause (G1 Evacuation Pause) (young), 0.0288186 secs]
   [Parallel Time: 23.2 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 518.9, Avg: 520.5, Max: 523.7, Diff: 4.9]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 0.3]
      [Update RS (ms): Min: 0.0, Avg: 0.4, Max: 0.9, Diff: 0.9, Sum: 1.7]
         [Processed Buffers: Min: 0, Avg: 3.0, Max: 8, Diff: 8, Sum: 12]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 17.5, Avg: 20.4, Max: 21.6, Diff: 4.2, Sum: 81.4]
      [Termination (ms): Min: 0.0, Avg: 0.4, Max: 0.6, Diff: 0.6, Sum: 1.7]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 18.1, Avg: 21.3, Max: 23.0, Diff: 4.8, Sum: 85.3]
      [GC Worker End (ms): Min: 541.8, Avg: 541.9, Max: 541.9, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.2 ms]
   [Other: 5.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 2.5 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 117.0M(117.0M)->0.0B(135.0M) Survivors: 16.0M->17.0M Heap: 318.2M(1140.0M)->210.6M(1322.0M)]
 [Times: user=0.02 sys=0.02, real=0.03 secs] 
2020-10-28T22:45:28.474-0800: 0.645: [GC pause (G1 Evacuation Pause) (young), 0.0116200 secs]
   [Parallel Time: 9.7 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 645.0, Avg: 645.0, Max: 645.1, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.1, Diff: 0.0, Sum: 0.5]
      [Update RS (ms): Min: 0.3, Avg: 0.4, Max: 0.4, Diff: 0.1, Sum: 1.4]
         [Processed Buffers: Min: 2, Avg: 4.8, Max: 6, Diff: 4, Sum: 19]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 8.5, Avg: 8.6, Max: 9.1, Diff: 0.6, Sum: 34.5]
      [Termination (ms): Min: 0.0, Avg: 0.5, Max: 0.6, Diff: 0.6, Sum: 1.9]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 9.6, Avg: 9.6, Max: 9.6, Diff: 0.0, Sum: 38.5]
      [GC Worker End (ms): Min: 654.7, Avg: 654.7, Max: 654.7, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.9 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.2 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 135.0M(135.0M)->0.0B(178.0M) Survivors: 17.0M->19.0M Heap: 383.2M(1322.0M)->261.0M(1468.0M)]
 [Times: user=0.02 sys=0.01, real=0.01 secs] 
2020-10-28T22:45:28.581-0800: 0.752: [GC pause (G1 Evacuation Pause) (young), 0.0171576 secs]
   [Parallel Time: 15.4 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 751.8, Avg: 751.8, Max: 751.8, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.4, Avg: 0.4, Max: 0.4, Diff: 0.0, Sum: 1.5]
         [Processed Buffers: Min: 2, Avg: 4.5, Max: 6, Diff: 4, Sum: 18]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 14.7, Avg: 14.7, Max: 14.7, Diff: 0.1, Sum: 58.8]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.3]
         [Termination Attempts: Min: 1, Avg: 1.2, Max: 2, Diff: 1, Sum: 5]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 15.3, Avg: 15.3, Max: 15.4, Diff: 0.1, Sum: 61.2]
      [GC Worker End (ms): Min: 767.1, Avg: 767.1, Max: 767.1, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 178.0M(178.0M)->0.0B(206.0M) Survivors: 19.0M->25.0M Heap: 473.7M(1468.0M)->311.3M(1584.0M)]
 [Times: user=0.02 sys=0.03, real=0.01 secs] 
2020-10-28T22:45:28.698-0800: 0.869: [GC pause (G1 Evacuation Pause) (young), 0.0222945 secs]
   [Parallel Time: 20.8 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 869.4, Avg: 869.5, Max: 869.5, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.4, Avg: 0.4, Max: 0.4, Diff: 0.0, Sum: 1.6]
         [Processed Buffers: Min: 2, Avg: 4.8, Max: 6, Diff: 4, Sum: 19]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 19.7, Avg: 19.8, Max: 20.0, Diff: 0.3, Sum: 79.4]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 0.9]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 20.6, Avg: 20.7, Max: 20.7, Diff: 0.1, Sum: 82.6]
      [GC Worker End (ms): Min: 890.1, Avg: 890.1, Max: 890.2, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 206.0M(206.0M)->0.0B(258.0M) Survivors: 25.0M->29.0M Heap: 565.7M(1584.0M)->377.0M(1677.0M)]
 [Times: user=0.03 sys=0.04, real=0.03 secs] 
2020-10-28T22:45:28.823-0800: 0.994: [GC pause (G1 Evacuation Pause) (young), 0.0168639 secs]
   [Parallel Time: 15.5 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 994.5, Avg: 994.6, Max: 994.6, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.6]
      [Update RS (ms): Min: 0.4, Avg: 0.4, Max: 0.4, Diff: 0.0, Sum: 1.7]
         [Processed Buffers: Min: 1, Avg: 5.0, Max: 7, Diff: 6, Sum: 20]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.2]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 14.7, Avg: 14.7, Max: 14.7, Diff: 0.0, Sum: 58.9]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.1]
         [Termination Attempts: Min: 1, Avg: 1.2, Max: 2, Diff: 1, Sum: 5]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 15.4, Avg: 15.4, Max: 15.4, Diff: 0.1, Sum: 61.5]
      [GC Worker End (ms): Min: 1009.9, Avg: 1009.9, Max: 1010.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.2 ms]
      [Free CSet: 0.1 ms]
   [Eden: 258.0M(258.0M)->0.0B(308.0M) Survivors: 29.0M->36.0M Heap: 698.1M(1677.0M)->452.4M(1752.0M)]
 [Times: user=0.03 sys=0.03, real=0.02 secs] 
Heap
 garbage-first heap   total 1794048K, used 698956K [0x0000000740000000, 0x00000007401036c0, 0x00000007c0000000)
  region size 1024K, 222 young (227328K), 36 survivors (36864K)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```


---

- java -Xloggc:gc.demo.log -XX:+UseG1GC -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis

```
➜  java java -Xloggc:gc.demo.log -XX:+UseG1GC -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps com.ian.geekjava.homework.GCLogAnalysis
正在执行...
执行结束!共生成对象次数:7921

```

```
Java HotSpot(TM) 64-Bit Server VM (25.144-b01) for bsd-amd64 JRE (1.8.0_144-b01), built on Jul 21 2017 22:07:42 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 8388608k(1203120k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC 
2020-10-28T22:48:19.969-0800: 0.185: [GC pause (G1 Evacuation Pause) (young), 0.0107117 secs]
   [Parallel Time: 9.6 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 185.1, Avg: 185.1, Max: 185.1, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.2, Avg: 0.2, Max: 0.3, Diff: 0.1, Sum: 0.9]
      [Update RS (ms): Min: 0.4, Avg: 0.5, Max: 0.5, Diff: 0.0, Sum: 1.8]
         [Processed Buffers: Min: 6, Avg: 6.2, Max: 7, Diff: 1, Sum: 25]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 8.5, Avg: 8.5, Max: 8.7, Diff: 0.2, Sum: 34.2]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 0.8]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 9.4, Avg: 9.5, Max: 9.6, Diff: 0.1, Sum: 37.9]
      [GC Worker End (ms): Min: 194.5, Avg: 194.6, Max: 194.7, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 1.0 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.5 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.2 ms]
      [Free CSet: 0.0 ms]
   [Eden: 102.0M(102.0M)->0.0B(89.0M) Survivors: 0.0B->13.0M Heap: 133.1M(2048.0M)->45.6M(2048.0M)]
 [Times: user=0.02 sys=0.02, real=0.01 secs] 
2020-10-28T22:48:20.007-0800: 0.224: [GC pause (G1 Evacuation Pause) (young), 0.0079714 secs]
   [Parallel Time: 7.3 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 223.7, Avg: 223.8, Max: 223.8, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.2, Max: 0.2, Diff: 0.1, Sum: 0.6]
      [Update RS (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.0, Sum: 0.6]
         [Processed Buffers: Min: 1, Avg: 1.5, Max: 2, Diff: 1, Sum: 6]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 6.8, Avg: 6.9, Max: 6.9, Diff: 0.1, Sum: 27.6]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 7.2, Avg: 7.3, Max: 7.3, Diff: 0.1, Sum: 29.0]
      [GC Worker End (ms): Min: 231.0, Avg: 231.0, Max: 231.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 89.0M(89.0M)->0.0B(89.0M) Survivors: 13.0M->13.0M Heap: 153.7M(2048.0M)->74.7M(2048.0M)]
 [Times: user=0.01 sys=0.01, real=0.01 secs] 
2020-10-28T22:48:20.041-0800: 0.257: [GC pause (G1 Evacuation Pause) (young), 0.0108916 secs]
   [Parallel Time: 10.3 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 257.2, Avg: 257.2, Max: 257.2, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.0, Sum: 0.5]
      [Update RS (ms): Min: 0.1, Avg: 0.2, Max: 0.2, Diff: 0.1, Sum: 0.7]
         [Processed Buffers: Min: 2, Avg: 2.8, Max: 4, Diff: 2, Sum: 11]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 9.6, Avg: 9.7, Max: 9.8, Diff: 0.2, Sum: 38.7]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.5]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 10.1, Avg: 10.2, Max: 10.2, Diff: 0.1, Sum: 40.6]
      [GC Worker End (ms): Min: 267.3, Avg: 267.4, Max: 267.4, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 89.0M(89.0M)->0.0B(89.0M) Survivors: 13.0M->13.0M Heap: 184.9M(2048.0M)->112.4M(2048.0M)]
 [Times: user=0.02 sys=0.02, real=0.01 secs] 
2020-10-28T22:48:20.077-0800: 0.294: [GC pause (G1 Evacuation Pause) (young), 0.0104866 secs]
   [Parallel Time: 9.9 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 293.7, Avg: 293.8, Max: 293.8, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.3, Avg: 0.3, Max: 0.3, Diff: 0.0, Sum: 1.1]
         [Processed Buffers: Min: 2, Avg: 3.8, Max: 5, Diff: 3, Sum: 15]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 8.6, Avg: 8.8, Max: 9.3, Diff: 0.7, Sum: 35.3]
      [Termination (ms): Min: 0.0, Avg: 0.5, Max: 0.7, Diff: 0.7, Sum: 2.0]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 9.7, Avg: 9.8, Max: 9.9, Diff: 0.1, Sum: 39.1]
      [GC Worker End (ms): Min: 303.5, Avg: 303.5, Max: 303.6, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 89.0M(89.0M)->0.0B(89.0M) Survivors: 13.0M->13.0M Heap: 229.4M(2048.0M)->144.2M(2048.0M)]
 [Times: user=0.02 sys=0.02, real=0.01 secs] 
2020-10-28T22:48:20.104-0800: 0.320: [GC pause (G1 Evacuation Pause) (young), 0.0096370 secs]
   [Parallel Time: 8.9 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 320.3, Avg: 320.9, Max: 321.8, Diff: 1.4]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.3]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 0.7]
         [Processed Buffers: Min: 0, Avg: 2.5, Max: 7, Diff: 7, Sum: 10]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 7.1, Avg: 7.7, Max: 8.2, Diff: 1.1, Sum: 30.8]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 0.8]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 7.3, Avg: 8.2, Max: 8.9, Diff: 1.6, Sum: 32.8]
      [GC Worker End (ms): Min: 329.1, Avg: 329.1, Max: 329.2, Diff: 0.2]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.2 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 89.0M(89.0M)->0.0B(89.0M) Survivors: 13.0M->13.0M Heap: 245.7M(2048.0M)->173.5M(2048.0M)]
 [Times: user=0.01 sys=0.02, real=0.01 secs] 
2020-10-28T22:48:20.138-0800: 0.355: [GC pause (G1 Evacuation Pause) (young), 0.0115879 secs]
   [Parallel Time: 10.9 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 355.0, Avg: 355.1, Max: 355.4, Diff: 0.5]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.5]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 0.9]
         [Processed Buffers: Min: 0, Avg: 2.8, Max: 5, Diff: 5, Sum: 11]
      [Scan RS (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.2]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 10.0, Avg: 10.1, Max: 10.2, Diff: 0.2, Sum: 40.5]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 10.3, Avg: 10.6, Max: 10.8, Diff: 0.5, Sum: 42.5]
      [GC Worker End (ms): Min: 365.7, Avg: 365.7, Max: 365.8, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.2 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 89.0M(89.0M)->0.0B(94.0M) Survivors: 13.0M->13.0M Heap: 286.5M(2048.0M)->204.0M(2048.0M)]
 [Times: user=0.01 sys=0.02, real=0.02 secs] 
2020-10-28T22:48:20.172-0800: 0.388: [GC pause (G1 Evacuation Pause) (young), 0.0120288 secs]
   [Parallel Time: 11.3 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 388.1, Avg: 388.1, Max: 388.2, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 0.7]
      [Update RS (ms): Min: 0.3, Avg: 0.5, Max: 0.5, Diff: 0.2, Sum: 1.8]
         [Processed Buffers: Min: 4, Avg: 5.8, Max: 8, Diff: 4, Sum: 23]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 10.5, Avg: 10.5, Max: 10.6, Diff: 0.1, Sum: 42.1]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.3]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 11.2, Avg: 11.3, Max: 11.3, Diff: 0.0, Sum: 45.0]
      [GC Worker End (ms): Min: 399.4, Avg: 399.4, Max: 399.4, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.2 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 94.0M(94.0M)->0.0B(119.0M) Survivors: 13.0M->14.0M Heap: 323.9M(2048.0M)->234.4M(2048.0M)]
 [Times: user=0.02 sys=0.03, real=0.01 secs] 
2020-10-28T22:48:20.219-0800: 0.435: [GC pause (G1 Evacuation Pause) (young), 0.0141538 secs]
   [Parallel Time: 13.4 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 435.0, Avg: 435.0, Max: 435.0, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.5]
      [Update RS (ms): Min: 0.2, Avg: 0.3, Max: 0.3, Diff: 0.1, Sum: 1.1]
         [Processed Buffers: Min: 2, Avg: 3.2, Max: 4, Diff: 2, Sum: 13]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 12.7, Avg: 12.8, Max: 12.9, Diff: 0.2, Sum: 51.3]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 13.3, Avg: 13.3, Max: 13.4, Diff: 0.1, Sum: 53.4]
      [GC Worker End (ms): Min: 448.3, Avg: 448.3, Max: 448.4, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.0 ms]
   [Eden: 119.0M(119.0M)->0.0B(1211.0M) Survivors: 14.0M->17.0M Heap: 384.6M(2048.0M)->275.3M(2048.0M)]
 [Times: user=0.02 sys=0.03, real=0.02 secs] 
Heap
 garbage-first heap   total 2097152K, used 1546904K [0x0000000740000000, 0x0000000740104000, 0x00000007c0000000)
  region size 1024K, 1010 young (1034240K), 17 survivors (17408K)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```

---
- java -jar gateway-server-0.0.1-SNAPSHOT.jar

```
➜  ~ wrk -t8 -c40 -d60s http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  8 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    13.96ms   39.28ms 654.18ms   92.23%
    Req/Sec     1.91k   727.07     3.94k    66.89%
  904101 requests in 1.00m, 107.94MB read
Requests/sec:  15048.37
Transfer/sec:      1.80MB
```

---
- java -jar -XX:+UseConcMarkSweepGC gateway-server-0.0.1-SNAPSHOT.jar

```
➜  ~ wrk -t8 -c40 -d60s http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  8 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    13.82ms   37.90ms 580.07ms   91.99%
    Req/Sec     2.00k     0.88k   11.27k    67.79%
  946563 requests in 1.00m, 113.01MB read
Requests/sec:  15748.58
Transfer/sec:      1.88MB
```


---

- java -jar -XX:+UseG1GC gateway-server-0.0.1-SNAPSHOT.jar


```
➜  ~ wrk -t8 -c40 -d60s http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  8 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    19.47ms   46.11ms 608.86ms   90.76%
    Req/Sec     1.36k   711.92     4.45k    62.85%
  648715 requests in 1.00m, 77.45MB read
Requests/sec:  10793.05
Transfer/sec:      1.29MB

```


