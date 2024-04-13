package com.zbank.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class QueryBuilder {
	
	private String url = "jdbc:mysql://localhost:3306/ZBank";
	private String userName = "root";
	private String password = "";
	 String driverClassName = "com.mysql.cj.jdbc.Driver";
	 
    private String tableName;
    private List<Integer> usedColumns;
    private List<Integer> whereConditions;
    private boolean isLimit = false;
    private boolean isOffset = false;
    private List<Integer> between;
    private boolean isNeedCount = false;
    
    
    private Connection getConnection() throws SQLException {
    	try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, userName, password);
	}
    public void seturl(String url) {
    	this.url = url;
    }
    public void setUserName(String userName) {
    	this.userName = userName;
    }
    public void setPassword(String password) {
    	this.password = password;
    }

    public QueryBuilder(String tableName) {
        this.tableName = tableName;
        this.usedColumns = new ArrayList<>();
        this.whereConditions = new ArrayList<>();
        this.between = new ArrayList<>();
        
    }

    public QueryBuilder column(int... columns) {
        for (int column : columns) {
        	usedColumns.add(column);
        }
        return this;
    }
 
    public QueryBuilder where(int... conditions) {
    	
    	for(int condition : conditions) {
    		whereConditions.add(condition);
    	}
        
        return this;
    }
 
    public QueryBuilder limit() {
    	this.isLimit = true;
    	return this;
    }
    
    public QueryBuilder offset() {
    	this.isOffset = true;
    	return this;
    }
    
    public QueryBuilder between(int... columns) {
    	for(int column : columns) {
    		between.add(column);
    	}
     	return this;
    }
    
	public QueryBuilder count(int count) {
		this.usedColumns.add(count);
		this.isNeedCount =true;
		return this;
	}

  
    public String buildSelect() throws SQLException {
        StringBuilder query = new StringBuilder("SELECT ");
      
        if (usedColumns.isEmpty()) {
        	
            query.append("*");
        } else {
        	if(isNeedCount) {
        		query.append(" COUNT(").append(getColumnNames(usedColumns).get(0)).append(")");
        		
        		
        	}else {
        	
        	 List<String> selectColumns = getColumnNames(usedColumns);
        	 
            for (int i = 0; i < selectColumns.size(); i++) {
                query.append(selectColumns.get(i));
                if (i < selectColumns.size() - 1) {
                    query.append(", ");
                }
            }
        }
        }
        query.append(" FROM ").append(tableName);
        
        if (!whereConditions.isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < getColumnNames(whereConditions).size(); i++) {
                query.append(getColumnNames(whereConditions).get(i)).append(" = ? ");
                
                if (i < whereConditions.size() - 1) {
                    query.append(" AND ");
                }
            }
        }
        if(!between.isEmpty()) {
        	
        	query.append(" AND ").append(getColumnNames(between).get(0)).append(" BETWEEN ? AND ? ");
        }
        if(isLimit) {
        	query.append(" LIMIT ? ");
        }
        if(isOffset) {
        	query.append(" OFFSET ? ");
        }
        System.out.println(query);

        return query.toString();

    }
    public String buildUpdate() throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        List<String> updateColumns = getColumnNames(this.usedColumns);
        
        for (int i = 0; i < updateColumns.size(); i++) {
            query.append(updateColumns.get(i)).append(" = ?");
            if (i < updateColumns.size() - 1) {
                query.append(", ");
            }
        }

        if (!whereConditions.isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < whereConditions.size(); i++) {
                query.append(getColumnNames(whereConditions).get(i)).append(" = ? ");
                if (i < whereConditions.size() - 1) {
                    query.append(" AND ");
                }
            }
        }
        System.out.println(query);
        return query.toString();
    }

    public String buildInsert() throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName);

        List<String> insertColumns = getColumnNames(usedColumns);
      
        if(!insertColumns.isEmpty()) {
        	query.append(" ( ");
        	
        	 for (int i = 0; i < insertColumns.size(); i++) {
                 query.append(insertColumns.get(i));
                 if (i < insertColumns.size() - 1) {
                     query.append(", ");
                 }
             }
        	 query.append(") ");
        }
 
        query.append(" VALUES (");

        for (int i = 0; i < insertColumns.size(); i++) {
            query.append("?");
            if (i < insertColumns.size() - 1) {
                query.append(", ");
            }
        }

        query.append(")");
 
        System.out.println(query);
        return query.toString();
    }
    
    private <V> List<String> getColumnNames(List<V> column) throws SQLException {
    	
    	List<String> columnNames = new ArrayList<String>();
        try(Connection connection = getConnection()) {
        	DatabaseMetaData metaData = connection.getMetaData();
          
        	 try (ResultSet rs = metaData.getColumns("ZBank", null, tableName, null)) {
        		 
        		 if(column.isEmpty()) {
        			 while(rs.next()) {
        				 columnNames.add(rs.getString("COLUMN_NAME"));
        			 }
        		 }else {
        			 int count =1;
        			 while (rs.next()) {
        				
        				 if(column.contains(count)) {
        					 
        					 columnNames.add(rs.getString("COLUMN_NAME")); 
        				 }
        				 count = count+1;
        			 }
        			 
        		 }
        			
        		return columnNames;
            }
        }
    }
	public  List<Map<Integer, Object>> executeQuery(String query,Object... values) throws SQLException  {
	
	
		  List<Map<Integer,Object>> resultList = new ArrayList<>();
		 
	    	try (Connection connection = getConnection()) {
	    		PreparedStatement statement = connection.prepareStatement(query);
	    		setValues(statement, values);
	    			
	    		ResultSet resultSet = statement.executeQuery() ;
	    		ResultSetMetaData metaData = resultSet.getMetaData();
	    		int columnCount = metaData.getColumnCount();
	   			
	   			while (resultSet.next()) {
	   				
	   				Map<Integer,Object> resultMap = new HashMap<>();
    				
    				for (int i = 1; i <= columnCount; i++) {
    					int key =i;
    					if(!usedColumns.isEmpty()) {
    						key = usedColumns.get(i-1);
    					}
    					resultMap.put(key, resultSet.getObject(i));
	   				}
    				resultList.add(resultMap);
	   			}
          return resultList;
	    	}    
		}
	
	public void execute(String query, Object...values ) throws SQLException {
		try (Connection connection = getConnection()) {
    		PreparedStatement statement = connection.prepareStatement(query);
    		setValues(statement, values);
    		statement.execute();
		}
	}
	
	private void setValues(PreparedStatement statement,Object[] values) throws SQLException {
		
		for(int i=1;i <= values.length;i++) {
		
			statement.setObject(i,values[i-1]);
		}
	}
	
}
