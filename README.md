# GridLayout
An Android layout for placing views in grids.

Each grid has exactly the same width and the same height.

##Gradle

```
dependencies {
    compile 'xiaofei.library:grid-layout:1.0'
}
```

##Usage

```
<xiaofei.library.gridlayout.GridLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:horizontalSpacing="10dp"
    app:verticalSpacing="20dp"
    app:numColumns="2"
    app:numRows="3">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="1"
        android:background="#0F0"/>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="1"
        android:background="#0F0"/>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="1"
        android:background="#0F0"/>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="1"
        android:background="#0F0"/>
</xiaofei.library.gridlayout.GridLayout>
```

See [MainActivity](https://github.com/Xiaofei-it/GridLayout/blob/master/app/src/main/java/xiaofei/library/gridlayouttest/MainActivity.java)
and its [layout](https://github.com/Xiaofei-it/GridLayout/blob/master/app/src/main/res/layout/activity_main.xml) for more information.