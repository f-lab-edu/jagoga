package com.project.jagoga.roomtype.presentation.dto;

import com.project.jagoga.roomtype.domain.RoomType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomTypeCreateRequestDto {

    @Positive(message = "유효하지 않은 숙소입니다.")
    private long accommodationId;

    @NotBlank(message = "룸 타입명은 빈 값일 수 없습니다.")
    private String name;

    private String description;

    public RoomType toEntity() {
        return RoomType.createInstance(accommodationId, name, description);
    }
}
