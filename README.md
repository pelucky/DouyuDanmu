# Douyu TV danmu collector

## What can it do!
- It can collect danmu from Douyu TV
- After package, you can edit conf/config.properties to change Room_id

## Result

```
pel@raspberrypi:~/DouyuDanmu/target $ sh run.sh start
java -XX:+TieredCompilation -Xmx1024m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=1 -XX:GCLogFileSize=100M -Dfile.encoding=UTF-8 -cp danmu-0.0.1-SNAPSHOT.jar:./conf com.pelucky.danmu.DanmuApp
start
pel@raspberrypi:~/DouyuDanmu/target $ OpenJDK Zero VM warning: TieredCompilation is disabled in this release.
19:54:37.809 [main] INFO  com.pelucky.danmu.util.TcpSocketClient - Connect to Server 124.95.174.146:8601.
19:54:37.934 [main] INFO  com.pelucky.danmu.util.TcpSocketClient - Open Socket successfully
19:54:37.960 [main] INFO  com.pelucky.danmu.util.Danmu - Danmu start succefully!
AcerSword: 赢了
浮夸的谎言: 这个叫啥游戏
斑驳o: 汪涵玩的确实不错
吼吼吼吼吼犀利: 老大练练李广
黄昏过后丶: 66666666666
彡宇: 66666666666
36摄氏度的阳光: 不容易
佰殇: 厉害了老大
yuyuyu123554: 6666666
ppykj001: 2333333333333
汉升汉升: 666666
我clgb: 666666666
是选择遗忘: 对面放水的厉害
自由的人生: 老大威武
静了光阴: 6666
天堂不也就这样: 水友演得好啊
V999999999999999: 厉害了，我的猴
折灬戟沉丿沙: 谁玩游戏不想赢啊，放锤子水
姐姐别摸我: 弹幕大神很NB
谢广坤666666: 6666
吃河蟹的痞老板: 阔以
有一点小简单: 这个游戏 表哥永远在
国民男神丶GeTao: 666
龘靐齾龗: 66666666666
Mass0000: 66666666666
小梵家: 666666
fhjmuqueen: 爱看不看  赢了就是放水？？草泥马的
纸币3: 666696
或许行123: 666666666666666
肥皂就用臭肥皂: 躺赢？
fzzzzzzzzzzzzz: 全是老大和队友一点点琢磨的那些说菜的好意思？
虫小七: 厉害啦 弟弟
褐瞳2016: 老大哪个区
暗黑の瓜皮: 终于赢了
瑾小主: 首胜啊
Treasure雨丶: 总算赢了一局
有一点小简单: 这配音
ganda2002: 可终于赢嘞
FadedUs: 66666666666
寅子家的冰箱贴: 6666666666666
chs303007: 终于赢了
waericdh: 老大笑了
黄昏过后丶: 666666666666666666666
eve封尘: 这配音是不是表哥配音的
均失: 66666666666
大貔貅兽: 66666666666666666666
Z空空即空空: 猴笑一个
折灬戟沉丿沙: 66666666666666666666
谛不凡: 老大在那个服务器啊
hongjiaming1: 终于赢了
壞槢慣: 振作啊 弟弟
简爱别逗: 66666666666
就是不吃鱼0: 喷子真多爱看看就看不看关键走
啊哈啊哈呵110: 终于赢了。。
月夜枫姜军: 看下甲片
我clgb: 666666666666
堕落的路西法11: 险些掉段
奥斯特里茨的阳光: 老大厉害了！！
小母牛不下崽: 66
水中深穴: 66666666666666
邪神魂: 更新你吗
A站二胡帝: 赢了
```

## Environment
- You should have at lesast Java 1.8 and Maven 3
- Linux is perfered, Windows is also OK


## Usage

```
mvn clean package
cd target
Linux start:sh run.sh start
Linux Stop:sh run.sh stop
Windows
```
## Configuration

```
danmu_server=openbarrage.douyutv.com 	# Open Barrage's server, Should not change!
danmu_port=8601				# Port, Should not change!
room_id=71415  				# Room_id of Douyu TV
```
