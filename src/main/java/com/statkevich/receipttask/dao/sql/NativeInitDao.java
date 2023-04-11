package com.statkevich.receipttask.dao.sql;

import org.apache.ibatis.jdbc.ScriptRunner;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class NativeInitDao {

    private final DataSource dataSource;

    public NativeInitDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initDB() {
        try {
            Connection connection = dataSource.getConnection();
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("init.sql"));
            scriptRunner.runScript(reader);
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
