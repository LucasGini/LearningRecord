# 组件通信——v-model



**概述**：实现父←→子之间互相通信，常用于组件封装（重要）



## v-model的本质

```vue
<template>
  <!-- 使用v-model指令 -->
  <input type='text' v-model='username'>
  
  <!-- 上面代码使用v-model的本质 -->
  <input type='text' :value="username" @input="username = $event.target.value">
</template>
```



## 组件标签上v-model的本质：:modelValue + update:modelValue事件

## 

```vue
<template>
  <!-- 组件标签上使用v-model指令 -->
  <CustomInput v-model='username'/>
  
  <!-- 组件标签上v-model的本质 -->
  <CustomInput :modelValue="username" @update:modelValue="username = $event">
</template>
```

CustomInput组件中

```vue
<template>
  <div class="box">
    <!-- 将接收的modelValue值赋给input元素的value属性，目的是为了呈现数据 -->
    <!-- 给input元素绑定input事件，除非input事件时，进而触发update:modelValue事件 -->
    <input
       type="text"
       :value="modelValue"
       @input="emit('update:modelValue', $event.target.value)"
    >
</template>

<script setup name="CustomInput">
  // 获取props
  defineProps('modelValue')
  // 声明事件
  const emit = defineEmits(['update:modelValue'])
</script>
```



## 更换value名称

## 

```vue
<template>
  <!-- 更换value，改成name -->
  <CustomInput v-model:name='username'/>
  
  <!-- 上面代码的本质 -->
  <CustomInput :name="username" @update:name="username = $event">
</template>
```

CustomInput组件中

```vue
<template>
  <div class="box">
    <!-- 将接收的modelValue值赋给input元素的value属性，目的是为了呈现数据 -->
    <!-- 给input元素绑定input事件，除非input事件时，进而触发update:modelValue事件 -->
    <input
       type="text"
       :value="name"
       @input="emit('update:name', $event.target.value)"
    >
</template>

<script setup name="CustomInput">
  // 获取props
  defineProps('name')
  // 声明事件
  const emit = defineEmits(['update:name'])
</script>
```



## 多次使用v-model

```vue
<template>
  <!-- 更换value，改成name -->
  <CustomInput v-model:name='username' v-model:pass="password"/>
  
  <!-- 上面代码的本质 -->
  <CustomInput :name="username" :pass="password" @update:name="username = $event" @update:pass="password = $event">
</template>
```







