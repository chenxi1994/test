package socket;

import java.net.ServerSocket;

/**
 * 服务器端socket相关服务接口
 */
public interface ServerSocketService {
    /**
     * 启动服务
     * @param port 监听的端口号
     * @return
     */
    public ServerSocket startServer(int port);

    /**
     * 将接受的数据写入文件中
     * @param fileDir
     */
    public void writeFile(String fileDir);

}
