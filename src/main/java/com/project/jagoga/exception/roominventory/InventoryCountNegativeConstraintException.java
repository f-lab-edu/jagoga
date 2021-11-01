package com.project.jagoga.exception.roominventory;

public class InventoryCountNegativeConstraintException extends RuntimeException {

    public InventoryCountNegativeConstraintException() {
        super("인벤토리 재고는 음수가 될 수 없습니다.");
    }
}