# 安装

按照狂神说Java的步骤进行安装，区别在于使用 `net start mysql` 命令启动mysql时，应先将my.ini 文件中的 `#skip-grant-tables` 如此注释掉。否则汇报10006错误。