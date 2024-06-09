# Django pychram debugger报错及解决方法

报错：
```
/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/bin/python /Applications/PyCharm CE.app/Contents/plugins/python-ce/helpers/pydev/pydevd.py --cmd-line --client 127.0.0.1 --port 65512 --file /Users/musfiqur.rahman/Applications/project/backend/mx-backend/src/manage.py runserver 
Connected to pydev debugger (build 222.3345.131)
Traceback (most recent call last):
  File "/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/lib/python3.9/site-packages/django/core/management/base.py", line 354, in run_from_argv
    self.execute(*args, **cmd_options)
  File "/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/lib/python3.9/site-packages/django/core/management/commands/runserver.py", line 61, in execute
    super().execute(*args, **options)
  File "/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/lib/python3.9/site-packages/django/core/management/base.py", line 398, in execute
    output = self.handle(*args, **options)
  File "/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/lib/python3.9/site-packages/django/core/management/commands/runserver.py", line 96, in handle
    self.run(**options)
  File "/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/lib/python3.9/site-packages/django/core/management/commands/runserver.py", line 103, in run
    autoreload.run_with_reloader(self.inner_run, **options)
  File "/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/lib/python3.9/site-packages/django/utils/autoreload.py", line 640, in run_with_reloader
    exit_code = restart_with_reloader()
  File "/Users/musfiqur.rahman/Applications/project/backend/mx-backend/.venv/lib/python3.9/site-packages/django/utils/autoreload.py", line 259, in restart_with_reloader
    p = subprocess.run(args, env=new_environ, close_fds=False)
  File "/Users/musfiqur.rahman/.pyenv/versions/3.9.2/Library/Frameworks/Python.framework/Versions/3.9/lib/python3.9/subprocess.py", line 505, in run
    with Popen(*popenargs, **kwargs) as process:
  File "/Users/musfiqur.rahman/.pyenv/versions/3.9.2/Library/Frameworks/Python.framework/Versions/3.9/lib/python3.9/subprocess.py", line 951, in __init__
    self._execute_child(args, executable, preexec_fn, close_fds,
  File "/Users/musfiqur.rahman/.pyenv/versions/3.9.2/Library/Frameworks/Python.framework/Versions/3.9/lib/python3.9/subprocess.py", line 1711, in _execute_child
    self._posix_spawn(args, executable, env, restore_signals,
  File "/Users/musfiqur.rahman/.pyenv/versions/3.9.2/Library/Frameworks/Python.framework/Versions/3.9/lib/python3.9/subprocess.py", line 1656, in _posix_spawn
    self.pid = os.posix_spawn(executable, args, env, **kwargs)
TypeError: new_warn_multiproc() got an unexpected keyword argument 'setsigdef'
```

解决方法：pychram设置->构建、执行、部署->python调试器，勾选“调试时自动附加到子进程”
