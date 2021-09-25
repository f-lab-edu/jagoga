package com.project.jagoga.accommodation.presentation.dto;

import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.AccommodationType;
import com.project.jagoga.accommodation.domain.address.City;
import com.project.jagoga.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class AccommodationRequestDto {

    @NotBlank(message = "숙소 이름은 빈 칸일 수 없습니다.")
    @Length(max = 20, message = "이름은 20자 이내로 입력하세요.")
    private String accommodationName;

    private Long ownerId;

    @NotBlank(message = "형식이 맞지 않습니다")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
    private String phoneNumber;

    @NotNull(message = "주소가 빈 칸일 수 없습니다.")
    private City city;

    @NotNull(message = "숙소 타입을 선택해주세요.")
    private AccommodationType accommodationType;

    private String description;
    private String information;

    protected AccommodationRequestDto() {
    }

    public AccommodationRequestDto(String accommodationName, Long ownerId, String phoneNumber,
                                   City city, AccommodationType accommodationType, String description,
                                   String information) {
        this.accommodationName = accommodationName;
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.accommodationType = accommodationType;
        this.description = description;
        this.information = information;
    }

    public Accommodation toEntity(User owner) {
        return Accommodation.builder()
            .accommodationName(accommodationName)
            .ownerId(owner.getId())
            .phoneNumber(phoneNumber)
            .cityId(city.getId())
            .accommodationType(accommodationType)
            .description(description)
            .information(information)
            .build();
    }
}
