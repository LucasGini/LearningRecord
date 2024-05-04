## markdow组件

1. 依赖安装

   ```liunx
   cd blog
   yarn add md-editor-v3
   yarn add @vavt/cm-extension
   yarn add @vavt/v3-extension
   yarn add --dev @babel/plugin-transform-private-methods
   ```

2. babel.config.js文件配置

   ```javascript
   module.exports = {
     presets: [
       '@vue/cli-plugin-babel/preset'
     ],
     plugins: [
       "@babel/plugin-transform-private-methods"
     ]
   }
   ```

3. 在components目录下创建文件WideNavbar.vue并编辑以下内容

   ```vue
   <template>
     <MdEditor v-model="text" />
   </template>
   
   <script setup>
   import { ref } from 'vue';
   import { MdEditor } from 'md-editor-v3';
   import 'md-editor-v3/lib/style.css';
   
   const text = ref('# Hello Editor');
   </script>
   
   <style scoped>
   
   </style>
   ```

4. 将组件挂载到主页面中

   ```vue
   <template>
     <WideNavbar></WideNavbar>
     <MdEditor></MdEditor>
   </template>
   
   <script>
   import MdEditor from "../components/MdEditor";
   import WideNavbar from "../components/WideNavbar";
   export default {
     components: {
       MdEditor,
       WideNavbar,
     }
   
   }
   </script>
   
   <style scoped>
   
   </style>
   ```

