package com.szl.syj.Dao;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


@Repository
public class CommDaoImpl implements ICommDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean executeSql(String sql) {
        try {
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    public List<Map<String, Object>> getAll(String words, String table) {
        String sql = "select "+words+" from "+table;
//        RowMapper<JSONObject> rowMapper=new BeanPropertyRowMapper<>(JSONObject.class);
//        List<JSONObject> list = jdbcTemplate.query(sql, rowMapper);
//        if(list != null && list.size() > 0){
//            return list;
//        }

        return jdbcTemplate.queryForList(sql);

    }

    public List<Map<String, Object>> get(String words, String table,String id,String value) {
        String sql = "select "+words+" from "+table +" where "+id +" = \'"+value+"\'";
        return jdbcTemplate.queryForList(sql);
    }

    public void truncate(String table) {
        String sql = "truncate table "+table;
        jdbcTemplate.execute(sql);
    }

    public List<String> getCol(String table) {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();  // not reusable
        jdbcTemplate.query("select * from "+table, countCallback);
        String[] columnNames = countCallback.getColumnNames();
        return Arrays.asList(columnNames);
    }





    public List<Map<String, Object>> getFoodCategoryByParentId(String words,String table) {
        String sql="select "+words+" from "+table;
        return jdbcTemplate.queryForList(sql);
    }
    public void clearCol(String columnname,String table) {
        String sql="update "+table+" set "+columnname+" =null";
        jdbcTemplate.execute(sql);
    }




    public void updateJsByIdm(JSONObject js,String tableName,String id) {
        String sql = "UPDATE " + tableName + " SET \n";
        List<String> columns = new ArrayList<>(js.keySet());
        List<String> values = new ArrayList<>();
        for(String col :columns){
            values.add(js.get(col).toString());
        }
        String contents = "";
        for (int i = 0; i < columns.size(); i++) {
            contents += columns.get(i) + "="+"\'"+values.get(i)+"\'" + "," + "\n";
        }
        contents = contents.substring(0, contents.length() - 2);
        sql = sql + contents + "\nwhere id =" + "'" + id + "'";
        this.executeSql(sql);
    }

    public int[] updateToFoodBusiness(List<JSONObject> list) {

        String sql = "update food_business set qymc_hash=?,xkzzsh_hash=?,xm_hash=? where id=?";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public int getBatchSize() {
                return list.size();
            }
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, list.get(i).get("qymc_hash"));
                ps.setObject(2, list.get(i).get("xkzzsh_hash"));
                ps.setObject(3, list.get(i).get("xm_hash"));
                ps.setObject(4, list.get(i).get("id"));
            }
        });
    }


}
