package com.github.tool.core.sftp.util;

import com.github.tool.base.exception.BusinessException;
import com.github.tool.core.sftp.prop.SftpProperties;
import com.jcraft.jsch.*;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.Properties;

/**
 * SftpUtil
 */
@Log4j2
@NoArgsConstructor
public class SftpUtil {

    private String host;
    private int port;
    private String username;
    private String password;
    private String basePath;
    private ChannelSftp sftp;
    private Session session;

    public SftpUtil(SftpProperties properties) {
        this(properties.getHost(), properties.getPort(), properties.getUsername(), properties.getPassword(), properties.getBasePath());
    }

    public SftpUtil(String host, Integer port, String username, String password, String basePath) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        if (basePath != null) {
            this.basePath = addSlashAtLast(basePath);
        }
    }

    /**
     * 连接、登录
     */
    public SftpUtil connect() {
        try {
            log.info(">>连接FTP服务器...");
            if (sftp != null && sftp.isConnected()) {
                return this;
            }
            JSch jSch = new JSch();
            session = jSch.getSession(username, host, port);
            session.setPassword(password);
            session.setServerAliveCountMax(3);
            session.setServerAliveInterval(60 * 1000);
            Properties config = new Properties();
            // 第一次登陆的时候提示，可选值：(ask | yes | no)
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            log.info(">>已连接到FTP服务器");
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            log.error(">>连接FTP失败", e);
            throw new BusinessException("连接FTP失败:" + host);
        }
        return this;
    }

    /**
     * 进入目录，如果没有会创建
     *
     * @param path        路径
     * @param permissions 权限设置 如 755
     */
    public SftpUtil cd(String path, String... permissions) {
        try {
            sftp.cd("/");
            sftp.cd(path);
        } catch (SftpException e) {
            mkdir(path, permissions);
        }
        return this;
    }

    /**
     * 创建目录
     *
     * @param path        路径
     * @param permissions 文件权限设置,如: "755"
     */
    private void mkdir(String path, String... permissions) {
        String[] dirs = path.split(File.separator);
        String tempPath = "";
        for (String dir : dirs) {
            if (null == dir || "".equals(dir)) {
                continue;
            }
            tempPath += "/" + dir;
            try {
                sftp.cd(tempPath);
            } catch (SftpException ex) {
                mkdirAndCd(tempPath, permissions);
            }
        }
    }

    private void mkdirAndCd(String path, String... permissions) {
        try {
            sftp.mkdir(path);
            if (permissions != null) {
                sftp.chmod(Integer.parseInt(permissions[0], 8), path);
            }
        } catch (SftpException e1) {
            e1.printStackTrace();
        }
        try {
            sftp.cd(path);
        } catch (SftpException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @param path        路径
     * @param permissions 文件权限设置,如: "755"
     */
    public void chmod(String path, String permissions) throws SftpException {
        sftp.chmod(Integer.parseInt(permissions, 8), path);
    }

    /**
     * 上传(ChannelSftp.OVERWRITE)
     *
     * @param inp      输入流
     * @param filename 文件名包含路径
     */
    public SftpUtil touch(InputStream inp, String filename) {
        return touch(inp, filename, ChannelSftp.OVERWRITE);
    }

    /**
     * 上传ftp
     *
     * @param inp      输入流
     * @param filename 文件名包含路径
     * @param mode     模式(ChannelSftp.OVERWRITE 、 ChannelSftp.RESUME、  ChannelSftp.APPEND)
     */
    public SftpUtil touch(InputStream inp, String filename, int mode) {
        try {
            log.info(">>正在保存文件内容 -> {}", filename);
            sftp.put(inp, filename, mode);
        } catch (SftpException e) {
            log.error("保存文件内容失败", e);
            throw new BusinessException("保存文件内容失败:" + host);
        }
        return this;
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        try {
            if (sftp != null && sftp.isConnected()) {
                sftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        } catch (Exception e) {
            log.error("断开连接FTP失败", e);
            throw new BusinessException("断开连接FTP失败:" + host);
        }
    }

    private String addSlashAtLast(String path) {
        int i = path.lastIndexOf(File.separator);
        if (i != path.length() - 1) {
            path += File.separator;
        }
        return path;
    }
}
