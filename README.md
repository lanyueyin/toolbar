# 功能介绍
1、标题在屏幕默认居中显示，可设置不居中显示
2、标题栏自动判断当前的Theme是否设置沉浸于状态栏底部，添加TopPadding自适应
3、依据模板初始化Toolbar，常用的样式可以继承ToolbarBaseMould抽象类创建一个模板
4、支持直接添加控件到Toolbar指定位置中
5、可指定目标控件，Toolbar会为目标控件增加一个marginTop，当背景延伸到Toolbar底部时，防止Toolbar遮盖住目标控件
# 使用
### 第一步:添加依赖
    implementation 'com.github.lanyueyin:toolbar:1.8.2'
### 第二步: 新建一个类继承ToolbarBaseMould抽象类，实现其中需要的方法
    public class NormalToolbarMould extends ToolbarBaseMould{
      public NormalToolbarMould(Toolbar toolbar) {
        //当调用super()方法时，会自动绑定到toolbar。
        //若需要根据参数创建控件，就不要调用super()方法，而是调用bindToolbar()方法
        super(toolbar)
      }

      @Override
      public View initLeftMould(Context context) {
        //此处返回的控件将放置在左边，返回nu11时，父控件将会隐藏，不占用空间
        return null;
      }

      @Override
      public View initTitleMould(Context context) {
        //此处返回的控件将放置在中间，返回nu11时，父控件将会隐藏，不占用空间
        return null;
      }

      @Override
      public View initRightMould(Context context) {
        //此处返回的控件将放置在右边，返回nu11时，父控件将会隐藏，不占用空间
        return null;
      }
    }
### 第三步: 在布局文件中添加Toolbar控件
    <com.lanyueyin.toolbar.Toolbar
      android:id="@+id/toolbarView"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"/>
### 第四步: Toolbar与ToolbarBaseMould的实现类绑定
    new NormalToolbarMould(this， findViewById(R.id.toolbarView));
### 第五步 (非必须) : 实现Toolbar不遮挡指定的控件
在xml中为Toolbar添加属性

    android:labelFor-"@id/..."

或者在代码中调用

    findViewById(R.id.toolbarView).setLabelFor(R.id...)

**理论上，只要其它控件都相对这个指定的控件布局，那么就可以实现布局整体往下移动一个Tolbar的高度，若背景需要延伸到标题栏下方，那么这就是个很人性化功能了**

### 示例:
[ToolbarBaseMould的实现类实例](https://github.com/lanyueyin/toolbar/blob/main/app/src/main/java/com/dep/myapplication/mould/NormalToolbarMould.java)主要是使用链式调用，因为看起来舒服