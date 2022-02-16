package com.dasd412.remake.api.controller.without_security.dto.writer;

import com.dasd412.remake.api.domain.diary.writer.Role;
import com.dasd412.remake.api.domain.diary.writer.Writer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WriterFindResponseDTO {

    private final Long writerId;

    private final String name;

    private final String email;

    private final Role role;

    public WriterFindResponseDTO(Writer writer) {
        this.writerId = writer.getId();
        this.name = writer.getName();
        this.email = writer.getEmail();
        this.role = writer.getRole();
    }

    public Long getWriterId() {
        return writerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", writerId)
                .append("name", name)
                .append("email", email)
                .append("role", role)
                .toString();
    }
}