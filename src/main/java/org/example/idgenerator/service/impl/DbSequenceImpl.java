package org.example.idgenerator.service.impl;

// 其他导入

import org.example.idgenerator.exception.SeqException;
import org.example.idgenerator.service.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 基于数据库实现方式
 */
@Service
public class DbSequenceImpl implements DbSequence {

    private static final String SEQUENCE_TABLE = "sequence";

    @Autowired
    private DataSource dataSource;

    @Override
    public String nextNo() throws SeqException {
        try (Connection conn = dataSource.getConnection()) {
            // 查询当前最大序列号
            try (PreparedStatement psSelect = conn.prepareStatement("SELECT MAX(seq_id) FROM " + SEQUENCE_TABLE)) {
                try (ResultSet rs = psSelect.executeQuery()) {
                    if (rs.next()) {
                        int currentMaxNo = rs.getInt(1);
                        int nextNo = currentMaxNo + 1;

                        // 插入新的序列号到历史记录表中
                        try (PreparedStatement psInsert = conn.prepareStatement("INSERT INTO sequence(name, seq_id) VALUES ('seq_name', ?)", Statement.RETURN_GENERATED_KEYS)) {
                            psInsert.setInt(1, nextNo);
                            psInsert.executeUpdate();

                            try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    return String.format("%09d", generatedKeys.getInt(1));
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("SQLException caught: " + e.getMessage());
                            e.printStackTrace();
                            throw new SeqException("数据库操作异常！",e);
                        }
                    } else {
                        throw new SeqException("序列号不存在");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SeqException("数据库连接或操作异常！",e);
        }
        return null;
    }
}
