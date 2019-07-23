# SearchBox - 自定义搜索框
>> 使用安卓原生控件组合制作的搜索框。支持设置左边图标、右边图标及提示文本，抛出右边图标方法，获取搜索框内容方法，具有清空功能。目前搜索框的字体格式默
认（个人觉得这样看着比较舒服，就懒得抛出设置，有兴趣可参考源码自行修改）。布局用的是安卓Constraintlayout约束布局，特别好用的布局，极力推荐，没有引
入约束布局依赖的记得加依赖。欢迎大家使用以及提出优化意见或Bug！
## 向项目中引入依赖
>> 在Project下的gradle中添加jitpack仓库依赖：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
>> 在app下的gradle中添加如下依赖：
```
dependencies {
    ...
    implementation 'com.github.renoside:SearchBox:v1.1.2'
    ...
}
```
## 如何使用
>> 布局文件示例：
```
<com.renoside.searchbox.SearchBox
    android:id="@+id/search_box"
    android:layout_width="0dp"
    android:layout_height="40dp"
    android:background="#803"
    app:input_hint="请输入搜索内容..."
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:left_ico="@drawable/search"
    app:right_ico="@drawable/camera" />
```
>> java代码中设置：
```
searchBox = findViewById(R.id.search_box);
button = findViewById(R.id.button);
searchBox.setOnRightIcoListener(new OnRightIcoListener() {
    @Override
    public void onClick() {
        Toast.makeText(MainActivity.this, "你点击了右边的图标", Toast.LENGTH_SHORT).show();
    }
});
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this, searchBox.getText(), Toast.LENGTH_SHORT).show();
    }
});
```
## 说明
>> 项目有许多不足之处，还望多多包涵           
>> 作者：renoside     
>> Github主页：https://github.com/renoside          
>> Demo地址：https://github.com/renoside/SearchBoxDemo  
