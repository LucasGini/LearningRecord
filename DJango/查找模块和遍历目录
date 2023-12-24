from importlib import import_module
import pkgutil

if __name__ == '__main__':
    module = import_module('django')  # 查找模块信息
    print(module.__path__)  # 获取模块路径，注意模块需要为文件夹
    print([j for i, j, k in pkgutil.iter_modules(module.__path__)])  # 遍历文件夹内的文件及目录
