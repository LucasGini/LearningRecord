# CSS字体属性
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CSS字体属性之字体系列</title>
    <style>
        h2 {
            font-family:"微软雅黑";
        }
        p {
            font-family: "Microsoft YaHei", Arial, Helvetica, sans-serif;
        }
        body {
            font-size: 16px;
        }
        /* 标题标签比较特殊，需要单独指导文字大小 */
        h2 {
            font-size: 16px;
        }
        .bold {
            /* font-weight: bold; */
            /* 700后面不要跟单位   等价于bold都是加粗的效果 */
            /* 实际开发中，提倡使用数字 表示加粗或者变细 */
            font-weight: 700;
        }
        
        h2 {
            font-weight: 400;
        }

        em {
            font-style: normal;
        }

        .italic {
            font-style: italic;
        }

        /* 想要div文字变倾斜 加粗 字号设置为16像素 并且是微软雅黑 */
        div {
            /* font-style: italic;
            font-weight: 700;
            font-size: 16px;
            font-family: "Microsoft yahei"; */
            /* 复合属性：简写的方式
            font:font-style  font-weight font-size/line-height font-family; */
            font: italic 700 16px 'Microsoft yahei';
        }

    </style>
</head>
<body>
<h2>维多利亚的秘密</h2>
<p>那是一抹众人中最漂亮的颜色，</p>
<p>优雅，淡然，有那么清澈。</p>
<em>前端总是伴随着困难和犯错，</em>
<p class="italic">静心，坦然，攻克一个又一个。</p>
<p class="bold">拼死也要克服他，</p>
<p>这就是维多利亚的秘密</p>
<div>三生三世十里挑花，一心一意白行代码</div>
</body>
</html>

```

## 字体系列
CSS使用font-family属性定义文本的字体系列。
```CSS
p {
    font-family: "微软雅黑";
}
div {
    font-family: Arial,"Microsoft YaHei", "微软雅黑";
}
```

各个字体之间必须使用英文状态下的逗号隔开

一般情况下，如果有空格隔开的多个单词组成的字体，加引号

尽量使用系统默认自带字体，保证在任何用户浏览器中都能正确显示

最常见的几个字体：body {font-family:'Microsoft YaHei',tahoma,arial,'Hiragino Sans GB';}

## 字体大小

CSS使用font-size属性定义字体大小。

```CSS
p {
    font-size: 20px;
}
```

px(像素)大小时我们网页的最常用的单位

谷歌浏览器默认的字体大小为16px

不同浏览器可能默认显示的字号大小不一致，我们尽量给一个明确值大小，不要默认大小

可以给body指定整个页面文字的大小

## 字体粗细

CSS使用font-weight属性设置文本属性

```CSS
p {
    font-weight: bold;
}
```

|   属性值   | 描述                                    |
|:-------:|:--------------------------------------|
| normal  | 默认值（不加粗）                              |
|  bold   | 定义粗体（加粗的）                             |
| 100-900 | 400等同于normal ，而700等同于bold  注意数字后面不跟单位 |


## 字体样式

```CSS
p {
    font-style: normal;
}
```

|   属性值   | 作用                                    |
|:-------:|:--------------------------------------|
| normal  | 默认值，浏览器会显示标准的字体样式                     |
| italic  | 浏览器会显示斜体的字体样式。                        |

一般用于给斜体标签（em，i）改为不倾斜字体

## font复合属性写法

字体属性可以把文字样式综合来写

```CSS
body {
    font: font-style font-weight font-size/line-height font-family;
}
```

使用font属性时，必须按上面语法格式中的顺序书写，不能更换顺序，并且各个属性间以空格隔开 不需要设置的属性可以省略，但必须保留font-size 和font-family属性，否则font属性将不起作用

## 字体属性总结
|     属性      | 表示   | 注意点                                  |
|:-----------:|:-----|:-------------------------------------|
|  font-size  | 字号   | 通常用的单位时px像素                          |
| font-family | 字体   | 实际工作按照团队约定来写字体                       |
| font-weight | 字体粗细 | 加粗是700或者bold   不加粗是normal 或者400      |
| font-style  | 字体样式 | 倾斜时itailc   不倾斜时normal               |
|    font     | 字体连写 | 1.字体连写诗有顺序的 不能随意换位置  2.其中字号和字体必须同时出现 |

