package com.fxl.frame.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * sftp工具类
 * @Description TODO
 * @author fangxilin
 * @date 2018年9月4日
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2018
 */
public class SFTPUtils {
    
    private static Logger log = LoggerFactory.getLogger(SFTPUtils.class);
    
    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp sftp = null;
    private static int timeout = 60000; //超时数,一分钟
    
    public static void main(String[] args) {
        try {
            String host = "192.168.10.72";
            int port = 22;
            String username = "root";
            String password = "CgGRxNx1J5iIm86j7POUGUpkt";
            getConnect(host, port, username, password);
            //upload("/home/cmsoftu/detail/", "E:\\recordfilecopy\\record1.txt", ftp);
            //download("/exam/455834765/20180423/", "455834765_2220785.pdf", "E:\\temp\\455834765_2220785.pdf");
            System.out.println("连接成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 连接sftp服务器
     * @createTime 2018年9月4日,上午10:49:57
     * @createAuthor fangxilin
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static void getConnect(String host, int port, String username, String password) throws Exception {
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        //不验证 HostKey
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        channel = sshSession.openChannel("sftp");
        channel.connect();
        sftp = (ChannelSftp) channel;
    }
    
    /**
     * 断开连接
     * @createTime 2018年11月27日,下午5:51:28
     * @createAuthor fangxilin
     * @throws Exception
     */
    public static void disConn() {
        try {
            if (null != sftp) {
                sftp.disconnect();
            }
            if (null != channel) {
                channel.disconnect();
            }
            if (null != session) {
                session.disconnect();
            } 
        } catch (Exception e) {
            log.error("sftp关闭连接异常", e);
        }
    }

 
    /**
     * 下载sftp服务器文件
     * @createTime 2018年9月4日,上午10:51:25
     * @createAuthor fangxilin
     * @param directory sftp服务器目录
     * @param downloadFile 目录下的文件名称
     * @param saveFile 本地保存文件路径
     * @param sftp
     */
    public static void download(String directory, String downloadFile, String saveFile) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            log.error("sftp下载文件异常", e);
        } finally {
            if (sftp.isConnected()) {
                disConn();
            }
        }
    }
    
    /**
     * 下载sftp服务器文件
     * @createTime 2018年9月4日,上午10:51:25
     * @createAuthor fangxilin
     * @param directory sftp服务器目录
     * @param downloadFile 目录下的文件名称
     * @param outStream 输出流
     * @param sftp
     */
    public static void download(String directory, String downloadFile, OutputStream outStream) {
        try {
            sftp.cd(directory);
            sftp.get(downloadFile, outStream);
        } catch (Exception e) {
            log.error("sftp下载文件异常", e);
        } finally {
            if (sftp.isConnected()) {
                disConn();
            }
 
        }
    }
}