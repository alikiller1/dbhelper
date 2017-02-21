package per.liuqh.dbhelper.generate.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import per.liuqh.dbhelper.generate.GenerateException;
import per.liuqh.dbhelper.generate.domain.DatabaseTableInfo;

/**
 * Created by ppd on 2017/2/20.
 */
public class DatabaseTableInfoUtils {

    public static List<DatabaseTableInfo> load(Connection conn, String database,String tableName) throws Exception {
        List<DatabaseTableInfo> list = new ArrayList<DatabaseTableInfo>();


        String sql = "select table_name,table_comment from information_schema.tables where table_schema=? and table_name like ?;";
        PreparedStatement preStatement = conn.prepareStatement(sql);
        preStatement.setString(1, database);
        preStatement.setString(2, "%"+tableName+"%");
        ResultSet result = preStatement.executeQuery();

        // 展开结果集数据库
        DatabaseTableInfo dti = null;
        while (result.next()) {
            // 通过字段检索
            String name = result.getString("table_name");
            String comment = result.getString("table_comment");

            dti = new DatabaseTableInfo();
            dti.setTableName(name);
            dti.setTableComment(comment);
            list.add(dti);
        }
        try {
            preStatement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        if(list.size()<=0){
        	throw new GenerateException("没有找到对应的表");
        }        
        return list;
    }
}
