package com.fxl.frame.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTPUtils工具类
 * @Description TODO
 * @author fangxilin
 * @date 2018年10月26日
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2018
 */
public class FTPUtils {

    private static Logger log = LoggerFactory.getLogger(FTPUtils.class);
    
    private static FTPClient ftp = new FTPClient();
    
    
    /**
     * Connect to FTP server.
     * @param host
     * @param port
     * @param user
     * @param password
     * @return
     * @throws IOException，抛出异常，连接或登陆失败
     */
    public static void connect(String host, int port, String user, String password) throws IOException {
        // Connect to server.
        ftp.setControlEncoding("UTF-8"); // 解决上传文件时文件名乱码
        ftp.connect(host, port);

        // Check rsponse after connection attempt.
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {//连接失败
            disconnect();
            throw new IOException("Can't connect to server '" + host + "'");
        }

        // Login.
        if (!ftp.login(user, password)) {//登陆失败
            disconnect();
            throw new IOException("Can't login to server '" + host + "'");
        }
        // Set data transfer mode.
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        // ftp.setFileType(FTP.ASCII_FILE_TYPE);
        // Use passive mode to pass firewalls.
        ftp.enterLocalPassiveMode();
        //设置超时时间30秒
        setTimeOut(30, 30, 30);
    }

    private static void setTimeOut(int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond) {
        try {
            ftp.setDefaultTimeout(defaultTimeoutSecond * 1000);
            // ftp.setConnectTimeout(connectTimeoutSecond * 1000);
            // //commons-net-3.5.jar
            ftp.setSoTimeout(connectTimeoutSecond * 1000); // commons-net-1.4.1.jar连接后才能设置
            ftp.setDataTimeout(dataTimeoutSecond * 1000);
        } catch (SocketException e) {
            log.error("setTimeout Exception:", e);
        }
    }


    /**
     * Test connection to ftp server
     * @return true, if connected
     */
    private static boolean isConnected() {
        return ftp.isConnected();
    }

    /**
     * Disconnect from the FTP server
     * @throws IOException on I/O errors
     */
    private static void disconnect() throws IOException {
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                log.error("断开连接异常：", e);
            }
        }
    }

    /**
     * ftp文件夹写入指定OutputStream
     * @createTime 2018年10月26日,上午10:46:41
     * @createAuthor fangxilin
     * @param ftpPathname
     * @param out
     * @throws IOException
     */
    public static void retrieveFile(String ftpPathname, OutputStream out) throws IOException {
        try {
            // Get file info.
            FTPFile[] fileInfoArray = ftp.listFiles(ftpPathname);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File '" + ftpPathname + "' was not found on FTP server.");
            }

            // Check file size.
            FTPFile fileInfo = fileInfoArray[0];
            long size = fileInfo.getSize();
            if (size > Integer.MAX_VALUE) {
                throw new IOException("File '" + ftpPathname + "' is too large.");
            }

            // Download file.
            if (!ftp.retrieveFile(ftpPathname, out)) {
                throw new IOException("Error loading file '" + ftpPathname + "' from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    
                }
            }
        }
    }

    /**
     * 将指定InputStream存储到ftp服务器
     * @createTime 2018年10月26日,上午10:50:10
     * @createAuthor fangxilin
     * @param ftpPathname
     * @param in
     * @throws IOException
     */
    public static void storeFile(String ftpPathname, InputStream in) throws IOException {
        try {
            if (!ftp.storeFile(ftpPathname, in)) {
                throw new IOException("Can't upload file '" + ftpPathname + "' to FTP server. Check FTP permissions and path.");
            }
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                
            }
        }
    }

    /**
     * 修改ftp文件夹名称
     * @param from
     * @param to
     * @throws IOException
     */
    public static boolean rename(String from, String to) throws IOException {
        return ftp.rename(from, to);
    }

    /**
     * 删除ftp文件夹
     * @createTime 2018年10月26日,上午10:54:05
     * @createAuthor fangxilin
     * @param ftpPathname
     * @throws IOException
     */
    public static void deleteFile(String ftpPathname) throws IOException {
        if (!ftp.deleteFile(ftpPathname)) {
            throw new IOException("Can't remove file '" + ftpPathname + "' from FTP server.");
        }
    }

    /**
     * 上传文件到ftp
     * @param ftpPathname server file name (with absolute path)
     * @param localFile local file to upload
     * @throws IOException
     */
    public static void upload(String ftpPathname, File localFile) throws IOException {
        // File check.
        if (!localFile.exists()) {
            throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
        }

        // Upload.
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(localFile));
            if (!ftp.storeFile(ftpPathname, in)) {
                throw new IOException("Can't upload file '" + ftpPathname + "' to FTP server. Check FTP permissions and path.");
            }

        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                
            }
        }
    }

    /**
     * 上传目录（会覆盖)
     * @param remotePath 远程目录 /home/test/a
     * @param localPath 本地目录 D:/test/a
     * @throws IOException
     */
    public static void uploadDir(String remotePath, String localPath) throws IOException {
        File file = new File(localPath);
        if (file.exists()) {
            if (!ftp.changeWorkingDirectory(remotePath)) {
                ftp.makeDirectory(remotePath); // 创建成功返回true，失败（已存在）返回false
                ftp.changeWorkingDirectory(remotePath); // 切换成返回true，失败（不存在）返回false
            }
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory() && !f.getName().equals(".") && !f.getName().equals("..")) {
                    uploadDir(remotePath + "/" + f.getName(), f.getPath());
                } else if (f.isFile()) {
                    upload(remotePath + "/" + f.getName(), f);
                }
            }
        }
    }

    /**
     * 下载文件到File
     * @param ftpPathname server file name (with absolute path)
     * @param localFile local file to download into
     * @throws IOException
     */
    public static void download(String ftpPathname, File localFile) throws IOException {
        // Download.
        OutputStream out = null;
        try {
            // Get file info.
            FTPFile[] fileInfoArray = ftp.listFiles(ftpPathname);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File " + ftpPathname + " was not found on FTP server.");
            }

            // Check file size.
            FTPFile fileInfo = fileInfoArray[0];
            long size = fileInfo.getSize();
            if (size > Integer.MAX_VALUE) {
                throw new IOException("File " + ftpPathname + " is too large.");
            }

            // Download file.
            out = new BufferedOutputStream(new FileOutputStream(localFile));
            if (!ftp.retrieveFile(ftpPathname, out)) {
                throw new IOException(
                        "Error loading file " + ftpPathname + " from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    
                }
            }
        }
    }

    /**
     * 下载目录（会覆盖)
     * @param remotePath 远程目录 /home/test/a
     * @param localPath 本地目录 D:/test/a
     * @return
     * @throws IOException
     */
    public static void downloadDir(String remotePath, String localPath) throws IOException {
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        FTPFile[] ftpFiles = ftp.listFiles(remotePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            if (ftpFile.isDirectory() && !ftpFile.getName().equals(".") && !ftpFile.getName().equals("..")) {
                downloadDir(remotePath + "/" + ftpFile.getName(), localPath + "/" + ftpFile.getName());
            } else {
                download(remotePath + "/" + ftpFile.getName(), new File(localPath + "/" + ftpFile.getName()));
            }
        }
    }

    /**
     * 返回文件夹下的所有文件名
     * @param filePath absolute path on the server
     * @return files relative names list
     * @throws IOException
     */
    public static List<String> listFileNames(String filePath) throws IOException {
        List<String> fileList = new ArrayList<String>();
        FTPFile[] ftpFiles = ftp.listFiles(filePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            if (ftpFile.isFile()) {
                fileList.add(ftpFile.getName());
            }
        }
        return fileList;
    }

    /**
     * 返回文件夹下的所有FTPFile
     * @param filePath directory
     * @return list
     * @throws IOException
     */
    public static List<FTPFile> listFiles(String filePath) throws IOException {
        List<FTPFile> fileList = new ArrayList<FTPFile>();
        FTPFile[] ftpFiles = ftp.listFiles(filePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            // FfpFileInfo fi = new FfpFileInfo();
            // fi.setName(ftpFile.getName());
            // fi.setSize(ftpFile.getSize());
            // fi.setTimestamp(ftpFile.getTimestamp());
            // fi.setType(ftpFile.isDirectory());
            fileList.add(ftpFile);
        }
        return fileList;
    }

    /**
     * 发送特定于站点的命令
     * @param args site command arguments
     * @throws IOException
     */
    public static void sendSiteCommand(String args) throws IOException {
        if (ftp.isConnected()) {
            try {
                ftp.sendSiteCommand(args);
            } catch (IOException ex) {
                
            }
        }
    }

    /**
     * Get current directory on ftp server
     * @return current directory
     */
    public static String printWorkingDirectory() {
        if (!ftp.isConnected()) {
            return "";
        }
        try {
            return ftp.printWorkingDirectory();
        } catch (IOException e) {
            
        }
        return "";
    }

    /**
     * Set working directory on ftp server
     * @param dir new working directory
     * @return true, if working directory changed
     */
    public static boolean changeWorkingDirectory(String dir) {
        if (!ftp.isConnected()) {
            return false;
        }
        try {
            return ftp.changeWorkingDirectory(dir);
        } catch (IOException e) {
            
        }
        return false;
    }

    /**
     * Change working directory on ftp server to parent directory
     * @return true, if working directory changed
     */
    public static boolean changeToParentDirectory() {
        if (!ftp.isConnected()) {
            return false;
        }
        try {
            return ftp.changeToParentDirectory();
        } catch (IOException e) {
            
        }
        return false;
    }

    /**
     * Get parent directory name on ftp server
     * 
     * @return parent directory
     */
    public static String printParentDirectory() {
        if (!ftp.isConnected()) {
            return "";
        }
        String w = printWorkingDirectory();
        changeToParentDirectory();
        String p = printWorkingDirectory();
        changeWorkingDirectory(w);
        return p;
    }

    /**
     * 创建目录
     * @param pathname
     * @throws IOException
     */
    public boolean makeDirectory(String pathname) throws IOException {
        return ftp.makeDirectory(pathname);
    }

    public static void main(String[] args) throws Exception {
        connect("192.168.0.17", 21, "lis1", "64LdJzyV");
        /*OutputStream out = new FileOutputStream("E:\temp");
        retrieveFile("/2018-10-23/1005420181023245812008.pdf", out);*/
        File file = new File("E:\temp");
        download("/2018-10-23/1005420181023245812008.pdf", file);
    }

}