package com.project.jagoga.roominventory.infrastructure;

import com.project.jagoga.exception.booking.NonBookableException;
import com.project.jagoga.roominventory.domain.RoomInventory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcRoomInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertRoomInventories(List<RoomInventory> roomInventories) throws
        DuplicateKeyException {
        String sql = "INSERT INTO room_inventory "
            + "(roomtype_id, inventory_date, available_count, created_at, modified_at) "
            + "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                RoomInventory roomInventory = roomInventories.get(index);
                ps.setLong(1, roomInventory.getRoomTypeId());
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
        String sql = "UPDATE room_inventory SET available_count = available_count - 1"
            + " WHERE roominventory_id = ?";

        int[] rowsAffectedArray = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                ps.setLong(1, roomInventories.get(index).getId());
            }

            @Override
            public int getBatchSize() {
                return roomInventories.size();
            }
        });

        if (rowsAffectedArray.length != roomInventories.size()) {
            throw new NonBookableException();
        }
    }

    public List<RoomInventory> batchSelectRoomInventories(long roomTypeId) {
        String sql = "SELECT * FROM ROOM_INVENTORY WHERE roomtype_id = ? FOR UPDATE";

        return jdbcTemplate.query(sql, new RowMapper<RoomInventory>() {

            @Override
            public RoomInventory mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoomInventory roomInventory = new RoomInventory(
                    rs.getLong("roominventory_id"),
                    roomTypeId,
                    rs.getDate("inventory_date").toLocalDate(),
                    rs.getInt("available_count")
                );
                return roomInventory;
            }
        }, roomTypeId);
    }

    public void batchChangeRoomInventories(List<RoomInventory> roomInventories, int count) {
        String sql = "UPDATE room_inventory SET available_count = available_count + ?"
            + " WHERE roominventory_id = ? and available_count = ?";

        int[] rowsAffectedArray = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                ps.setInt(1, count);
                ps.setLong(2, roomInventories.get(index).getId());
                ps.setInt(3, count);
            }

            @Override
            public int getBatchSize() {
                return roomInventories.size();
            }
        });
    }
}
