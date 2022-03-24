# TagHolder.find

*基于版本 0.4*

`TagHolder`有两个`find`方法：

* `List<? extend NodeLink> find(String tag)`
* `List<? extend NodeLink> find(String tag, Map<String, String> param)`

`tag`表示了节点名称，例如`body`、`div`等，需要一个完整的节点名，目前版本不支持正则匹配。  
`param`表示节点的属性值，例如需要匹配`class`是`colorful`的`div`节点，那么`param`就需要`put("class", "colorful")`。
返回值是一个匹配的节点数组，按照节点顺序排列（基于深度优先）。

