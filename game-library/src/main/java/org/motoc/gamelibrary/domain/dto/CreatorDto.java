package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.domain.enumeration.CreatorRole;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Dto for creator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorDto {


    private Long id;

    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Role cannot be null")
    private CreatorRole role;

    @Valid
    private ContactDto contact;

}