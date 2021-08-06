```java
//批处理+预编译
//spend 4583 milliseconds to insert 1000000
    public void batchAdd() {
        Connection connection = null;
        try {
            connection =(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false&rewriteBatchedStatements=true", "root", "123456");
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long begin = System.currentTimeMillis();
    String prefix = "insert into user (id,name) values (?,?)";
    try {
        PreparedStatement pst = (PreparedStatement) connection.prepareStatement(prefix);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100000; j++) {
                String no = String.valueOf(i * 100000 + j);
                pst.setInt(1, i * 100000 + j);
                pst.setString(2, no);
                pst.addBatch();
            }
            pst.executeBatch();
            connection.commit();
        }
        pst.close();
        connection.close();

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    Long end = System.currentTimeMillis();
    System.out.println("spend " + (end - begin) + " milliseconds to insert 1000000");
}
```



```java
    //多值拼装+非预编译
    //spend 8411 milliseconds to insert 1000000
    public void appendSqlAdd() {
        Connection connection = null;
        Statement st = null;
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false", "root", "123456");
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long begin = System.currentTimeMillis();
        StringBuffer prefix = new StringBuffer("insert into user (id,name) values ");
        try {
            st = (Statement) connection.createStatement();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 100000; j++) {
                    if ((j) != 0) {
                        prefix.append(",");
                    }
                    String no = String.valueOf(i * 100000 + j);
                    System.out.println(no);
                    prefix.append("(" + no + "," + no + ")");
                }
                st.execute(prefix.toString());
                connection.commit();
                prefix = new StringBuffer("insert into user (id,name) values ");
            }
            st.close();
            connection.close();
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    Long end = System.currentTimeMillis();
    System.out.println("spend " + (end - begin) + " milliseconds to insert 1000000");

}
```

```java
	// SQL预编译+多值拼装sql
    // spend 8973 milliseconds to insert 1000000
    @Override
    public void dynamicSqlAdd() {
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false", "root", "123456");
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long begin = System.currentTimeMillis();
        StringBuffer prefix = new StringBuffer("insert into user (id,name) values ");
        try {
for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100000; j++) {
                if ((j) != 0) {
                    prefix.append(",");
                }
                String no = String.valueOf(i * 100000 + j);
                System.out.println(no);
                prefix.append("(" + no + "," + no + ")");
            }
            st = (PreparedStatement) connection.prepareStatement(prefix.toString());
            st.execute();
            connection.commit();
            prefix = new StringBuffer("insert into user (id,name) values ");
        }
        st.close();
        connection.close();

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    Long end = System.currentTimeMillis();
    System.out.println("spend " + (end - begin) + " milliseconds to insert 1000000");
}
```