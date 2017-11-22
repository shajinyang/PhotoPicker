![](sjylogo.png)
# 图片选择库

### 基本介绍
####  链式调用，本地相册选择，支持多选，单选，压缩，裁剪，批量压缩，裁剪


### 如何使用

#### Android Studio
    第一步：
      在项目的gradle里配置
      allprojects {
      		repositories {
      			...
      			maven { url 'https://jitpack.io' }
      		}
      	}

      第二步：
      在module的gradle里配置
      dependencies {
      	        compile 'com.github.shajinyang:SPayUtil:1.0.0'
      	}

### 使用示例

#### 支付宝支付
    new AliPayHelper
        .PBuilder()
        .setAppId(stringHttpResult.data.getPARTNER())
        .setPid(stringHttpResult.data.getSELLER())
        .setRs2(stringHttpResult.data.getRSA_PRIVATE())
        .setBody("")
        .setSubject("测试")
        .setTotalMoney("0.01")
        .setTradeNo("637366373663736")
        .setTargetId("737363")
        .create()
        .pay(MainActivity.this);










