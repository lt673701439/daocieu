package com.liketry.interaction.benison.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据采集工具类
 * @author dell
 *
 */
public class DataGatherUtils {
	
	private static final Log log = LogFactory.getLog(DataGatherUtils.class);
	
	//数据库连接信息
	private static String user = null;
    private static String pwd = null;
    private static String url = null;
    private static String driver = null;
    private int count;//记录数
	
	private static DataGatherUtils instance = null;
	
	public static synchronized DataGatherUtils getInstance(){
			
		if (instance == null){
			instance = new DataGatherUtils();
			instance.setPropertiesValue();
		}
		return instance;
	}
	
	/**
	 * 设置配置文件信息
	 * @return
	 */
	private void setPropertiesValue()
	{
		Properties properties = new Properties();
		
		InputStream privatePro = this.getClass().getResourceAsStream("/application.yml");
		
		try{
			properties.load(new InputStreamReader(privatePro, "UTF-8"));
		}
		catch (IOException e){
			//TODO...修改为记录日志
			log.info("私有属性文件加载失败");
		}
		catch (Exception e){
			//TODO...修改为记录日志
			log.info("私有属性文件不存在");
		}
		
		this.user = properties.getProperty("username");
		this.pwd = 	properties.getProperty("password");
		this.url = properties.getProperty("url");
		this.driver = properties.getProperty("driverClassName");
	}
	
	/**
	 * 连接数据库
	 * @return
	 */
	public static Connection getCon() {
        Connection con = null;
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url, user, pwd);
            if (con != null) {
                System.out.println("你已连接到数据库：" + con.getCatalog());
            }
        } catch (Exception e) {
            System.out.println("连接数据库失败！");
            e.printStackTrace();
        }
        return con;
    } 
	
	/**
	 * 执行新增sql
	 * @param sql语句
	 * @return
	 */
  public boolean insertDB(String sql) {
	  
        Connection con = null;
        Statement stm = null;
        boolean flag = false;
        
        try {
            con = getCon();
            stm = con.createStatement();
            int i = stm.executeUpdate(sql);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            close(null, stm, con);
        }
        return flag;
    }
  
	  /**
		 * 执行查询sql
		 * @param sql语句
		 * @return
		 */
	public ResultSet selectDB(String sql) {
		  
	      Connection con = null;
	      Statement stm = null;
	      ResultSet set = null;
	      
	      try {
	          con = getCon();
	          stm = con.createStatement();
	          set = stm.executeQuery(sql);
	      } catch (Exception e) {
	          e.printStackTrace();
	      } 
	      return set;
	  }
	  
    /**
     * 关闭相关连接
     * @param rs
     * @param stm
     * @param con
     */
    public void close(ResultSet rs, Statement stm, Connection con) {
        if (rs != null)
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (stm != null)
            try {
                stm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (con != null)
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
	
	/**
     * 功能：解析文本文件
     */
    public void loadFile(String path,String openFileStyle) {
    	
        try {
            RandomAccessFile raf = new RandomAccessFile(path, openFileStyle);
            String line_record = raf.readLine();
            while (line_record != null) {
                // 解析每一条记录
                parseRecord(line_record);
                line_record = raf.readLine();
            }
            log.info("<====解析文件成功：成功记录"+count+"条数据=====>");
            
        } catch (Exception e) {
        	log.info("<=====文件解析失败=======>");
            e.printStackTrace();
        }
        
    }
    
   /**
     * 功能：解析敏感词数据并插入敏感词表
     * line_record 读出来的数据
     */
    private void parseRecord(String line_record) throws Exception {
    	
    	//新增到数据库
    	String id = UUID.randomUUID().toString().replaceAll("-", "");
    	String text = tranStr(line_record);
    	String sql = "insert into bu_sensitive_words values('" + id + "','"
                + text + "','','')";
    	
    	if(insertDB(sql)){
    		count++;
    	}else{
    		log.error("<=====新增敏感词《"+text+"》失败========>");
    	}
        
    }
    
    /**
     * 转换字符串编码格式
     * @param oldstr 需要转换的字符串
     * @return
     */
    private String tranStr(String oldstr) {
        String newstr = "";
        try {
            newstr = new String(oldstr.getBytes("ISO-8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newstr;
    }

    /**
     * 运行函数
     * @param args
     */
	public static void main(String[] args) {
		DataGatherUtils.getInstance().loadFile("E:/敏感词库大全.txt","r");
	}

}
