package com.project.jagoga.roominventory.presentation.dto;

import com.project.jagoga.roominventory.domain.InventoryAddPeriod;
import com.project.jagoga.roominventory.domain.InventoryValidPeriod;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@InventoryAddPeriod(message = "기간은 90일 이내여야 합니다.")
public class RoomInventoryUpdateRequestDto implements InventoryValidPeriod {

    @NotNull(message = "기간 시작 일자가 명시되어야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "기간 종료 일자가 명시되어야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private int count;

    @Override
    public boolean isValidPeriod() {
        if (startDate == null || endDate == null) {
            return false;
        }

        return endDate.isBefore(startDate.plusMonths(3))
            && (endDate.isEqual(startDate) || endDate.isAfter(startDate));
    }
}
