package com.project.jagoga.room.presentation.dto;

import com.project.jagoga.room.domain.Room;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomCreateRequestDto {

    @NotBlank(message = "방 번호는 빈 값일 수 없습니다.")
    @Length(max = 20, message = "이름은 20자 이내로 입력하세요")
    private String roomNumber;

    public Room toEntity(long roomTypeId) {
        return Room.createInstance(roomTypeId, roomNumber);
    }
}
