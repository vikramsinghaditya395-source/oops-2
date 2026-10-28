package com.voltpay.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record BillRequest(
    @NotBlank String customerCode,
    @NotBlank @Pattern(regexp="\\d{4}-\\d{2}", message="Period must be YYYY-MM") String period,
    @Min(0) long previousReading,
    @Min(0) long currentReading,
    @NotBlank String category,
    @NotNull LocalDate dueDate
) {}
