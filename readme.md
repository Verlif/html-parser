# Html Parser

html文件解析器

超简单的`HTML`格式信息解析器，利用了 [vars-parser](https://github.com/Verlif/vars-parser/) 来解析`HTML`中的标签（以下称为 __节点__ ）信息，然后生成一个可查询的节点管理器。
通过这个管理器即可获取节点内容。

可以做的事情包括：

* 遍历节点结构
* 搜索节点（通过标签名或参数信息来`find`）
* 链式定位节点（`.name("body").name("div", 2).name("ul").index(5)`，通过IDE点就完事了)
* 获取节点内容（`content()`或是自定义`content(VarsHandler)`）

请注意，内嵌`js`可能会影响节点的解析，因为目前节点是通过`<`与`>`来判定标签的，当内嵌的`js`中含有这些符号时，可能会造成节点错误（未来可能会修复）。

## 使用

例如我这里有一个这样的`HTML`文件：

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>
    <script src='main.js'></script>
</head>
<body>
<div class="first">
    <div class="secend"></div>
</div>
<div class="third">
    <label id="label">你好</label>
</div>
</body>
</html>
```

那么我们只需要这样的代码就可以获取到`label`的内容：

```java
// 加载html文件
File file = new File("E:\\test\\index.html");
HtmlParser parser = new HtmlParser(file);
// 开始解析文件，并生成节点管理器
TagNodeHolder holder = parser.parser();
// 创建参数条件，用于精确匹配
Map<String, String> params = new HashMap<>(2);
params.put("id", "label");
// 通过find方法来获取标签节点对象
List<TagNodeHolder.NodeLink> list = holder.find("label", params);
for (TagNodeHolder.NodeLink nodeLink : list) {
    // 打印标签内容
    System.out.println("node: " + nodeLink.content());
}

// 也可以通过这样的方式精准定位
TagNodeHolder.NodeLink link = holder
        .name("html")
        .name("body")
        // 注意，index(int)与index(String, int)都是从0开始的。
        // 所有下面这行代码实际上是指向的第二个名为 div 的标签。
        .name("div", 1)
        .name("label");
System.out.println(link.content());
```

运行上述代码后，可以在控制台看到以下结果：

```text
你好
你好
```

## 添加依赖

1. 添加Jitpack仓库源

> maven
> ```xml
> <repositories>
>    <repository>
>        <id>jitpack.io</id>
>        <url>https://jitpack.io</url>
>    </repository>
> </repositories>
> ```

> Gradle
> ```text
> allprojects {
>   repositories {
>       maven { url 'https://jitpack.io' }
>   }
> }
> ```

2. 添加依赖

> maven
> ```xml
>    <dependencies>
>        <dependency>
>            <groupId>com.github.Verlif</groupId>
>            <artifactId>html-parser</artifactId>
>            <version>0.1</version>
>        </dependency>
>    </dependencies>
> ```

> Gradle
> ```text
> dependencies {
>   implementation 'com.github.Verlif:html-parser:0.1'
> }
> ```
