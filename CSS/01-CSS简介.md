# CSS简介

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>体验CSS语法规范</title>
    <style>
        /* 选择器 {样式}*/
        /* 给谁改样式 {改什么样式}*/
        p {
            color: red;
            font-size: 12px;
        }
    </style>
</head>
<body>
    <P>有点意思</P>
</body>
</html>
```

## HTML的局限性

HTML只关注内容语义。如\<h1>表明这个是一个大标题，\<p>表名这个是一个段落，\<img>表明这是一个图片，\<a>表明这是一个链接。

## CSS-网页的美容师
* CSS是层叠样式表（Cascading Style Sheets）的简称。也称为CSS样式表或级联样式表。
* CSS也是一种标记语言
* CSS主要用于设置HTML页面中文本内容（字体、大小、对齐方式等）、图片外形（宽高、边框样式、边距等）以及版面的布局和外观显示样式
* CSS可以美化HTML，让HTML更漂亮，让页面布局更简单。

## 总结：

1、HTML主要做结构，显示元素内容

2、CSS美化HTML,布局网页

3、CSS最大价值：由HTML专注去做呈现结构，样式交给CSS，即结构（HTML）和样式（CSS）相分离

## CSS语法规范
* CSS规则由两个部分构成：选择器以及一条或多条声明。
* 选择器是用于指定CSS样式的HTML标签，花括号内是对该对象设置的具体样式
* 属性和属性值以“键值对”的形式出现
* 属性是对指定的对象设置样式属性，例如字体大小、文本颜色等
* 属性和属性值之间用英文“:”分开
* 多个“键值对”之间用英文“;”进行区分

## CSS代码风格

1、样式格式书写

①紧凑格式

```CSS
p { color: red;font-size: 12px;}
```
②展开格式

```CSS
p {
    color: red;
    font-size: 12px;
}
```

2、样式大小写

推荐样式选择器，属性，属性名关键字全部使用小写字母，特殊情况除外。

3、空格规范

①属性值前面，冒号后面，保留一个空格

②选择器（标签）和大括号中间保留空格

