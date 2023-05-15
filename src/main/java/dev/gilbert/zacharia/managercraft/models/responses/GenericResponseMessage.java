package dev.gilbert.zacharia.managercraft.models.responses;

import dev.gilbert.zacharia.managercraft.models.DataToObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GenericResponseMessage extends DataToObject {

    String command;
    boolean success;
    String message;
}
