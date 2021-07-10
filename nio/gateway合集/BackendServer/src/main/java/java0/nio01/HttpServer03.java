package java0.nio01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer03 {
    public static void main(String[] args) throws IOException {
        //服务端监听8088接口
        ServerSocket serverSocket=new ServerSocket(8088);
        int i=0;
        while(true){
            try{
                //服务端尝试接受server端的请求
                Socket socket=serverSocket.accept();
                System.out.println(i++);
                //服务端处理客户端请求
                service(socket);
            }catch (IOException e){
                e.printStackTrace();

            }
        }
    }

    private static void service(Socket socket){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[8 * 1024];
            int len = inputStream.read(buffer);
            outputStream.write(buffer, 0, len);
            byte[] input = outputStream.toByteArray();
            String inputContent = new String(input, "UTF-8");
            System.out.println("Server收到请求:\n" + inputContent);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body="hello,nio3";
            printWriter.println("Content-length:"+body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

