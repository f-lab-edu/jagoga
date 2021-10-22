package com.project.jagoga.roominventory.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.jagoga.roominventory.domain.InventoryAddPeriod;
import com.project.jagoga.roominventory.domain.InventoryValidPeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@InventoryAddPeriod(message = "기간은 30일 이내여야 합니다.")
public class RoomInventoryAddRequestDto implements InventoryValidPeriod {

    @NotNull(message = "기간 시작 일자가 명시되어야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "기간 종료 일자가 명시되어야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Positive(message = "재고는 0 이상이어야 합니다.")
    private int availableCount;

    @Override
    public boolean isValidPeriod() {
        if (startDate == null || endDate == null) {
            return false;
        }

        return endDate.isBefore(startDate.plusMonths(3))
            && (endDate.isEqual(startDate) || endDate.isAfter(startDate));
    }
}
