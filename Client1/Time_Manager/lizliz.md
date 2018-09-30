#### 进度
- 优秀分配列表页
- 优秀分配详情页
- 日报表页
- 周报表页

#### 存在的问题
- 优秀分配页瀑布流的实现
- 折线图表的实现
- 点击按钮页面的切换

#### 新引用的包
- app下
(```)
	compile 'com.ashokvarma.android:bottom-navigation-bar:2.0.3'
    implementation 'com.android.support:recyclerview-v7:26.1.0'
    implementation 'com.prolificinteractive:material-calendarview:1.4.0'
    implementation 'com.github.PhilJay:MPAndroidChart:v2.1.6'
    compile 'org.quanqi:mpandroidchart:1.7.5'
(```)

- project下
(```)
	allprojects {
	    repositories {
	        maven { url 'https://jitpack.io' }
	    }
	}
(```)

#### 特殊说明
