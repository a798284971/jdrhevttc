# Host: localhost  (Version 5.7.17-log)
# Date: 2018-05-02 18:42:40
# Generator: MySQL-Front 5.4  (Build 4.153) - http://www.mysqlfront.de/

/*!40101 SET NAMES utf8 */;

#
# Structure for table "adminer"
#

DROP TABLE IF EXISTS `adminer`;
CREATE TABLE `adminer` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Data for table "adminer"
#

INSERT INTO `adminer` VALUES (1,'admin','admin');

#
# Structure for table "checkcode"
#

DROP TABLE IF EXISTS `checkcode`;
CREATE TABLE `checkcode` (
  `username` varchar(255) NOT NULL DEFAULT '',
  `timeStamp` varchar(255) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "checkcode"
#

INSERT INTO `checkcode` VALUES ('123456789','20171220094200',0,'12345'),('17190175362','20180408200203',1,'11368'),('798284971','20171220093400',0,'12345');

#
# Structure for table "choose_question"
#

DROP TABLE IF EXISTS `choose_question`;
CREATE TABLE `choose_question` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `superioe` int(11) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `A` varchar(255) DEFAULT NULL,
  `B` varchar(255) DEFAULT NULL,
  `C` varchar(255) DEFAULT NULL,
  `D` varchar(255) DEFAULT NULL,
  `hasPic` int(11) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

#
# Data for table "choose_question"
#

INSERT INTO `choose_question` VALUES (1,7,'汽车有一个发动机。汽车和发动机之间属于（  ）关系','一般具体','主从关系','分类关系','整体部分',0,NULL,0),(2,7,'结构化程序设计主要强调程序的（  ）。','效率','速度','可读性','大小',0,NULL,0),(3,8,'下面几种白盒测试技术，哪种是最强的覆盖准则（）。','语句覆盖','条件覆盖','判定覆盖','条件组合覆盖',0,NULL,0),(4,8,'软件测试类型按开发阶段分是？','需求测试、单元测试、集成测试、验证测试','单元测试、集成测试、确认测试、系统测试、验收测试','单元测试、集成测试、验收测试','调试、单元测试、集成测试、用户测试',0,NULL,0),(5,9,'设无向树T有3个3度和2个2度顶点，其余顶点都是树叶，则T有（  ）片树叶。','3','4','5','6',0,NULL,0),(6,10,'三次射击能射中最少一次的概率为095，请问一次射击能中的概率是多少？','0.63','0.5','0.32','0.85',0,NULL,0),(7,11,'线性表的顺序存储结构是一种（  ）的存储结构','随机存储','顺序存取','索引存取','散列存取',0,NULL,0),(8,12,'两个字符串相等的冲要条件是（  ）。','两个字符串的长度相等','两个字符串中对应位置上的字符相等','同时具备(A)和(B)两个条件','以上答案都不对',0,NULL,0),(9,13,'若在线性表中采用折半查找法查找元素，该线性表应该：（  ）','元素按值有序','采用顺序存储结构','元素按值有序切采用顺序存储结构','元素按值有序切采用链式存储结构',0,NULL,0),(10,14,'一个排序算法的时间复杂度与（  ）有关','排序算法的稳定性','所需比较关键字的次数','所采用的存储结构','所需辅助存储空间的大小',0,NULL,0),(11,15,'which of the following port is belong to FTP （）','80','21','25','53',0,NULL,0),(12,16,'关系模型中，一个关键字是（）。','可由多个任意属性组成','至多由一个属性组成','可由一个或多个其值能唯一标识该关系模式中任何元组的属性组成','以上都不是',0,NULL,0),(13,17,'关于继承和实现说法正确的是？（）','类可以实现多个接口，接口可以继承多个接口','类可以实现多个接口，接口不能继承多个接口','类和接口都可以实现多个接口','类和接口都不可以实现多个接口',0,NULL,0),(14,17,'如果一个方法或变量是\"private\"访问级别，那么它的访问范围是：','在当前类，或者子类中','在当前类或者它的父类中','在当前类，或者它所有的父类中','在当前类中',0,NULL,0),(15,18,'有关结构体和类的说法不正确的是（）？','结构体直接继承与System.ValueType','结构体不可以声明构造函数','结构是值类型的，而类是引用类型的','结构体可以继承接口',0,NULL,0),(16,7,'哪些设计模式是降低资源使用率：','prototype','singleton','flyweight','abstract factory',0,'',1),(17,7,'下列能对一堆数组a进行正确初始化的语句是（）','int a[10] =(0,0,0,0);','int a[10] = {};','int a[] = {0};','int a[10] = {10*a};',0,'',1),(18,8,'测试设计员的职责有哪些？','制定测试计划','设计测试用例','设计测试过程、脚本','评估测试活动',0,NULL,1),(19,17,'下列说法错误的是（）','其他选项均不正确','java线程类优先级相同','Thread和Runnalble接口没有区别','如果一个类继承了某个类，只能使用Runnable实现线程',0,NULL,1),(20,17,'单例模式中，两个基本要点（）和单子类自己提供单例','构造函数私有','静态工厂方法','以上都不对','唯一实例',0,NULL,1);

#
# Structure for table "answer"
#

DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `answer` varchar(255) DEFAULT NULL,
  `analysis` varchar(500) DEFAULT NULL,
  `question_id` int(11) NOT NULL DEFAULT '0',
  `true_rate` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`question_id`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `choose_question` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "answer"
#

INSERT INTO `answer` VALUES ('2',' 分析对象间的关系，将相关对象抽象成类，其目的是为了简化关联对象，利用类的继承性建立具有继承性层次的类结构。其中一般-特殊结构是把一组有一般—特殊关系的类组织在一起而得到的结构，它是一个以类为结点，以一般-特殊关系为边的连通有向图。整体—部分结构是把—组具有整体—部分关系的类组织在一起的结构，它是一个以类为结点，以整体-部分关系为边的连通有向图。分析题目可知应选择B选项。',1,0),('3','结构化程序设计方法的主要原则可以概括为自顶向下、逐步求精、模块化及限制使用 goto语句，总的来说可使程序结构良好、易读、易理解、易维护。',2,0),('4','只针对程序逻辑中显式存在的语句。如果if结构中没有给出else后的执行分支，语句覆盖就不会考虑这种情况。语句覆盖对多分支的逻辑运算是无法全面反映的，只在乎运行一次，不考虑其他情况。',3,0),('2','单元测试：\n 单元测试又称为模块测试，是针对软件设计的最小单位程序模块进行正确性检查的测试工作，单元测试需要从程序内部结构出发设计测试用例，多个模块可以平行地独立进行单元测试。\n（2）集成测试\n又称为组装测试或联合测试，在单元测试的基础上，需要将所有模块按照概要设计说明书和详细设计说明书的要求进行组装。\n（3）确认测试\n确认测试的目标是验证软件的功能和性能以及其他特性是否与用户的要求一致。确认测试一般包括有效性测试和软件配置复查。一般有第三方测试机构进行。\n（4）系统测试\n 软件作为计算机系统的一部分，与硬件、网络、外设、支撑软件、数据以及人员结合在一起，在实际或模拟环境下，对计算机系统进行测试，\n目的在于与系统需求比较，发现问题\n（5）验收测试\n以用户为主的测试，软件开发人员和质量保证人员参加，由用户设计测试用例。\n不是对系统进行全覆盖测试，而是对核心业务流程进行测试。\n\n所以软件测试类型按开发阶段分为单元测试，集成测试，确认测试，系统测试，验收测试。故选B。',4,0),('3','暂无解析',5,0),('1','三次射击‘至少’能中一次的概率是0.95，一次不中概率是1-p，三次不中的概率是(1-p)^3=1-0.95，1-p=0.368，p=0.632',6,0),('2','本题考查数据的存储结构。线性表的链式存储结构中的每一个存储结点不仅含有一个数据元素，还包括指针，每一个指针指向一个与本结点有逻辑关系的结点，此类存储方式属于顺序存储。',7,0),('3','暂无解析',8,0),('3','只有当线性表中数据元素按值大小有序排列，并且采用顺序存储结构时才能使用折半查找方法查找元素。即使线性表中数据元素按值大小有序排列，但采用的不是顺序存储结构(如链式)，仍然不能够采用折半查找方法。本题应选C。',9,0),('2','在计算时间复杂度的时候，先找出算法的基本操作，然后根据相应的各语句确定它的执行次数',10,0),('2','暂无解析',11,0),('1','唯一标识性是关键字的一个重要性质，关键字可以只有一个属性，也可由多个属性组成“复合关键字”；无冗余性是关键字的另一个重要性质。',12,0),('1','暂无解析',13,0),('4','\n\nprivate 修饰的方法和变量只能在当前类中访问\n                 不带任何修饰符，只能在同一包内的类中，被访问\n                 protected 只能在子类或者当前类中被访问\n                public 所有类中可以访问',14,0),('4','结构：不能从另外一个结构或者类继承，本身也不能被继承，虽然结构没有明确的用sealed声明，可是结构是隐式的sealed',15,0),('2,3','首先单例模式肯定降低了资源使用率，保证该类的实例永远只有一个！\r\n\r\n原型模式适用于在初始化信息不发生变换的情况，克隆的方法比较适合，主要的目的是避免重新初始化对象，如果后面需要对新对象进行，还需要区分深拷贝和浅拷贝。无论是深拷贝还是浅拷贝只是复制了资源，并没有降低资源使用率。\r\n\r\n享元模式(Flyweight): 基于共享技术用于把一些共同的信息（或模块）抽象出来，避免了大量相似类的开销，也降低了资源的使用率。',16,0),('3,4','暂无解析',17,0),('2,3','暂无解析',18,0),('2,3,4','暂无解析',19,0),('1,4','构造方法私有不允许直接继承与直接new只能通过单例模式有的getInstance方法获取实例，并确保该实例在内存中的唯一性',20,0);

#
# Structure for table "correct_rate"
#

DROP TABLE IF EXISTS `correct_rate`;
CREATE TABLE `correct_rate` (
  `question_id` int(11) NOT NULL DEFAULT '0',
  `all_num` int(11) DEFAULT '0',
  `wrong` int(11) DEFAULT '0',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "correct_rate"
#

INSERT INTO `correct_rate` VALUES (1,7,1),(2,12,1),(3,6,0),(4,6,0),(5,0,0),(6,0,1),(7,0,0),(8,0,0),(9,0,0),(10,0,0),(11,0,0),(12,0,0),(13,0,0),(14,0,0),(15,0,0),(16,6,0),(17,6,0),(18,3,0),(19,0,0),(20,0,0);

#
# Structure for table "lunbo"
#

DROP TABLE IF EXISTS `lunbo`;
CREATE TABLE `lunbo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `imgsrc` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Data for table "lunbo"
#

INSERT INTO `lunbo` VALUES (3,'http://jdr-interview.oss-cn-beijing.aliyuncs.com/resouce/f8d1cf20c086441e9ef618620835e729.jpg','123'),(4,'http://jdr-interview.oss-cn-beijing.aliyuncs.com/resouce/506d5d68e36347a3b95173b8eda7e4c5.jpg','456'),(5,'http://jdr-interview.oss-cn-beijing.aliyuncs.com/resouce/94f49a0baf254a4980d6858b547d6430.jpg','789');

#
# Structure for table "message"
#

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "message"
#

INSERT INTO `message` VALUES (1,'测试','www.baidu.com','2017-12-18 09:30:00'),(2,'测试1','www.baidu.com','2017-12-18 09:49:44');

#
# Structure for table "signin_talk"
#

DROP TABLE IF EXISTS `signin_talk`;
CREATE TABLE `signin_talk` (
  `talk_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `talk_text` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`talk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "signin_talk"
#

INSERT INTO `signin_talk` VALUES (1,1,'我在打卡了1天，学习了0道题','20171218164000'),(2,2,'啊啊啊','20171018164000'),(3,2,'我也是','20171219104000');

#
# Structure for table "use_talk"
#

DROP TABLE IF EXISTS `use_talk`;
CREATE TABLE `use_talk` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `talk_id` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Data for table "use_talk"
#

INSERT INTO `use_talk` VALUES (4,1,1,'好开心','20171219144333'),(5,1,1,'好开心a','20171219145044');

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `birth` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `avastar` varchar(255) DEFAULT '',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'jdr','jdr','小九','女',NULL,NULL,NULL),(2,'admin','456','嘿嘿','男','2017-11-05',NULL,NULL),(3,'798284971','123456',NULL,NULL,NULL,NULL,NULL),(4,'123456789','123456',NULL,NULL,NULL,NULL,NULL),(5,'17190175362','123456','毒药','男','2018-01-15','河北师范大学','https://jdr-interview.oss-cn-beijing.aliyuncs.com/headimg/1524898108681.png');

#
# Structure for table "user_colllect"
#

DROP TABLE IF EXISTS `user_colllect`;
CREATE TABLE `user_colllect` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "user_colllect"
#

INSERT INTO `user_colllect` VALUES (1,1,1,'2017-12-09 16:15:23',0),(2,1,2,'2017-12-10 15:22:45',0),(4,1,20,'2017-12-11 10:12:51',0),(5,5,2,'2018-4-28 17:51:28',0),(6,5,3,'2018-4-28 17:51:30',0);

#
# Structure for table "user_fightup"
#

DROP TABLE IF EXISTS `user_fightup`;
CREATE TABLE `user_fightup` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `talk_id` int(11) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "user_fightup"
#

INSERT INTO `user_fightup` VALUES (3,1,1,'20171218174140');

#
# Structure for table "user_setting"
#

DROP TABLE IF EXISTS `user_setting`;
CREATE TABLE `user_setting` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `exam_num` int(11) DEFAULT '5',
  `exam_type` varchar(255) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_setting_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_setting"
#

INSERT INTO `user_setting` VALUES (1,1,'2'),(2,5,'0'),(3,5,'1'),(4,5,'0'),(5,5,'0');

#
# Structure for table "user_status"
#

DROP TABLE IF EXISTS `user_status`;
CREATE TABLE `user_status` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `question_num` int(11) DEFAULT '0',
  `sign_num` int(11) DEFAULT '0',
  `study_num` int(11) DEFAULT '0',
  `time_stamp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_status_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_status"
#

INSERT INTO `user_status` VALUES (1,0,1,0,'20171218110800'),(3,0,0,0,NULL),(4,0,0,0,NULL),(5,0,0,1,'20171218110800');

#
# Structure for table "user_teststatus"
#

DROP TABLE IF EXISTS `user_teststatus`;
CREATE TABLE `user_teststatus` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `exer_id` int(11) NOT NULL,
  `study_num` int(11) DEFAULT '0',
  `right_rate` double DEFAULT '0',
  PRIMARY KEY (`uid`,`exer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Data for table "user_teststatus"
#

INSERT INTO `user_teststatus` VALUES (1,7,0,0),(1,8,0,0),(2,7,0,0),(3,7,0,0),(4,7,0,0),(5,7,1,0),(5,8,2,0),(5,9,0,0),(5,10,0,0),(5,11,0,0),(5,12,0,0),(5,13,0,0),(5,14,0,0),(5,18,2,0);

#
# Structure for table "user_wrong"
#

DROP TABLE IF EXISTS `user_wrong`;
CREATE TABLE `user_wrong` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `superioe` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "user_wrong"
#

INSERT INTO `user_wrong` VALUES (1,1,1,'2017-12-10 15:16:41',0,7),(2,1,2,'2017-12-10 15:22:28',0,7),(3,1,20,'2017-12-10 15:22:40',0,17),(4,5,6,'2018-05-02 17:42:38',0,10);

#
# Structure for table "zx_exercise"
#

DROP TABLE IF EXISTS `zx_exercise`;
CREATE TABLE `zx_exercise` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `superioe` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

#
# Data for table "zx_exercise"
#

INSERT INTO `zx_exercise` VALUES (1,'软件开发',0),(2,'数学与逻辑',0),(3,'数据结构',0),(4,'算法',0),(5,'计算机基础',0),(6,'编程语言',0),(7,'软件工程',1),(8,'软件测试',1),(9,'组合数学',2),(10,'概率统计',2),(11,'数组',3),(12,'字符串',3),(13,'查找',4),(14,'排序',4),(15,'网络基础',5),(16,'数据库',5),(17,'java',6),(18,'c#',6);
