package socket.impl;

import socket.ServerSocketService;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketServiceImpl implements ServerSocketService {

    public static ServerSocket serverSocket;

    /**
     * 启动服务
     */
    @Override
    public ServerSocket startServer(int port) {

        if (null == serverSocket) {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("启动服务成功("+ InetAddress.getLocalHost().getHostAddress()+":"+port+");");
            } catch (IOException e) {
                //启动服务失败
                e.printStackTrace();
            }
        }

        return serverSocket;


    }

    /**
     * 将接受的数据写入文件中
     * @param fileDir 文件目录
     */
    @Override
    public void writeFile(final String fileDir) {
        //开一个线程不停的监听消息,可以达到不停的接受消息数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Socket socket=null;
                    try {
                         socket = serverSocket.accept();
                        //获取输入流
                        InputStream inputStream = socket.getInputStream();
                        if (null != inputStream) {
                            write(fileDir, inputStream);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if(null !=socket){
                                socket.close();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }


    /**
     * 将数据写入文件中
     *
     * @param fileDir     文件目录
     * @param inputStream 输入流
     */
    private void write(String fileDir, InputStream inputStream) {

        String filePath = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bufferedWriter = null;
        try {
            String fileName = bufferedReader.readLine();//第一行表示文件名
            filePath = fileDir + File.separator + fileName; //文件路径
            File file = new File(filePath);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            //int lineNumber = 0;
            String readLine = "";
            while ((readLine = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(readLine);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
            }

            System.out.println("文件写入成功("+filePath+")");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //关闭流
            try {
                if (null != bufferedWriter) {
                    bufferedWriter.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();

                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
