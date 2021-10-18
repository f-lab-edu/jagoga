package com.project.jagoga.roomtype.presentation.dto;

import com.project.jagoga.roomtype.domain.RoomType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class RoomTypeCreateRequestDto {

    @NotBlank(message = "룸 타입명은 빈 값일 수 없습니다.")
    @Length(max = 20, message = "이름은 20자 이내로 입력하세요")
    private String name;

    @Length(max = 200, message = "설명은 200자 이내로 입력하세요")
    private String description;

    @Positive(message = "가격은 0원 이상이어야 합니다.")
    private int price;

    public RoomType toEntity(long accommodationId, long ownerId) {
        return RoomType.createInstance(accommodationId, name, description, price, ownerId);
    }
}
