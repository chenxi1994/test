import socket.ServerSocketService;
import socket.impl.ServerSocketServiceImpl;

public class MainClass {

    public static void main(String[] args) {

        ServerSocketService serverSocketService = new ServerSocketServiceImpl();
        //启动服务
        serverSocketService.startServer(9000); //9000 表示监听的端口号
        String fileDir = "E:"; //文件目录
        //将接受到的数据写入文件中
        serverSocketService.writeFile(fileDir);
    }
}
