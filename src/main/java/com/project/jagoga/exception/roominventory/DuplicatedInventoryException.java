package com.project.jagoga.exception.roominventory;

public class DuplicatedInventoryException extends RuntimeException {

    public DuplicatedInventoryException() {
        super("이미 존재하는 인벤토리는 새로 추가할 수 없습니다.");
    }
}