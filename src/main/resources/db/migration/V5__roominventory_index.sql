CREATE UNIQUE INDEX idx_roomtype_date ON room_inventory(roomtype_id, inventory_date);
CREATE INDEX idx_roomtype ON room_inventory(roomtype_id);