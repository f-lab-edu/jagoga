package com.project.jagoga.roominventory.infrastructure;

import com.project.jagoga.roominventory.domain.RoomInventory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcRoomInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertRoomInventories(List<RoomInventory> roomInventories) {
        String sql = "INSERT INTO room_inventory "
            + "(roomtype_id, inventory_date, available_count, created_at, modified_at) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                RoomInventory roomInventory = roomInventories.get(index);
                ps.setLong(1, roomInventory.getRoomType().getId());
                ps.setObject(2, roomInventory.getInventoryDate());
                ps.setInt(3, roomInventory.getAvailableCount());
                ps.setObject(4, LocalDateTime.now());
                ps.setObject(5, LocalDateTime.now());
            }

            @Override
            public int getBatchSize() {
                return roomInventories.size();
            }
        });
    }

    public void batchReduceRoomInventories(List<RoomInventory> roomInventories) {
        String sql = "UPDATE room_inventory SET available_count = ? WHERE roominventory_id = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                ps.setInt(1, roomInventories.get(index).getAvailableCount() - 1);
                ps.setLong(2, roomInventories.get(index).getId());
            }

            @Override
            public int getBatchSize() {
                return roomInventories.size();
            }
        });
    }
}
