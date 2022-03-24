# TagHolder.link

*基于版本 0.4*

`TagHolder.link`是基于`link`语法写成的，类似于`selector`的查找方法。

支持的语法规则如下：

* 字母开头 - 标签名。例如`div`、`li`等。
* `#` 开头 - 匹配参数`id`，无论标签名。例如`#footer`可以匹配`<abc id="footer">`
* `[]` 包裹的数字 - 当前节点下的第几个子节点（从0开始，参数小于0则从0开始）。<br/>
    * 例如`[2]`匹配当前节点下的第3个子节点。<br/>
    * 例如`div[2]`匹配当前节点下的第3个名为div的子节点。<br/>
    * 例如`div;class=colorful[2]`匹配当前节点下的名为div且参数class为colorful的第3个子节点。

所有的标识间都通过`>`来隔断，例如：

```java
NodeLink node=TagHolder.link("#footer>div>[2]>p");
```

上述字符串表会按照以下顺序获取标签：

1. `id`为`footer`的标签 下的
2. 第 __1__ 个名为`div`的标签 下的
3. 第 __3__ 个标签 下的
4. 第 __1__ 个名为`p`的标签