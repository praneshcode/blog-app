package com.pranesh.blog.payloads;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class RoleDto {
	
	private int id;

	@NotNull
	private String name;
}
