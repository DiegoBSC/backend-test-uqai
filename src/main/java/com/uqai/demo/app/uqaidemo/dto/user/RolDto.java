package com.uqai.demo.app.uqaidemo.dto.user;

import com.uqai.demo.app.uqaidemo.enums.EnumRol;
import com.uqai.demo.app.uqaidemo.enums.EnumStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolDto {
    private Long id;
    private EnumRol name;
    private EnumStatus status = EnumStatus.ACT;
}
