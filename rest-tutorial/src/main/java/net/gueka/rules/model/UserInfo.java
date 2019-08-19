package net.gueka.rules.model;

import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @NotEmpty
    String userId;

    String name;

    String surname;

    @NotEmpty
    Date initDate;

    Calendar calendarDate;
}