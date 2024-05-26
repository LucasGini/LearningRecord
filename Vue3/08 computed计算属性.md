# computed计算属性



## computed计算属性概述

* **作用**：根据已有数据计算新数据



## computed基本用法

``` vue
<template>
  <div>
    姓：<input type="text" v-model="firstName"> <br>
    名：<input type="text" v-model="lastName"> <br>
    全名：<span>{{fullName}}</span> <br>
  </div>
</template>
<script setup>
 import {ref, computed} from 'vue'
 let firstName = ref('zhang')
 let lastName = ref('san')
 
 // 计算属性具有缓存性，调用一次后，如果后续调用firstName和lastName的数据没有变更，则不会重新计算
 // firstName或lastName发生变更，会重新计算
 // 这样定义的计算属性是只读的，不可以修改的
 let fullName = computed(()=> {
   return firstName.value.slice(0, 1).toUpperCase() + firstName.value.slice(1) + '-' + lastName.value 
 })
 
 // 这样定义的计算属性可以修改
 let fullName = computed(()=> {
   get(){
     return firstName.value.slice(0, 1).toUpperCase() + firstName.value.slice(1) + '-' + lastName.value 
   },
    // 实际还是修改了firstName和lastName
   set(newValue) {
    const [str1, str2] = newValue.split('-')
    firstName.value = str1
    lastName.value = str2
   }
 })
 
 function changeFullName
</script>
```

