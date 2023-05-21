package com.github.tool.sql;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * java entity 转 SQL
 */
public class JavaToSQLUtil {

    /**
     * test
     */
    public static void main(String[] args) throws Exception {
        run("com.github.tool.sql.example", "test");
    }

    private static final String ID = "id";
    private static final String STRING = "java.lang.String";
    private static final String INTEGER = "java.lang.Integer";
    private static final String iNT = "int";
    private static final List<String> INTS = Arrays.asList(iNT, INTEGER);
    private static final String LONG = "java.lang.Long";
    private static final String DOUBLE = "java.lang.Double";
    private static final String DATE = "java.util.Date";

    /**
     * 执行
     *
     * @param packageName  包名
     * @param databaseName 数据库名称
     */
    public static void run(String packageName, String databaseName) throws Exception {
        Set<Class<?>> classes = getClasses(packageName);
        classes.forEach(clazz -> covert(clazz, databaseName));
    }

    public static void covert(Class<?> clazz, String databaseName) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder filedSQL = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            try {
                String name = fields[i].getName();
                if (name.equals("serialVersionUID")) {
                    continue;
                }
                // 数据库命名
                name = underScoreName(name);
                filedSQL.append("\t");
                if (ID.equals(name)) {
                    filedSQL.append(name + " int NOT NULL AUTO_INCREMENT COMMENT ''");
                } else if (STRING.equals(fields[i].getGenericType().getTypeName())) {
                    filedSQL.append(name + " varchar(255) NOT NULL DEFAULT '' COMMENT ''");
                } else if (INTS.contains(fields[i].getGenericType().getTypeName())) {
                    filedSQL.append(name + " int NOT NULL DEFAULT '0' COMMENT ''");
                } else if (LONG.equals(fields[i].getGenericType().getTypeName())) {
                    filedSQL.append(name + " bigint NOT NULL DEFAULT '0' COMMENT ''");
                } else if (DOUBLE.equals(fields[i].getGenericType().getTypeName())) {
                    filedSQL.append(name + " decimal(8, 3) NOT NULL DEFAULT 0.000 COMMENT ''");
                } else if (DATE.equals(fields[i].getGenericType().getTypeName())) {
                    filedSQL.append(name + " timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT ''");
                } else {
                    throw new Exception("unknown type");
                }
                filedSQL.append(",\n");
            } catch (Exception ex) {
            }
        }
        String sql = "CREATE TABLE " + databaseName + "." + clazz.getSimpleName().toLowerCase()
                + "(\n"
                + filedSQL.toString()
                + "\tPRIMARY KEY (`id`)"
                + "\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT=''; ";
        System.out.println(sql);
        System.out.println();
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param pack 包名
     */
    public static Set<Class<?>> getClasses(String pack) throws Exception {
        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    System.err.println("file类型的扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    System.err.println("jar类型的扫描");
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            throw new Exception("添加用户自定义视图类错误 找不到此类的.class文件");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new Exception("在扫描用户定义视图时从jar包获取文件出错");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     */
    public static void findAndAddClassesInPackageByFile(String packageName,
                                                        String packagePath,
                                                        final boolean recursive,
                                                        Set<Class<?>> classes) throws Exception {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            throw new Exception("用户定义包名 " + packageName + " 下没有任何文件");
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(file -> {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    //classes.add(Class.forName(packageName + '.' + className));
                    //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    throw new Exception("添加用户自定义视图类错误 找不到此类的.class文件");
                }
            }
        }
    }

    /**
     * 将驼峰式命名的字符串转换为下划线小写方式
     * 例如：userName -> user_name
     */
    public static String underScoreName(String name) {
        if (null == name) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toLowerCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toLowerCase());
            }
        }
        return result.toString();
    }
}