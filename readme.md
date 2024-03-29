# Html Parser

html文件解析器

超简单的`HTML`格式信息解析器，利用了 [vars-parser](https://github.com/Verlif/vars-parser/) 来解析`HTML`中的标签（以下称为 __节点__ ）信息，然后生成一个可查询的节点管理器。
通过这个管理器即可获取节点内容。实际上，这是一个简单的`xml解析器`。

可以做的事情包括：

* 遍历节点结构
* 允许`HTML片段`，例如以下这种方式，没有`<html>`或`<body>`等标签，只要是闭合标签都可以解析。

  ```html
  <div class="third">
    <label id="label">你好</label>
  </div>
  ```

* 搜索节点（通过标签名或参数信息来`find`）
* 链式定位节点（`.name("body").name("div", 2).name("ul").index(5)`，通过IDE点就完事了)
* link语法定位节点（`.link("html>body>div[2]>[4]>label")`可能会更方便一些）
* 获取节点内容（`content()`或是自定义`content(VarsHandler)`）
* 获取节点参数（`props()获取参数表`)

请注意，当前版本会忽略掉`script`中的标签，将`script`作为纯文本内容。

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
    <div id="second">这里是second的节点</div>
    <div class="second2">这里是second2的节点</div>
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
// 或是通过name方法来匹配第一个标签
NodeLink nodeLink = holder.name("label;id=label");
if (nodeLink != null) {
    System.out.println(nodeLink.total());
}

// 也可以通过这样的方式精准定位
NodeLink node = holder
        .name("html")
        .name("body")
        // 注意，index(int)与index(String, int)都是从0开始的。
        // 所有下面这行代码实际上是指向的第二个名为 div 的标签。
        .name("div", 1)
        .name("label");
System.out.println(node.content());

// 会获得与上面的方式相同的结果
NodeLink link = holder
        .link("html>body>div[1]>label");
// 或者直接通过语法匹配
NodeLink link = holder
        .link("label;id=label");
```

运行上述代码后，可以在控制台看到以下结果：

```text
你好
你好
```

## 常用方法

* [link方法](docs/link.md)
* [find方法](docs/find.md)
* [select方法](docs/select.md)

## 添加依赖

1. 添加Jitpack仓库源

   __lastVersion__: [![](https://jitpack.io/v/Verlif/html-parser.svg)](https://jitpack.io/#Verlif/html-parser)

   maven

   ```xml
   <repositories>
      <repository>
          <id>jitpack.io</id>
          <url>https://jitpack.io</url>
      </repository>
   </repositories>
   ```

   Gradle

   ```text
   allprojects {
     repositories {
         maven { url 'https://jitpack.io' }
     }
   }
   ```

2. 添加依赖

   maven

   ```xml
      <dependencies>
          <dependency>
              <groupId>com.github.Verlif</groupId>
              <artifactId>html-parser</artifactId>
              <version>lastVersion</version>
          </dependency>
      </dependencies>
   ```

   Gradle

   ```text
   dependencies {
     implementation 'com.github.Verlif:html-parser:lastVersion'
   }
   ```
