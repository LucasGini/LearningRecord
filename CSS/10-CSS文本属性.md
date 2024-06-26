# CSS文本属性
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>文本属性</title>
    <style>
    div {
        color: #00ff22;
        line-height: 16px;
    }
    h1 {
        /* 本质是让h1盒子里面的文字水平居中对齐 */
        text-align:center
    }
    p {
        text-decoration: underline;
        text-indent: 2em;
    }
    a {
        text-decoration: none;
    }
    </style>

</head>
<body>
<h1>居中对齐的标题</h1>
<div>听说喜欢pink色的男生，都喜欢男人</div>
<p>粉红色的回忆</p>
<a href="#">粉红色的回忆</a>
</body>
</html>
```


CSS Text 属性可定义文本的外观，比如文本的颜色、对齐文本、装饰文本、文本缩进、行间距等。

### 文本颜色

color 属性拥有定义文本的颜色

```CSS
div {
    color: red;
}
```

|  颜色表示   | 属性值                       |
|:-------:|:--------------------------|
| 预定义的颜色值 | red，green，blue，pink       |
|  十六进制   | #FF0000 #FF6600，#29D794   |
|  RGB代码  | rgb(255,0,0)或(100%,0%,0%) |

### 文本对齐
text-align 属性用于设置元素内文本内容的水平对齐方式。
```CSS
div {
    text-align: center;
}
```

|  属性值   | 解释   |
|:------:|:-----|
|  left  | 左对齐  |
| right  | 右对齐  |
| center | 居中对齐 |


### 文本装饰
text-decoration 属性拥有规定添加到文本的修饰。可以给文本添加下划线/删除线/上划线
```CSS
div {
    text-decoration: underline;
}
```


|     属性值      | 描述           |
|:------------:|:-------------|
|     none     | 默认。没有装饰线     |
|  underline   | 下划线。链接a自带下划线 |
|   overline   | 上划线          |
| line-through | 删除线          |


### 文本缩进

text-indent 属性用来指定文本的第一行的缩进，通常是将段落的首行缩进

```CSS
div {
    text-indent: 2em;
}
```

em 是一个相对单位，就是当前元素（font-size）1个文字的大小，如果当前元素没有设置大小，则会按照父元素的1个文字大小。

### 行间距

line-height 属性用于设置行间的距离（行高）。可以控制文字行与行之间的距离。

```CSS
p {
    line-height: 26px;
}
```

### 文本属性总结

|        属性        |  表示  | 注意点                         |
|:----------------:|:----:|:----------------------------|
|      color       | 文本颜色 | 通常用十六进制而且是简写形式表示            |
|    text-align    | 文本对齐 | 可以设定文字水平的对齐方式               |
|   text-indent    | 文本缩进 | 用于段落首行缩进两个字的距离              |
| text- decoration | 文本修饰 | 添加下划线underline  取消下划线  none |
|   line-height    |  行高  | 控制行与行之间的距离                  |


